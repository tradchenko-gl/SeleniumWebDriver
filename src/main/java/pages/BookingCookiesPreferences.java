package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookingCookiesPreferences {
    WebDriver driver;

    @FindBy(id = "onetrust-banner-sdk")
    WebElement manageCookiesPreferencesPopUp;

    @FindBy(id = "onetrust-accept-btn-handler")
    WebElement acceptButton;

    public void acceptCookiesPreferences() {
        if(manageCookiesPreferencesPopUp.isDisplayed()) {
            acceptButton.click();
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.invisibilityOf(manageCookiesPreferencesPopUp));
    }

    public BookingCookiesPreferences(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
