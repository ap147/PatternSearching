# COMP317-17A_AssignmentTwo_PatternSearching

COMP317-17A   Assignment 2:  Pattern Searching

This assignment is intended to give you experience building a regular expression (regexp) FSM compiler and corresponding pattern matcher. Students are to work in pairs to complete this assignment. Your implementation is expected to be written in Java and run on a Linux machine such as those in Lab 6 (which are the machines upon which your solution will be tested for marking purposes). Deviation from this specification requires prior approval from the lecturer or tutor.

Due:  Tuesday, 25th April 2017, 11:30pm

Overview:     Implement a regexp pattern searcher using the FSM, deque and compiler techniques outlined in class. Your solution must consist of two programs: one called REcompile.java and the other called REsearch.java. The first of these must accept a regexp pattern as a command-line argument (enclosed within double-quotes—see "Note" below), and produce as standard output a description of the corresponding FSM, such that each line of output includes four things: the state-number, the input-symbol this state must match (or indication it is a branching state, or similar), and two numbers indicating the two possible next states if a match is made. The second program must accept, as standard input, the output of the first program, then it must execute a search for matching patterns within the text of a file whose name is given as a command-line argument. Each line of the text file that contains a match is output to standard out just once, regardless of the number of times the pattern might be satisfied in that line.

Regexp specification:     For this assignment, a wellformed regexp is specified as follows:

any symbol that does not have a special meaning (as given below) is a literal that matches itself
. is a wildcard symbol that matches any literal
adjacent regexps are concatenated to form a single regexp
* indicates closure (zero or more occurrences) on the preceding regexp
? indicates that the preceding regexp can occur zero or one time
| is an infix alternation operator such that if r and e are regexps, then r|e is a regexp that matches one of either r or e
( and ) may enclose a regexp to raise its precedence in the usual manner; such that if e is a regexp, then (e) is a regexp and is equivalent to e. e cannot be empty.
[ and ] may enclose a list of literals and matches one and only one of the enclosed literals. Any special symbols in the list lose their special meaning, except ] which must appear first in the list if it is a literal. The enclosed list cannot be empty.
\ is an escape character that matches nothing but indicates the symbol immediately following the backslash loses any special meaning and is to be interpretted as a literal symbol
operator precedence is as follows (from high to low):
escaped characters (i.e. symbols preceded by \)
parentheses (i.e. the most deeply nested regexps have the highest precedence)
list of alternative literals (i.e. [ and ])
repetition operators (i.e. * + and ?)
concatenation
alternation (i.e. |)
You must implement your own parser/compiler, and your own FSM (simulating two-state and branching machines) similar to how it was shown to you in class, and you must implement your own dequeue to support the search (i.e. no Java libraries or other tools for these).

Note:     Operating system shells typically parse command-line arguments as regular expressions, and some of the special characters defined for this assignment are also special characters for the command-line interpreter of various operating systems. This can make it hard to pass your regexp into the argument vector of your program. You can get around most problems by simply enclosing your regexp command-line argument within double-quote characters, which is what you should do for this assignment. To get a double-quote character into your regexp, you have to escape it by putting a backslash in front of it, and then the backslash is removed by the time the string gets into your program's command-line argument vector. There is only one other situation where Linux shells remove a backslash character from a quoted string, and that is when it precedes another backslash. For this assignment, it is the string that gets into your program that is the regexp—which may entail some extra backslashes in the argument. (N.b. Windows command prompt shell has a different syntax for parsing regexps than does Linux, so if you develop on a windows box, make sure you make the necessary adjustments for it to run under linux.)
Submission:       To simplify marking, please place only your source code and optional README in an otherwise empty directory/folder whose name is your student ID number and the student ID number of your partner separated with an underscore such that the first student ID is smaller than the second, then compress it and submit through moodle. See your tutor for details. 
  
  Tony C Smith, Mar 2017
  
  http://www.cs.waikato.ac.nz/~tcs/COMP317/assign2-2017.html
