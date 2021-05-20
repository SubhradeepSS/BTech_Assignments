#include <stdio.h>
#include <netdb.h>
#include <unistd.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#define MAX 80
#define PORT 8081
#define SA struct sockaddr

int main()
{
    int noClients;
    printf("Enter the no.of clients:");
    scanf("%d", &noClients);
    int sockfd, connectionFD[noClients], len;
    struct sockaddr_in servaddr, cli;
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd == -1) {
        printf("socket creation failed...\n");
        exit(0);
    }
    else
    printf("Socket successfully created..\n");
    bzero(&servaddr, sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
    servaddr.sin_port = htons(PORT);
    if ((bind(sockfd, (SA*)&servaddr, sizeof(servaddr))) != 0) {
        printf("socket bind failed...\n");
        exit(0);
    }
    else
        printf("Socket successfully binded..\n");
    if ((listen(sockfd, 5)) != 0) {
        printf("Listen failed...\n");
        exit(0);
    }
    else
        printf("Server listening..\n");
    len = sizeof(cli);
    for(int i =0; i < noClients; i++){
        connectionFD[i] = accept(sockfd, (SA*)&cli, &len);
        if (connectionFD[i] < 0) {
            printf("server acccept failed...\n");
            exit(0);
        }
        else
            printf("server accepted the client no: %d\n", i);
    }
    int n = 0, option, noMulticast, unicast;
    char buff[MAX];
    while(1){
        bzero(buff, MAX);
        n = 0;
        printf("\nWould you like to Broadcast(Enter 0) or Multicast(Enter 1): ");
        scanf("%d", &option);
        printf("Enter the news:");
        while ((buff[n++] = getchar()) != '$');
        buff[--n] = '\n';
        if(option == 0){
            printf("it's here");
            for(int i = 0; i < noClients; i++){
                write(connectionFD[i], buff, sizeof(buff));
                if (strncmp("exit", buff, 4) == 0) {
                printf("Server Exit...\n");
                }
            }
        }
        else{
            printf("Enter the no.of multicast Clients:");
            scanf("%d", &noMulticast);
            for (int i = 0; i < noMulticast; i++)
            {
            printf("\nEnter the clientId: ");
            scanf("%d", &unicast);
            if(unicast < noClients){
                write(connectionFD[unicast], buff, sizeof(buff));
                if (strncmp("exit", buff, 4) == 0) {
                printf("Server Exit...\n");
                }
            }
            else{
                printf("Invalid ClientId\n");
            }
            }
            
        }
    }
    close(sockfd);
}