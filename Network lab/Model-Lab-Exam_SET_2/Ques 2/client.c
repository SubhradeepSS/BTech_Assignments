#include <stdio.h>
#include <netdb.h>
#include <unistd.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#define MAX 80
#define PORT 8081
#define SA struct sockaddr
void func(int socketFD)
{
	char buff[MAX];
	int n;
	for (;;) {
        bzero(buff, sizeof(buff));
        n = 0;
        read(socketFD, buff, sizeof(buff));
        printf("News received from Server : %s", buff);
        if ((strncmp(buff, "exit", 4)) == 0) {
            printf("Client Exit...\n");
            break;
        }
	}
}
int main()
{
	int socketFD, connfd;
	struct sockaddr_in servaddr, cli;
	socketFD = socket(AF_INET, SOCK_STREAM, 0);
	if (socketFD == -1) {
		printf("socket creation FAILED\n");
		exit(0);
	}
	else
		printf("Socket successfully created\n");
	bzero(&servaddr, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	inet_pton(AF_INET, "127.0.0.1", &servaddr.sin_addr);
	servaddr.sin_port = htons(PORT);
	if (connect(socketFD, (SA*)&servaddr, sizeof(servaddr)) != 0) {
		printf("Failed to connect with the SERVER\n");
		exit(0);
	}
	else
		printf("Connected successfully to the SERVER\n");
	func(socketFD);
	close(socketFD);
}