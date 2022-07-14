package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class SearchForAccommodationTests extends BaseTest{

    @Test
    public void destinationShouldBeRequired() {
        search.clickSearchButton();
        Assert.assertEquals("Error:\nEnter a destination to start searching.", search.destinationErrorMessageText());
    }

    @Test
    public void shouldNotBePossibleToSearchForAccommodationForMoreThan30Nights(){
        String destination = "New York";
        LocalDate checkInDate = LocalDate.parse("2022-08-01");
        LocalDate checkOutDate = LocalDate.parse("2022-09-01");

        search.searchForAccommodation(destination, checkInDate, checkOutDate);
        Assert.assertEquals("Sorry, reservations for more than 30 nights aren't possible.", search.searchDatesErrorMessageText());
    }

    @Test
    public void searchForAccommodation() {
        String destination = "New York";
        LocalDate checkInDate = LocalDate.parse("2022-08-09");
        LocalDate checkOutDate = LocalDate.parse("2022-08-20");
        int adults = 3;
        search.searchForAccommodation(destination, checkInDate, checkOutDate, adults);
    }


}
