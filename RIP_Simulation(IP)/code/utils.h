#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

class RoutingEntry{
public:
  string dstip, nexthop;
  string ip_interface;
  int cost;
};

class Comparator{
public:
  bool operator()(const RoutingEntry &R1, const RoutingEntry &R2){
    if (R1.cost == R2.cost){
      return R1.dstip.compare(R2.dstip) < 0;
    }
    else if (R1.cost > R2.cost){
      return false;
    }
    return true;
  }
};

struct routingtbl{
  vector<RoutingEntry> tbl;
};


class RouteMsg{
public:
  string from;              
  struct routingtbl *mytbl; 
  string recvip;          
};


class NetInterface{
private:
  string ip;
  string connectedTo; 

public:
  string getip(){
    return this->ip;
  }

  string getConnectedIp(){
    return this->connectedTo;
  }

  void setip(string ip){
    this->ip = ip;
  }

  void setConnectedip(string ip){
    this->connectedTo = ip;
  }
};


class Node{
private:
  string name;
  vector<pair<NetInterface, Node *>> interfaces;

protected:
  struct routingtbl mytbl;
  virtual void recvMsg(RouteMsg *msg){
    cout << "Base" << endl;
  }

  bool isMyInterface(string eth)
  {
    for (int i = 0; i < interfaces.size(); ++i)
    {
      if (interfaces[i].first.getip() == eth)
        return true;
    }
    return false;
  }

public:
  void setName(string name){
    this->name = name;
  }

  void addInterface(string ip, string connip, Node *nextHop){
    NetInterface eth;
    eth.setip(ip);
    eth.setConnectedip(connip);
    interfaces.push_back({eth, nextHop});
  }

  void addTblEntry(string myip, int cost){
    RoutingEntry entry;
    entry.dstip = myip;
    entry.nexthop = myip;
    entry.ip_interface = myip;
    entry.cost = cost;
    mytbl.tbl.push_back(entry);
  }

  string getName(){
    return this->name;
  }

  struct routingtbl getTable(){
    return mytbl;
  }

  void printTable(){
    Comparator myobject;
    sort(mytbl.tbl.begin(), mytbl.tbl.end(), myobject);
    cout << this->getName() << ":" << endl;
    for (int i = 0; i < mytbl.tbl.size(); ++i){
      cout << mytbl.tbl[i].dstip << "\t" << mytbl.tbl[i].nexthop << "\t" << mytbl.tbl[i].ip_interface << "\t\t" << mytbl.tbl[i].cost << endl;
    }
  }

  void sendMsg(){
    struct routingtbl ntbl;
    for (int i = 0; i < mytbl.tbl.size(); ++i){
      ntbl.tbl.push_back(mytbl.tbl[i]);
    }

    for (int i = 0; i < interfaces.size(); ++i){
      RouteMsg msg;
      msg.from = interfaces[i].first.getip();
      msg.mytbl = &ntbl;
      msg.recvip = interfaces[i].first.getConnectedIp();
      interfaces[i].second->recvMsg(&msg);
    }
  }
};


class RoutingNode : public Node{
public:
  void recvMsg(RouteMsg *msg);
};