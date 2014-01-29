// Written for CS2420 assignment 10 by Chad Miller and Liang Zhang

package assignHashQuadChain;

/**
 * A mediocre hash function.
 * @author Chad Miller & Liang Zhang
 */
public class MediocreHashFunction implements HashFunctor{

	@Override
	public int hash(String item) {
		int hashValue = 0;
		for(int i = 0; i < item.length(); i++)
			hashValue += item.charAt(i);
			
		return hashValue;
	}

}
