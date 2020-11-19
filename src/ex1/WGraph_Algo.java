package ex1;

import javax.swing.*;
import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph gAlgo; // our graph we will work with for the algorithms

    @Override
    public void init(weighted_graph g) {
        this.gAlgo = g;
    }

    @Override
    public weighted_graph getGraph() {
        return gAlgo;
    }
    //making copy based on the copies of nodes and graphs we made.
    @Override
    public weighted_graph copy() {
        weighted_graph copy = new WGraph_DS(gAlgo);
        return copy;
    }
//this method works the same as it worked in ex0
    @Override
    public boolean isConnected() {
        //check basic cases
        if (gAlgo == null) {
            return false;
        }
        if (gAlgo.nodeSize() == 0) {
            return true;
        }

        //run dfs on graph with any vertex - here we'll take the first which for sure exists after checking base cases
        Collection<node_info> vertexList = gAlgo.getV();
        node_info first_node = vertexList.iterator().next();

        //if visited vertex number equals to nodeSize - graph is connected
        return (runDfsAlgo(first_node, 0) == gAlgo.nodeSize());
    }

    int runDfsAlgo(node_info node, int count) {
        //using stack to keep dfs order
        Set<Integer> visited = new HashSet<>();
        Stack<node_info> stack = new Stack<>();
        stack.push(node);

        while (!stack.empty()) {
            node = stack.peek();
            stack.pop();
            if (!visited.contains(node.getKey())) {
                visited.add(node.getKey());
                count++;
            }
            Collection<node_info> neighborsList = ((WGraph_DS.Node_info) (node)).getNi();
            for (node_info neighbor : neighborsList) {
                if (!visited.contains(neighbor.getKey())) {
                    stack.push(neighbor);
                }
            }
        }
        return count;
    }

    //set tags to given value
    private void setTags(Collection<node_info> nodeList, double value) {
        for (node_info node : nodeList) {
            node.setTag(value);
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        //basic cases
        if (gAlgo.getV(src) == null || gAlgo.getV(dest) == null) {
            System.out.println("graph doesn't contain one of the keys or both");
            return -1;
        }
        if (src == dest) {
            System.out.println("src and dest are the same key");
            return 0;
        }

        //shortestPathDist will use shortestPath to calculate the distance between src and dest
        if (shortestPath(src, dest) == null) {
            return -1;
        }
        //the result is the tag of dest node
        return gAlgo.getNode(dest).getTag();
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> resultList = new ArrayList<>();
        //basic cases
        if (gAlgo.getV(src) == null || gAlgo.getV(dest) == null) {
            System.out.println("graph doesn't contain one of the keys or both");
            return null;
        }
        //if src=dest return a list which contain only src
        if (src == dest) {
            System.out.println("src and dest are the same key");
            resultList.add(gAlgo.getNode(src));
            return resultList;
        }
        setTags(gAlgo.getV(), Integer.MAX_VALUE);//  the tag will represent our distance from src , for now all nodes tags initialize to infinity
        resetInfoBlack(gAlgo.getV());// a node with info=black means he wasn't visited
        HashMap<Integer, WGraph_DS.Node_info> prev = new HashMap<>();
        WGraph_DS.Node_info node = ((WGraph_DS.Node_info) (gAlgo.getNode(src)));
        //run Dijkstra on the graph to calculate shortest paths
        runDijkstraAlgo(node, prev);

        //after running the Dijkstra algo we can populate the path using the predecessor from prev map
        WGraph_DS.Node_info currentNode = ((WGraph_DS.Node_info) (gAlgo.getNode(dest)));
        while (prev.get(currentNode.getKey()) != null) {
            resultList.add(0, currentNode);//by adding the index 0 the node will be add to the list from the right side
            currentNode = prev.get(currentNode.getKey());
        }
        //know we have to add the src node to the list as well
        if (!resultList.isEmpty()) {
            resultList.add(0, gAlgo.getNode(src));
            return resultList;
        }
        return null;
    }

    private void runDijkstraAlgo(WGraph_DS.Node_info node, HashMap<Integer, WGraph_DS.Node_info> prev) {
        PriorityQueue<WGraph_DS.Node_info> queue = new PriorityQueue<>(new WGraph_DS.Node_info());
        queue.add(node);//node represent the src
        node.setTag(0);
        prev.put(node.getKey(), null); //first the value of src is null (he have no predecessor)

        while (!queue.isEmpty()) {
            WGraph_DS.Node_info currentNode = queue.remove();// the node that will removed is the node with lowest tag as we defined the priority queue
            currentNode.setInfo("white");//update that we visited this current node
            for (node_info neighbor : currentNode.getNi()) {
                if (neighbor.getInfo().equalsIgnoreCase("black")) {//making sure we are working on a node that wasn't visited already
                    double newDistance = currentNode.getTag() + currentNode.getEdgeNodeMap().get(neighbor.getKey());//calculating the distance we passed to get to this node
                    if (newDistance < neighbor.getTag()) {//the distance is shorter
                        neighbor.setTag(newDistance);//,update the tag
                        prev.put(neighbor.getKey(), currentNode);// current node means from where we arrived to this node.
                    }
                    queue.add((WGraph_DS.Node_info) neighbor);
                }
            }
        }

    }
    //a method which reset all the info of the nodes to black
    private void resetInfoBlack(Collection<node_info> nodeList) {
        for (node_info node : nodeList) {
            node.setInfo("black");
        }
    }
    // this method Saves this weighted  graph to the given file name
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.gAlgo);

            fileOutputStream.close();
            objectOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //This method load a graph to this graph algorithm
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            weighted_graph deserializedGraph = (weighted_graph) objectInputStream.readObject();
            deserializedGraph.getV().forEach(node-> System.out.println(node.getKey()));
            fileInputStream.close();
            objectInputStream.close();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
