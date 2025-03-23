* to execute and run test cases

  mvn clean install exec:java -Dexec.mainClass="mainapp.MyApp" -DskipTests=true

git config --global user.email ""
git config --global user.name ""
---------------------------------------------------------------------------------
git init
echo "*.log" >> .gitignore
touch example.log
git add .gitignore
git commit -m "Add .gitignore to ignore log files"

mkdir empty_directory
touch empty_directory/.gitkeep
git add empty_directory/.gitkeep
git commit -m "Track empty directory using .gitkeep"

touch tracked_file.txt
git add tracked_file.txt
git commit -m "Add tracked file"
git rm --cached tracked_file.txt
echo "tracked_file.txt" >> .gitignore
git commit -m "Stop tracking tracked_file.txt"
