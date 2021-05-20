#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <string.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <sys/time.h>

#define PORT 8080

#define MAXSZ 1024

int main()
{
    int sockfd, newsockfd, num;
    struct sockaddr_in serverAddress, clientAddress;
    int opt = 1, i;
    int n, clientAddressLength, k;
    char msg[MAXSZ], msg1[MAXSZ];
    char *data;
    data = "Sample data$";

    sockfd = socket(AF_INET, SOCK_STREAM, 0);

    memset(&serverAddress, 0, sizeof(serverAddress));
    serverAddress.sin_family = AF_INET;
    serverAddress.sin_addr.s_addr = htonl(INADDR_ANY);
    serverAddress.sin_port = htons(PORT);

    bind(sockfd, (struct sockaddr *)&serverAddress, sizeof(serverAddress));
    listen(sockfd, 30);

    int count = 1;
    int cliL = sizeof(clientAddress);
    newsockfd = accept(sockfd, (struct sockaddr *)&clientAddress, &cliL);
    setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT, &opt, sizeof(opt));
    printf("How many frames to send? :");
    scanf("%d", &num);

    int vis[num + 1];
    for (i = 0; i <= num; i++)
        vis[i] = 0;

    int window_size = 4;
    while (count <= window_size)
    {
        sprintf(msg, "%d%s", count, data);

        k = send(newsockfd, msg, sizeof(msg), 0);

        printf("\nSent frame number %d \n", count);
        count++;
    }
    int ack = 1;
    int j;

    int lost_frame = 0;
    int lost_frame_index = 6;

    while (ack <= num)
    {
        int n = recv(newsockfd, msg1, MAXSZ, 0);
        if (n > 0)
        {
            int recv_ack = msg1[0] - '0';
            printf("Received acknowledgement with %d\n", recv_ack);
            if (recv_ack <= ack)
            {
                vis[ack] = 1;
                ack++;
                if (count <= num)
                {
                    sprintf(msg, "%d%s", count, data);

                    if (count == lost_frame_index && lost_frame)
                        lost_frame = 0;
                    else
                        k = send(newsockfd, msg, sizeof(msg), 0);

                    printf("\nSent frame number %d\n", count);
                    count++;
                }
            }
            else
            {
                vis[recv_ack] = 1;
                sprintf(msg, "%d%s", ack, data);
                k = send(newsockfd, msg, sizeof(msg), 0);
                printf("\nRetransmitted frame number %d\n", ack);
                ack++;
                while (vis[ack] == 1 && ack <= num)
                    ack++;
            }
        }
    }
    int p = recv(newsockfd, msg1, MAXSZ, 0);
    if (p > 0)
    {
        int recv_ack = msg1[0] - '0';
        printf("Received acknowledgement with %d\n", recv_ack);
    }
    printf("\n\nSuccessful");
    return 0;
}
