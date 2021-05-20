#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#define PORT 8080
int main(int argc, char const *argv[])
{
    printf("----Client----\n");
    int sock = 0, valread;
    struct sockaddr_in serv_addr;
    sock = socket(AF_INET, SOCK_STREAM, 0);
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(PORT);
    inet_pton(AF_INET, "127.0.0.1", &serv_addr.sin_addr);
    connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr));
    FILE *fp;
    fp = fopen("client.txt", "r");
    fseek(fp, 0, SEEK_END);
    long fsize = ftell(fp);
    fseek(fp, 0, SEEK_SET); /* same as rewind(f); */
    char *string = malloc(fsize + 1);
    fread(string, 1, fsize, fp);
    send(sock, string, strlen(string), 0);
    printf("The file was sent successfully\n");
    return 0;
}
