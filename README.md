# sudoku5x5

Simple Java program to find a solution for the sudoku.
There was 5 x 5 field split into 5 colored zones:
```
+-+-+-+-+-+
|Y Y Y|P P|
+-+-+-+-+-+
|Y Y|B|P P|
+-+-+-+-+-+
|O|B B B|P|
+-+-+-+-+-+
|O O|B|G G|
+-+-+-+-+-+
|O O|G G G|
+-+-+-+-+-+
```
Orange, Yellow, Pink, Green, Blue zones must contain unique numbers from 1..5
and the same uniqueness must hold for rows and columns.

Starting condition:
```
+-+-+-+-+-+
|Y Y Y|P P|
+-+-+-+-+-+
|Y Y|B|3 P|
+-+-+-+-+-+
|O|4 B 1|P|
+-+-+-+-+-+
|O 5|B|G G|
+-+-+-+-+-+
|O O|G G G|
+-+-+-+-+-+
```
A solution exists.

It's trivial to modify code to print all solutions.

Interesting questions:
 * How many variants total were evaluated for feasibility?
 * How many checks were made in average for 1 variant?
 * For alternative solutions, how many backtracks to do?
