#include<bits/stdc++.h>
using namespace std;
int stl(int arr[],int n){
    int c=0;
    set<int> s;
    s.insert(arr[0]);
    for(int i=1;i<n;i++){
        s.insert(arr[i]);
        auto it=s.upper_bound(arr[i]);
        c+=distance(it,s.end());
    }
    return c;
}
int main(){
   int arr[] = { 1, 3, 6, 4, 5 }; 
    int n = sizeof(arr) / sizeof(arr[0]); 
    int ans = stl(arr, n); 
    cout << " Number of inversions are " << ans; 
    return 0; 
}
