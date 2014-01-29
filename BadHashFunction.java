
package assignHashQuadChain;

/**
 * A bad hash function.
 */
public class BadHashFunction implements HashFunctor{

	@Override
	public int hash(String item) {
		return item.length();
	}

}
