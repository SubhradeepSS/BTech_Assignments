#include<bits/stdc++.h>
using namespace std;

void power(int n,int k){
    int r=1;
    while(k>0){
        if(k%2)
            r*=n;
        k>>=1;
        n=n*n;
    }
    cout<<r;
}
int main() 
{ 
    power(2,3); 
    return 0; 
} 
