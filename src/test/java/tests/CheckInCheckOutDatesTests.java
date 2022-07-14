package tests;

import org.testng.Assert;
import org.testng.annotations.Test;


import java.time.LocalDate;

public class CheckInCheckOutDatesTests extends BaseTest{

    @Test
    public void enteredCityShouldAppearInTheList() {
        String destinationCity = "New York";
        destination.enterDestination(destinationCity);
        Assert.assertEquals(destinationCity, destination.searchForDestinationCityInTheList(destinationCity));
    }

    @Test
    public void testDateSelectionWithLocalDate() {
        LocalDate checkInDate = LocalDate.parse("2022-07-15");
        LocalDate checkOutDate = LocalDate.parse("2022-09-09");
        tripDates.selectDate(checkInDate, checkOutDate);
        Assert.assertEquals(dateHelper.getDateToBeDisplayed(checkInDate), tripDates.getCheckInDateValue());
        Assert.assertEquals(dateHelper.getDateToBeDisplayed(checkOutDate), tripDates.getCheckOutDateValue());

    }


}
