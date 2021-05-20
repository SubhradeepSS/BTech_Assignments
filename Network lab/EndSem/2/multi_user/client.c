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
  int n, clientID;
  printf("Enter client no:");
  scanf("%d", &clientID);
  // bzero(buff, sizeof(buff));
  // write(socketFD, "hello\n", 6);
  // read(socketFD, buff, sizeof(buff));
  // printf("From peer : %s\n", buff);
  for (;;)
  {
    if (clientID == 1)
    {

      bzero(buff, sizeof(buff));
      printf("Enter the string : ");
      n = 0;
      while ((buff[n++] = getchar()) != '\n')
        ;
      write(socketFD, buff, sizeof(buff));
      bzero(buff, sizeof(buff));
      read(socketFD, buff, sizeof(buff));
      printf("From peer : %s\n", buff);
      bzero(buff, sizeof(buff));
      read(socketFD, buff, sizeof(buff));
      printf("From peer : %s\n", buff);
      if ((strncmp(buff, "exit", 4)) == 0)
      {
        printf("Client Exit...\n");
        break;
      }
    }
    else if (clientID == 2)
    {

      bzero(buff, sizeof(buff));
      read(socketFD, buff, sizeof(buff));
      printf("From peer : %s\n", buff);
      bzero(buff, sizeof(buff));
      printf("Enter the string : ");
      n = 0;
      while ((buff[n++] = getchar()) != '\n')
        ;
      write(socketFD, buff, sizeof(buff));
      bzero(buff, sizeof(buff));
      read(socketFD, buff, sizeof(buff));
      printf("From peer : %s\n", buff);
      if ((strncmp(buff, "exit", 4)) == 0)
      {
        printf("Client Exit...\n");
        break;
      }
    }
    else
    {

      bzero(buff, sizeof(buff));
      read(socketFD, buff, sizeof(buff));
      printf("From peer : %s\n", buff);
      bzero(buff, sizeof(buff));
      read(socketFD, buff, sizeof(buff));
      printf("From peer : %s\n", buff);
      bzero(buff, sizeof(buff));
      printf("Enter the string : ");
      n = 0;
      while ((buff[n++] = getchar()) != '\n')
        ;
      write(socketFD, buff, sizeof(buff));
      if ((strncmp(buff, "exit", 4)) == 0)
      {
        printf("Client Exit...\n");
        break;
      }
    }
  }
}
int main()
{
  int socketFD, connfd;
  struct sockaddr_in servaddr, cli;
  socketFD = socket(AF_INET, SOCK_STREAM, 0);
  if (socketFD == -1)
  {
    printf("socket creation failed...\n");
    exit(0);
  }
  else
    printf("Socket successfully created..\n");
  bzero(&servaddr, sizeof(servaddr));
  servaddr.sin_family = AF_INET;
  inet_pton(AF_INET, "127.0.0.1", &servaddr.sin_addr);
  servaddr.sin_port = htons(PORT);
  if (connect(socketFD, (SA *)&servaddr, sizeof(servaddr)) != 0)
  {
    printf("connection with the server failed...\n");
    exit(0);
  }
  else
    printf("connected to the server..\n");
  func(socketFD);
  close(socketFD);
  return 0;
}