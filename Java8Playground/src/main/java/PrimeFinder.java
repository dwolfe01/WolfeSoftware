

import java.math.BigInteger;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimeFinder {
	
	private BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
	private BigInteger myBigPrime = new BigInteger("982451653");
	//private BigInteger myBigPrime = new BigInteger("15485864");//NOT A PRIME
//	private BigInteger myBigPrime = new BigInteger("67280421310721");

	public static void main(String[] args) {
		new PrimeFinder().go();

	}

	private void go() {
		long startTime =  System.currentTimeMillis();
		//IntStream.rangeClosed(0, 100).filter(i -> isPrimeNumber(new BigInteger(i+""))).forEach(i -> System.out.println(i));
//		System.out.println("SEQUENTIAL Is Prime: " + myBigPrime + " " + isPrimeNumber(myBigPrime));
//		System.out.println(System.currentTimeMillis() - startTime + " millseconds");
		startTime =  System.currentTimeMillis();
		System.out.println("PARALLEL Is Prime: " + myBigPrime + " " + isPrimeNumberParallel(myBigPrime));
		System.out.println(System.currentTimeMillis() - startTime + " millseconds");
	}
	
	private boolean isPrimeNumberParallel(BigInteger primeCandidate) {
		//get a stream of potential divisors
		int takeWhile = primeCandidate.divide(TWO).intValue();
		Stream.iterate(1L, n  ->  n  + 1).limit(491225826).collect(Collectors.toList());
//		List<BigInteger> collect = Stream.iterate(bi("2"), i -> i.add(BigInteger.ONE)).limit(takeWhile).collect(Collectors.toList());
		//List<Long> collect = Stream.iterate(1L, n  ->  n  + 1).limit(takeWhile).collect(Collectors.toList());
//		Stream<List<BigInteger>> myNumbers = Stream.of(collect);
		//myNumbers.parallel().forEach(System.out::println);
		//boolean isPrime = divisors.parallel().noneMatch(i -> doesDivide(i, primeCandidate));
		return false;
	}

	private boolean doesDivide(BigInteger divisor, BigInteger dividend){
		BigInteger[] divideAndRemainder = dividend.divideAndRemainder(divisor);
		if (divideAndRemainder[1]==BigInteger.ZERO){
			return true;
		} else {
			return false;
		}
	}

	private boolean isPrimeNumber(BigInteger primeCandidate) {
		BigInteger divider = TWO;
		BigInteger startPoint = primeCandidate.divide(divider);
		while (divider.compareTo(startPoint)<=0){
			BigInteger[] divideAndRemainder = primeCandidate.divideAndRemainder(divider);
			if (divideAndRemainder[1]==BigInteger.ZERO){
				return false;
			}
			divider = divider.add(BigInteger.ONE);
		}
		return true;
	}
	
	private BigInteger bi(String number){
		return new BigInteger(number);
	}
	
}
