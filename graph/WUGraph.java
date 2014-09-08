/* WUGraph.java */

package graph;
import list.*;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {

    int numVertices;                   //stores number of Vertices in the graph
    int numEdges;                     //stores the number of Edges in the graph
    DList list;                      //stores a circular DList containing a list of Vertex
    HashTableChained vertexHash;    //stores a Vertex Hastable that maps an application-suuplied vertex to the internal Vertex data structure 
    HashTableChained edgeHash;     //stores an Edge Hastable that maps an unordered pair of objects (application-supplied) to the internal Edge data structure 
    /**
     * WUGraph() constructs a graph having no vertices or edges.
     *
     * Running time:  O(1).
     * @Author Eeshan Agarwal
     */
    public WUGraph()
    {
        vertexHash = new HashTableChained();
        edgeHash = new HashTableChained();
        list = new DList();
        numEdges = 0;
        numVertices = 0;
    }

    /**
     * vertexCount() returns the number of vertices in the graph.
     *
     * Running time:  O(1).
     * @Author Eeshan Agarwal
     */
    public int vertexCount()
    {
        return numVertices; //return the number of vertices
    }

    /**
     * edgeCount() returns the total number of edges in the graph.
     *
     * Running time:  O(1).
     * @Author Eeshan Agarwal
     */
    public int edgeCount()
    {
        return numEdges; //return the number of edges
    }

    /**
     * getVertices() returns an array containing all the objects that serve
     * as vertices of the graph.  The array's length is exactly equal to the
     * number of vertices.  If the graph has no vertices, the array has length
     * zero.
     *
     * (NOTE:  Do not return any internal data structure you use to represent
     * vertices!  Return only the same objects that were provided by the
     * calling application in calls to addVertex().)
     *
     * Running time:  O(|V|).
     * @Author Pratham Makni Alag 
     */
    public Object[] getVertices()
    {
        Object [] vertices = new Object[numVertices];       //initializes an Object array of size equal to the number of vertices
        int ctr = 0;
        for (Object temp : list)                          //traverse the list containing Vertices
        {
            vertices[ctr++] = ((Vertex) temp).getVal(); //add the vertex to the array at position ctr
        }
        return vertices;                              //return the Object array
    }

    /**
     * addVertex() adds a vertex (with no incident edges) to the graph.
     * The vertex's "name" is the object provided as the parameter "vertex".
     * If this object is already a vertex of the graph, the graph is unchanged.
     *
     * Running time:  O(1).
     * @Author Pratham Makni Alag 
     */
    public void addVertex(Object vertex)
    {
        if(isVertex(vertex) == true)                       //check if the input vertex is already a Vertex of the graph
        {
            return;                                      //return with the graph unchanged
        }       
        else                                           //if the input vertex is not already a Vertex of the graph
        {
            list.insertBack(new Vertex (vertex));    //append the vertex to the back of the list of vertices
            vertexHash.insert(vertex, list.back()); //map the the vertex object to the internal representation the vertex
            numVertices++;                         //increment the count of the number of vertices by one
        }
    }

    /**
     * removeVertex() removes a vertex from the graph.  All edges incident on the
     * deleted vertex are removed as well.  If the parameter "vertex" does not
     * represent a vertex of the graph, the graph is unchanged.
     *
     * Running time:  O(d), where d is the degree of "vertex".
     * @Author Pratham Makni Alag 
     */
    public void removeVertex(Object vertex)
    {
        if (isVertex(vertex) == true)                       //check if the input vertex is a Vertex of the graph
        {
            try
            {
                Vertex remove = getVertex(vertex);          
                for (Object temp : (DList) remove.edges()) //traverse the list of edges connected to the input Vertex
                {
                    Edge curr = (Edge) temp;
                    removeEdge(((Object)((VertexPair) curr.getPair()).geto1()), ((Object)((VertexPair) curr.getPair()).geto2())); //remove the edge from the graph
                }
                numVertices--;                                     //decrease the number of vertices of the graph
                ((DListNode) vertexHash.remove(vertex)).remove(); //remove the reference of the vertex from the hashtable
            }
            catch (InvalidNodeException e)
            {
                System.out.println("Invalid Node Exception: " + e);
            }
        }
    }

    /**
     * isVertex() returns true if the parameter "vertex" represents a vertex of
     * the graph.
     *
     * Running time:  O(1).
     * @Author Pratham Makni Alag 
     */
    public boolean isVertex(Object vertex)
    {
        if (vertexHash.find(vertex) == null) //returns false if the vertex is not found in the hash table of vertices
        {
            return false;
        }
        else
        {
            return true;            //returns true if the vertex is found in the hash table of vertices
        }
    }

    /**
     * degree() returns the degree of a vertex.  Self-edges add only one to the
     * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
     * of the graph, zero is returned.
     *
     * Running time:  O(1).
     * @Author Eeshan Agarwal
     * 
     */
    public int degree(Object vertex)
    {
        if (!isVertex(vertex))      //if the vertex does not exist in the graph return 0
        {
            return 0;
        }
        else
        {
            try
            {
                return (int) ((DList) getVertex(vertex).edges()).length(); //return the length of the list of edges connected to the input-vertex
            }
            catch (InvalidNodeException e)
            {
                  System.out.println("Invalid Node Exception: " + e);
            }
        }
        return 0;
    }

    /**
     * getNeighbors() returns a new Neighbors object referencing two arrays.  The
     * Neighbors.neighborList array contains each object that is connected to the
     * input object by an edge.  The Neighbors.weightList array contains the
     * weights of the corresponding edges.  The length of both arrays is equal to
     * the number of edges incident on the input vertex.  If the vertex has
     * degree zero, or if the parameter "vertex" does not represent a vertex of
     * the graph, null is returned (instead of a Neighbors object).
     *
     * The returned Neighbors object, and the two arrays, are both newly created.
     * No previously existing Neighbors object or array is changed.
     *
     * (NOTE:  In the neighborList array, do not return any internal data
     * structure you use to represent vertices!  Return only the same objects
     * that were provided by the calling application in calls to addVertex().)
     *
     * Running time:  O(d), where d is the degree of "vertex".
     * @Author Eeshan Agarwal
     */
    public Neighbors getNeighbors(Object vertex)
    {

        Neighbors neighbors = new Neighbors();  //intianlize a new Neigbors object

        try
        {
            if (isVertex(vertex) == false) //if the vertex is not a vertex of the graph return null
            {
                return null;
            }

            if (degree(vertex) == 0) //if the vertex has a degree of zero or has no edges connected to it return null
            {
                return null;
            }

            neighbors.neighborList = new Object [degree(vertex)]; //initialize the array of objects of size equal to the degree of the vertex
            neighbors.weightList = new int [degree(vertex)];

            Vertex rmr = getVertex(vertex);
            int ctr=0;
            
            for(Object current : (DList) rmr.edges())                              //traverse the edges of the this vertex
            {
                neighbors.weightList[ctr] = ((Edge) current).weight();           //append the weight of the edge to the array 
                Object u = ((VertexPair) ((Edge) current).getPair()).geto1();   //store the unordered pair of vertices that connect the edge
                Object v = ((VertexPair) ((Edge) current).getPair()).geto2();
                neighbors.neighborList[ctr] = u.equals(vertex) ? v:u ;        //append the vertex that is not equal to the input-vertex to the array of objects 
                ctr++;                                                       //increment the countrer
            }
        }

        catch (InvalidNodeException e)
        {
             System.out.println("Invalid Node Exception: " + e);
        }

        return neighbors; //return the neighbors object
    }
    
    /**
     * getVertex() is a private 'helper' method of WUGraph.java that takes an Object as its parameter
     * and looks for this in the hash table that maps it to references to the internal representation of a vertex 
     * and returns the internal represenation of the Vertex.
     * 
     * Running time: O(1).
     * @Author Eeshan Agarwal
     */
    private Vertex getVertex(Object vertex) throws InvalidNodeException {
        return ((Vertex) ((DListNode) vertexHash.find(vertex)).item());
    }

    /**
     * getEdge() is a private 'helper' method of WUGraph.java that takes an Object as its parameter
     * and looks for this in the hash table that maps it to references to the internal representation of an edge 
     * and returns the internal represenation of the Edge.
     * 
     * Running time: O(1).
     * @Author Eeshan Agarwal
     */
    private Edge getEdge(Object edge) throws InvalidNodeException {
        return ((Edge) ((DListNode) edgeHash.find(edge)).item());
    }

    /**
     * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
     * u and v does not represent a vertex of the graph, the graph is unchanged.
     * The edge is assigned a weight of "weight".  If the graph already contains
     * edge (u, v), the weight is updated to reflect the new value.  Self-edges
     * (where u == v) are allowed.
     *
     * Running time:  O(1).
     * @Author Eeshan Agarwal
     */
    public void addEdge(Object u, Object v, int weight)
    {
        VertexPair connect = new VertexPair(u, v);  //create a new VertexPair containing the unodered pair of objects (vertices) (u,v)

        Edge fEdge = new Edge (connect, weight);  //first 'half' edge
        Edge sEdge = new Edge (connect, weight); //second 'half' edge (or 'partner' edge)
        try
        {
            if (isEdge(u, v) == true)
            {                                              //incase edge exits
                getEdge(connect).updateWeight(weight);    //update the weight of the existing edge 
            }
            else
            {                                             //if edge not existent
                numEdges++;                              //increment the count of the edges by one
                getVertex(u).edges().insertBack(fEdge); //insert the edge to the back of the list of edges connected to the vertex 'u'

                if (u.equals(v) == false)            //if the edge is not a self edge
                {
                    getVertex(v).edges().insertBack(sEdge);                   //insert the other 'half' edge to the back of the list of edges connected to the vertex 'v'
                    fEdge.setHEdge((DListNode)getVertex(v).edges().back());  //set Edge fEdge's otherEdge reference to the Edge 'sEdge'
                    sEdge.setHEdge((DListNode)getVertex(u).edges().back()); //set Edge sEdge's otherEdge reference to the Edge 'fEdge'
                }

                edgeHash.insert(connect, getVertex(u).edges().back()); //insert the VertexPair into the hashtable of edges and map into the internal represenation of the edge
            }
        } 
        catch (InvalidNodeException e)
        {
             System.out.println("Invalid Node Exception: " + e);
        }
    }

    /**
     * removeEdge() removes an edge (u, v) from the graph.  If either of the
     * parameters u and v does not represent a vertex of the graph, the graph
     * is unchanged.  If (u, v) is not an edge of the graph, the graph is
     * unchanged.
     *
     * Running time:  O(1).
     * @Author Eeshan Agarwal
     */
    public void removeEdge(Object u, Object v)
    {
        if (isEdge(u, v) == false) //if the input unodered pair is not an edge of the graph return with the graph unchanged
        {
            return;
        }
        
        VertexPair connect = new VertexPair(u, v); //create a new VertexPair containing the unodered pair of objects (vertices) (u,v)
        try
        {
            if (u.equals(v) == true)           //if the edge is a self edge
            {
                ((DListNode) edgeHash.remove(connect)).remove(); //remove the self edge from the hash table of edges
            }
            else
            {
                DListNode fEdge =  ((DListNode) edgeHash.remove(connect)); //remove the first 'half' edge from the hastable and store it's reference
                ((DListNode)((Edge)fEdge.item()).otherEdge()).remove();   //traverse the reference to find the corresponding 
                                                                         //second 'half' edge or 'partner' edge and remove it from Dlist of edges of the vertex 'v'
                fEdge.remove();                                         //remove the first 'half' edge from Dlist of edges of the vertex 'u'
            }
            numEdges--;                                               //decrease the count of edges by one
        }
        catch (InvalidNodeException e)
        {
            System.out.println("Invalid Node Exception: " + e);
        }
    }

    /**
     * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
     * if (u, v) is not an edge (including the case where either of the
     * parameters u and v does not represent a vertex of the graph).
     *
     * Running time:  O(1).
     * @Author Eeshan Agarwal
     */
    public boolean isEdge(Object u, Object v)
    {
        if (isVertex(u) == false || (isVertex(v) == false))  //check if 'u' and 'v' are vertices of the rgaph
        {
            return false;                                  //return false if either of them are not vertcies
        }
        VertexPair check = new VertexPair(u, v);         //create a new VertexPair containing the unodered pair of objects (vertices) (u,v)
        if (edgeHash.find(check) == null)               //check the hash table of edges of the vertex pair and return false if it is not found
        {
            return false;
        }
        return true;                                //return true if (u,v) represent an edge
    }

    /**
     * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
     * an edge (including the case where either of the parameters u and v does
     * not represent a vertex of the graph).
     *
     * (NOTE:  A well-behaved application should try to avoid calling this
     * method for an edge that is not in the graph, and should certainly not
     * treat the result as if it actually represents an edge with weight zero.
     * However, some sort of default response is necessary for missing edges,
     * so we return zero.  An exception would be more appropriate, but also more
     * annoying.)
     *
     * Running time:  O(1).
     * @Author Eeshan Agarwal
     */
    public int weight(Object u, Object v)                   
    {
        if (isEdge(u, v))                                      //check  if (u,v) represent an edge
        {
            VertexPair connect = new VertexPair(u, v);       //create a new VertexPair containing the unodered pair of objects (vertices) (u,v)
            try  
            {
                return (int) (getEdge(connect)).weight(); //return the weight of the corresponding edge (u,v)
            }
            catch (InvalidNodeException e)
            {
                 System.out.println("Invalid Node Exception: "+ e);
            }
        }
        return 0;                                    //if (u,v) do not represent an edge return a weight of 0
    }
}
