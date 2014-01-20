#include <stdio.h>

/**
Experiments for Head First C.
*/

void array_len_printer(char s[]){
    // Expected to return actual size of array/string.
    // Wrong. C Went behind your back and passed a pointer here.
    printf("array_len_printer: sizeof s? %d\n", (int) sizeof(s));
}

void pointer_len_printer(char *s){
    // Expected to return 4 or 8, depending on architecture.
    // Yep!
    printf("pointer_len_printer: sizeof s? %d\n", (int) sizeof(s));
}

int main(){
    char s[] = "Hello World is a stupid example and should never be used.";
    printf("main: sizeof char aray s: %i\n", (int) sizeof(s));
    char *t = s;
    printf("main: sizeof t where char *t = s == %i\n", (int) sizeof(t));
    printf("main: GOING TO array_len_printer WITH s AS ARGUMENT THEN t.\n");
    array_len_printer(s);
    array_len_printer(t);
    printf("main: GOING TO pointer_len_printer WITH s AS ARGUMENT THEN t.\n");
    pointer_len_printer(s);
    pointer_len_printer(t);
    return 0;
}
