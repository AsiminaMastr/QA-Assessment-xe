# 🏠 XE.gr Property Search – UI Automation

A complete end-to-end UI automation test for **XE.gr Real Estate Search**, built with:

- **Java 17**
- **Selenium WebDriver**
- **TestNG**
- **Page Object Model (POM)**

This test validates search functionality, results accuracy, pagination, lazy loading, and sorting behavior on XE’s property listing portal.

---

## 🎯 Project Goal

To ensure that the **Rent property search** on XE.gr functions correctly under a combined set of filters and dynamic UI behaviors.

The test simulates real user actions and validates **every listing** returned in the results.

---

## 🔍 Test Scope

### **Applied Search Filters**
- **Transaction**: Rent (Ενοικίαση)
- **Property Type**: Residence (Κατοικία)
- **Area**: Select *all* autocomplete options matching **“Παγκράτι”**
- **Price Range**: **€200–€700**
- **Size Range**: **75–150 m²**
- **Sorting**: Price (descending)

---

## ✔ Validation Rules

For **every listing** shown on the results page, across all pages and lazy-loaded items:

| Validation | Expected |
|-----------|----------|
| Price range | 200–700 € |
| Square meters | 75–150 m² |
| Max number of images | ≤ 30 |
| Sorting | Proper descending price order |
| Step-level reporting | PASS/FAIL with explanations |

The test logs **exactly which listing passed or failed**, including price, square meters, and number of images.

---

## 🧩 Architecture & Design

This project follows the **Page Object Model** (POM) pattern.
src/test/java/
└── gr/xe/qa/
├── base/
│ └── BaseTest.java
├── pages/
│ ├── HomePage.java
│ └── SearchResultsPage.java
├── util/
│ └── AdCard.java
└── tests/
└── RentSearchSmokeTest.java



### **BaseTest.java**
- WebDriver setup  
- Window management  
- Global waits  
- Teardown  

### **HomePage.java**
Handles:
- Accepting cookies  
- Selecting Rent + Residence  
- Selecting *all* Παγκράτι-related areas  
- Clicking Search  

### **SearchResultsPage.java**
Responsible for:
- Applying price & size filters  
- Sorting by price (descending)  
- Reading lazy-loaded cards via scroll  
- Handling pagination  
- Returning `AdCard` objects  

### **AdCard.java**
Extracts:
- Price  
- Size (m²)  
- Image count (carousel, placeholder, sponsored)  

### **RentSearchSmokeTest.java**
Main E2E scenario with:
- `[STEP X]` logs  
- PASS/FAIL classification  
- Detailed reasons for failures  
- Soft assertions  

---

📌 Requirements Coverage
Feature	Status
Rent / Residence selection	✔
Παγκράτι autocomplete selection	✔
Price filter	✔
Size filter	✔
Lazy-load handling (scroll)	✔
Pagination	✔
Price validation	✔
Size validation	✔
≤ 30 images validation	✔
Sorting by price descending	✔
Step-level PASS/FAIL logs	✔
Detailed explanations	✔
✨ Highlights

🔄 Handles dynamic UI with lazy loading (infinite scroll)

🧩 Fully modular POM design

📝 Rich debugging logs for each listing

📊 Soft assert strategy → full report before failure

💡 Realistic simulation of a human user

👤 Author

Asimina Mastrogianni
Quality Assurance Engineer
Java | Selenium | Test Automation
