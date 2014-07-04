import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class QuickSort {

	public static void main(String[] args) {
		// if (args.length != 1) {
		// System.out.println("Please enter exactly one argument");
		// }
		List<Integer> numbers = new ArrayList<Integer>(Arrays.asList(1, 344, 4,
				3, 6, 2, 54, 67, 2, 4, 8, 90));
		System.out.println(QuickSort.quickSort(numbers));// note the toString
															// method does not
															// pring out the
															// memory address?!
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
		return numbers.stream().filter(predicate).collect(Collectors.toList());
	}
}
