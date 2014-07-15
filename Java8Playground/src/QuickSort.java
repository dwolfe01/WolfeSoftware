import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class QuickSort {

	private static final int howManyNumbers = 2000000;

	public static void main(String[] args) {
		// if (args.length != 1) {
		// System.out.println("Please enter exactly one argument");
		// }
		long startTime = System.currentTimeMillis();
		List<Integer> numbers = generateListOfRandomNumbers(howManyNumbers);
		System.out.println("Created " + howManyNumbers + " Random Numbers: "
				+ (System.currentTimeMillis() - startTime) + " milliseconds");
		startTime = System.currentTimeMillis();
		QuickSort.quickSort(numbers);// note the toString
		// method does not
		// pring out the
		// memory address?!
		System.out.println("Completed: "
				+ (System.currentTimeMillis() - startTime) + " milliseconds");
	}

	private static List<Integer> generateListOfRandomNumbers(int howManyRandoms) {
		List<Integer> numbers = new ArrayList<Integer>();
		Random random = new Random();
		for (int x = 0; x < howManyRandoms; x++) {
			numbers.add(random.nextInt(howManyRandoms));
		}
		return numbers;
	}

	public static List<Integer> quickSort(List<Integer> numbers) {
		if (numbers.size() <= 1)// exit if the size is 1 or empty because a list
			// of size
			// 1 is in sorted order
			return numbers;
		// choose a pivot this is a non intelligent way of choosing a pivot.
		Integer myPivot = numbers.remove(0);
		List<Integer> sortedList = new ArrayList<Integer>();
		sortedList.addAll(quickSort(pivot(numbers, i -> i < myPivot)));
		sortedList.add(myPivot);
		sortedList.addAll(quickSort(pivot(numbers, i -> i >= myPivot)));
		return sortedList;
	}

	public static List<Integer> pivot(List<Integer> numbers,
			Predicate<Integer> predicate) {
		// return pivotNoJava8Streams(numbers, predicate);
		return numbers.stream().filter(predicate).collect(Collectors.toList());
	}

	public static List<Integer> pivotNoJava8Streams(List<Integer> numbers,
			Predicate<Integer> predicate) {
		List<Integer> pivotList = new ArrayList<Integer>();
		for (Integer i : numbers) {
			if (predicate.test(i))
				pivotList.add(i);
		}
		return pivotList;
	}
}
