#include <stdio.h>

/**
From Head First C.
*/

void go_south_east(int *lat, int *lon){
    printf("go_south_east: Ampersands: %p, %p\n", &lat, &lon);
    printf("go_south_east: Stars: %d, %d\n", *lat, *lon);
    printf("go_south_east: Plain: %p, %p\n", lat, lon);
    // Try *lat--; *lon--;
    // Will that work?
    *lat = *lat - 1;
    *lon = *lon - 1;
}

int main(){
    int latitude = 32;
    int longitude = -64;
    printf("main: Addresses: %p, %p\n", &latitude, &longitude);
    go_south_east(&latitude, &longitude);
    printf("Avast! Now at: [%i, %i]\n", latitude, longitude);
    return 0;
}

