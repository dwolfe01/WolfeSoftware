import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class PrimeFinder {
	
	private BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
	Consumer<BigInteger> divisorConsumer = l -> {};//do not consume the divisors by default
	
	public PrimeFinder() {}
	
	/* this will allow you to consume all the divisors that have been checked in the process of evaluating your prime candidate*/
	public PrimeFinder(Consumer<BigInteger> divisorConsumer) {
		this.divisorConsumer = divisorConsumer;
	};
	
	public boolean isPrimeNumberParallel(BigInteger primeCandidate) {
		if (checkKnownPrimes(primeCandidate)){
			return false;
		}
		int takeWhile = primeCandidate.divide(TWO).intValue();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<Boolean> doesDivide1 = executor.submit(() -> doAnyOfTheseDivisorsDivideThePrimeCandididate(2,takeWhile/2, primeCandidate));
		Future<Boolean> doesDivide2 = executor.submit(() -> doAnyOfTheseDivisorsDivideThePrimeCandididate(takeWhile/2, takeWhile, primeCandidate));
		try {
			return !doesDivide1.get() && !doesDivide2.get();
		} catch (InterruptedException|ExecutionException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	public boolean isPrimeNumber(BigInteger primeCandidate) {
		if (checkKnownPrimes(primeCandidate)){
			return false;
		}
		BigInteger divider = TWO;
		BigInteger lastPossibleDivisor = primeCandidate.divide(divider);
		while (divider.compareTo(lastPossibleDivisor)<=0){
			if (doesDivide(divider,primeCandidate)){
				return false;
			}
			divider = divider.add(BigInteger.ONE);
		}
		return true;
	}

	private boolean checkKnownPrimes(BigInteger primeCandidate) {
		return List.of(0,1).contains(primeCandidate.intValue());
	}
	
	private boolean doAnyOfTheseDivisorsDivideThePrimeCandididate(int i, int j, BigInteger primeCandidate) {
		if (i<2) {
			i = 2;
		}
		for (int x=i; x<=j;x++) {
			if (doesDivide(new BigInteger(x+""), primeCandidate)) {
				return true;
			}
		}
		return false;
	}

	private boolean doesDivide(BigInteger divisor, BigInteger dividend){
		divisorConsumer.accept(divisor);
		BigInteger[] divideAndRemainder = dividend.divideAndRemainder(divisor);
		if (divideAndRemainder[1]==BigInteger.ZERO){
			return true;
		} else {
			return false;
		}
	}

}
