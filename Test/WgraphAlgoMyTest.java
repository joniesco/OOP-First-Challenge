package Test;

import ex1.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WgraphAlgoMyTest {

    @Test
    void isConnected() {
        weighted_graph g0 = WGraph_test.graph_creator(0);
        weighted_graph_algorithms ag0 = new WGraph_Algo();

        ag0.init(g0);
        assertTrue(ag0.isConnected());
       //one vertex
        g0 = WGraph_test.graph_creator(1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());


        g0.addNode(1);
        assertFalse(ag0.isConnected());

        // 2 nodes with one edge
        g0.connect(0,1,5);
        ag0.init(g0);
        assertTrue(ag0.isConnected());
        ag0.init(g0);
        g0 = WGraph_test.graph_creator(5);
        ag0.init(g0);
        assertFalse(ag0.isConnected());
        //connect
        for (int i=0; i<4; i++){
            g0.connect(i,i+1,i+4);
        }
        ag0.init(g0);
        assertTrue(ag0.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g0 = CreateGraph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());
        double d = ag0.shortestPathDist(0,4);
        assertEquals(d, 6.0);
         d = ag0.shortestPathDist(10,43);
        assertEquals(d, 184.0);
        d = ag0.shortestPathDist(0,46);
        assertEquals(d, 7.0);

    }

    @Test
    void shortestPath() {
        weighted_graph g0 = CreateGraph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sp = ag0.shortestPath(0,10);
        //double[] checkTag = {0.0, 1.0, 2.0, 3.1, 5.1};
        int[] checkKey=new int[11];
        for (int i=0; i<11; i++)
            checkKey[i] = i;
        int i = 0;
        for(node_info n: sp) {
//            assertEquals(n.getTag(), checkTag[i]);
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }


    }

    @Test
    void save_load() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,30,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 = WGraph_DSTest.graph_creator(10,30,1);
        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);
    }


    @Test
    void copy(){
        weighted_graph g = WGraph_DSTest.graph_creator (10, 10, 1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g);
        weighted_graph copy = ag0.copy();
        assertEquals(copy.getV().size(), g.getV().size());
        g.addNode(100);
        assertNotEquals(copy.getV().size(), g.getV().size());

    }

    private weighted_graph CreateGraph() {
        weighted_graph g0 = new WGraph_DS();
        int V =100;
        for (int i = 0; i<V; i++){
            g0.addNode(i);
        }
        // connect edge
        int E = 50;
        for (int i = 0; i<E; i++){
            g0.connect(i,i+1,i);
        }
        g0.connect(0,46,7);

        return g0;
    }
}