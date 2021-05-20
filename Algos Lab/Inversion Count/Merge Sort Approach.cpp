#include<bits/stdc++.h>
using namespace std;
int merge(int a[],int t[],int l,int m,int h){
    int c=0;
    int i=l,j=m+1,k=l;
    while(i<=m&&j<=h){
        if(a[i]<=a[j])
            t[k++]=a[i++];
        else{
            t[k++]=a[j++];
            c+=(m-i+1);
        }
    }
    while(i<=m){
        t[k++]=a[i++];
    }
    while(j<=h)
        t[k++]=a[j++];
    for(int i=l;i<=h;i++)
    a[i]=t[i];
    return c;
}
int mergesort(int a[],int t[],int l,int h){
    int count=0;
    if(l<h){
        int m=(l+h)/2;
        count+=mergesort(a,t,l,m);
        count+=mergesort(a,t,m+1,h);
        count+=merge(a,t,l,m,h);
    }
    return count;
}
int merge_sort(int a[],int n){
    int t[n];
    return mergesort(a,t,0,n-1);
}
int main(){
   int arr[] = { 1, 3, 6, 4, 5 }; 
    int n = sizeof(arr) / sizeof(arr[0]); 
    int ans = merge_sort(arr, n); 
    cout << " Number of inversions are " << ans; 
    return 0; 
}
