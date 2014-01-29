// Written for CS2420 assignment 10 by Chad Miller 

package assignHashQuadChain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import assignBSTSpellCheck.BinarySearchTree;

@SuppressWarnings("unused")
public class HashTableAnalysis {

	public static void main(String[] args) {
		//timeHashTableAdd();
		//countCollisionHashTableAdd();
		timeHashTableContains();
		//countCollisionHashTableContains();
	}


	private static void timeHashTableAdd() {
		//timeQuadProbeHashTableAdd(new BadHashFunction());
		//timeQuadProbeHashTableAdd(new MediocreHashFunction());
		timeQuadProbeHashTableAdd(new GoodHashFunction());
		//timeChainingHashTableAdd(new BadHashFunction());
		//timeChainingHashTableAdd(new MediocreHashFunction());
		timeChainingHashTableAdd(new GoodHashFunction());
	}


	/**
	 * Times the performance of add method for QuadProbeHashTable with a given hash function.
	 */
	private static void timeQuadProbeHashTableAdd(HashFunctor hash) {
		System.out.println("\n" + "QB Table " + hash.getClass() + "\n");
		// Experiment timesToLoop times and use the average running time
		int timesToLoop = 300;

		// For each problem size n . . .
		for (int n = 1000; n <= 10000; n += 1000) {

			long startTime, midpointTime, stopTime;

			startTime = System.nanoTime();

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			while (System.nanoTime() - startTime < 1000000000) { 
				// empty block 
			}
			// Now, run the test. 
			startTime = System.nanoTime();

			for (int i=0;i<(timesToLoop);i++) {
				// adds a random collection to the hash set
				QuadProbeHashTable set = new QuadProbeHashTable(89, hash);
				set.addAll(generateRandomCollection(n, timesToLoop));
			}

			midpointTime = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) { 
				QuadProbeHashTable set = new QuadProbeHashTable(89, hash);
				generateRandomCollection(n, timesToLoop);
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop and generating
			// random collection from the cost of running the loop, adding random collection
			// Average it over the number of runs.
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
					/ timesToLoop;
			System.out.println(n + "\t" + averageTime);	
		}
	}
	

	/**
	 * Times the performance of add method for ChainingHashTable with a given hash function.
	 */
	private static void timeChainingHashTableAdd(HashFunctor hash) {
		System.out.println("\n" + "C Table " + hash.getClass() + "\n");
		// Experiment timesToLoop times and use the average running time
		int timesToLoop = 300;

		// For each problem size n . . .
		for (int n = 1000; n <= 10000; n += 1000) {

			long startTime, midpointTime, stopTime;

			startTime = System.nanoTime();

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			while (System.nanoTime() - startTime < 1000000000) { 
				// empty block 
			}
			// Now, run the test. 
			startTime = System.nanoTime();

			for (int i=0;i<(timesToLoop);i++) {
				// adds a random collection to the hash set
				ChainingHashTable set = new ChainingHashTable(n, hash);
				set.addAll(generateRandomCollection(n, timesToLoop));
			}

			midpointTime = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) { 
				ChainingHashTable set = new ChainingHashTable(89, hash);
				generateRandomCollection(n, timesToLoop);
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop and generating
			// random collection from the cost of running the loop, adding random collection
			// Average it over the number of runs.
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
					/ timesToLoop;
			System.out.println(n + "\t" + averageTime);	
		}
	}

	
	private static void countCollisionHashTableAdd() {
		//countCollisionQuadProbeHashTableAdd(new BadHashFunction());
		//countCollisionQuadProbeHashTableAdd(new MediocreHashFunction());
		countCollisionQuadProbeHashTableAdd(new GoodHashFunction());
		//countCollisionChainingHashTableAdd(new BadHashFunction());
		//countCollisionChainingHashTableAdd(new MediocreHashFunction());
		countCollisionChainingHashTableAdd(new GoodHashFunction());
	}


