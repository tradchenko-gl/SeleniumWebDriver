package pages;

import helpMethods.Child;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GuestsForm {
    WebDriver driver;

    @FindBy(xpath = "//span[@class='xp__guests__count']")
    WebElement guestsArea;

    @FindBy(xpath = "//div[@id='xp__guests__inputs-container']")
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

    String childAgeSelect = "//select[@name='age']";

    String childAgeS = "//select[@name='age']";

    @FindBy(xpath = "//div[contains(@class, 'children')]//button[contains(@data-bui-ref, 'subtract')]")
    WebElement decreaseChildrenNumber;

    @FindBy(xpath = "//div[contains(@class, 'children')]//button[contains(@data-bui-ref, 'add')]")
    WebElement increaseChildrenNumber;

    @FindBy(xpath = "//div[contains(@class, 'guests')]//span[@data-room-count]")
    WebElement roomsNumber;

    @FindBy(xpath = "//div[contains(@class, 'rooms')]//button[contains(@data-bui-ref, 'subtract')]")
    WebElement decreaseRoomsNumber;

    @FindBy(xpath = "//div[contains(@class, 'rooms')]//button[contains(@data-bui-ref, 'add')]")
    WebElement increaseRoomsNumber;

    public GuestsForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void addGuests(int adults, List<Child> children) {
        openGuestsForm();
        enterAdultsNumber(adults);
        enterChildrenNumber(children.size());
        enterChildrenAge(children);
    }

    public String getAdultsCount() {
        return adultsCount.getText();
    }

    public void enterAdultsNumber(int adultsNumber) {
        openGuestsForm();
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

    public void enterChildrenNumber(int childrenNumber) {
        openGuestsForm();
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

    public int getChildrenNumber() {
        return Integer.parseInt(childrenNumber.getText());
    }

    public void selectRoomsNumber(int rooms) {
        int roomsCounter = getRoomsNumber();
        if(rooms != roomsCounter) {
            openGuestsForm();
            if(rooms > roomsCounter) {
                int diff = rooms - roomsCounter;
                for(int i = 0; i < diff; i++) {
                    increaseRoomsNumber.click();
                }
            } else if (rooms < roomsCounter) {
                int diff = roomsCounter - rooms;
                for (int i = 0; i < diff; i++) {
                    decreaseRoomsNumber.click();
                }
            }
        }
    }

    public int getRoomsNumber() {
        String[] roomCount = (roomsNumber.getText()).split(" ");
        return Integer.parseInt(roomCount[0]);
    }

//Private methods

    private void openGuestsForm() {
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

    private void enterChildrenAge(List<Child> children) {
        List<WebElement> childrenAgeSelects = driver.findElements(By.xpath(childAgeSelect));
        if(children.size() == childrenAgeSelects.size()) {
            int childNum = 0;
            for (WebElement age : childrenAgeSelects) {
                Select ageSelect = new Select(age);
                ageSelect.selectByValue(String.valueOf(children.get(childNum).getAge()));
                childNum++;
            }
        }
    }
}
