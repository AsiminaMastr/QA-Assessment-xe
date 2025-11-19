package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.AdCard;

import java.util.ArrayList;
import java.util.List;

public class SearchResults {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private By priceFilterButton =
            By.cssSelector("[data-testid='price-filter-button']");
    private By minPriceInput =
            By.cssSelector("[data-testid='minimum_price_input']");
    private By maxPriceInput =
            By.cssSelector("[data-testid='maximum_price_input']");
    private By sizeFilterButton =
            By.cssSelector("[data-testid='size-filter-button']");
    private By minSizeInput =
            By.cssSelector("[data-testid='minimum_size_input']");
    private By maxSizeInput =
            By.cssSelector("[data-testid='maximum_size_input']");
    private By paginationContainer = By.cssSelector("ul.results-pagination");
    private By nextPageLink       = By.cssSelector("ul.results-pagination a[rel='next']");
    private By adCardLocator =
            By.cssSelector("div.common-ad[data-testid^='property-ad-']");
    private By sortDropdownButton =
            By.cssSelector("[data-testid='open-property-sorting-dropdown']");
    private By priceDescOption =
            By.cssSelector("[data-testid='price_desc']");


    public SearchResults(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


    public SearchResults setPriceRange(int min, int max) {
        // 1. open filter
        wait.until(ExpectedConditions.elementToBeClickable(priceFilterButton)).click();

        // 2. fill min
        WebElement minInput =
                wait.until(ExpectedConditions.elementToBeClickable(minPriceInput));
        minInput.clear();
        minInput.sendKeys(String.valueOf(min));
        // 3. fill max
        WebElement maxInput =
                wait.until(ExpectedConditions.elementToBeClickable(maxPriceInput));
        maxInput.clear();
        maxInput.sendKeys(String.valueOf(max));
        // 4. enter to apply changes
        maxInput.sendKeys(Keys.ENTER);

        return this;
    }

    public SearchResults setSizeRange(int min, int max) {

        // 1. filter
        wait.until(ExpectedConditions.elementToBeClickable(sizeFilterButton)).click();

        // 2. min value
        WebElement minInput =
                wait.until(ExpectedConditions.elementToBeClickable(minSizeInput));
        minInput.clear();
        minInput.sendKeys(String.valueOf(min));

        // 3. max value
        WebElement maxInput =
                wait.until(ExpectedConditions.elementToBeClickable(maxSizeInput));
        maxInput.clear();
        maxInput.sendKeys(String.valueOf(max));

        // 4.CLICK OUTSIDE to close popup
        WebElement body = driver.findElement(By.tagName("body"));
        body.click();
        waitForResultsToLoad();
        return this;
    }
    private void waitForResultsToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(paginationContainer));
    }

    public boolean hasNextPage() {
        try {
            WebElement next = driver.findElement(nextPageLink);
            WebElement li = next.findElement(By.xpath("./.."));
            String classes = li.getAttribute("class");

            //next page exists
            return !classes.contains("disabled") && !classes.contains("invisible");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean goToNextPageIfExists() {
        if (!hasNextPage()) {
            return false;
        }

        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(nextPageLink));
        next.click();

        waitForResultsToLoad();
        return true;
    }

    public List<AdCard> getAllAdsOnCurrentPageWithScroll() {

        waitForResultsToLoad();

        JavascriptExecutor js = (JavascriptExecutor) driver;

        int previousCount = -1;
        List<WebElement> cards = new ArrayList<>();

        while (true) {
            cards = driver.findElements(adCardLocator);
            int currentCount = cards.size();

            // log για debug
            System.out.println("Currently loaded ads: " + currentCount);

            if (currentCount == previousCount) {
                break;
            }

            previousCount = currentCount;

            if (currentCount == 0) {
                break;
            }

            // Scroll to last element in ordr to fetch all
            WebElement last = cards.get(currentCount - 1);
            js.executeScript("arguments[0].scrollIntoView({block:'end'});", last);


            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // all cards from page
        List<AdCard> ads = new ArrayList<>();
        for (WebElement card : cards) {
            ads.add(new AdCard(driver, wait, card));
        }
        return ads;
    }

    public void sortByPriceDescending() {

        wait.until(ExpectedConditions.elementToBeClickable(sortDropdownButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(priceDescOption)).click();
        waitForResultsToLoad();
    }

}
