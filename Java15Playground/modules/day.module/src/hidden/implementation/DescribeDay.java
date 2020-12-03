package hidden.implementation;

import day.DayInterface;

import java.time.DayOfWeek;

public class DescribeDay implements DayInterface {

    public static void main(String[] args){
        DescribeDay switchStatement = new DescribeDay();
        new DescribeDay().goClassic(DayOfWeek.TUESDAY);
        new DescribeDay().goJava14(DayOfWeek.TUESDAY);
    }

    public void whatSortOfDayIsIt(DayOfWeek day){
        this.goJava14(day);
    }

    private void goJava14(DayOfWeek day) {
        var thisDay = switch (day) {
            case MONDAY, WEDNESDAY -> "This is a great day";
            case TUESDAY -> "Tuesday is a long day";
            default -> throw new IllegalStateException("Unexpected value: " + day);
        };
        System.out.println(thisDay);
    }

    private void goClassic(DayOfWeek day) {
        switch (day) {
            case MONDAY, WEDNESDAY:
                System.out.println("This is a great day");
                break;
            case TUESDAY:
                System.out.println("Tuesday is a long day");
                break;
        }
    }

}
