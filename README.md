# Frequent itemsets

How to run the project

1) The folder cosists of Apriori.txt just convert the extension to jar executable file(.jar), src folder consists of source code. And 
my generated outputs.

2) Open the command prompt and set the working directory to the folder that has the jar file

3) Using the jar file you can enter the below command for running my program.

java -jar Apriori.jar 75 1 inputfile\path\filename outputfile\path\filename 

3) For different test cases, just change the min_support value 75 to something different and frequent itemset k from 1 to something different. example :

 java -jar Apriori.jar 10 2 inputfile\path\filename outputfile\path\filename

Change the input path and give appropriate output path.

Running the Program in Eclipse:

1) Create a project

2) copy the src folders notepad in the project.

3) Run the project by right clicking on the project name.

4) Select "Run as" then select "Run Configurations.."

5) Go to Arguments tab and give the arguments "min_sup value" "k" "input path with file.txt" "output path with file.txt".
Similar to above path conventions for jar files.

6) Apply changes and click on Run.

IMP NOTE: MY PROGRAM AFTER RUNNING CREATES TWO ADDITIONAL TXT FILES. THEY ARE STRING CONVERSION TO INTEGERS OF INPUT FILE AND FINAL OUTPUT IN INTEGERS WHICH GETS CONVERTED TO STRING FORMAT.