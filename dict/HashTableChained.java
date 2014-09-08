/* HashTableChained.java */

package dict;
import list.DList;
import list.ListNode;
import list.InvalidNodeException;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained <K,V> {

    protected DList[] table;
    protected int buckets, size;

    private boolean isPrime(int n) {
        boolean check = true;
        for (int i = 2; i < n / 2; i++) {
            if (n % i == 0) {
                check = false;
            }
        }
        return check;
    }

    /**
     *  Construct a new empty hash table intended to hold roughly sizeEstimate
     *  entries.  (The precise number of buckets is up to you, but we recommend
     *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
     **/

    public HashTableChained(int sizeEstimate) {
        buckets = sizeEstimate % 103;
        if (buckets < 0) {
            buckets+= sizeEstimate;
        }
        while (!(isPrime(buckets))) {
            buckets++;
        }
        table = new list.DList[sizeEstimate];
        size = 0;
    }

    /**
     *  Construct a new empty hash table with a default size.  Say, a prime in
     *  the neighborhood of 100.
     **/

    public HashTableChained() {
        table = new list.DList[101];
    }

    /**
     *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
     *  to a value in the range 0...(size of hash table) - 1.
     *
     *  This function should have package protection (so we can test it), and
     *  should be used by insert, find, and remove.
     **/

    int compFunction(int code) {
        return Math.abs(((101*code + 103) % 2147483647) % (table.length));
    }

    /**
     *  Returns the number of entries stored in the dictionary.  Entries with
     *  the same key (or even the same key and value) each still count as
     *  a separate entry.
     *  @return number of entries in the dictionary.
     **/

    public int size() {
        return size;
    }

    /**
     *  Tests if the dictionary is empty.
     *
     *  @return true if the dictionary has no entries; false otherwise.
     **/

    public boolean isEmpty() {
        return (size==0);
    }

    /**
     *  Create a new Entry object referencing the input key and associated value,
     *  and insert the entry into the dictionary.  Return a reference to the new
     *  entry.  Multiple entries with the same key (or even the same key and
     *  value) can coexist in the dictionary.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the key by which the entry can be retrieved.
     *  @param value an arbitrary object.
     *  @return an entry containing the key and value.
     **/

    public Entry <K,V> insert(K key, V value) {

        if(size/table.length >=1)
        {
            this.resize();
        }

        Entry<K,V> entryInsert = new Entry<K,V>();
        entryInsert.key = key;
        entryInsert.value = value;
        int indexInsert = compFunction(key.hashCode());
        if (table[indexInsert] == null) {
            list.DList newlist = new list.DList();
            newlist.insertFront(entryInsert);
            table[indexInsert] = newlist;
        } else {
            table[indexInsert].insertFront(entryInsert);
        }
        size++;
        return entryInsert;
    }

    /**
     *  Search for an entry with the specified key.  If such an entry is found,
     *  return it; otherwise return null.  If several entries have the specified
     *  key, choose one arbitrarily and return it.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the search key.
     *  @return an entry containing the key and an associated value, or null if
     *          no entry contains the specified key.
     **/

    public V find(K key) {
        int bucket = compFunction(key.hashCode());
        if(table[bucket]!=null)
        {
            ListNode node = table[bucket].front();
            while (node.isValidNode()) {
                try {
                    Entry <K,V> entry = (Entry) node.item();
                    if (entry != null && key.equals(entry.key)) {
                        return entry.value();
                    }
                    node = node.next();
                } catch (InvalidNodeException ex) {}
            }
        }
        return null;
    }

    /**
     *  Remove an entry with the specified key.  If such an entry is found,
     *  remove it from the table and return it; otherwise return null.
     *  If several entries have the specified key, choose one arbitrarily, then
     *  remove and return it.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the search key.
     *  @return an entry containing the key and an associated value, or null if
     *          no entry contains the specified key.
     */

    public V remove(K key) {
        int bucket = compFunction(key.hashCode());
        ListNode node = table[bucket].front();
        while (node.isValidNode()) {
            try {
                Entry<K,V> entry = (Entry) node.item();
                if (entry != null && key.equals(entry.key)) {
                    node.remove();
                    size--;
                    return entry.value();
                }
                node = node.next();
            } catch (InvalidNodeException ex) {}
        }
        return null;
    }

    /**
     *  Resises the hash table to keep the load factor constant.
     *  Note that running there are no restrictions on the running time
     *  of this method.
     */
    private void resize() {
        HashTableChained<K,V> change = new HashTableChained<K,V>(size*2);
        for (int i = 0; i < size; i++) {
            ListNode node = table[i].front();
            while (node.isValidNode()) {
                try {
                    Entry <K,V> entry = (Entry) node.item();
                    change.insert(entry.key, entry.value);
                    node = node.next();
                } catch (InvalidNodeException ex) {}
            }
        }
        this.table = change.table;
    }

    /**
     *  Remove all entries from the dictionary.
     */
    public void makeEmpty() {
        size = 0;
        table = new DList[table.length-1];
    }

}
