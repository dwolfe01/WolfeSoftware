package day.info;

import day.DayInterface;

import java.time.DayOfWeek;
import java.util.ServiceLoader;

public class DayInfo {

    public static void main(String[] args){
        Iterable<DayInterface> services = ServiceLoader.load(DayInterface.class);
        DayInterface service = services.iterator().next();
        DayOfWeek day = DayOfWeek.valueOf(args[0].toUpperCase());
        service.whatSortOfDayIsIt(day);
    }

}
