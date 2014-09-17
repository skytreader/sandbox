/*
cient.c -- a stream socket client demo
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>

#include <arpa/inet.h>

#define PORT "3868" // the port client will be connecting to 

#define MAXDATASIZE 100 // max number of bytes we can get at once 

// get sockaddr, IPv4 or IPv6:
void *get_in_addr(struct sockaddr *sa)
{
    if (sa->sa_family == AF_INET) {
        return &(((struct sockaddr_in*)sa)->sin_addr);
    }

    return &(((struct sockaddr_in6*)sa)->sin6_addr);
}

int main(int argc, char *argv[])
{
    int sockfd, numbytes;  
    char buf[MAXDATASIZE];
    struct addrinfo hints, *servinfo, *p;
    int rv;
    char s[INET6_ADDRSTRLEN];

    if (argc != 2) {
        fprintf(stderr,"usage: client hostname\n");
        exit(1);
    }

    memset(&hints, 0, sizeof hints);
    hints.ai_family = AF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;

    if ((rv = getaddrinfo(argv[1], PORT, &hints, &servinfo)) != 0) {
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
        return 1;
    }

    // loop through all the results and connect to the first we can
    for(p = servinfo; p != NULL; p = p->ai_next) {
        if ((sockfd = socket(p->ai_family, p->ai_socktype,
                p->ai_protocol)) == -1) {
            perror("client: socket");
            continue;
        }

        if (connect(sockfd, p->ai_addr, p->ai_addrlen) == -1) {
            close(sockfd);
            perror("client: connect");
            continue;
        }

        break;
    }

    if (p == NULL) {
        fprintf(stderr, "client: failed to connect\n");
        return 2;
    }

    inet_ntop(p->ai_family, get_in_addr((struct sockaddr *)p->ai_addr),
            s, sizeof s);
    printf("client: connecting to %s\n", s);

    freeaddrinfo(servinfo); // all done with this structure
    int packet[140];
    //version
    packet[0] = 1;
    //message length
    packet[1] = 0;
    packet[2] = 2;
    packet[3] = 0;
    // RPET field
    packet[4] = (int) 'R';
    // command code
    packet[5] = 2;
    packet[6] = 5;
    packet[7] = 7;
    // app id
    packet[8] = 0;
    packet[9] = 0;
    packet[10] = 0;
    packet[11] = 0;
    // hop id
    packet[12] = 1;
    packet[13] = 1;
    packet[14] = 1;
    packet[15] = 1;
    // end id
    packet[16] = 1;
    packet[17] = 1;
    packet[18] = 1;
    packet[19] = 1;

    packet[20] = 0x00;
    packet[21] = 0x00;
    packet[22] = 0x01;
    packet[23] = 0x08;
    packet[24] = 0x40;
    packet[25] = 0x00;
    packet[26] = 0x00;
    packet[27] = 0x16;
    packet[28] = 0x73;
    packet[29] = 0x74;
    packet[30] = 0x61;
    packet[31] = 0x2e;
    packet[32] = 0x73;
    packet[33] = 0x70;
    packet[34] = 0x72;
    packet[35] = 0x69;
    packet[36] = 0x6e;
    packet[37] = 0x74;
    packet[38] = 0x2e;
    packet[39] = 0x63;
    packet[40] = 0x6f;
    packet[41] = 0x6d;
    packet[42] = 0x00;
    packet[43] = 0x00;
    packet[44] = 0x01;
    packet[45] = 0x28;
    packet[46] = 0x40;
    packet[47] = 0x00;
    packet[48] = 0x00;
    packet[49] = 0x12;
    packet[50] = 0x73;
    packet[51] = 0x70;
    packet[52] = 0x72;
    packet[53] = 0x69;
    packet[54] = 0x6e;
    packet[55] = 0x74;
    packet[56] = 0x2e;
    packet[57] = 0x63;
    packet[58] = 0x6f;
    packet[59] = 0x6d;
    packet[60] = 0x00;
    packet[61] = 0x00;
    packet[62] = 0x01;
    packet[63] = 0x01;
    packet[64] = 0x40;
    packet[65] = 0x00;
    packet[66] = 0x00;
    packet[67] = 0x0e;
    packet[68] = 0x00;
    packet[69] = 0x01;
    packet[70] = 0x0a;
    packet[71] = 0x1e;
    packet[72] = 0x60;
    packet[73] = 0xc8;
    packet[74] = 0x00;
    packet[75] = 0x00;
    packet[76] = 0x01;
    packet[77] = 0x0a;
    packet[78] = 0x40;
    packet[79] = 0x00;
    packet[80] = 0x00;
    packet[81] = 0x0c;
    packet[82] = 0x00;
    packet[83] = 0x00;
    packet[84] = 0x28;
    packet[85] = 0xaf;
    packet[86] = 0x00;
    packet[87] = 0x00;
    packet[88] = 0x01;
    packet[89] = 0x0d;
    packet[90] = 0x00;
    packet[91] = 0x00;
    packet[92] = 0x00;
    packet[93] = 0x15;
    packet[94] = 0x4c;
    packet[95] = 0x61;
    packet[96] = 0x6e;
    packet[97] = 0x64;
    packet[98] = 0x73;
    packet[99] = 0x6c;
    packet[100] = 0x69;
    packet[101] = 0x64;
    packet[102] = 0x65;
    packet[103] = 0x48;
    packet[104] = 0x53;
    packet[105] = 0x47;
    packet[106] = 0x57;
    packet[107] = 0x00;
    packet[108] = 0x00;
    packet[109] = 0x01;
    packet[110] = 0x02;
    packet[111] = 0x40;
    packet[112] = 0x00;
    packet[113] = 0x00;
    packet[114] = 0x0c;
    packet[115] = 0x01;
    packet[116] = 0x00;
    packet[117] = 0x00;
    packet[118] = 0x00;
    packet[119] = 0x01;
    packet[120] = 0x16;
    packet[121] = 0x40;
    packet[122] = 0x00;
    packet[123] = 0x00;
    packet[124] = 0x0c;
    packet[125] = 0x4f;
    packet[126] = 0x32;
    packet[127] = 0xa0;
    packet[128] = 0x86;
    //
    packet[129] = 0x00;
    packet[130] = 0x00;
    packet[131] = 0x00;
    packet[132] = 0x00;
    packet[133] = 0x00;
    packet[134] = 0x00;
    packet[135] = 0x00;
    packet[136] = 0x00;
    packet[137] = 0x00;
    packet[138] = 0x00;
    packet[139] = 0x00;


    printf("%d\n", (int)sizeof(packet[0]));
    send(sockfd, packet, 20, 1);

    if ((numbytes = recv(sockfd, buf, MAXDATASIZE-1, 0)) == -1) {
        perror("recv");
        exit(1);
    }

    buf[numbytes] = '\0';

    printf("client: received '%s'\n",buf);

    close(sockfd);

    return 0;
}
