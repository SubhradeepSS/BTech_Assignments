#include "utils.h"
#include <iostream>
using namespace std;

// Routing algorithm used - Bellman Ford algorithm
void routingAlgo(vector<RoutingNode *> nd){
	int num = nd.size();
	bool nc = false;
	vector<struct routingtbl> rtb;
	int num2 = 0;

	while (true){
		nc = true; 
		rtb.clear();

		for (int j = 0; j < num; j++){
			rtb.push_back(nd[j]->getTable());
		}

		for (int i = 0; i < num; i++){
			nd[i]->sendMsg();
		}

		for (int k = 0; k < num; k++){
			if (rtb[k].tbl.size() != (nd[k]->getTable()).tbl.size()){
				nc = false;
				break;
			}

			for (int k3 = 0; k3 < (nd[k]->getTable()).tbl.size(); k3++){
				if (rtb[k].tbl[k3].nexthop == (nd[k]->getTable()).tbl[k3].nexthop){
					if (rtb[k].tbl[k3].ip_interface == (nd[k]->getTable()).tbl[k3].ip_interface)
						continue;
					else{
						nc = false;
						break;
					}
				}
				else{
					nc = false;
					break;
				}
			}
		}
		if(nc)
			break;
	}
}

void RoutingNode::recvMsg(RouteMsg *msg){
	int n = mytbl.tbl.size();
	int k = msg->mytbl->tbl.size();
	bool efound = false;
	int i = 0, j = 0;

	while (j < k){
		mytbl.tbl.push_back(msg->mytbl->tbl[j]);
		mytbl.tbl[mytbl.tbl.size() - 1].cost++;
		mytbl.tbl[mytbl.tbl.size() - 1].nexthop = msg->from;
		mytbl.tbl[mytbl.tbl.size() - 1].ip_interface = msg->recvip;
		efound = false;

		for (i = 0; i < n; i++){
			if (((msg->mytbl->tbl[j]).dstip) == (mytbl.tbl[i].dstip)){
				efound = true;

				if ((msg->mytbl->tbl[j].cost + 1) < mytbl.tbl[i].cost) {
					if (isMyInterface(msg->mytbl->tbl[j].nexthop)){
						break;
					}
					else{
						mytbl.tbl[i].nexthop = msg->from;
						mytbl.tbl[i].ip_interface = msg->recvip;
						mytbl.tbl[i].cost = msg->mytbl->tbl[j].cost + 1;
						break;
					}
				}
			}
		}
		
		if (efound) {
			mytbl.tbl.pop_back();
		}

		j++;
	}
}
