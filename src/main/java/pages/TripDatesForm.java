package pages;

import helpMethods.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.util.List;

public class TripDatesForm {
    DateHelpMethods datesHelp = new DateHelpMethods();
    WebDriver driver;

    @FindBy(className = "bui-calendar__content")
    WebElement calendar;

    @FindBy(xpath = "//div[@data-bui-ref='calendar-prev']")
    WebElement previousMonth;

    @FindBy(xpath = "//div[@data-bui-ref='calendar-next']")
    WebElement nextMonth;

    @FindBy(xpath = "//div[@data-placeholder='Check-in']")
    WebElement checkInDateValue;

    @FindBy(xpath = "//div[@data-placeholder='Check-out']")
    WebElement checkOutDateValue;

    String calendarMonths = "//div[@class='bui-calendar__month']";
    String calendarDateTemplate = "//span[@aria-label='%d']";

    @FindBy(xpath = "//div[@data-mode='checkin']")
    WebElement checkInDate;

    public TripDatesForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void openCalendar() {
        if(!calendar.isDisplayed()) {
            checkInDate.click();
        }
    }

    public void selectDate(LocalDate checkInDate, LocalDate checkOutDate) {
        LocalDate currentDate = LocalDate.now();

        //Check if checkInDate  is not in the past
        if(checkInDate.isBefore(currentDate)) {
            System.out.println("Date cannot be in the past");
        }
        //if checkInDate is not in the past select it from Calendar
        int checkInYear = checkInDate.getYear();
        int checkInMonth = checkInDate.getMonthValue();
        int checkInDay = checkInDate.getDayOfMonth();

        openCalendar();
        selectYearMonth(checkInYear, checkInMonth);
        selectDay(checkInDay);

        //Check Out date
        int checkOutYear = checkOutDate.getYear();
        int checkOutMonth = checkOutDate.getMonthValue();
        int checkOutDay = checkOutDate.getDayOfMonth();
        //Check ou date should be greater than check in date
        if(checkOutDate.isBefore(checkInDate)) {
            System.out.println("Check out date cannot be before check In date");
        }
        selectYearMonth(checkOutYear, checkOutMonth);
        selectDay(checkOutDay);
    }

    public void selectDate(String date) {
        String[] dayMonthYear = date.split(" ");
        String day = dayMonthYear[0];
        String month = dayMonthYear[1];
        int year = Integer.parseInt(dayMonthYear[2]);
        openCalendar();
        selectYearMonth(month, year);
        selectDay(day);
    }

    public String getCheckInDateValue() {
        return checkInDateValue.getText();
    }

    public String getCheckOutDateValue() {
        return checkOutDateValue.getText();
    }

    //PRIVATE METHODS

    private By dateLocatorFromTemplate(String date) {
        String locator = "//td[@data-date='" + date + "']";
        return By.xpath(locator);
    }

    private List<WebElement> getMonthsDisplayed() {
        List<WebElement> months = driver.findElements(By.xpath(calendarMonths));
        return months;
    }

    private void selectYearMonth(String month, int year) {
        int firstCalMonthNum;
        int monthToSelectNum = datesHelp.getMonthNumber(month);
        int firstCalYear = getYearFromCalendar();
        int currentYear = datesHelp.getCurrentYear();
        //Check year and move to the next month-year if needed
        if(year >= currentYear && year > firstCalYear) {
            do {
                nextMonth.click();
                //get new first calendar year
                firstCalYear = getYearFromCalendar();

            } while(year > firstCalYear);
        }
        //Check month and move to the next month if needed
        firstCalMonthNum = getMonthNumberFromCalendar();
        if(monthToSelectNum > firstCalMonthNum) {
            int diff = monthToSelectNum - firstCalMonthNum;
            for(int i = 0; i < diff; i++) {
                nextMonth.click();
            }
        }
    }

    private void selectYearMonth(int year, int month) {
        int firstCalendarMonth;
        int firstCalendarYear = getYearFromCalendar();
        int currentYear = datesHelp.getCurrentYear();

        if(year >= currentYear && year > firstCalendarYear) {
            do {
                nextMonth.click();
                //get new first calendar year
                firstCalendarYear = getYearFromCalendar();

            } while(year > firstCalendarYear);
        }

        firstCalendarMonth = getMonthNumberFromCalendar();
        if(month > firstCalendarMonth) {
            int diff = month - firstCalendarMonth;
            for(int i = 0; i < diff; i++) {
                nextMonth.click();
            }
        }

    }

    private void selectDay(String day) {
        driver.findElement(By.xpath("//span[text()='" + day + "']")).click();
    }

    private void selectDay(int day) {
        driver.findElement(By.xpath("//span[text()='" + day + "']")).click();
    }

    private int getMonthNumberFromCalendar() {
        List<WebElement> months = getMonthsDisplayed();
        String[] first = months.get(0).getText().split(" ");
        String firstCalMonth = first[0];
        int firstCalMonthNum = DateHelpMethods.getMonthNumber(firstCalMonth);
        return firstCalMonthNum;
    }

    private int getYearFromCalendar() {
        List<WebElement> months = getMonthsDisplayed();
        String[] first = months.get(0).getText().split(" ");
        String firstCalYear = first[1];
        return Integer.parseInt(firstCalYear);

    }

}
