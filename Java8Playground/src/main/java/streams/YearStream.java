package streams;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class YearStream {

	public static void main(String[] args) {
		Stream<LocalDate> newYearDays = Stream.iterate(LocalDate.of(2018, Month.JANUARY, 1), date -> date.minusYears(1))
				.limit(100);
		newYearDays.filter(day -> day.getDayOfWeek() == DayOfWeek.MONDAY).forEach(YearStream::print2);
	}
	
	public static Consumer<LocalDate> print(){
		return s -> System.out.println(s);
	}
	
	public static void print2(LocalDate date){
		System.out.println(date);
	}
}
