/*
* StamonVM SFN LIbrary
* @filename stdio.st
* @author CLimber-Rong
*/

import stddef;
import math;

func puts(s) {
    def port = 0;
    sfn port, s;
}

func print(n) {
    puts(str(n));
}

func println(n) {
    puts(str(n));
    puts("\n");
}

func input(s) {
    puts(str(s));

    def port = 5;
    def arg;

    sfn port, arg;

    return arg;
}