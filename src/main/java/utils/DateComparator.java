package utils;

public class DateComparator {
    public static int compareDates(int day1, int month1, int year1, int day2, int month2, int year2) {
        if(year1 > year2)
            return 1;
        else if(year1 < year2)
            return -1;
        else {
            if(month1 > month2)
                return 1;
            else if(month1 < month2)
                return -1;
            else {
                if(day1 > day2)
                    return 1;
                else if(day1 < day2)
                    return -1;
                else
                    return 0;
            }
        }
    }
}
