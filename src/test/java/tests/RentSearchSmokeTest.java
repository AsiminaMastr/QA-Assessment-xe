package tests;

import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import base.BaseTest;
import pages.SearchResults;
import utils.AdCard;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.List;



public class RentSearchSmokeTest extends BaseTest {

    private static final int MIN_PRICE = 200;
    private static final int MAX_PRICE = 700;
    private static final int MIN_SIZE = 75;
    private static final int MAX_SIZE = 150;
    private static final int MAX_IMAGES = 30;

    @Test
    public void RentSearchPagkratiSmokeTest() {

        
        SoftAssert soft = new SoftAssert();

        // STEP 1 – Open home page & basic selections
        logStep("1", "Open home page, accept cookies, select Rent + Residence, select Pagkrati areas");

        HomePage home = new HomePage(driver, wait)
                .open(BASE_URL)
                .acceptCookiesIfPresent()
                .selectRentAndResidence()
                .selectAllPagkratiAreas();

        logStepPass("1");

        // STEP 2 – Set filters on search results (price & size)
        logStep("2", "Submit search and apply price 200–700 and size 75–150");

        SearchResults resultsPage = home.submitSearch()
                .setPriceRange(200, 700)
                .setSizeRange(75, 150);

        logStepPass("2");

        // STEP 3 – Verify all ads on all pages
        logStep("3", "Verify that ALL ads in ALL pages respect price/size range and have ≤ 30 images");

        int page = 1;
        boolean hasMore = true;

        while (hasMore) {

            List<AdCard> ads = resultsPage.getAllAdsOnCurrentPageWithScroll();


            soft.assertTrue(ads.size() > 0,
                    "[STEP 3] Page " + page + " returned 0 ads – expected at least one.");

            int index = 1;
            for (AdCard ad : ads) {
                int price = ad.getPrice();
                int sqm   = ad.getSquareMeters();
                int imgs  = ad.getNumberOfImages();

                Reporter.log("Page " + page + " | Ad " + index +
                        " | price=" + price +
                        " | sqm=" + sqm +
                        " | images=" + imgs, true);

                // price
                if (!(price >= 200 && price <= 700)) {
                    logStepFail("3", "Page " + page + " ad " + index +
                            " has price " + price + " € (expected 200–700 €)");
                }
                soft.assertTrue(price >= 200 && price <= 700,
                        "[STEP 3] Page " + page + " ad " + index +
                                " has price " + price + " € (expected 200–700 €)");

                // sqm
                if (!(sqm >= 75 && sqm <= 150)) {
                    logStepFail("3", "Page " + page + " ad " + index +
                            " has size " + sqm + " τ.μ. (expected 75–150 τ.μ.)");
                }
                soft.assertTrue(sqm >= 75 && sqm <= 150,
                        "[STEP 3] Page " + page + " ad " + index +
                                " has size " + sqm + " τ.μ. (expected 75–150 τ.μ.)");

                // images
                if (imgs > 30) {
                    logStepFail("3", "Page " + page + " ad " + index +
                            " has " + imgs + " images (max allowed 30)");
                }
                soft.assertTrue(imgs <= 30,
                        "[STEP 3] Page " + page + " ad " + index +
                                " has " + imgs + " images (max allowed 30)");

                index++;
            }

            hasMore = resultsPage.goToNextPageIfExists();
            page++;
        }

        logStepPass("3");

        // STEP 4 – sort by price desc
        logStep("4", "Sort by price descending and verify order on first page");

        resultsPage.sortByPriceDescending();
        List<AdCard> sortedAds = resultsPage.getAllAdsOnCurrentPageWithScroll();
        List<Integer> prices = new ArrayList<>();
        for (AdCard ad : sortedAds) {
            prices.add(ad.getPrice());
        }

        for (int i = 0; i < prices.size() - 1; i++) {
            int current = prices.get(i);
            int next    = prices.get(i + 1);

            if (current < next) {
                logStepFail("4", "Sorting error at positions " + (i + 1) + " and " +
                        (i + 2) + ": " + current + " < " + next);
            }

            soft.assertTrue(current >= next,
                    "[STEP 4] Sorting error: position " + (i + 1) + " (" + current +
                            " €) < position " + (i + 2) + " (" + next + " €)");
        }

        logStepPass("4");
        soft.assertAll();
    }
}
