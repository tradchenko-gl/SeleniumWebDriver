package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.BookingCookiesPreferences;
import pages.BookingStays;

import java.time.Duration;

public class FirstTestClass {
    WebDriver driver;
    //String baseURL = "https://www.expedia.com";
    String baseURL = "https://www.booking.com/";
    BookingCookiesPreferences cookiesPreferences;
    BookingStays stays;
    //StaysPage staysPage;


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "/Personal/Education/Drivers/geckodriver.exe");
        driver = new FirefoxDriver();
        cookiesPreferences = new BookingCookiesPreferences(driver);
        stays = new BookingStays(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();
        driver.get(baseURL);
        cookiesPreferences.acceptCookiesPreferences();

    }

    @Test
    public void enteredCityShouldAppearInTheList() {
        String destinationCity = "New York";
        stays.enterDestination(destinationCity);
        Assert.assertEquals(destinationCity, stays.searchForDestinationCityInTheList(destinationCity));
    }

    @Test
    public void adultsCounterShouldBeUpdatedWhenAdultsNumberIsChanged() {
        int adults1 = 5;
        int adults2 = 1;
        String expectedCounter1 = adults1 + " adults";
        String expectedCounter2 = adults2 + " adult";

        stays.enterAdultsNumber(adults1);
        Assert.assertEquals("Verify that adults counter is updated to " + expectedCounter1, expectedCounter1,stays.getAdultsCount());
        stays.enterAdultsNumber(adults2);
        Assert.assertEquals("Verify that adults counter is updated to" + expectedCounter2, expectedCounter2,stays.getAdultsCount());
    }

    @Test
    public void shouldNotBePossibleToUpdateAdultsTo0() {
        Integer expected = 1;
        stays.enterAdultsNumber(0);
        Assert.assertEquals(expected, stays.getAdultsNumber());
        Assert.assertEquals("1 adult", stays.getAdultsCount());
    }

    @Test
    public void shouldNotBePossibleToUpdateAdultsToMoreThanMax() {
        Integer max = 30;
        stays.enterAdultsNumber(max+1);
        Assert.assertEquals(max, stays.getAdultsNumber());
        Assert.assertEquals(max + " adults", stays.getAdultsCount());

    }

    @Test
    public void childAgeSelectShouldBeDisplayedIfChildrenNumberIsNotZero() {
        int childrenNumber = 2;
        stays.enterChildrenNumber(childrenNumber);
        Assert.assertTrue(stays.isChildAgeControlVisible());
        Assert.assertEquals(childrenNumber, stays.getChildAgeControlsNumber());
    }

    @Test
    public void testDateSelection() {
        String checkInDate = "12 July 2022";
        String checkOutDate = "19 July 2022";
        stays.openCalendar();
        stays.selectDate(checkInDate);
        stays.selectDate(checkOutDate);

    }

    @After
    public void tearDown() throws Exception {
       // driver.quit();
    }




}
