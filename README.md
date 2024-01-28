# JDU  

Write a utility program that shows how much space files occupy in a given directory

***Launching the program:***

jdu [options] directories

dir - root scan directory (current default directory)

***Options:***  

--depth n - recursion depth (set some default limit)
-L - navigate through symlinks
--limit n - show the n heaviest files and/or directories (set some default limit)

***The result of the work:***  

Tree-like ordered display of files and directories in a given directory

**Program requirements:**

*  the program must contain unit tests
*  the program must be written in the OOP style
*  the program must use the build system
  
**_______________________________**  

**To run:**  
mvn compile  
mvn exec:java -Dexec.mainClass="oop.diskUsage.Jdu"  
