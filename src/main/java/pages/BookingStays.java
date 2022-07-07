package pages;

import dev.failsafe.internal.util.Assert;
import org.asynchttpclient.util.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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

    String calendarMonths = "//div[@class='bui-calendar__month']";

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
    WebElement cildrenCount;

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

    public void selectCheckInDate(String date) {
        openCalendar();
        driver.findElement(dateLocatorFromTemplate(date)).click();

    }

    public void selectCheckOutDate(String date) {
        openCalendar();
        driver.findElement(dateLocatorFromTemplate(date)).click();

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
    private Integer getAdultsNumber() {
        return Integer.parseInt(adultsNumber.getText());
    }

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
        String locator = "//div//td[@data-date='" + date + "']";
        return By.xpath(locator);
    }

    private List<WebElement> getMonths() {
        List<WebElement> months = driver.findElements(By.xpath(calendarMonths));
        return months;
    }

}