	/**
	 * Counts the number of collisions of add method for 
	 * QuadProbeHashTable with a given hash function.
	 */
	private static void countCollisionQuadProbeHashTableAdd(HashFunctor hash) {
		System.out.println("\n" + "QB Table " + hash.getClass() + "\n");
		// Experiment timesToLoop times and use the average number of collisions
		int timesToLoop = 10;

		// For each problem size n . . .
		for (int n = 1000; n <= 10000; n += 1000) {
			int total = 0;

			for (int i=0;i<(timesToLoop);i++) {
				// adds a random collection to the hash set
				QuadProbeHashTable set = new QuadProbeHashTable(89, hash);
				set.setCountCollision(true);
				Collection<String> c = generateRandomCollection(n, timesToLoop); 
				for (String str : c) {
					set.add(str);
					total += set.getCollisions();	// updates number of collisions
				}
			}
			// Compute average number of collisions for the add method
			double averageCollision = ( (double) total / timesToLoop ) / n;
			System.out.println(n + "\t" + averageCollision);	
		}
	}
	

	/**
	 * Counts the number of collisions of add method for 
	 * ChainingHashTable with a given hash function.
	 */
	private static void countCollisionChainingHashTableAdd(HashFunctor hash) {
		System.out.println("\n" + "C Table " + hash.getClass() + "\n");
		// Experiment timesToLoop times and use the average number of collisions
		int timesToLoop = 10;

		// For each problem size n . . .
		for (int n = 1000; n <= 10000; n += 1000) {
			int total = 0;

			for (int i=0;i<(timesToLoop);i++) {
				// adds a random collection to the hash set
				ChainingHashTable set = new ChainingHashTable(n, hash);
				set.setCountCollision(true);
				Collection<String> c = generateRandomCollection(n, timesToLoop); 
				for (String str : c) {
					set.add(str);
					total += set.getCollisions();	// updates number of collisions
				}
			}
			// Compute average number of collisions for the add method
			double averageCollision = ( (double) total / timesToLoop ) / n;
			System.out.println(n + "\t" + averageCollision);	
		}
	}

	
	private static void timeHashTableContains() {
		//timeQuadProbeHashTableContains(new BadHashFunction());
		//timeQuadProbeHashTableContains(new MediocreHashFunction());
		timeQuadProbeHashTableContains(new GoodHashFunction());
		//timeChainingHashTableContains(new BadHashFunction());
		//timeChainingHashTableContains(new MediocreHashFunction());
		timeChainingHashTableContains(new GoodHashFunction());
	}
	
	
	/**
	 * Times the performance of contains method for QuadProbeHashTable with a given hash function.
	 */
	private static void timeQuadProbeHashTableContains(HashFunctor hash) {
		System.out.println("\n" + "QB Table " + hash.getClass() + "\n");
		// Experiment timesToLoop times and use the average running time
		int timesToLoop = 10;

		// For each problem size n . . .
		for (int n = 1000; n <= 10000; n += 1000) {

			long startTime, midpointTime, stopTime;

			startTime = System.nanoTime();

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			while (System.nanoTime() - startTime < 1000000000) { 
				// empty block 
			}
			// Now, run the test. 
			startTime = System.nanoTime();

			for (int i=0;i<(timesToLoop);i++) {
				// adds a random collection to the hash set
				Collection<String> c = generateRandomCollection(n, timesToLoop);
				QuadProbeHashTable set = new QuadProbeHashTable(89, hash);
				set.addAll(c);
				// calls contains method for each item in the collection
				for(String k : c)
					set.contains(k);
			}
			
			midpointTime = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) { 
				// adds a random collection to the hash set
				Collection<String> c = generateRandomCollection(n, timesToLoop);
				QuadProbeHashTable set = new QuadProbeHashTable(89, hash);
				set.addAll(c);
				// calls contains method for each item in the collection
				for(String k : c)
					;
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop, generating
			// random collection, and adding the collection from the cost of running the loop, 
			// generating random collection, adding the collection, and calling contains method
			// Average it over the number of runs.
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
					/ timesToLoop;
			System.out.println(n + "\t" + averageTime);	
		}
	}
	
	
	/**
	 * Times the performance of contains method for ChainingHashTable with a given hash function.
	 */
	private static void timeChainingHashTableContains(HashFunctor hash) {
		System.out.println("\n" + "C Table " + hash.getClass() + "\n");
		// Experiment timesToLoop times and use the average running time
		int timesToLoop = 10;

		// For each problem size n . . .
		for (int n = 1000; n <= 10000; n += 1000) {

			long startTime, midpointTime, stopTime;

			startTime = System.nanoTime();

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.
			while (System.nanoTime() - startTime < 1000000000) { 
				// empty block 
			}
			// Now, run the test. 
			startTime = System.nanoTime();

			for (int i=0;i<(timesToLoop);i++) {
				// adds a random collection to the hash set
				Collection<String> c = generateRandomCollection(n, timesToLoop);
				ChainingHashTable set = new ChainingHashTable(n, hash);
				set.addAll(c);
				// calls contains method for each item in the collection
				for(String k : c)
					set.contains(k);
			}
       
			
			midpointTime = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) { 
				// adds a random collection to the hash set
				Collection<String> c = generateRandomCollection(n, timesToLoop);
				ChainingHashTable set = new ChainingHashTable(n, hash);
				set.addAll(c);
				// calls contains method for each item in the collection
				for(String k : c)
					;
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop, generating
			// random collection, and adding the collection from the cost of running the loop, 
			// generating random collection, adding the collection, and calling contains method
			// Average it over the number of runs.
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
					/ timesToLoop;
			System.out.println(n + "\t" + averageTime);	
		}
	}
	
	
	
