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
    // May generate some compile errors. Just ignore for sake of proof.
    printf("main: ADDRESS PRINTOUTS\n");
    printf("main: \%p of s is %p\n", s);
    printf("main: & of s is %p\n", &s);
    printf("main: \%p of t is %p\n", t);
    printf("main: & of t is %p\n", &t);
    /*
    Note: Bawal. Generates the following error messages upon compile:

    array_sizes.c: In function ‘main’:
    array_sizes.c:31:5: warning: passing argument 1 of ‘array_len_printer’ from incompatible pointer type [enabled by default]
    array_sizes.c:7:6: note: expected ‘char *’ but argument is of type ‘char (*)[58]’
    array_sizes.c:32:5: warning: passing argument 1 of ‘array_len_printer’ from incompatible pointer type [enabled by default]
    array_sizes.c:7:6: note: expected ‘char *’ but argument is of type ‘char **’
    array_sizes.c:34:5: warning: passing argument 1 of ‘pointer_len_printer’ from incompatible pointer type [enabled by default]
    array_sizes.c:13:6: note: expected ‘char *’ but argument is of type ‘char (*)[58]’
    array_sizes.c:35:5: warning: passing argument 1 of ‘pointer_len_printer’ from incompatible pointer type [enabled by default]
    array_sizes.c:13:6: note: expected ‘char *’ but argument is of type ‘char **’

    Line numbers have been translated due to this comment. Oh meta.
    */
    /*
    printf("main: GOING TO array_len_printer (AGAIN), SAME ARG SEQUENCE BUT WITH &.\n");
    array_len_printer(&s);
    array_len_printer(&t);
    printf("main: GOING TO pointer_len_printer (AGAIN), SAME ARG SEQUENCE BUT WITH &.\n");
    pointer_len_printer(&s);
    pointer_len_printer(&t);
    */
    return 0;
}
