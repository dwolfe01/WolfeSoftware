import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class PrimeFinder {
	
	Consumer<BigInteger> divisorConsumer = l -> {};//do not consume the divisors by default
	
	public PrimeFinder() {}
	
	/* this will allow you to consume all the divisors that have been checked in the process of evaluating your prime candidate*/
	public PrimeFinder(Consumer<BigInteger> divisorConsumer) {
		this.divisorConsumer = divisorConsumer;
	};
	
	public boolean isPrimeNumberParallel(BigInteger primeCandidate) {
		if (checkKnownNonPrimes(primeCandidate)){
			return false;
		}
		if (primeCandidate.equals(BigInteger.TWO)) {
			return true;
		}
		if (primeCandidate.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
			return false;
		}
		BigInteger takeWhile = primeCandidate.divide(BigInteger.TWO);
		BigInteger halfOfTakeWhile = takeWhile.divide(BigInteger.TWO);
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<Boolean> doesDivide1 = executor.submit(() -> doAnyOfTheseDivisorsDivideThePrimeCandididate(new BigInteger("3"),halfOfTakeWhile, primeCandidate));
		Future<Boolean> doesDivide2 = executor.submit(() -> doAnyOfTheseDivisorsDivideThePrimeCandididate(halfOfTakeWhile, takeWhile, primeCandidate));
		try {
			return !doesDivide1.get() && !doesDivide2.get();
		} catch (InterruptedException|ExecutionException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	public boolean isPrimeNumber(BigInteger primeCandidate) {
		if (checkKnownNonPrimes(primeCandidate)){
			return false;
		}
		if (primeCandidate.equals(BigInteger.TWO)) {
			return true;
		}
		if (primeCandidate.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
			return false;
		}
		BigInteger lastPossibleDivisor = primeCandidate.divide(BigInteger.TWO);
		BigInteger divider = new BigInteger("3");
		while (divider.compareTo(lastPossibleDivisor)<=0){
			if (doesDivide(divider,primeCandidate)){
				return false;
			}
			divider = divider.add(BigInteger.TWO);
		}
		return true;
	}

	private boolean checkKnownNonPrimes(BigInteger primeCandidate) {
		return List.of(0,1).contains(primeCandidate.intValue());
	}
	
	private boolean doAnyOfTheseDivisorsDivideThePrimeCandididate(BigInteger start, BigInteger end, BigInteger primeCandidate) {
		if (start.compareTo(BigInteger.TWO)==-1) {
			start = new BigInteger("3");
		}
		if (start.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
			start = start.add(BigInteger.ONE);
		}
		while (start.compareTo(end)==-1) {
			if (doesDivide(start, primeCandidate)) {
				return true;
			}
			start = start.add(BigInteger.TWO);
		}
		return false;
	}

	private boolean doesDivide(BigInteger divisor, BigInteger dividend){
		divisorConsumer.accept(divisor);
		if (dividend.mod(divisor)==BigInteger.ZERO){
			return true;
		} else {
			return false;
		}
	}

}
