There are 4 files 
1. Percolation.java 
2. PercolationSlow.java
3. PercolationStats.java
4. PercolationVisualizer.java

PercolationSlow.java is used in the stats class so you will need to compile that also.

To compile each need to do
javac -classpath .:stdlib.jar:algs4.jar MyProgram.java

And to run
java -classpath .:stdlib.jar:algs4.jar MyProgram

To pass the text file
java -classpath .:stdlib.jar:algs4.jar MyProgram < test.txt

There are 3 arguments for the Stats class
First is grid size
Second is number of tests 
Third is type “fast” or “slow”
