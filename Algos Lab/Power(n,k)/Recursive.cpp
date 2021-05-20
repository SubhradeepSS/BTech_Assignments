#include<bits/stdc++.h>
using namespace std;

int power(int n,int k){
    if(n==0)
        return 0;
    if(k==0)
        return 1;
    int t=power(n,k/2);
    if(k%2)
        return n*t*t;
    return t*t;
}
int main() 
{ 
    cout<<power(2,3); 
    return 0; 
} 
