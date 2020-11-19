package ex1;
import ex1.WGraph_DS;
import ex1.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_test {
    private static final Random _rnd = null;


    public static weighted_graph graph_creator (int v_size) {
        weighted_graph g = new WGraph_DS();
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        return g;
    }


    //test for runtime making a large graph
    @Test
    void AddNodeAndRemove() {
        weighted_graph graph = new WGraph_DS();
        for (int i = 0; i < 1000000; i++) {
            graph.addNode(i);
            if (i%100==0)
                graph.addNode(i); //the key exist

        }
        assertEquals(1000000, graph.nodeSize());
        for (int i = 10; i < 20; i++) {
            graph.removeNode(i);
            graph.removeNode(i);
        }
        assertEquals((1000000-10), graph.nodeSize());


    }
    @Test
    void AddEdgesAndRemove() {
        weighted_graph   graph = new WGraph_DS();
        int v =1000000,e = 1000000;
        graph.addNode(0);
        for (int i = 1; i < e+1; i++) {
            graph.addNode(i);
            graph.connect(i,i-1,i-1);
        }
        assertEquals(e, graph.edgeSize());
        for (int i = 1; i < (30000+1); i++) {
            graph.removeEdge(i,i-1);
            graph.removeEdge(i,i-1);
        }
        e= e-30000;
        assertEquals(e, graph.edgeSize());
    }
    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        for (int i=0; i<=3; i++){
            g.addNode(i);
        }
        for (int i=0; i<3; i++) {
            g.connect(0, i+1, i+1);
        }
        g.removeNode(4); //not exist
        assertTrue(g.hasEdge(1,0));
        g.removeNode(0);
        assertFalse(g.hasEdge(1,0));

        int e = g.edgeSize();
        assertEquals(0,e);
        int v= g.nodeSize();
        assertEquals(3,v);
    }
    // Test for addNode function

    @Test
    void addNode(){
        weighted_graph   graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.addNode(7);
        graph.connect(1,2,3);
        graph.connect(2,3,3);
        graph.connect(3,4,3);
        graph.connect(4,5,3);
        graph.connect(5,6,3);
        graph.connect(6,7,3);
        assertEquals(7, graph.nodeSize());
        assertNotEquals(8, graph.nodeSize());


    }
    //this test makes sure that we cannot add and exist key to the graph
    @Test
    void addExistsNode (){
        weighted_graph   graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(1);
        graph.addNode(1);
        graph.addNode(1);
        assertEquals(1, graph.nodeSize());
    }

    @Test
    void removeNotExistNode (){
        weighted_graph   graph = new WGraph_DS();
        graph.addNode(1);
        graph.removeNode(7);
        assertEquals(1, graph.nodeSize());

    }



}

