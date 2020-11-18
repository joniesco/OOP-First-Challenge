package ex1;

import java.util.*;

public class WGraph_DS implements weighted_graph, java.io.Serializable {
    private HashMap<Integer, node_info> myNodes;/// will represent us our graph (keys-int , value -node info)
    private int NumOfEdge = 0; //  will count us the total amount of the edges in the graph
    private int MC = 0; // a variable that represent us the changes tha were made during the work on the graph

    static class Node_info implements node_info, Comparator<Node_info>, java.io.Serializable {
        private static int count = 0;
        private int key;
        private HashMap<Integer, node_info> nei;
        private HashMap<Integer, Double> edge; // the value of each key in the map is the weight value
        private String info;
        private double tag;

        // constructor:
        // the count variable rule is to make sure each key will be unique
        // neighbors list will  be presented by Hashmap
        //edges list will be presented by Hashmap(key is the neighbor , value is the weight of the edge)
        public Node_info() {
            this.key = count;
            count++;
            this.nei = new HashMap<Integer, node_info>();
            this.tag = 0;
            this.edge = new HashMap<Integer, Double>();
        }
        //constructor
        public Node_info(int key) {
            this.key = key;
            this.nei = new HashMap<Integer, node_info>();
            this.tag = 0;
            this.edge = new HashMap<Integer, Double>();
        }
        // a copy on which we pass and update all the fields
        public Node_info(node_info other) {
            this.nei = new HashMap<Integer, node_info>();
            for (node_info a : ((Node_info) (other)).getNi()) {
                nei.put(a.getKey(), a);
            }
            this.edge = new HashMap<Integer, Double>();
            for (Map.Entry<Integer, Double> entry : ((Node_info) (other)).getEdgeNodeMap().entrySet()) {
                edge.put(entry.getKey(), entry.getValue());
            }
            info = other.getInfo();
            tag = other.getTag();
            key = other.getKey();
        }

        // A method that will reset our counter
        public static void resetCounter() {
            count = 0;
        }
       // this method return us the neighbor list of our node_info
        public Collection<node_info> getNi() {
            return this.nei.values();
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }
        // this method checks if a given key is a neighbor of our node_info
        public boolean hasNi(int key) {
            return nei.containsKey(key);
        }
        //this method give us the list of the edges that are connected to our node_info
        public HashMap<Integer, Double> getEdgeNodeMap() {
            return this.edge;
        }
        // a comparator that compares the tags of 2 nodes
        //this method will help us to know which node will be in priority in the priority queue (see in graph algorithm class)
        @Override
        public int compare(Node_info o1, Node_info o2) {
            if (o1.getTag() > o2.getTag()) return 1;
            if (o1.getTag() < o2.getTag()) return -1;
            return 0;
        }
        //this method return us the weight of the edge of 2 given nodes
        public double getEdge(Node_info node1, Node_info node2) {
            if (node1.getEdgeNodeMap().get(node2.getKey()) == null) {//making sure the nodes are neighbors
                return -1;
            } else {
                return node1.getEdgeNodeMap().get(node2.getKey());
            }
        }
        // a method that checks if 2 node_infos are the same (by keys and by values)
        @Override
        public boolean equals(Object other) {
            //basic cases: first we check that we are not working on the same object (they are pointing to the same place in the memory)
            // second, we check that they are the same type
            if (other == this) {
                return true;
            }
            if (!(other instanceof Node_info)) {
                return false;
            }
            Node_info otherNode = (Node_info) other;

            if (otherNode.getKey() != this.getKey()) {
                return false;
            }
            // we go over the neighbors verifying if they are the same and if the edges are the same
            Collection<node_info> otherNeighbors = otherNode.getNi();
            for (node_info otherNeighbor : otherNeighbors) {
                if (!this.nei.containsKey(otherNeighbor.getKey())) {
                    return false;
                }
                if (getEdge((Node_info) otherNeighbor, this) != this.edge.get(otherNeighbor.getKey())) {
                    return false;
                }
            }

            return true;
        }

        //end of inner class Node
    }
    //Constructor
    // The graph will be presented by a hashmap

    public WGraph_DS() {
        this.myNodes = new HashMap<Integer, node_info>();
        Node_info.resetCounter();//Reset the counter each time we create a graph
    }
    //deep copy

    public WGraph_DS(weighted_graph other) {
        myNodes = new HashMap<Integer, node_info>();
        Collection<node_info> otherNodes = other.getV();
        for (node_info a : otherNodes) {
            node_info newNode = new Node_info(a);
            myNodes.put(a.getKey(), newNode);
        }
        for (node_info a : otherNodes) {
            for (node_info b : otherNodes) {
                if (other.hasEdge(a.getKey(), b.getKey())) {
                    this.connect(a.getKey(), b.getKey(), ((Node_info) (a)).edge.get(b.getKey()));
                }
            }
        }
        this.NumOfEdge = other.edgeSize();
        this.MC = other.getMC();
    }
    //The method checks if the given key appears at tha hash map , if it is the method returns the value

