package day;

import java.time.DayOfWeek;

public interface DayInterface {

    public default void whatSortOfDayIsIt(DayOfWeek day){
        System.out.println("Defaut method - its an horrible day.");
    };

}
