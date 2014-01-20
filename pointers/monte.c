#include <stdio.h>

/**
Initial guess: strings are immutable even in C?

Correct! However, if we declared an array instead of a memory pointer, we
could modify the string.

 - The string "JQK" is written to the constants section of memory. This section
   of memory is _read only_.
 - The cards variable is created in the stack and is given the address of "JQK".
 - What changes then, when you declare it as an array? An array is allocated
   (with its own copy of data) in the stack. The "JQK" in the constants section
   of the memory is still there but we have a copy outside of read-only memory
   thanks to the array declaration.
*/

int main(){
    //char *cards = "JQK"; // DECLARE ARRAY HERE
    char cards[] = "JQK";
    char a_card = cards[2];
    cards[2] = cards[1];
    cards[1] = cards[0];
    cards[0] = cards[2];
    cards[2] = cards[1];
    cards[1] = a_card;
    puts(cards);
    return 0;
}
