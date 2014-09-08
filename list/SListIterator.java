package list;
import java.util.*;

/**
 *  A SListIterator implents the standar Java Iterator interface for tranversing a SList.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public class SListIterator implements Iterator {
    SListNode n;
    
    /**
     * SListIterator constructor sets the SListNode to the first node of the list
     * @Param SList 
     */
    public SListIterator(SList l) {
        n = l.head;
    }
    
    /**
     * HasNext() returns true if the Iterator has more items to return, or false if it has already returned every
     * item in the sequence. 
     * 
     * @return true or false depending on if the Iterator has more items to return
     */
    public boolean hasNext() {
        return n != null;
    }
    
    /**
     * next() methods returns the first item in the sequence and then each subsequent time next() is called,
     * it returns the next item in the sequence. 
     * 
     * @return Object the next item in the sequenc
     */
    public Object next() 
    {
        if (n == null) 
        {
            throw new NoSuchElementException();
        }
        Object i = n.item;
        n = n.next;
        return i;
    }
    
    /**
     * remove() methods removes the item from the list
     */
    public void remove() {
        try {
            n.remove();
        } 
        catch (InvalidNodeException e) 
        {
            throw new NoSuchElementException();
        }
    }
}