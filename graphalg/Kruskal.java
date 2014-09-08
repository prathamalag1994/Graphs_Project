/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import dict.*; // Importing chained hash table
import list.*; // Importing doubly linked list

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 * @author Jvalin Tejpal
 */

public class Kruskal {

    /**
     * minSpanTree() returns a WUGraph that represents the minimum spanning tree
     * of the WUGraph g.  The original WUGraph g is NOT changed.
     *
     * @param g The weighted, undirected graph whose MST we want to compute.
     * @return A newly constructed WUGraph representing the MST of g.
     */
    public static WUGraph minSpanTree(WUGraph g) {
        // Create a new graph tGraph
        WUGraph tGraph = new WUGraph();

         // Get the vertices of 'g'
        // Create Chained-Hash-Table for disjoint set
        Object[] gVertices = g.getVertices();
        HashTableChained indices = new HashTableChained(g.vertexCount());

         // Populate tGraph with vertices of 'g'
        // Store index of vertices in Chained-Hash-Table
        for (int i = 0; i < gVertices.length; i++) 
        {
            tGraph.addVertex(gVertices[i]);
            indices.insert(gVertices[i], new Integer(i));
        }

        // Get all the edges of 'g'
        int index = 0;
        for (int j = 0; j < gVertices.length; j++)
        {
            Neighbors neighbours = g.getNeighbors(gVertices[j]);
            if (neighbours != null)
            {
                for (int k = 0; k < neighbours.weightList.length; k++) 
                {
                    index++;
                }
            }
        }
                                                       // Creating an array of edges to be sorted by quick sort later.
        KruskalEdge[] edges = new KruskalEdge[index]; // Creating an array of just the right size.
        index=0;                                     // re-setting index to 0
        for (int j = 0; j < gVertices.length; j++) 
        {                                          // Going through each vertex in the graph
            Neighbors neighbours = g.getNeighbors(gVertices[j]); // And getting their neighbours and the weights of the edges connecting them
            if (neighbours != null) 
            {                                                   // If the edge has a neighbour ...
                for (int k = 0; k < neighbours.weightList.length; k++) 
                {
                    KruskalEdge kEdge = new KruskalEdge(gVertices[j], neighbours.neighborList[k], neighbours.weightList[k]); // Internalise the edge as a KruskalEdge
                    edges[index] = kEdge; // Saving it in an array
                    index++;             // Move over to the next array index
                }
            }
        }

        // Sort through the array of weighted edges (KruskalEdges)
        // Look at QuickSrot.java (from Lab 12)
        QuickSort.quicksort(edges);

        // Finding the edges of T for the minimum spanning tree using  disjoint sets
        DisjointSets tSet = new DisjointSets(g.vertexCount());
        int ctr =0;
        while(tGraph.edgeCount() < g.vertexCount() -1 )
        {
            KruskalEdge edge = edges[ctr];
            int root1 = tSet.find((int)indices.find(edge.getV1()));    //finds the first root 
            int root2 = tSet.find((int)indices.find(edge.getV2()));   //finds the second root
            if(root1 != root2)                                    //if the roots are not equal, append the edge
            {
                tSet.union(root1, root2); 
                tGraph.addEdge(edge.getV1(), edge.getV2(), edge.getWeight());
            }
            ctr++;
        }

        return tGraph; //return the new graph
    }

}
