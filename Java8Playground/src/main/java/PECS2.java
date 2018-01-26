import java.util.ArrayList;
import java.util.List;

public class PECS2 {
	
	public static void main(String[] args) {
		List<Number> numbers = new ArrayList<>();
		Integer integer = new Integer(3);
		numbers.add(integer);
		doSomethingWithNumber((Object)integer);
		numbers.add(3.0);
		printPRODUCER(numbers);
		//printCONSUMER(numbers);
	}

	private static void printCONSUMER(List<? super Number> numbers) {
		numbers.stream().forEach(PECS2::doSomethingWithNumber);
	}

	private static void printPRODUCER(List<? extends Number> numbers) {
		numbers.stream().forEach(n -> doSomethingWithNumber(n));
	}
	
	private static void doSomethingWithNumber(Object o) {
		System.out.println("Object:" + o);
	}
	
	private static void doSomethingWithNumber(Integer o) {
		System.out.println("Integer:" + o);
	}

}
