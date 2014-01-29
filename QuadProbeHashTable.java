// Written for CS2420 assignment 10 by Chad Miller 

package assignHashQuadChain;

import java.util.Collection;

/**
 * A hash set data structure which uses quadratic probing to resolve collision. 
 * The hash function for the hash set is provided by the user. 
 */
public class QuadProbeHashTable implements Set<String>{

	private HashEntry[] items;	// an array of entries -- internal representation of the hash set
	private int size;			// number of entries contained in this hash set
	private HashFunctor hash;	// the hash function
	private boolean countCollision;	// indicator for whether counting collisions for methods
	private int collisionCounts;	// the total number of collisions for all operations 


	/**
	 * An inner class which represents an entry of the hash set.
	 * It wraps the value of the entry and its status. 
	 * Since we delete an entry by marking it inactive, we use a status field
	 * to denote whether the entry is deleted or not.
	 */
	class HashEntry {
		public String item;			// the value of the entry
		public boolean isActive;	// the status of the entry  
									// -- false for deleted; true for not deleted

		/**
		 * Constructs a new entry with the given value of string.
		 */
		public HashEntry(String s) {
			this(s, true);
		}

		/**
		 * Constructs a new entry with the given value of string and status.
		 */
		public HashEntry(String i, boolean active){
			item = i;
			isActive = active;
		}
	}
	

	/** 
	 * Constructs a hash table of the given capacity that uses the hashing function
	 * specified by the given functor.
	 */
	public QuadProbeHashTable(int capacity, HashFunctor functor) {
		items = new HashEntry[capacity];
		size = 0;
		hash = functor;
		countCollision = false;
		collisionCounts = 0;
	}
	

	@Override
	public boolean add(String item) {
		// if load factor (= number of entries / internal array size) is bigger than 0.5
		// then expand the internal array and rehash
		if (loadFactor() > 0.5) rehash();
		
		// find the position where the given item should be inserted or the given item located 
		int pos = finds(item);
		if(items[pos] == null) {		// item is never added to the set -- add it
			items[pos] = new HashEntry(item);
			size++;
			return true;
		}
		else if(items[pos].isActive)	// item is presently in the set
			return false;
		else {							// item is not in the set presently -- add it 
			items[pos].isActive = true;
			size++;
			return true;
		}
	}

	
	/**
	 * Finds the position of the given item in the internal array of the hash set or the position
	 * the given item should be inserted. 
	 * The entry at the position returned has three possibilities: 
	 * the entry value is item and the status is active which means 
	 * the given item is presently in the set; 
	 * the entry value is item and the status is inactive which means 
	 * the given item is presently not in the set but was in the set before;
	 * null if the given item is presently not in the set and was never added to the set 
	 * @param item - the given item to be searched
	 * @throws NullPointerException if the item is null
	 */
	private int finds(String item) {
		// compute the hash code of the given item -- this is the position the item should be
		// located if there is no collision and the item was added to the set before
		int pos = hashCode(item);
		// count stores the number of collisions for finding the given item
		int count = 0;
		// search for the given item until an empty (null) entry is encountered or it is found
		// (in the sense that it is presently in the set or was in the set before)
		while(items[pos]!=null && !items[pos].item.equals(item)) {
			count++;
			pos += 2*count-1;		// quadratic probing
			pos %= items.length;
		}
		if (countCollision)			// we are counting collisions -- update collisionCounts
			collisionCounts = count;	
		return pos;
	}


	/**
	 * Computes the hash code of the given item using the hash function associated with this 
	 * hash set.
	 */
	private int hashCode(String item) {
		int result = hash.hash(item)%items.length;

		if (result >= 0)
			return result;
		else	// wrap around to avoid negative hash code
			return result + items.length;
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
		size = 0;
	}

	
	@Override
	public boolean contains(String item) {
		// compute the hash code of the given item -- this is the position the item should be
		// located if there is no collision and the item was added to the set before
		int pos = finds(item);
		if(items[pos] == null)	// the given item is never added to the set
			return false;
		else 					// the given item was added before; whether it is presently  
								// presently in the set depends on whether it is active 
			return items[pos].isActive;
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
		// compute the hash code of the given item -- this is the position the item should be
		// located if there is no collision and the item was added to the set before
		int pos = finds(item);
		if(items[pos] == null) {		// the item is never added to the set
			return false;
		}
		else if(items[pos].isActive) {	// the item is presently in the set -- remove it
			items[pos].isActive = false;
			size--;
			return true;
		}
		else {							// the item is not in the set presently 
			return false;
		}
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
		return size==0;
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
	
	
	/**
	 * Expands this hash table and rehashes the entries.
	 */
	private void rehash() {
		HashEntry[] temp = items; 
		items = new HashEntry[nextPrime(2*items.length)];
		size = 0;
		
		for (int i=0; i<temp.length; i++) 
			if (temp[i] != null && temp[i].isActive)
				add(temp[i].item);
	}
	
	
	/**
	 * Finds the smallest prime number that is larger than n.
	 */
	private static int nextPrime (int n) {
		if (n%2 == 0) n++;
		for (; !isPrime(n); n+=2);
		return n;
	}

	
	/**
	 * Checks if the given number is a prime number.
	 */
	private static boolean isPrime(int n) {
		if (n<=1) return false;
		for (int i=2; i<Math.sqrt(n); i++)
			if (n%i==0) return false;		
		return true;
	}
}
