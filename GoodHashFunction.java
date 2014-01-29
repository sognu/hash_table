
package assignHashQuadChain;

/**
 * A good hash function.
 */
public class GoodHashFunction implements HashFunctor{

	@Override
	public int hash(String item) {
		int hashValue = 0;
		for(int i = 0; i < item.length(); i++)
			hashValue = hashValue*31 + item.charAt(i);
			
		return hashValue;
	}

}