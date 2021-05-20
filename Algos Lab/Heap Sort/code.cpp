#include <bits/stdc++.h> 
using namespace std; 
void heapify(int arr[],int n,int i){
    int la=i;
    int l=2*i+1,r=2*i+2;
    if(l<n&&arr[l]>arr[la])
        la=l;
    if(r<n&&arr[r]>arr[la])
        la=r;
    if(la!=i){swap(arr[i],arr[la]);
        heapify(arr,n,la);}
}
void heapsort(int arr[],int n){
    for(int i=n/2-1;i>=0;i--)
        heapify(arr,n,i);
    for(int i=n-1;i>=0;i--){
        swap(arr[i],arr[0]);
        heapify(arr,i,0);
    }
    for(int i=0;i<n;i++)
        cout<<arr[i]<<" ";
}

int main() 
{ 
	int arr[] = {12, 11, 10, 5, 6, 2, 30}; 
	int n = sizeof(arr)/sizeof(arr[0]); 
	heapsort(arr, n); 
	return 0; 
} 