	private static void countCollisionHashTableContains() {
		countCollisionQuadProbeHashTableContains(new BadHashFunction());
		countCollisionQuadProbeHashTableContains(new MediocreHashFunction());
		countCollisionQuadProbeHashTableContains(new GoodHashFunction());
		countCollisionChainingHashTableContains(new BadHashFunction());
		countCollisionChainingHashTableContains(new MediocreHashFunction());
		countCollisionChainingHashTableContains(new GoodHashFunction());
	}


	/**
	 * Counts the number of collisions of contains method for 
	 * QuadProbeHashTable with a given hash function.
	 */
	private static void countCollisionQuadProbeHashTableContains(HashFunctor hash) {
		System.out.println("\n" + "QB Table " + hash.getClass() + "\n");
		// Experiment timesToLoop times and use the average number of collisions
		int timesToLoop = 10;

		// For each problem size n . . .
		for (int n = 1000; n <= 10000; n += 1000) {
			int total = 0;

			for (int i=0;i<(timesToLoop);i++) {
				// adds a random collection to the hash set
				Collection<String> c = generateRandomCollection(n, timesToLoop);
				QuadProbeHashTable set = new QuadProbeHashTable(89, hash);
				set.setCountCollision(true);
				set.addAll(c);
				// calls contains method for each item in the collection
				for(String k : c) {
					set.contains(k);
					total += set.getCollisions();
				}
			}
			// Compute average number of collisions for the contains method
			double averageCollision = ( (double) total / timesToLoop ) / n;
			System.out.println(n + "\t" + averageCollision);	
		}
	}
	

	/**
	 * Counts the number of collisions of contains method for 
	 * ChainingHashTable with a given hash function.
	 */
	private static void countCollisionChainingHashTableContains(HashFunctor hash) {
		System.out.println("\n" + "C Table " + hash.getClass() + "\n");
		// Experiment timesToLoop times and use the average number of collisions
		int timesToLoop = 10;

		// For each problem size n . . .
		for (int n = 1000; n <= 10000; n += 1000) {
			int total = 0;

			for (int i=0;i<(timesToLoop);i++) {
				// adds a random collection to the hash set
				Collection<String> c = generateRandomCollection(n, timesToLoop);
				ChainingHashTable set = new ChainingHashTable(n, hash);
				set.setCountCollision(true);
				set.addAll(c);
				// calls contains method for each item in the collection
				for(String k : c) {
					set.contains(k);
					total += set.getCollisions();
				}
			}
			// Compute average number of collisions for the contains method
			double averageCollision = ( (double) total / timesToLoop ) / n;
			System.out.println(n + "\t" + averageCollision);	
		}
	}
	
	
	
	/**
	 * Generates a collection of random strings.
	 * @param size - the size of the generated collection, i.e. number of strings
	 * @param seed - the seed that controls the randomness of the collection
	 */
	public static Collection<String> generateRandomCollection(int size, int seed) {

		String[] arr = new String[size];
		Random rng = new Random(seed);
		
		for (int i=0; i<arr.length; i++) {
			int strLength = rng.nextInt(50);
			char[] c = new char[strLength]; 
			for(int j = 0; j < strLength; j++){
				c[j] = (char) ('a'+rng.nextInt(26) );  	
			}
			arr[i] = c.toString();
		}
		return Arrays.asList(arr);
	}

}


