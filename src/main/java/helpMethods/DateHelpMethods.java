package helpMethods;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateHelpMethods {

    public static int getCurrentYear() {
        LocalDate date = LocalDate.now();
        String currentDate = date.toString();
        String[] yearMonthDay = currentDate.split("-");
        return Integer.parseInt(yearMonthDay[0]);
    }

    public static int getMonthNumber(String month) {
        switch(month) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
        }
        return -1;
    }

    public String getDateToBeDisplayed(LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String monthToDisplay = getShortenedMonth(month);
        String dayOfWeek = getDayOfWeek(date);

        return dayOfWeek + ", "+ monthToDisplay + " " + day;

    }

    private String getDayOfWeek(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int dayNum = dayOfWeek.getValue();

        switch(dayNum) {
            case 1: return "Mon";
            case 2: return "Tue";
            case 3: return "Wed";
            case 4: return "Thu";
            case 5: return "Fri";
            case 6: return "Sat";
            case 7: return "Sun";
        }
        return "-1";
    }

    private String getShortenedMonth(int month) {
        switch(month) {
            case 1: return "Jan";
            case 2: return "Feb";
            case 3: return "Mar";
            case 4: return "Apr";
            case 5: return "May";
            case 6: return "Jun";
            case 7: return "Jul";
            case 8: return "Aug";
            case 9: return "Sept";
            case 10: return "Oct";
            case 11: return "Nov";
            case 12: return "Dec";
        }

        return "-1";
    }

}
