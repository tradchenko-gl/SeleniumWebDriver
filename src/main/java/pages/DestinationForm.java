package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DestinationForm {
    WebDriver driver;

    @FindBy(id = "ss")
    WebElement destination;

    @FindBy(xpath = "//ul[@aria-label='List of suggested destinations ']")
    WebElement destinationsList;

    @FindBy(id = "sb_entire_place_checkbox")
    WebElement entirePlaceCheckBox;

    @FindBy(id = "sb_travel_purpose_checkbox")
    WebElement travelingForWorkCheckBox;

    String suggestedDestinationsLocator = "//ul//li//span[@class='search_hl_name']";

    String destinationLocatorTemplate = "//ul//li//span[text()='%d']";

    String dateTemplate = "//span[@aria-label='%d']";

    public DestinationForm(WebDriver driver) {
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



//Help private methods






}
