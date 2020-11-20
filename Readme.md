EX_1:
Inner Class Node_info implements node_info:
This inner class represents a node_info which is a vertex in a weighted  graph that contains few fields:
  key, info and tag. Each node can have  vertices “neighbors” which means there are weighted edges(a unergative weight) that connect it to each one of these vertices .
The fields of this class are:
-Count (static int): A variable which help us to make sure each node has a unique key. 
Count starts at zero and in each creating new node_info, count increases by one,
 so two different nodes cannot have the same key. Count will be reset to zero on each new graph creation.
- Key (int): Represents the key of the node.
- Nei (HashMap<Integer, node_info>): Represents a list of the node’s neighbors. Implemented by an HashMap in form of neighbor_key - > neighbor_node_info
Edge (HashMap<Integer, Double>): Represents a list of the node’s neighbors and the weight of the edge which connect the specific neighbor. Implemented by an HashMap in form of neighbor_key - > double weight
 - Info (String): Represent the info (meta data) of the node(will help us for algos)
- Tag (int): A temp variable, can be used for some markings and calculation of algorithms.
Methods:
Removing a given node, adding a given node, get the key of the given node, get the neighbors nodes of the given node, Boolean method hasNi which check if the given node is a neighbor.
 And resetCounter, a static method which reset the count to zero.
 
-Class WGraph_DS implements graph:
This class represents an undirected graph, which is a collection of nodes/vertex, the nodes can be connected to each other, but not necessarily. Creating a new graph will reset count in Node_info.
The fields of this class are:
- MyNodes (HashMap<Integer, node_info>): A HashMap of data nodes which represent our graph, in form of node_key -> node_data.
- NumOfEdge (int): Represents the number of edges that the graph contains.
- MC (int): Represents the number of operations that were made on the graph. Each update on the graph, will raise MC by one. Initial value is zero.
Methods:
Adding/removing a node to/from the graph, connecting between nodes (by weighted edge) in the graph.
 Get size (num of nodes), specific node, MC, list of graph’s nodes and list of neighbors. Also, hasEdge which check if 2 given nodes are connected by a direct edge.

Class Graph_Algo implements graph_algorithms

This class represents some algorithms on the graph that will help us to get some information.
The fields of this class are:
-gAlgo (graph): Represents our actual graph.
Methods and algorithms:

-isConnected:

The method will determine if the graph “is connected” – a graph is connected if and only if there is a valid path from every node to each other node.
 The method will first check some basic cases such as empty graph, and after it will use runDfsAlgo which will retrieve the number of all the visited nodes from a given source in graph -
 here source will be the first node in graph node list (getting by using iterator). isConnected will return true only if this number equals to the nodeSize of the graph.

-runDfsAlgo:


Helping data structure: stack.
In this method we will use the DFS algorithm. The method gets a source to start from and will calculate all the possible paths from it using the DFS and the neighbors list. 
The DFS will be implemented by using a stack which will contain all the visited vertex and will always handle first the last visited vertex (DFS principle). 
To mark a visited node, the algorithm will use the tag in each node – 1 will represent a visited node, and 0 will represent a non-visited one. Also, for each visited node, the algorithm will increase the counter, and at the end, will retrieve it.
 Before retrieving the counter, it will reset all nodes tags to 0 (resetTags), so it’ll can be use in other run.

-shortestPath list:

The method retrieves a list of vertices which represents a path in the graph from a given source and destination, if exists. 
The method will first check some basic cases, such as same source and destination node, or if one of these doesn’t exists in the graph. Then, will create an empty HashMap which will contain for each vertex it’s predecessor on the path. 
This map will be filled by calling runDijkstraAlgo , and then will collect the path by using it in reverse way, starting by the destination.

-shortestPathDist:

The method will retrieve the size of the path between a given source and destination. The method will  check basic cases and than will use shortestPath and will just return the tag of the dest node 
 which will represent the distance  (the total weight) to get from the source node to the distance node.



-runDijkstraAlgo:

Helping data structure: priorityqueue, HashMap<Integer, WGraph_DS.Node_info> prev
 
This method help us to calculate the smallest weight to get from a given node to another given node. First all node tags initialize to infinity and node ingo to "black" , 
than we start with the source node and update his tag to 0. And putting this node into the hashmap first the value of src is null (he have no predecessor)
And finally we will add this node into our priority queue.
Starting of the loop: while our queue is not empty 
We will make a remove from the queue to a  temporary node (current node)
If there is more than one node in the queue the first to get out is the one with the lowest tag as we defined it 
Now we will pass over all the node neighbors  . and we will check if  the neighbor wasn’t visited: we will check the edge weight ,
 and if the edge weight of the neighbor plus the tag of the current node is less then the neighbor's tag so update the tag of the neighbor  
  and put him into the map , the key is the node key and the value is the current node so we will know from where we arrived . and finally after the loop ends we will have a list of nodes. 
In each node we can know how we arrived to him(via who) and how much it cost us (weight) by the node tags.


-Load:
This method load a graph to this graph algorithm

-Save:
this method Saves this weighted  graph to the given file name

-copy
this method make a deep copy of our graph

