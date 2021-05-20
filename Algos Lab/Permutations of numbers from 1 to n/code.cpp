#include<bits/stdc++.h>
using namespace std;

void permutation(int n){
    string s="";
    for(int i=1;i<=n;i++)
        s+=to_string(i);
    sort(s.begin(),s.end());
    do{
        cout<<s<<endl;
    }while(next_permutation(s.begin(),s.end()));
}

int main() 
{ 
    permutation(4); 
    return 0; 
} 
