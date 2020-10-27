package day.info;

import day.DayInterface;

import java.time.DayOfWeek;
import java.util.Iterator;
import java.util.ServiceLoader;

public class DayInfo {

    public static void main(String[] args) {
        Iterable<DayInterface> services = ServiceLoader.load(DayInterface.class);
        Iterator<DayInterface> iterator = services.iterator();
        while (iterator.hasNext()){
            DayInterface service = iterator.next();
            DayOfWeek day = DayOfWeek.valueOf(args[0].toUpperCase());
            service.whatSortOfDayIsIt(day);
        }
    }

}

