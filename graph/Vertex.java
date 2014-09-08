package graph;
import list.*;

/**
 * The Vertex class represents a Vertex of an WUGraph (weighted, undirected graph) . The protected values are only accessable through public methods.
 * An Vertex class object holds the following data:
 * Object: the value of the Vertex
 * DList: A circular DList storing the edges connected to this vertex
 * @Author Eeshan Agarwal
 */

class Vertex{
    
    protected Object value;
    protected DList edges = new DList();
    
    /**
     * Vertex contructor to assign the value of the Vertex to the input
     */
    Vertex(Object value)
    {
        this.value = value;
    }
    
    /**
     * getVal() method returns the value of the Vertex
     * Running time: O(1)
     * @Return Object value
     */
    Object getVal()
    {
        return value;
    }

     /**
     * edges() method returns the DList of edges connected to this vertex
     * Running time: O(1)
     * @Return DList edges
     */
    DList edges()
    {
        return edges;
    }
   
}
        
