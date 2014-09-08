package graphalg;

/* QuickSort.java */

/**
 *  The QuickSort class implements the 'quick sort' sorting algorithm for
 *  sorting an array of KruskalEdges, which in itself is just an internal
 *  representation of edges of a graph for the Kruskal algorithm. It utilises
 *  a slightly editted version of C.A.R Hoare's Quick Sort Algorithm. This
 *  particular implementation has been taken from Lab12 of CS 61B in Spring
 *  2014.
 *
 *  @author Kathy Yelick and Mark Allen Weiss
 *  @editor Jvalin Tejpal
 */

public final class QuickSort {

    public static void quicksort(KruskalEdge[] a) {
        quicksort(a, 0, a.length - 1);
    }

    /**
     *  Method to swap two KruskalEdge objects in an array.
     *  @param a an array of Kruskal Edges.
     *  @param index1 the index of the first int to be swapped.
     *  @param index2 the index of the second int to be swapped.
     **/
    public static void swapReferences(KruskalEdge[] a, int index1, int index2) {
        KruskalEdge tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }

    /**
     *  This is a slightly modified version of C.A.R Hoare's Quick Sort algorithm.
     *  This will handle arrays that are already sorted, and arrays with duplicate
     *  keys.
     *
     *  If you think of an array as going from the lowest index on the left to
     *  the highest index on the right then the parameters to this function are
     *  lowest index or left and highest index or right.  The first time you call
     *  this function it will be with the parameters 0, a.length - 1.
     *
     *  @param a       a KruskalEsge array
     *  @param lo0     left boundary of array partition
     *  @param hi0     right boundary of array partition
     **/
    private static void quicksort(KruskalEdge a[], int lo0, int hi0) {
        int lo = lo0;
        int hi = hi0;
        int mid;

        if (hi0 > lo0) {

            // Arbitrarily establishing partition element as the midpoint of
            // the array.
            swapReferences(a, lo0, (lo0 + hi0)/2);
            mid = a[(lo0 + hi0) / 2].getWeight();

            // loop through the array until indices cross.
            while (lo <= hi) {
                // find the first element that is greater than or equal to
                // the partition element starting from the left Index.
                while((lo < hi0) && (a[lo].getWeight() < mid)) {
                    lo++;
                }

                // find an element that is smaller than or equal to
                // the partition element starting from the right Index.
                while((hi > lo0) && (a[hi].getWeight() > mid)) {
                    hi--;
                }
                // if the indices have not crossed, swap them.
                if (lo <= hi) {
                    swapReferences(a, lo, hi);
                    lo++;
                    hi--;
                }
            }

            // If the right index has not reached the left side of array
            // we must now sort the left partition.
            if (lo0 < hi) {
                quicksort(a, lo0, hi);
            }

            // If the left index has not reached the right side of array
            // must now sort the right partition.
            if (lo < hi0) {
                quicksort(a, lo, hi0);
            }
        }
    }
}
