package pages;

import helpMethods.DateHelpMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class BookingStays {
    WebDriver driver;

    @FindBy(id = "ss")
    WebElement destination;

    @FindBy(xpath = "//ul[@aria-label='List of suggested destinations ']")
    WebElement destinationsList;

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

//    @FindBy(xpath = "(//div[@class='bui-calendar__wrapper'])[0]")
//    WebElement checkInDateCalendar;
//
//    @FindBy(xpath = "(//div[@class='bui-calendar__wrapper'])[1]")
//    WebElement checkOutDateCalendar;

    @FindBy(xpath = "//div[@data-mode='checkin']")
    WebElement checkInDate;

    @FindBy(xpath = "//button[@class='sb-searchbox__button ']")
    WebElement searchButton;

    @FindBy(id = "sb_entire_place_checkbox")
    WebElement entirePlaceCheckBox;

    @FindBy(id = "sb_travel_purpose_checkbox")
    WebElement travelingForWorkCheckBox;

    @FindBy(xpath = "//span[@class='xp__guests__count']")
    WebElement guestsArea;

    @FindBy(id = "xp__guests__inputs-container")
    WebElement guestsContainer;

    @FindBy(xpath = "//span[@data-adults-count='']")
    WebElement adultsCount;

    @FindBy(xpath = "//div[contains(@class, 'adults')]//span[contains(@data-bui-ref, 'value')]")
    WebElement adultsNumber;

    @FindBy(xpath = "//div[contains(@class, 'adults')]//button[contains(@data-bui-ref, 'subtract')]")
    WebElement decreaseAdultsNumber;

    @FindBy(xpath = "//div[contains(@class, 'adults')]//button[contains(@data-bui-ref, 'add')]")
    WebElement increaseAdultsNumber;

    @FindBy(xpath = "//span[@data-children-count='']")
    WebElement childrenCount;

    @FindBy(xpath = "//div[contains(@class, 'children')]//span[contains(@data-bui-ref, 'value')]")
    WebElement childrenNumber;

    @FindBy(xpath = "//select[@name='age']")
    WebElement childAge;

    String childAgeS = "//select[@name='age']";

    @FindBy(xpath = "//div[contains(@class, 'children')]//button[contains(@data-bui-ref, 'subtract')]")
    WebElement decreaseChildrenNumber;

    @FindBy(xpath = "//div[contains(@class, 'children')]//button[contains(@data-bui-ref, 'add')]")
    WebElement increaseChildrenNumber;

    @FindBy(xpath = "//div[contains(@class, 'rooms')]//span[contains(@data-bui-ref, 'value')]")
    WebElement roomsNumber;

    @FindBy(xpath = "//div[contains(@class, 'rooms')]//button[contains(@data-bui-ref, 'subtract')]")
    WebElement decreaseRoomsNumber;

    @FindBy(xpath = "//div[contains(@class, 'rooms')]//button[contains(@data-bui-ref, 'add')]")
    WebElement increaseRoomsNumber;

    String suggestedDestinationsLocator = "//ul//li//span[@class='search_hl_name']";

    String destinationLocatorTemplate = "//ul//li//span[text()='%d']";

    String dateTemplate = "//span[@aria-label='%d']";

    public BookingStays(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
//Destination

    public void enterDestination(String destinationCity) {

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        destination.sendKeys(destinationCity);
        wait.until(ExpectedConditions.visibilityOf(destinationsList));

    }

    public void selectDestinationFromTheList(String destination) {
        List<WebElement> suggestedDestinations = driver.findElements(By.xpath("//ul//li//span[@class='search_hl_name']"));
        for(WebElement el : suggestedDestinations) {
            String s = el.getText();
            if(s.equals(destination)) {
                el.click();
            }
        }
    }

    public String searchForDestinationCityInTheList(String destinationCity) {
        List<WebElement> suggestedDestinations = driver.findElements(By.xpath("//ul//li//span[@class='search_hl_name']"));
        for(WebElement el : suggestedDestinations) {
            String s = el.getText();
            if(s.equals(destinationCity)) {
                return s;
            }
        }
        return "-1";
    }

//Check in and Check out dates

    public void openCalendar() {
        if(!calendar.isDisplayed()) {
            checkInDate.click();
        }
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

    public void selectDate(LocalDate date) {
        //Check if date  is not in the past
        LocalDate currentDate = LocalDate.now();
        if(date.isBefore(currentDate)) {
            System.out.println("Date cannot be in the past");
        }
        //if date is not in the past select it from Calendar
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        openCalendar();
        selectYearMonth(year, month);
        selectDay(day);
    }

    public String getCheckInDateValue() {
        return checkInDateValue.getText();
    }

    public String getCheckOutDateValue() {
        return checkOutDateValue.getText();
    }

//Guests
    public String getAdultsCount() {
        return adultsCount.getText();
    }

    public void enterAdultsNumber(int adultsNumber) {
        openGuestsArea();
        int actualAdults = getAdultsNumber();
        if(actualAdults != adultsNumber) {
            int difference = adultsNumber - actualAdults;
            if(difference > 0) {
                addAdults(difference);
            } else {
                removeAdults(Math.abs(difference));
            }
        }
    }

    public Integer getAdultsNumber() {
        return Integer.parseInt(adultsNumber.getText());
    }

//    public boolean isDecreaseAdultsEnabled() {
//        if(decreaseAdultsNumber.isEnabled()) {
//            return true;
//        }
//        return false;
//    }
//
//    public boolean isIncreaseAdultsEnabled() {
//        if(increaseAdultsNumber.isEnabled()) {
//            return true;
//        }
//        return false;
//    }

    public void enterChildrenNumber(int childrenNumber) {
        openGuestsArea();
        int actualChildren = getChildrenNumber();
        if(actualChildren != childrenNumber) {
            int difference = childrenNumber - actualChildren;
            if(difference > 0) {
                addChildren(difference);
            } else {
                removeChildren(Math.abs(difference));
            }
        }
    }

    public int getChildAgeControlsNumber() {
        List<WebElement> num = driver.findElements(By.xpath(childAgeS));
        return num.size();
    }

    public boolean isChildAgeControlVisible() {
        if(childAge.isDisplayed()) {
            return true;
        }
        return false;
    }


//Help private methods

    private int getChildrenNumber() {
        return Integer.parseInt(childrenNumber.getText());
    }

    private void openGuestsArea() {
        if(!guestsContainer.isDisplayed()) {
            guestsArea.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(guestsContainer));
        }
    }

    private void addAdults(int adultsNum) {
        for(int i = 0; i < adultsNum; i++) {
            increaseAdultsNumber.click();
        }
    }

    private void removeAdults(int adultsNum) {
        //guestsArea.click();
        for(int i = 0; i < adultsNum; i++) {
            decreaseAdultsNumber.click();
        }
    }

    private void addChildren(int childrenNum) {
        for(int i = 0; i < childrenNum; i++) {
            increaseChildrenNumber.click();
        }
    }

    private void removeChildren(int childrenNum) {
        for(int i = 0; i < childrenNum; i++) {
            decreaseChildrenNumber.click();
        }
    }

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
        int monthToSelectNum = DateHelpMethods.getMonthNumber(month);
        int firstCalYear = getYearFromCalendar();
        int currentYear = DateHelpMethods.getCurrentYear();
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
        int currentYear = DateHelpMethods.getCurrentYear();

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
