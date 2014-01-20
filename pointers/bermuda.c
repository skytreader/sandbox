#include <stdio.h>

/**
From Head First C.
*/

void go_south_east(int *lat, int *lon){
    /*
    go_south_east is passed the addresses of the latitude and longitude. Due to
    the method declaration, the addresses are dereferenced so that we can get the
    actual value of latitude and longitude.

    (1) printing the "address of an address"? It is noticeably different from the
    addresses printed in main.

    (2) prints the actual value of latitude and longitude. (What can be gathered
    from this?)

    (3) prints addresses similar to the one in main.

    Conclusion:
        The dereferenced declaration at the start of the function only serves to
        note that we are expecting pointer values, not just any other integer;
        we still need to dereference them when we use it in the function.
        Unstarred, they hold memory addresses.
    */
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

