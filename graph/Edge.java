package graph;
import list.*;

/**
 * The Edge class represents an Edge of an WUGraph (weighted, undirected graph) . The protected values are only accessable through public methods.
 * An Edge class object holds the following data:
 * VertexPair: represents a pair of objects that act as vertices in the graph
 * Weight: represents the weight of this edge
 * otherEdge: represents the partner or 'half-edge' reference. 
 * @Author Eeshan Agarwal
 */

class Edge
{
    protected VertexPair vPair;
    protected int weight;
    protected DListNode otherEdge;
   
    /**
     * Edge contructor to assign the variables and to set otherEdge to null
     */
    Edge(VertexPair vPair, int weight)
    {
        this.vPair = vPair;
        this.weight = weight;
        otherEdge = null;
    }
    
    /** 
     * updateWeight() method to set the weight of the edge to the new input weight
     * Running time: O(1)
     * @Param Integer weight
     */
    
    void updateWeight (int weight)
    {
        this.weight = weight;
    }
    
    /**
     * weight() method returns the weight of the edge
     * Running time: O(1)
     * @Return Integer
     */
    int weight()
    {
        return weight;
    }
    
    /**
     * getPair() method returns the VertexPair of the edge
     * Running time: O(1)
     * @Return Object vPair
     */
    Object getPair()
    {
        return vPair;
    }
    
    /**
     * otherEdge() method returns the other reference of the edge
     * Running time: O(1)
     * @Return DListNode otherEdge
     */
    DListNode otherEdge()
    {
        return otherEdge;
    }
    
    /**
     * setHEdge() method sets the otherEdge reference to the input DListNode
     * Running time: O(1)
     * @Param DListNode
     */
    void setHEdge(DListNode half)
    {
        otherEdge = half;
    }
}