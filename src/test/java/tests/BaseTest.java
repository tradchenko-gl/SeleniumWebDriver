package tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pages.*;
import helpMethods.*;

import java.io.File;
import java.time.Duration;

public class BaseTest {
    WebDriver driver;
    String baseURL = "https://www.booking.com/";
    BookingCookiesPreferences cookiesPreferences;
    GuestsForm guests;
    DestinationForm destination;
    TripDatesForm tripDates;
    SearchForm search;
    DateHelpMethods dateHelper = new helpMethods.DateHelpMethods();

    @BeforeMethod
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "/Personal/Education/Drivers/geckodriver.exe");
        driver = new FirefoxDriver();
        cookiesPreferences = new BookingCookiesPreferences(driver);
        guests = new GuestsForm(driver);
        destination = new DestinationForm(driver);
        tripDates = new TripDatesForm(driver);
        search = new SearchForm(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();
        driver.get(baseURL);
        cookiesPreferences.acceptCookiesPreferences();

    }

    @AfterMethod
    public void tearDown() throws Exception {
        String fileName = getRandomString(10) + ".png";
        String directory = System.getProperty("user.dir") + "//screenshots//";
        File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File(directory + fileName));
        driver.quit();
    }

    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        String characters = "bcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for(int i = 0; i < length; i++) {
            int index = (int)(Math.random()*characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
}
