#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <string.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <time.h>

#define PORT 8080
#define SERVER_IP "127.0.0.1"

#define MAXSZ 1024

int main()
{
    int sockfd;
    struct sockaddr_in serverAddress;

    char msg1[MAXSZ], msg_recv[MAXSZ], ack_msg[MAXSZ], q, w;

    sockfd = socket(AF_INET, SOCK_STREAM, 0);

    memset(&serverAddress, 0, sizeof(serverAddress));
    serverAddress.sin_family = AF_INET;
    serverAddress.sin_addr.s_addr = inet_addr(SERVER_IP);
    serverAddress.sin_port = htons(PORT);

    connect(sockfd, (struct sockaddr *)&serverAddress, sizeof(serverAddress));

    int count = 1;
    int lost_ack = 0;
    int lost_ack_index = 8;

    int exp_Seq = 0;

    while (count <= 30)
    {

        int n = recv(sockfd, msg_recv, MAXSZ, 0);
        if (n == 0)
            break;

        int recv_Seq = msg_recv[0] - '0';

        int ack_to_send = recv_Seq;

        printf("\nReceived frame having sequence number: %d\n", recv_Seq);
        printf("Message: ");
        for (int j = 1; j < strlen(msg_recv); j++)
            if (msg_recv[j] != '$')
                printf("%c", msg_recv[j]);

        srand(time(NULL));
        int sl = rand() % 4;
        sleep(sl);
        sprintf(ack_msg, "%d", ack_to_send);

        printf("\nAcknowledgement sent:%d\n", ack_to_send);

        if (lost_ack && lost_ack_index == ack_to_send)
            lost_ack = 0;
        else
            send(sockfd, ack_msg, strlen(ack_msg) + 1, 0);
        count++;
    }

    close(sockfd);
    printf("Completed!!!");
    return 0;
}
