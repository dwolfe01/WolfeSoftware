import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class CalculateInterest {

	public static void main(String[] args) {
		double principal = 1000;
		double rate = 0.05;
		int numberOfYears = 10;
		System.out.printf("%s%20s%n", "Year", "Amount");
		for (int x = 1;x<=numberOfYears; x++){
			principal = principal * (1.0 + rate);
			System.out.printf("%4d%20.2f%n", x, principal);
		}
		System.out.printf("%s%20s%n", "Year", "Amount");
		double amount;
		principal = 1000;
		for (int x = 1;x<=numberOfYears; x++){
			double pow = Math.pow(1.0 + rate, x);
			amount = principal * pow;
			System.out.printf("%4d%20.2f%10.2f%n", x, amount, pow);
		}
		System.out.printf("%s%20s%n", "Year", "Amount");
		final double myPrincipal = principal;
		IntConsumer consumer = i -> System.out.printf("%4d%20.2f%n", i, myPrincipal * Math.pow(1.0 + rate, i));
		IntStream.rangeClosed(1, numberOfYears).forEach(consumer);
	}

}
