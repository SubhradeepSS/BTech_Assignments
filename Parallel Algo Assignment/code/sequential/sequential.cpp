#include <bits/stdc++.h>
using namespace std;

long long nCr(long long n, long long r)
{
    long long p = 1, k = 1;
 
    if (n - r < r)
        r = n - r;
 
    if (r != 0) {
        while (r) {
            p *= n;
            k *= r;
 
            long long m = __gcd(p, k);

            p /= m;
            k /= m;
 
            n--;
            r--;
        }
    }
 
    else
        p = 1;
 
    return p;
}

void printArray(long long arr[], long long n)
{
    for (long long i = 0; i < n; i++)
        cout << arr[i] << " ";
    cout << endl;
}


void nextCombination(long long combinationArr[], long long n, long long m)
{
    long long j = m;
    while (j > 0)
    {
        if (combinationArr[j - 1] < n - m + j)
        {
            combinationArr[j - 1]++;

            for (int i = j; i < m; i++)
                combinationArr[i] = combinationArr[i - 1] + 1;

            printArray(combinationArr, m);
            break;
        }
        else
            j--;
    }
}


void sequentialCombination(long long n, long long m)
{
    long long combinationArr[m];

    //initalize the array
    for (long long i = 0; i < m; i++)
        combinationArr[i] = i + 1;

    printArray(combinationArr, m);

    //number of iteration for the loop to run
    long long totalCombination = nCr(n, m) - 1;

    for (long long i = 0; i < totalCombination; i++)
        nextCombination(combinationArr, n, m);
}


int main()
{
	long long n, m;
    cout << "\nEnter the values of n and m (n>m) respectively: ";
    cin >> n >> m;

    clock_t start, end;
    start = clock();

    cout << "\nFollowing are the different combinations formed:\n";
    sequentialCombination(n, m);

    end = clock();
    double time_taken = double(end - start) / double(CLOCKS_PER_SEC);
    cout << "\nTime taken for sequential algorithm: " << time_taken << "secs" << "\n\n";
}