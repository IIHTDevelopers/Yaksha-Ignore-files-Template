package com.libraryapp.test.functional;

import static com.libraryapp.test.utils.TestUtils.businessTestFile;
import static com.libraryapp.test.utils.TestUtils.currentTest;
import static com.libraryapp.test.utils.TestUtils.testReport;
import static com.libraryapp.test.utils.TestUtils.yakshaAssert;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.io.IOException;

import mainapp.MyApp;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class MainFunctionalTest {

    @AfterAll
    public static void afterAll() {
        testReport();
    }

    @Test
    @Order(1)
    public void testGitIgnoreFile() throws IOException {
        try {
            // Check if .gitignore file is present
            File gitIgnoreFile = new File(".gitignore");
            boolean isGitIgnorePresent = gitIgnoreFile.exists();

            // Check if .gitignore contains "*.log"
            boolean containsLogPattern = false;
            if (isGitIgnorePresent) {
                String content = new String(java.nio.file.Files.readAllBytes(gitIgnoreFile.toPath()));
                containsLogPattern = content.contains("*.log");
            } else {
                // System.out.println(".gitignore doesn't contain line to ignore all .log files");
                System.out.println(".gitignore file not present");
            }

            File exampleLogFile = new File("example.log");
            boolean isExampleLogPresent = exampleLogFile.exists();

            if (!isExampleLogPresent) {
                System.out.println("example.log file not present");
            }

            if (!containsLogPattern) {
                System.out.println(".gitignore doesn't contain line to ignore all .log files");
            }

            // Check if "example.log" file is ignored
            String checkIgnoreOutput = MyApp.executeCommand("git check-ignore -v example.log").trim();
            boolean isExampleLogIgnored = checkIgnoreOutput.contains(".gitignore");
            if (!isExampleLogIgnored) {
                System.out.println("example.log file not getting ignored");
            }

            // Assert the conditions
            yakshaAssert(currentTest(), isGitIgnorePresent && isExampleLogPresent && containsLogPattern && isExampleLogIgnored, businessTestFile);
        } catch (Exception ex) {
            yakshaAssert(currentTest(), false, businessTestFile);
        }
    }

    @Test
    @Order(2)
    public void testEmptyDirectoryAndCommit() throws IOException {
        try {
            // 1. Check if there is a directory named "empty_directory" and it contains only ".gitkeep"
            File emptyDirectory = new File("empty_directory");
            boolean isEmptyDirectoryPresent = emptyDirectory.exists() && emptyDirectory.isDirectory();
            boolean containsOnlyGitkeep = false;

            if (isEmptyDirectoryPresent) {
                // List files inside the directory to check if only ".gitkeep" is present
                String[] filesInDirectory = emptyDirectory.list();
                if (filesInDirectory != null && filesInDirectory.length == 1 && filesInDirectory[0].equals(".gitkeep")) {
                    containsOnlyGitkeep = true;
                }
            } else {
                System.out.println("empty_directory does not exist.");
            }

            if (isEmptyDirectoryPresent && !containsOnlyGitkeep) {
                System.out.println("empty_directory is either missing or does not contain only .gitkeep file.");
            }

            // 2. Check whether a commit with message "Track empty directory using .gitkeep" is present using "git log --oneline"
            String gitLogOutput = MyApp.executeCommand("git log --oneline").trim();
            boolean isCommitPresent = gitLogOutput.contains("Track empty directory using .gitkeep");

            if (isEmptyDirectoryPresent && containsOnlyGitkeep && !isCommitPresent) {
                System.out.println("Commit 'Track empty directory using .gitkeep' not found.");
            }

            // Assert the conditions: 
            yakshaAssert(currentTest(), isEmptyDirectoryPresent && containsOnlyGitkeep && isCommitPresent, businessTestFile);
        } catch (Exception ex) {
            yakshaAssert(currentTest(), false, businessTestFile);
        }
    }

    @Test
    @Order(3)
    public void testTrackedFileAndGitIgnore() throws IOException {
        try {
            // 1. Check if the tracked_file.txt file is present
            File trackedFile = new File("tracked_file.txt");
            boolean isTrackedFilePresent = trackedFile.exists();

            if (!isTrackedFilePresent) {
                System.out.println("tracked_file.txt file not present.");
            }

            // 2. Check if "cat .gitignore" contains "tracked_file.txt"
            boolean isTrackedFileInGitIgnore = false;
            File gitIgnoreFile = new File(".gitignore");
            if (gitIgnoreFile.exists()) {
                String content = new String(java.nio.file.Files.readAllBytes(gitIgnoreFile.toPath()));
                isTrackedFileInGitIgnore = content.contains("tracked_file.txt");

                if (!isTrackedFileInGitIgnore) {
                    System.out.println("tracked_file.txt is not ignored");
                }
            } else {
                System.out.println(".gitignore file is not present.");
            }

            // Assert the conditions: 
            yakshaAssert(currentTest(), isTrackedFilePresent && isTrackedFileInGitIgnore, businessTestFile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            yakshaAssert(currentTest(), false, businessTestFile);
        }
    }

}
