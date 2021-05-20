#include<bits/stdc++.h>
using namespace std;

//(x^y)%k

int mod_exp(int x,int y,int k){
    x=x%k;
    int r=1;
    while(y>0){
        if(y%2)
            r=(r*x)%k;
        y>>=1;
        x=(x*x)%k;
    }
    return r;
}
int main() 
{ 
    cout<<mod_exp(2,5,13); 
    return 0; 
} 
