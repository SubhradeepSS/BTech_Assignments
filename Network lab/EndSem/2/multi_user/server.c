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
void chatFunc(int sockfd1, int sockfd2, int sockfd3)
{
  char buff[MAX];
  int n, count = 0;
  // bzero(buff, MAX);
  // read(sockfd1, buff, sizeof(buff));
  // bzero(buff, MAX);
  // read(sockfd2, buff, sizeof(buff));
  // write(sockfd1, "start\n", 6);
  for (;; count++)
  {
    bzero(buff, MAX);
    read(sockfd1, buff, sizeof(buff));
    printf("From client1: %s\n", buff);
    write(sockfd2, buff, sizeof(buff));
    write(sockfd3, buff, sizeof(buff));
    if (strncmp("exit", buff, 4) == 0)
    {
      printf("Server Exit...\n");
      break;
    }

    //    From client 2
    bzero(buff, MAX);
    read(sockfd2, buff, sizeof(buff));
    printf("From client2: %s\n", buff);
    write(sockfd1, buff, sizeof(buff));
    write(sockfd3, buff, sizeof(buff));
    if (strncmp("exit", buff, 4) == 0)
    {
      printf("Server Exit...\n");
      break;
    }
    //  From client 3
    bzero(buff, MAX);
    read(sockfd3, buff, sizeof(buff));
    printf("From client3: %s\n", buff);
    write(sockfd1, buff, sizeof(buff));
    write(sockfd2, buff, sizeof(buff));
    if (strncmp("exit", buff, 4) == 0)
    {
      printf("Server Exit...\n");
      break;
    }
  }
}

int main()
{
  int sockfd, connectionFD1, connectionFD2, connectionFD3, len;
  struct sockaddr_in servaddr, cli;
  sockfd = socket(AF_INET, SOCK_STREAM, 0);
  if (sockfd == -1)
  {
    printf("socket creation failed...\n");
    exit(0);
  }
  else
    printf("Socket successfully created..\n");
  bzero(&servaddr, sizeof(servaddr));
  servaddr.sin_family = AF_INET;
  servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
  servaddr.sin_port = htons(PORT);

  // CHat 1
  if ((bind(sockfd, (SA *)&servaddr, sizeof(servaddr))) != 0)
  {
    printf("socket bind failed...\n");
    exit(0);
  }
  else
    printf("Socket successfully binded..\n");
  if ((listen(sockfd, 5)) != 0)
  {
    printf("Listen failed...\n");
    exit(0);
  }
  else
    printf("Server listening..\n");
  len = sizeof(cli);
  connectionFD1 = accept(sockfd, (SA *)&cli, &len);
  if (connectionFD1 < 0)
  {
    printf("server acccept failed...\n");
    exit(0);
  }
  else
    printf("server acccept the client...\n");

  //CHat 2

  connectionFD2 = accept(sockfd, (SA *)&cli, &len);
  if (connectionFD2 < 0)
  {
    printf("server acccept failed...\n");
    exit(0);
  }
  else
    printf("server acccept the client...\n");

  // chat 3
  connectionFD3 = accept(sockfd, (SA *)&cli, &len);
  if (connectionFD2 < 0)
  {
    printf("server acccept failed...\n");
    exit(0);
  }
  else
    printf("server acccept the client...\n");
  chatFunc(connectionFD1, connectionFD2, connectionFD3);
  close(sockfd);
  return 0;
}