    @Override
    public node_info getNode(int key) {
        if (myNodes.containsKey(key))// verifying that the graph contains the given key
            return myNodes.get(key);
        return null;
    }
    //The method checks if a given node(key) is in the neighbors list(hashmap) of a other given node(key)
    //There is a direct access to the map so the complexity is O(1)

    @Override
    public boolean hasEdge(int node1, int node2) {
        return ((Node_info) (myNodes.get(node1))).hasNi(node2);
    }
    //return the weight of the edge (node1, node1). In case
    //      there is no such edge returns -1
    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2)) return -1;//verifying if there is an edge between the nodes

        return ((Node_info) (myNodes.get(node1))).edge.get(node2);
    }
///method to add an node to our graph
    @Override
    public void addNode(int key) {
        if (!myNodes.containsKey(key)) { //the node will be added only if he is not already belongs to the graph
            myNodes.put(key, new Node_info(key));

            MC++; // a change was made - updating MC
        }
    }
    //this method making two existing  nodes  that are not neighbors to be neighbors and update there weight by a given weight
    @Override
    public void connect(int node1, int node2, double w) {
        if (w >= 0 && myNodes.containsKey(node1) && myNodes.containsKey(node2) && node1 != node2) {
            if (!hasEdge(node1, node2))
                NumOfEdge++;//there is no an edge between the nodes so we add one and updating the num of edge
            ((Node_info) (myNodes.get(node1))).nei.put(node2, myNodes.get(node2));
            ((Node_info) (myNodes.get(node1))).edge.put(node2, w);
            ((Node_info) (myNodes.get(node2))).nei.put(node1, myNodes.get(node1));
            ((Node_info) (myNodes.get(node2))).edge.put(node1, w);
            MC++;
        }
    }

    @Override
    //this method gives us the list of all our nodes
    public Collection<node_info> getV() {
        return myNodes.values();
    }
    //this method gives us a list of neighbors of a specific given node
    @Override
    public Collection<node_info> getV(int node_id) {
        return ((Node_info) (myNodes.get(node_id))).nei.values();
    }
    //this method removes a node from the graph and returns it
    @Override
    public node_info removeNode(int key) {
        if (myNodes.containsKey(key)) {//making sure the node belongs to the graph
            for (node_info n : getV(key)) {
                ((Node_info) (n)).nei.remove(key);////updating all the nodes that they are no longer neighbors with the given key
                ((Node_info) (n)).edge.remove(key);//deleting all the edges that  the given node had
                NumOfEdge--;//a edge was removed in each iteration, updating the num of edges
            }
            MC++; //a change was made
            return (myNodes.remove(key));
        }
        return null;
    }
    // in this method we remove a edge that connects between 2 nodes and updating all the relevant lists that the edge was removed
    @Override
    public void removeEdge(int node1, int node2) {
        if ( myNodes.containsKey(node1) && myNodes.containsKey(node2)&& hasEdge(node1,node2)) {
            ((Node_info) (myNodes.get(node1))).nei.remove(node2);
            ((Node_info) (myNodes.get(node1))).edge.remove(node2);
            ((Node_info) (myNodes.get(node2))).nei.remove(node1);
            ((Node_info) (myNodes.get(node2))).edge.remove(node1);
            NumOfEdge--;// a edge was removed
            MC++;// a change was made
        }
    }
    // this method return us how many nodes we have in our graph
    @Override
    public int nodeSize() {
        return myNodes.size();
    }
    // this method return us how many edges we have in our graph
    @Override
    public int edgeSize() {
        return NumOfEdge;
    }
    // this method return us how much changes were made in our graph
    @Override
    public int getMC() {
        return MC;
    }
    // a method that checks if 2 graphs are the same (by keys and by values)

    @Override
    public boolean equals(Object g) {
        //basic cases: first we check that we are not working on the same object (they are pointing to the same place in the memory)
        // second, we check that they are  from the same type
        if (g == this) {
            return true;
        }
        if (!(g instanceof WGraph_DS)) {
            return false;
        }
        WGraph_DS graph1 = (WGraph_DS) g;
        // now verifying with graph1 if he equals to our graph

        if (graph1.getMC() != this.getMC()) {
            return false;
        }

        if (graph1.edgeSize() != this.edgeSize()) {
            return false;
        }
        return checkNodes(graph1);
    }
// this helper method compare between nodes in the graph by the way we defined it in the Node info class
    private boolean checkNodes(WGraph_DS graph1) {
        Collection<node_info> nodeList = graph1.getV();
        for (node_info otherNode : nodeList) {
            if (!otherNode.equals(this.myNodes.get(otherNode.getKey()))) {
                return false;
            }
        }
        return true;
    }
}
