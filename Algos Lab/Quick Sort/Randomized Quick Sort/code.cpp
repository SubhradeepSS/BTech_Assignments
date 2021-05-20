#include<bits/stdc++.h>
using namespace std;
using namespace std::chrono;

int partition(int a[],int l,int h){
    int p=a[h];
    int i=l-1;
    for(int j=l;j<h;j++){
        if(a[j]<p)
            {
                i++;
                swap(a[i],a[j]);
            }
    }
    swap(a[i+1],a[h]);
    return (i+1);
}
int partition_random(int a[],int l,int h){
    srand(time(NULL));
    int r=l+rand()%(h-l);
    swap(a[r],a[h]);
    return partition(a,l,h);
}
void q_sort(int a[],int n,int l,int h){
    while(l<h){
        int p=partition_random(a,l,h);
        q_sort(a,n,l,p-1);
        l=p+1;
    }
}
int main() 
{ 
    int n=1000;
    int a[1000]={0};
    auto start=high_resolution_clock::now();
    q_sort(a,n,0,n-1);
    /*
    for(int i=0;i<6;i++)
        cout<<a[i]<<" ";*/
    auto end=high_resolution_clock::now();
    auto duration=duration_cast<microseconds>(end-start);
    cout<<duration.count();
} 
