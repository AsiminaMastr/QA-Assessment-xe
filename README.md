XE.gr Property Search – UI Automation

This repository contains an automated UI test for XE.gr Real Estate Search, implemented with Selenium WebDriver, Java, and TestNG.

The test performs a complete end-to-end validation of the property search workflow, including filtering, area selection, result validation, lazy loading, pagination, and sorting.

🔍 Test Scope

The test verifies that the XE property search works correctly when applying the following criteria:

Search Criteria

Transaction: Rent (Ενοικίαση)

Property Type: Residence (Κατοικία)

Area: All autocomplete options matching “Παγκράτι”

Price Range: €200–€700

Size Range: 75–150 m²

Sorting: Price (descending)

Validation Criteria

For every displayed listing (including lazy-loaded and multi-page results):

Price is within 200–700 €

Size is within 75–150 m²

Listing contains ≤ 30 images

Sorting by price (descending) works correctly

Step-level pass/fail results are logged

Failed checks include detailed explanations

📁 Project Structure
src/test/java/
 └── gr/xe/qa/
     ├── base/
     │    └── BaseTest.java
     ├── pages/
     │    ├── HomePage.java
     │    └── SearchResultsPage.java
     ├── util/
     │    └── AdCard.java
     └── tests/
          └── RentSearchSmokeTest.java

BaseTest.java

Initializes WebDriver, browser configuration, and global waits.

HomePage.java

Handles cookie banner, filters (Rent/Residence), autocomplete selection for Παγκράτι, and submitting the search.

SearchResultsPage.java

Handles price/size filters, sorting by price descending, pagination, and lazy-loading through scroll-based loading.

AdCard.java

Represents a single listing. Extracts:

Price

Square meters

Number of images (supports carousels, placeholders, and sponsored ads)

RentSearchSmokeTest.java

End-to-end test containing:

Step-by-step logging

Pass/fail granularity

Detailed failure messages

Soft assertions to collect all issues before failing

🚀 Running the Tests
Install dependencies
mvn clean install

Run all tests
mvn test

View TestNG Reports

Reports are generated under:

target/surefire-reports/


These include:

Step-level execution logs ([STEP X])

Detailed pass/fail messages

Assertions and stack traces

✔ Features

Full E2E UI workflow

Dynamic autocomplete handling

Lazy-loading support via scroll

Multi-page result handling

Accurate carousel image counting

Sorting verification

Structured step-by-step result logs

Soft assertion–based reporting

🧪 Example Output (Console)
[STEP 1] Open home page, accept cookies, select filters
[STEP 1][PASS]

[STEP 2] Apply price and size filters
[STEP 2][PASS]

[STEP 3] Validate results in all lazy-loaded ads
Page 1 | Ad 5 | price=700 | sqm=93 | images=12
Page 1 | Ad 6 | price=600 | sqm=80 | images=13
[STEP 3][PASS]

[STEP 4] Sorting by price descending
[STEP 4][PASS]

📌 Requirements Coverage
Requirement	Status
Rent / Residence selection	✔
Παγκράτι autocomplete handling	✔
Price & size filters	✔
Lazy-loaded ads collection	✔
Pagination support	✔
Validate price range	✔
Validate size range	✔
Validate ≤ 30 images	✔
Sort by price desc	✔
Step-level pass/fail reporting	✔
Detailed failure explanations	✔
👤 Author

Created by Asimina Mastrogianni
QA Engineer | Java & Selenium Test Automation
