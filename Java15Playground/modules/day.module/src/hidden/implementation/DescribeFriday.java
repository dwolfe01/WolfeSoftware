package hidden.implementation;

import day.DayInterface;

import java.time.DayOfWeek;

public class DescribeFriday implements DayInterface {

    public static void main(String[] args){
        DescribeFriday switchStatement = new DescribeFriday();
        new DescribeFriday().goJava14(DayOfWeek.TUESDAY);
    }

    public void whatSortOfDayIsIt(DayOfWeek day){
        this.goJava14(day);
    }

    private void goJava14(DayOfWeek day) {
        switch (day) {
            case FRIDAY -> System.out.println("This is a great friday");
        }
    }

}
