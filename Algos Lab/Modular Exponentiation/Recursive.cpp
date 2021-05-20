#include<bits/stdc++.h>
using namespace std;

//(x^y)%k

int mod_exp(int x,int y,int k){
    if(y==0)
        return 1;
    x=x%k;
    int t=mod_exp(x,y/2,k);
    if(y%2)
        return x*((t*t)%k);
    return (t*t)%k;
}
int main() 
{ 
    cout<<mod_exp(2,6,13); 
    return 0; 
} 
