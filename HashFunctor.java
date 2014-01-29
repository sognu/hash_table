package assignHashQuadChain;

/**
 * Serves as a guide for how to define a functor that contains a hashing
 * function for String items (i.e., the hash method).
 * 
 */
public interface HashFunctor {

	/**
	 * Computes the hash code of the given item
	 * @throws NullPointerException if the item is null
	 */
	public int hash(String item);

}