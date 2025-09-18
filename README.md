# SauceDemo Automation Testing

This project is an automated testing suite for the [SauceDemo](https://www.saucedemo.com/) web application. It is developed using **Java**, **Selenium WebDriver**, and **TestNG**, and simulates user interactions such as logging in, adding/removing items from the cart, and completing the checkout process.

---

## 🛠️ Tools & Frameworks

- **TestNG** – For structuring and prioritizing test cases  
- **Selenium WebDriver** – For interacting with the web UI  
- **EdgeDriver** – Used as the default browser for this test suite  
- **WebDriverManager** – To manage drivers automatically
---
## ✅ Features & Test Scenarios

1. **Login Test**  
   - Enters valid credentials and logs into the application.

2. **Add to Cart Test**  
   - Adds two specific items to the shopping cart.

3. **Remove from Cart Test**  
   - Removes the previously added items from the cart.

4. **Re-Add Items Test**  
   - Adds the same two items again after removal.

5. **Checkout Process Test**  
   - Navigates to the cart, fills out the checkout form, and completes the purchase.

6. **Logout Test**  
   - Logs the user out via the burger menu.

7. **Add All Items to the Cart (Even-Indexed Only)**  
   - Iterates over all available products and adds only those with even indexes to the cart.  
   - Prints the name and price of each product and logs whether it was added or not based on its index.

8. **(Commented) Dynamic Price Handling (Optional)**  
   - Includes optional logic (currently commented) to collect product prices dynamically into a list.  
   - Can be used for advanced validations, like total price calculations or conditional filtering based on price.

---
## 📌 Notes

- `Thread.sleep()` is used for wait simulation, but in real-world scenarios, explicit or fluent waits are preferred for reliability.
- `@BeforeTest` sets up the browser, and `@AfterTest` closes it after test execution.
- Test priority ensures logical and sequential flow of the test cases.

---
## 📸 Screenshots
![SauceDemoOutput](https://github.com/user-attachments/assets/106f1d02-312f-445e-bc40-cfda5bbcfedf)   
![eclipse_aoHI2XXlrS](https://github.com/user-attachments/assets/b27b9696-0e07-4a3a-8894-a3e2781e9dfc)


"# Booking-Automation" 
"# Booking-Automation" 
