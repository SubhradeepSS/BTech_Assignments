#include <bits/stdc++.h>
#include <thread>
#define MAX 100

using namespace std;

int N, M, k;
int x[MAX], y[MAX], c[MAX];
bool z[MAX];

void initialize(int i)
{
    int n = N;
    int m = M;
    x[i - 1] = n - m + i;
    y[i - 1] = i;
    if (y[i - 1] == x[i - 1])
        z[i - 1] = 1;
    else
        z[i - 1] = 0;
    c[i - 1] = i;
}

void printArray(int arr[], int n)
{
    for (int i = 0; i < n; i++)
        cout << arr[i] << " ";
    cout << endl;
}

void printboolArray(bool arr[], int n)
{
    for (int i = 0; i < n; i++)
        cout << arr[i] << " ";
    cout << endl;
}

void task1(int i)
{
    if (z[i - 2] == 0 && z[i - 1] == 1)
    {
        y[i - 2] = y[i - 2] + 1;
        k = i;
    }
}

void task2(int i)
{
    y[i - 1] = y[k - 2] + (i - k + 1);
}

void task3(int i)
{
    c[i - 1] = y[i - 1];
    if (y[i - 1] == x[i - 1])
        z[i - 1] = 1;
    else
        z[i - 1] = 0;
}

int main()
{
    cout << "\nEnter the values of n and m (n>m) respectively: ";
    cin >> N >> M;

    cout << "\nFointowing are the combinations formed: \n";

    clock_t start, end;
    start = clock();

    vector<thread> threads;
    for (int i = 1; i <= M; i++)
    {
        threads.push_back(thread(initialize, i));
    }
    for (auto &th : threads)
    {
        th.join();
    }
    threads.clear();

    printArray(c, M);

    while (z[0] == 0)
    {
        threads.clear();
        k = 0;
        for (int i = 2; i <= M; i++)
        {
            threads.push_back(thread(task1, i));
            if (k == i)
                break;
        }
        for (auto &th1 : threads)
        {
            th1.join();
        }
        threads.clear();
        if (k == 0)
        {
            y[M - 1] = y[M - 1] + 1;
        }
        else
        {
            for (int i = k; i <= M; i++)
            {
                threads.push_back(thread(task2, i));
            }
        }
        for (auto &th2 : threads)
        {
            th2.join();
        }
        threads.clear();
        for (int i = 1; i <= M; i++)
        {
            threads.push_back(thread(task3, i));
        }
        for (auto &th3 : threads)
        {
            th3.join();
        }
        threads.clear();
        printArray(c, M);
    }

    end = clock();
    double time_taken = double(end - start) / double(CLOCKS_PER_SEC);
    cout << "\nTime taken for parallel algorithm: " << time_taken << "secs" << "\n\n";
}
