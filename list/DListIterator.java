package list;
import java.util.*;

/**
 *  A DListIterator implents the standar Java Iterator interface for tranversing a DList.
 *
 *  DO NOT CHANGE THIS FILE.
 **/
class DListIterator implements Iterator 
{
    DList list;
    DListNode temp;
    DListNode next;
    /**
     * DListIterator constructor sets the next node to the first node of the list
     * @Param List 
     */
    DListIterator (DList l)
    {
        list = l;
        next = (DListNode) list.front();
    }

    /**
     * HasNext() returns true if the Iterator has more items to return, or false if it has already returned every
     * item in the sequence. 
     * 
     * @return true or false depending on if the Iterator has more items to return
     */
    public boolean hasNext()
    {
        return next.isValidNode();
    }

    /**
     * next() methods returns the first item in the sequence and then each subsequent time next() is called,
     * it returns the next item in the sequence. 
     * 
     * @return Object the next item in the sequenc
     */
    public Object next() 
    {
        try 
        {
            temp = next;
            next = (DListNode) next.next();
            return temp.item();
        } 
        catch (InvalidNodeException e) 
        {
            throw new NoSuchElementException();
        }
    }

    /**
     * remove() methods removes the item from the list
     */
    public void remove() {
        try 
        {
            temp.remove();
        } 
        catch (InvalidNodeException e) 
        {
            throw new NoSuchElementException();
        }
    }
}