module day.module {
    exports day;
    provides day.DayInterface with hidden.implementation.DescribeDay, hidden.implementation.DescribeFriday;
}