#include<bits/stdc++.h>
using namespace std;
priority_queue<int> insert(priority_queue<int> q,int x){
    q.push(x);
    return q;
}
void extract_max(priority_queue<int> q){
    cout<<q.top()<<endl;
}
void sort_q(priority_queue<int> q){
    while(!q.empty()){
        cout<<q.top()<<" ";
        q.pop();
    }
    cout<<endl;
}
int main(){
    priority_queue<int> q;
    int k,x;
    do{
        cout<<"1.Insert 2.Extract Max 3.Sort Display 4.Exit\n";
        cin>>k;
        switch(k){
            case 1:
            cin>>x;
            q=insert(q,x);
            break;
            case 2:
            extract_max(q);
            break;
            case 3:
            sort_q(q);
            break;
            case 4:
            exit(1);
        }
    }while(k!=4);
}
