#include<bits/stdc++.h>
using namespace std;
//x*a+y*b=gcd(x,y)
int gcd(int x,int y){
    if(y==0)
        return x;
    return gcd(y,x%y);
}
void ab(int x,int y){
    int g=gcd(x,y);
    int a=1,b=-1;
    while(x*a+y*b!=g){
        if(x*a+y*b<g)
            a++;
        else
            b--;
    }
    cout<<"a="<<a<<" b="<<b;
}
int main() 
{ 
    int x=1547;
    int y=560;
    ab(max(x,y),min(x,y));
    return 0; 
} 
