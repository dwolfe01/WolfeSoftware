import java.util.function.Predicate;

public class LambdasAndClosures {

	public static void main(String[] args) {
		LambdasAndClosures lac = new LambdasAndClosures();
		Predicate<Integer> isEvenNumber = number -> number % 2 == 0;
		System.out.println(lac.testMyNumber(isEvenNumber, new Integer(4)));
		System.out.println(lac.testMyNumber(number -> number % 2 == 0, new Integer(4)));
	}

	public boolean testMyNumber(Predicate<Integer> predicate, Integer object) {
		return predicate.test(object);
	}

}
