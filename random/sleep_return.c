#include <stdbool.h>
#include <stdio.h>
#include <unistd.h>

/*
Observe the return code on CTRL + C (or via kill).
*/

int main(){
    while(true){
        printf("Say, 'Oh all things are gonna happen naturally...'\n");
        sleep(1);
    }

    return 0;
}
