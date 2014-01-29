package assignHashQuadChain;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A hash set data structure which uses separate chaining to resolve collision. 
 * The hash function for the hash set is provided by the user. 
 */
public class ChainingHashTable implements Set<String> {

	private HashEntry[] items;	// an array of entries -- each entry is a linked list
	private int size;			// number of elements contained in this hash set
	private HashFunctor hash;	// the hash function
	private boolean countCollision;	// indicator for whether counting collisions for methods
	private int collisionCounts;	// the total number of collisions for all operations

	
	/**
	 * An inner class that wrap a linked list. 
	 * (In Java we can not create an array of generic type.)
	 */
	class HashEntry {
		public LinkedList<String> entryChain;
		
		public HashEntry() {
			entryChain = new LinkedList<String>();
		}
	}


	/** 
	 * Constructs a hash table of the given capacity that uses the hashing function
	 * specified by the given functor.
	 */
	public ChainingHashTable(int capacity, HashFunctor functor) {
		items = new HashEntry[capacity];
		for (int i=0; i<capacity; i++) 
			items[i] = new HashEntry();
		size = 0;
		hash = functor;
		countCollision = false;
		collisionCounts = 0;
	}


	/**
	 * Computes the hash code of the given item using the hash function associated with this 
	 * hash set.
	 */
	public int hashCode(String item) {
		int result = hash.hash(item)%items.length;
		
		if (result >= 0)
			return result;
		else	// wrap around to avoid negative hash code
			return result + items.length;
	}


	@Override
	public boolean add(String item) {
		if(contains(item))	// the given item is in this set
			return false;
		else {				// the given item is not in this set -- add it
			items[hashCode(item)].entryChain.addFirst(item);
			size++;
			return true;
		}
	}

	
	@Override
	public boolean addAll(Collection<? extends String> items) {
		// the status indicating whether this set is altered or not
		boolean flag = false;
		for (String s : items) {
			flag = add(s) || flag ;
		}
		return flag;
	}

	
	@Override
	public void clear() {
		int capacity = items.length;
		items = new HashEntry[capacity];
		for (int i=0; i<capacity; i++)
			items[i] = new HashEntry();
		size = 0;
	}
	

	@Override
	public boolean contains(String item) {
		// compute the hash code of the given item -- this is the position the item should be
		// located if there is no collision
		int pos = hashCode(item);
		// number of collisions in locating the given item
		int count = items[pos].entryChain.indexOf(item);
		
		if (count == -1) {	// the item is not in the set
			if (countCollision)	// we are counting collisions -- update collisionCounts
				collisionCounts = items[pos].entryChain.size();
			return false;
		}
		else {				// the item is not in the set
			if (countCollision)	// we are counting collisions -- update collisionCounts
				collisionCounts = count;
			return true;
		}
	}

	
	@Override
	public boolean containsAll(Collection<? extends String> items) {
		// the status indicating if this set contains all items in the given collection
		boolean flag = true;
		for(String s: items) {
			flag = contains(s) && flag;
		}
		return flag;
	}


	/**
	 * Ensures that this set does not contain the specified item.
	 * 
	 * @param item
	 *          - the item whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is,
	 *         if the input item was actually removed); otherwise, returns false
	 * @throws NullPointerException
	 *           if the item is null
	 */
	public boolean remove(String item) {
		if (items[hashCode(item)].entryChain.remove(item)) {	// the item is in the set
			size--;
			return true;
		}
		else 													// the item is not in the set
			return false;
	}


	/**
	 * Ensures that this set does not contain any of the items in the specified
	 * collection.
	 * 
	 * @param items
	 *          - the collection of items whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is,
	 *         if any item in the input collection was actually removed);
	 *         otherwise, returns false
	 * @throws NullPointerException
	 *           if any of the items is null
	 */
	public boolean removeAll(Collection<? extends String> items) {
		// the status indicating whether this set is altered or not
		boolean flag = false;
		for(String s: items) {
			// try to remove each item in the collection
			flag = remove(s) || flag;
		}
		return flag;
	}
	
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	
	@Override
	public int size() {
		return size;
	}


	/**
	 * Returns the load factor of this hash set.
	 */
	public double loadFactor() {
		return (double)size/items.length;
	}
	
	
	/**
	 * Sets the countCollision according to the given status.
	 */
	public void setCountCollision(boolean status) {
		countCollision = status;
	}
	
	
	/**
	 * Returns the total number of collisions for all operations involving searching
	 */
	public int getCollisions() {
		return collisionCounts;
	}
}
