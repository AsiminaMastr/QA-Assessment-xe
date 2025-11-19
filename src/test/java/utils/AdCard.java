package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AdCard {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final WebElement root;

    // ---- Locators----

    private By priceLocator =
            By.cssSelector("[data-testid='property-ad-price']");
    private By titleLocator =
            By.cssSelector("[data-testid='property-ad-title']");
    private By carouselRootLocator =
            By.cssSelector(".property-ad-images-carousel");

    private By carouselSlidesLocator =
            By.cssSelector("[data-testid='property-ad-images-carousel'] .slick-slide:not(.slick-cloned)");
    private By imageForHoverLocator =
            By.cssSelector("[data-testid='ad-gallery-image-1-img'], img");
    private By singleImageLocator =
            By.cssSelector("[data-testid='property-ad-image'], .common-ad-no-image");

    // -----------------------------------

    public AdCard(WebDriver driver, WebDriverWait wait, WebElement root) {
        this.driver = driver;
        this.wait = wait;
        this.root = root;
    }

    public int getPrice() {
        String text = root.findElement(priceLocator).getText();
        String digits = text.replaceAll("[^0-9]", "");
        return Integer.parseInt(digits);
    }


    public int getSquareMeters() {
        String title = root.findElement(titleLocator).getText();
        String digits = title.replaceAll("[^0-9]", "");
        return Integer.parseInt(digits);
    }


        public int getNumberOfImages() {

            try {
                WebElement carouselRoot = root.findElement(carouselRootLocator);
                List<WebElement> slides =
                        carouselRoot.findElements(carouselSlidesLocator);
                int count = slides.size();

                System.out.println("DEBUG - ad " +
                        root.getAttribute("data-testid") +
                        " → slides found: " + count);
                return slides.size();
            } catch (NoSuchElementException e) {
                // 2) no carousel -> image existence -> 1
                List<WebElement> singleImages = root.findElements(singleImageLocator);
                if (!singleImages.isEmpty()) {
                    return 1;
                }
                // 3) no image -> 0
                return 0;
            }
        }

}