package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GuestsTests extends BaseTest{

    @Test
    public void adultsCounterShouldBeUpdatedWhenAdultsNumberIsChanged() {
        int adults1 = 5;
        int adults2 = 1;
        String expectedCounter1 = adults1 + " adults";
        String expectedCounter2 = adults2 + " adult";

        guests.enterAdultsNumber(adults1);
        Assert.assertEquals(expectedCounter1, guests.getAdultsCount());
        guests.enterAdultsNumber(adults2);
        Assert.assertEquals(expectedCounter2, expectedCounter2, guests.getAdultsCount());
    }

    @Test
    public void shouldNotBePossibleToUpdateAdultsTo0() {
        Integer expected = 1;
        guests.enterAdultsNumber(0);
        Assert.assertEquals(expected, guests.getAdultsNumber());
        Assert.assertEquals("1 adult", guests.getAdultsCount());
    }

    @Test
    public void shouldNotBePossibleToUpdateAdultsToMoreThanMax() {
        Integer max = 30;
        guests.enterAdultsNumber(max+1);
        Assert.assertEquals(max, guests.getAdultsNumber());
        Assert.assertEquals(max + " adults", guests.getAdultsCount());

    }

    @Test
    public void childAgeSelectShouldBeDisplayedIfChildrenNumberIsNotZero() {
        int childrenNumber = 2;
        guests.enterChildrenNumber(childrenNumber);
        Assert.assertTrue(guests.isChildAgeControlVisible());
        Assert.assertEquals(childrenNumber, guests.getChildAgeControlsNumber());

    }

    @Test
    public void shouldNotBePossibleToAddMoreThan10Children() {
        int childrenNumber = 11;
        guests.enterChildrenNumber(childrenNumber);
        Assert.assertEquals(10, guests.getChildrenNumber());
        Assert.assertEquals(10, guests.getChildAgeControlsNumber());

    }

    @Test
    public void shouldNotBePossibleToSetRoomsTo0() {
        int rooms = 0;
        guests.selectRoomsNumber(rooms);
        Assert.assertEquals(1, guests.getRoomsNumber());

    }

}
