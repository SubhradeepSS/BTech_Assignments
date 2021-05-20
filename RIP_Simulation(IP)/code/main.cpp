#include "utils.h"
#include <iostream>

vector<RoutingNode *> distanceVectorNodes;

void routingAlgo(vector<RoutingNode *> distanceVectorNodes);

int main(){
  int N; // no of topologies
  cin >> N;

  for (int top_c=0; top_c<N; top_c++){
    int n;
    cin >> n; // no of nodes in the topology
    string name;
    distanceVectorNodes.clear();
    for (int i = 0; i < n; i++){
      RoutingNode *newnode = new RoutingNode();
      cin >> name;
      newnode->setName(name);
      distanceVectorNodes.push_back(newnode);
    }

    cin >> name;
    
    while (name != "DELIMITER"){ 
      for (int i = 0; i < distanceVectorNodes.size(); i++){
        string myeth, oeth, oname;

        if (distanceVectorNodes[i]->getName() == name){
          cin >> myeth >> oeth >> oname;
          for (int j = 0; j < distanceVectorNodes.size(); j++){
            if (distanceVectorNodes[j]->getName() == oname){
              
              distanceVectorNodes[i]->addInterface(myeth, oeth, distanceVectorNodes[j]);
              distanceVectorNodes[i]->addTblEntry(myeth, 0);
              break;
            }
          }
        }
      }

      cin >> name;
    }
    
    routingAlgo(distanceVectorNodes);

    cout << "\nFollowing is the Routing Table for the topology " << top_c+1 << ":\n";
    cout << "Destination IP\tNext Hop\tEthernet Interface\tHop Count\n";
    for (int i = 0; i < distanceVectorNodes.size(); i++){
      distanceVectorNodes[i]->printTable();
      cout << endl;
    }
    cout << "\n\n";

  }
}
