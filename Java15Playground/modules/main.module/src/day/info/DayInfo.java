package day.info;

import day.DescribeDay;

import java.time.DayOfWeek;

public class DayInfo {

    public static void main(String[] args){
        new DescribeDay().whatSortOfDayIsIt(DayOfWeek.valueOf(args[0].toUpperCase()));
    }

}
