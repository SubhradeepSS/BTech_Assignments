#include<bits/stdc++.h>
using namespace std;
void rec_insertion_sort(int a[],int n){
    if(n<=1)
        return;
    rec_insertion_sort(a,n-1);
    int last=a[n-1];
    int j=n-2;
    while(j>=0&&a[j]>last){
        a[j+1]=a[j];
        j--;
    }
    a[j+1]=last;
}
int main(){
    int a[]={2,1,4,2,6};
    rec_insertion_sort(a,5);
    for(int i=0;i<5;i++)
        cout<<a[i]<<" ";
}
