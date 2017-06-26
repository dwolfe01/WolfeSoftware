import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class QuickSortREVAMP {

	private static final int howManyNumbers = 2000000;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		List<Integer> numbers = generateListOfRandomNumbers(howManyNumbers);
		System.out.println("Created " + howManyNumbers + " Random Numbers: "
				+ (System.currentTimeMillis() - startTime) + " milliseconds");
		startTime = System.currentTimeMillis();
		QuickSortREVAMP.quickSort(numbers);
		System.out.println("Completed: "
				+ (System.currentTimeMillis() - startTime) + " milliseconds");
	}

	public static List<Integer> quickSort(List<Integer> numbers) {
		if (numbers.size() <= 1)// exit if the size is 1 or empty because a list
			// of size
			// 1 is in sorted order
			return numbers;
		Integer myPivot = numbers.remove(0);
		List<Integer> sortedList = new ArrayList<Integer>();
		List<Integer> lessThanPivot = numbers.stream().filter(i -> i < myPivot).collect(Collectors.toList());
		List<Integer> moreThanPivot = numbers.stream().filter(i -> i >= myPivot).collect(Collectors.toList());
		sortedList.addAll(quickSort(lessThanPivot));
		sortedList.add(myPivot);
		sortedList.addAll(quickSort(moreThanPivot));
		return sortedList;
	}
	
	private static List<Integer> generateListOfRandomNumbers(int howManyRandoms) {
		List<Integer> numbers = new ArrayList<Integer>();
		Random random = new Random();
		for (int x = 0; x < howManyRandoms; x++) {
			numbers.add(random.nextInt(howManyRandoms));
		}
		return numbers;
	}
	
}
