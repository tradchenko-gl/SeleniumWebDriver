package pages;

import helpMethods.Child;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.util.List;

public class SearchForm {
    WebDriver driver;

    @FindBy(xpath = "//div[@id='destination__error']//div")
    WebElement destinationErrorMessage;

    @FindBy(xpath = "//button[@class='sb-searchbox__button ']")
    WebElement searchButton;

    @FindBy(xpath = "//div[@data-component='search/dates/dates-errors']")
    WebElement datesErrorMessage;


    public SearchForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void searchForAccommodation(String destination, LocalDate checkInDate, LocalDate checkOutDate) {
        DestinationForm destinationForm = new DestinationForm(driver);
        TripDatesForm tripDatesForm = new TripDatesForm(driver);

        destinationForm.enterDestination(destination);
        tripDatesForm.selectDate(checkInDate, checkOutDate);
        searchButton.click();
    }

    public void searchForAccommodation(String destination, LocalDate checkInDate, LocalDate checkOutDate, int adultsNumber) {
        DestinationForm destinationForm = new DestinationForm(driver);
        TripDatesForm tripDatesForm = new TripDatesForm(driver);
        GuestsForm guests = new GuestsForm(driver);

        destinationForm.enterDestination(destination);
        tripDatesForm.selectDate(checkInDate, checkOutDate);
        guests.enterAdultsNumber(adultsNumber);
        searchButton.click();
    }

    public void searchForAccommodation(String destination, LocalDate checkInDate, LocalDate checkOutDate, int adultsNumber, List<Child> children) {
        DestinationForm destinationForm = new DestinationForm(driver);
        TripDatesForm tripDatesForm = new TripDatesForm(driver);
        GuestsForm guests = new GuestsForm(driver);

        destinationForm.enterDestination(destination);
        tripDatesForm.selectDate(checkInDate, checkOutDate);
        guests.enterAdultsNumber(adultsNumber);
        guests.enterChildrenNumber(children.size());
        searchButton.click();
    }

    public void searchForAccommodation(String destination, LocalDate checkInDate, LocalDate checkOutDate,
                                       int adultsNumber, List<Child> children, int roomsNumber) {
        DestinationForm destinationForm = new DestinationForm(driver);
        TripDatesForm tripDatesForm = new TripDatesForm(driver);
        GuestsForm guests = new GuestsForm(driver);

        destinationForm.enterDestination(destination);
        tripDatesForm.selectDate(checkInDate, checkOutDate);
        guests.enterAdultsNumber(adultsNumber);
        guests.enterChildrenNumber(children.size());

        searchButton.click();
    }


    public void clickSearchButton() {
        searchButton.click();
    }

    public String destinationErrorMessageText() {
        return destinationErrorMessage.getText();
    }

    public String searchDatesErrorMessageText() {
        return datesErrorMessage.getText();
    }



}
