package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.SearchResults;
import java.util.ArrayList;
import java.util.List;


public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // *** LOCATORS
    private By rentDropdownButton = By.cssSelector("[data-testid='open-property-transaction-dropdown']");
    private By rentOption = By.xpath("//li[contains(.,'Ενοικίαση')]");
    private By residenceCategory = By.cssSelector("[data-testid='open-property-type-dropdown']");
    private By residenceOption = By.cssSelector("[data-testid='re_residence']");
    private By areaInput = By.cssSelector("[data-testid='area-input']");
    private By areaDropdownOptions = By.cssSelector("[data-testid='geo_place_id_dropdown_panel'] .dropdown-panel-option");
    private By searchButton = By.cssSelector("[data-testid='submit-input']");
    private By acceptCookiesBtn = By.id("accept-btn");


    public HomePage open(String baseUrl) {
        driver.get(baseUrl);
        return this;
    }

    public HomePage selectRentAndResidence() {
        wait.until(ExpectedConditions.elementToBeClickable(rentDropdownButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(rentOption)).click();
        // Select "Κατοικία"
        wait.until(ExpectedConditions.elementToBeClickable(residenceCategory)).click();
        wait.until(ExpectedConditions.elementToBeClickable(residenceOption)).click();

        return this;
    }

    public HomePage acceptCookiesIfPresent() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesBtn));
            btn.click();
        } catch (Exception e) {
        }
        return this;
    }

    public HomePage selectAllPagkratiAreas() {

        // 1. Focus ον input
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(areaInput));
        input.click();
        input.clear();
        input.sendKeys("παγκρατι");

        // 2. wait for the dropdown
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='geo_place_id_dropdown_panel']")));

        // 3. take all options in a list
        List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(areaDropdownOptions)
        );

        // hold texts
        List<String> optionTexts = new ArrayList<>();
        for (WebElement opt : options) {
            optionTexts.add(opt.getText().trim());
        }

        // 4.for every text start selection
        for (String text : optionTexts) {

            // focus on input
            input = wait.until(ExpectedConditions.elementToBeClickable(areaInput));
            input.click();
            input.clear();
            input.sendKeys("παγκρατι");

            // wait for options
            List<WebElement> currentOptions = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(areaDropdownOptions)
            );

            for (WebElement opt : currentOptions) {
                if (opt.getText().trim().equals(text)) {
                    opt.click();     // select specific area
                    break;
                }
            }
        }

        return this;
    }

    public SearchResults submitSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        return new SearchResults(driver, wait);
    }
}

