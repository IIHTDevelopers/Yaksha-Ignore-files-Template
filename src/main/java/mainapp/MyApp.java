package mainapp;

import java.util.regex.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class MyApp {

    // Method to check if the directory is initialized with Git
    // public static String isGitInitialized() {
    //     String gitDirectoryExists;
    //     try {
    //         gitDirectoryExists = executeCommand("git rev-parse --is-inside-work-tree").trim();
    //         if (gitDirectoryExists.equals("true")) {
    //             return "true";
    //         } else {
    //             return "false";
    //         }
    //     } catch (Exception e) {
    //         return "";
    //     }
    // }

    // // Method to check if there is an amended commit in the reflog
    // public static String isAmendedCommitPresent() {
    //     try {
    //         // Get the reflog output
    //         String reflogOutput = executeCommand("git reflog").trim();

    //         // Print all lines of the reflog output for debugging
    //         System.out.println("Git Reflog Output: \n" + reflogOutput);

    //         // Check if the output contains a commit with "amend" and "adding file2.txt"
    //         String targetMessage = "(amend): adding file2.txt".toLowerCase(); // Target string in lowercase

    //         // Split the reflog output into lines and check each line for the amendment
    //         String[] reflogLines = reflogOutput.split("\n");

    //         for (String line : reflogLines) {
    //             System.out.println("line");
    //             System.out.println(line);
    //             // Convert each line to lowercase to make the matching case-insensitive
    //             if (line.toLowerCase().contains(targetMessage)) {
    //                 // Extract the commit hash (the first part of the line)
    //                 String commitHash = line.split(" ")[0];

    //                 if (commitHash != null && !commitHash.isEmpty()) {
    //                     // Show the full commit details using the commit hash
    //                     String commitDetails = executeCommand("git show " + commitHash);
    //                     System.out.println("Commit details for amended commit:\n" + commitDetails);

    //                     Pattern pattern1 = Pattern.compile("adding file2.txt");
    //                     Pattern pattern2 = Pattern.compile("asdf");

    //                     Matcher matcher1 = pattern1.matcher(commitDetails);
    //                     Matcher matcher2 = pattern2.matcher(commitDetails);

    //                     boolean foundPattern1 = matcher1.find();
    //                     boolean foundPattern2 = matcher2.find();

    //                     if (foundPattern1 && foundPattern2) {
    //                         System.out.println("Both 'adding file2.txt' and 'asdf' are present in the commit details.");
    //                     } else if (foundPattern1) {
    //                         System.out.println("'adding file2.txt' is present, but 'asdf' is not.");
    //                     } else if (foundPattern2) {
    //                         System.out.println("'asdf' is present, but 'adding file2.txt' is not.");
    //                     } else {
    //                         System.out.println("Neither 'adding file2.txt' nor 'asdf' are present in the commit details.");
    //                     }

    //                     return "true";
    //                 }
    //             }
    //         }

    //         return "false"; // If no matching commit or amend message is found
    //     } catch (Exception e) {
    //         System.out.println("Error in isAmendedCommitPresent method: " + e.getMessage());
    //         return "";
    //     }
    // }

    // // Helper method to extract the commit hash from the reflog output
    // private static String getCommitHashFromReflog(String reflogOutput, String targetMessage) {
    //     String[] lines = reflogOutput.split("\n");
    //     for (String line : lines) {
    //         if (line.toLowerCase().contains(targetMessage.toLowerCase())) {
    //             // Extract and return the commit hash (the first part of the line)
    //             return line.split(" ")[0];
    //         }
    //     }
    //     return null; // Return null if no matching line is found
    // }

    // // Method to check if there is a revert commit in the reflog and ensure 'file.txt' is not present
    // public static String isRevertCommitPresentAndFileNotPresent() {
    //     try {
    //         // Step 1: Check the reflog for a revert commit
    //         String reflogOutput = executeCommand("git reflog").trim();

    //         // Step 2: Search for the commit containing "adding file.txt" and get its commit hash
    //         Pattern pattern1 = Pattern.compile("adding file.txt");

    //         String[] reflogLines = reflogOutput.split("\n");
    //         String commitHashForAddingFile = null;

    //         for (String line : reflogLines) {
    //             Matcher matcher1 = pattern1.matcher(line);
    //             boolean foundPattern1 = matcher1.find();
    //             // if (line.toLowerCase().contains(targetMessage)) {
    //             if (foundPattern1) {
    //                 // Extract the commit hash (the first part of the line)
    //                 commitHashForAddingFile = line.split(" ")[0];
    //                 break;
    //             }
    //         }

    //         if (commitHashForAddingFile == null) {
    //             return "false"; // No commit with "adding file.txt" found
    //         }

    //         // Step 3: Check if a revert commit exists and verify the revert message contains the commit hash
    //         for (String line : reflogLines) {
    //             if (line.toLowerCase().contains("revert")) {
    //                 // Extract the commit hash for the revert commit (the first part of the line)
    //                 String revertCommitHash = line.split(" ")[0];

    //                 // Step 4: Get the commit details of the revert commit using the revert commit hash
    //                 String revertCommitDetails = executeCommand("git show " + revertCommitHash).trim();
    //                 System.out.println("revertCommitDetails");
    //                 System.out.println(revertCommitDetails);

    //                 Pattern pattern2 = Pattern.compile("This reverts commit " + commitHashForAddingFile);
    //                 Matcher matcher1 = pattern1.matcher(revertCommitDetails);
    //                 boolean foundPattern1 = matcher1.find();

    //                 // Step 5: Check if the revert commit message contains "This reverts commit <hash of adding file.txt>"
    //                 // if (revertCommitDetails.contains("This reverts commit " + commitHashForAddingFile)) {
    //                 if (foundPattern1) {
    //                     // Step 6: Check if the file "file.txt" is not present in the working directory
    //                     String fileStatus = executeCommand("git ls-files file.txt").trim();
    //                     if (fileStatus.isEmpty()) {
    //                         return "true"; // Revert commit exists, and file.txt is not present
    //                     } else {
    //                         return "false"; // file.txt is still present
    //                     }
    //                 }
    //             }
    //         }

    //         return "false"; // No matching revert commit found with correct message
    //     } catch (Exception e) {
    //         return "";
    //     }
    // }

    // Method to execute Git commands
    public static String executeCommand(String command) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(new File(".")); // Ensure this is the correct directory where Git repo is located
        processBuilder.command("bash", "-c", command);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitVal = process.waitFor();
        if (exitVal == 0) {
            return output.toString();
        } else {
            throw new RuntimeException("Failed to execute command: " + command);
        }
    }

    // Main method to manually test the methods
    public static void main(String[] args) {
        try {
            // String gitDirectoryExists = isGitInitialized();
            // if (gitDirectoryExists.equals("true")) {
            //     System.out.println("Git repository initialized successfully.");
            // } else {
            //     System.out.println("Git repository not initialized.");
            //     return;
            // }

            // String amendedCommitExists = isAmendedCommitPresent();
            // if (amendedCommitExists.equals("true")) {
            //     System.out.println("Amended commit found.");
            // } else {
            //     System.out.println("No amended commit found.");
            // }

            // String revertCommitAndFileStatus = isRevertCommitPresentAndFileNotPresent();
            // if (revertCommitAndFileStatus.equals("true")) {
            //     System.out.println("Revert commit found and file.txt is not present.");
            // } else {
            //     System.out.println("No revert commit found or file.txt is present.");
            // }

        } catch (Exception e) {
            System.out.println("Error in main method: " + e.getMessage());
        }
    }
}
