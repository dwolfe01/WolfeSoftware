package spliterator;

import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Takes a start date and iterates backwards one year at a time.
 */
public class YearSpliterator implements Spliterator<LocalDate> {
	private LocalDate date;
	private int sizeOfStream;

	public YearSpliterator(LocalDate startDate, int sizeOfStream) {
		this.date = startDate;
		this.sizeOfStream = sizeOfStream;
	}

	public long estimateSize() {
		return sizeOfStream;
	}

	public boolean tryAdvance(Consumer<? super LocalDate> action) {
		Objects.requireNonNull(action);
		if (--sizeOfStream > 0) {
			action.accept(date);
			date = date.minusYears(1);
			return true;
		} else {
			return false;
		}
	}

	public Spliterator<LocalDate> trySplit() {
		return null;
	}

	public int characteristics() {
		return DISTINCT | IMMUTABLE | NONNULL | ORDERED | SORTED;
	}

	public Comparator<LocalDate> getComparator() {
		return Comparator.reverseOrder();
	}

	public static void main(String... args) {
//		Stream<LocalDate> newYearDays = StreamSupport.stream(new YearSpliterator(LocalDate.of(2018, Month.JANUARY, 1), 100),
//				false);
//		newYearDays.filter(day -> day.getDayOfWeek() == DayOfWeek.MONDAY).forEach(System.out::println);
		
		Stream.of(DISTINCT,IMMUTABLE,NONNULL,ORDERED,SORTED).forEach(YearSpliterator::showBinary);
	}

	private static void showBinary(int x) {
		System.out.printf("%05d",Integer.toBinaryString(x));
	}
}