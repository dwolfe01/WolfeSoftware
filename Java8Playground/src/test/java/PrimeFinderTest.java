import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigInteger;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class PrimeFinderTest {
	
	List<Integer> primes = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199);
	private BigInteger myBigPrime = new BigInteger("982451653");
	private BigInteger myBigPrime2 = new BigInteger("15485864");//NOT A PRIME
	private BigInteger myBigPrime3 = new BigInteger("15485");//NOT A PRIME
	private BigInteger myBigPrime4 = new BigInteger("67280421310721");
	PrimeFinder primeFinder;
	
	private volatile long numberOfComparisons = 0;
	
	private synchronized void incrementNumberOfComparisons() {
		numberOfComparisons++;
	}
	
	@Before
	public void init() {
		numberOfComparisons = 0;
	}
	
	@Test
	void shouldFindFirst100Primes() {
		primeFinder = new PrimeFinder();
		testPrimesWithThisMethod(p -> primeFinder.isPrimeNumber(new BigInteger(p+"")));
	}
	
	@Test
	void shouldFindFirst100PrimesParallel() {
		primeFinder = new PrimeFinder();
		testPrimesWithThisMethod(p -> primeFinder.isPrimeNumberParallel(new BigInteger(p+"")));
	}
	
	@Test
	void shouldFindSayIsPrime2() {
		primeFinder = new PrimeFinder();
		assertTrue(primeFinder.isPrimeNumberParallel(new BigInteger("2")));
	}

	@Test
	void shouldFindSayIsPrime4() {
		primeFinder = new PrimeFinder();
		assertFalse(primeFinder.isPrimeNumberParallel(new BigInteger("4")));
	}
	
	@Test
	void shouldFindSayIsPrime7() {
		primeFinder = new PrimeFinder();
		assertTrue(primeFinder.isPrimeNumberParallel(new BigInteger("7")));
	}
	
	@Test
	void shouldFindSayIsPrimeMyBigPrime() {
		primeFinder = new PrimeFinder(x -> this.incrementNumberOfComparisons());
		long startTime =  System.currentTimeMillis();
		assertTrue(primeFinder.isPrimeNumber(myBigPrime));
		System.out.println("Non Parallel Finished..." + (System.currentTimeMillis() - startTime) + " millseconds" + " numberOfComparisons:" + numberOfComparisons);
	}
	
	@Test
	void shouldFindSayIsPrimeMyBigPrimeParallel() {
		primeFinder = new PrimeFinder(x -> this.incrementNumberOfComparisons());
		long startTime =  System.currentTimeMillis();
		assertTrue(primeFinder.isPrimeNumberParallel(myBigPrime));
		System.out.println("Parallel Finished..." + (System.currentTimeMillis() - startTime) + " millseconds" + " numberOfComparisons:" + numberOfComparisons);
	}

	private void testPrimesWithThisMethod(IntPredicate primePredicate) {
		List<Integer> listOfPrimes = IntStream.rangeClosed(0, 200).filter(primePredicate).boxed().collect(Collectors.toList());
		boolean listsAreEqual = listOfPrimes.equals(primes);
		if (!listsAreEqual) {
			printReasonsForFailure(listOfPrimes);
			fail("fail");
		}
	}

	private void printReasonsForFailure(List<Integer> listOfPrimes) {
		//primes has prime numbers not in calculated list
		primes.stream().filter(p -> !listOfPrimes.contains(p)).forEach(p -> System.out.println("Algorithm missed this one: " + p));
		//calculated list has numbers that are not in primes
		listOfPrimes.stream().filter(p -> !primes.contains(p)).forEach(p -> System.out.println("Algorithm wrongly says this is a prime: " + p));
	}

}
