package graphalg;

/* KruskalEdge.java */

/**
 *  The KruskalEdge class internalises the representation of an edge in a
 *  graph for the pruposes of implmeneting the Kruskal algorithm for find-
 *  ing the minimum spanning tree.
 *  @author Jvalin Tejpal
 */

public class KruskalEdge implements Comparable {
    private Object v1, v2;     // Adjascent Vertices of edge (v1, v2)
    private int weight;        // Weight of edge

    /**
     *  Constructor of an edge with two vertices and weight of the edge
     *
     *  @param v1 is one vertex of the edge.
     *  @param v2 is another vertex of the edge.
     *  @param weight is the weight of the edge.
     **/
    public KruskalEdge(Object v1, Object v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    /**
     *  A comparison method that overrides the default method by returning
     *  1 if weight of 'this' edge is greater than weight of the edge being
     *  compared to. -1 if it is lesser and 0 if it is the same.
     *
     *  @param e is an Object intended to be a KruskalEdge
     **/
    // @SuppressWarnings("unchecked") Doesn't work unless e is made a generic object.
    public int compareTo(Object e) 
    {
        if (this.weight > ((KruskalEdge) e).weight) {
            return 1;
        } else if (this.weight == ((KruskalEdge) e).weight) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     *  Getter method that returns one vertex of a particular edge.
     *  @Return Object
     **/
    public Object getV1() {
        return this.v1;
    }

    /**
     *  Getter method that returns the other vertex of a particular edge.
     *  @Return Object 
     **/
    public Object getV2() {
        return this.v2;
    }

    /**
     *  Getter method that returns the weight of a particular edge.
     *  @Return int weight
     **/
    public int getWeight() {
        return this.weight;
    }
}
