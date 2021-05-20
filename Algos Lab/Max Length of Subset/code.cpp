#include<bits/stdc++.h>
using namespace std;
int sum(string s){
    int r=0;
    for(int i=0;s[i];i++)
        r+=(s[i]-'0');
    return r;
}
void subset_length(string s,int d){
    int n=s.size();
    string a,b;
    int l,max=0;
    for(int i=0;i<pow(2,n);i++){
        a="";
        for(int j=0;j<n;j++)
            if(i&(1<<j))
                a+=s[j];
        if(sum(a)==d){
            l=a.size();
            if(l>max)
                {
                    b=a;
                    max=l;
                }
        }
    }
    cout<<"Subset = "<<b<<" of length = "<<max;
}
int main()  
{  
    string s="012345";
    int d=6;
    subset_length(s,d);
}  
  
