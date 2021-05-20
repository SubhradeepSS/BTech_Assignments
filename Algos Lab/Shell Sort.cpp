#include <bits/stdc++.h> 
using namespace std; 

void shell_sort(int arr[],int n){
    int j;
    for(int gap=n/2;gap>0;gap/=2){
        for(int i=gap;i<n;i++){
            int k=arr[i];
            for(j=i;j>=gap&&arr[j-gap]>k;j-=gap)
                arr[j]=arr[j-gap];
            arr[j]=k;
        }
    }
    for(int i=0;i<n;i++)
        cout<<arr[i]<<" ";
}

int main() 
{ 
	int arr[] = {12, 11, 10, 5, 6, 2, 30}; 
	int n = sizeof(arr)/sizeof(arr[0]); 
	shell_sort(arr, n); 
	return 0; 
} 
