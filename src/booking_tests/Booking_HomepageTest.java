package booking_tests;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Booking_HomepageTest extends TestData {

	WebDriver driver;
	WebDriverWait wait;

	@BeforeTest
	public void setup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get(TestData.MainPage); // force English
	}

	@Test(priority = 1, enabled = true)
	public void homepageLoadsSuccessfully() throws InterruptedException {
		Thread.sleep(3000);
		// Check title
		String title = driver.getTitle();
		Assert.assertTrue(title.contains("Booking.com")); // the tab title not the url
		System.out.println(title);

		// Check header exists
		WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("header")));
		Assert.assertTrue(header.isDisplayed());

		// Check destination input field exists (Dubai)
		WebElement destinationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ss")));
		Assert.assertTrue(destinationInput.isDisplayed());

		// Check search button exists
		WebElement searchButton = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("button[type='submit'] span[class='ca2ca5203b']")));
		Assert.assertTrue(searchButton.isDisplayed());
	}

	@Test(priority = 2, enabled = true)
	public void searchAccommodation() throws InterruptedException {
		// Dynamic dates
		LocalDate today = LocalDate.now();
		LocalDate checkInDateValue = today.plusDays(7);
		LocalDate checkOutDateValue = today.plusDays(10);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String checkInStr = checkInDateValue.format(formatter);
		String checkOutStr = checkOutDateValue.format(formatter);

		// Enter destination
		WebElement destinationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ss")));
		destinationInput.clear();
		destinationInput.sendKeys("Paris");
		Thread.sleep(2000);
		destinationInput.sendKeys(Keys.ARROW_DOWN);
		destinationInput.sendKeys(Keys.ENTER);

		// check-in date
		WebElement checkInDate = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[data-date='" + checkInStr + "']")));
		checkInDate.click();

		// check-out date
		WebElement checkOutDate = wait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector("span[data-date='" + checkOutStr + "']")));
		checkOutDate.click();

		// Click search button
		WebElement searchButton = wait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("button[type='submit'] span[class='ca2ca5203b']")));
		searchButton.click();

		// Wait for results page (property list container)
		WebElement resultsList = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='list']")));
		Assert.assertTrue(resultsList.isDisplayed());
	}

	@Test(priority = 3, dependsOnMethods = "searchAccommodation", enabled = true)
	public void applyStarRatingFilter() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,2300)");

		// Apply a filter 4-star hotels
		WebElement fourStarFilter = wait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-filters-item='class:class=4']"))); // Check
																													// DevTools
		fourStarFilter.click();

		// Verify that the filter is applied (badge appears)
		WebElement appliedFilterBadge = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("button[aria-label='4 stars'] span[class='cd46a6a263']")));
		Assert.assertTrue(appliedFilterBadge.isDisplayed());
	}

	@Test(priority = 4, dependsOnMethods = "searchAccommodation", enabled = true)
	public void sortByLowestPrice() {
		// Open the sort dropdown
		WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("div[class='f6e3a11b0d a19a26a18c e95943ce9b'] div span[class='a297f43545']")));
		sortDropdown.click();

		// Click "Price (lowest first)" option
		WebElement lowestPriceOption = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Price (lowest first)']")));
		lowestPriceOption.click();

		// Verify sort was applied
		WebElement activeSort = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[@class='de576f5064 fcd8e16f81 ee2c96c33c']")));
		Assert.assertTrue(activeSort.getText().contains("Price (lowest first)"));
	}

	@Test(priority = 5, dependsOnMethods = "searchAccommodation", enabled = true)
	public void openAccommodationDetailPage() throws InterruptedException {
		Thread.sleep(3000);
		// Click the first property in the results list
		WebElement firstProperty = wait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-testid='availability-cta-btn']"))); // Check
																													// DevTools
		firstProperty.click();

		// Switch to new tab so it can read the new tab content
		Set<String> handles = driver.getWindowHandles();
		List<String> allTabs = new ArrayList<>(handles);
		driver.switchTo().window(allTabs.get(1));

		// Verify property name is displayed
		WebElement propertyName = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ddb12f4f86.pp-header__title")));
		Assert.assertTrue(propertyName.isDisplayed());

		// Verify price is displayed (the one that in the table)
		WebElement propertyPrice = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("span[class='prco-valign-middle-helper']"))); // DevTools
		Assert.assertTrue(propertyPrice.isDisplayed());
	}

	@Test(priority = 6, dependsOnMethods = "openAccommodationDetailPage", enabled = true)
	public void bookingProcess() throws InterruptedException {
		Thread.sleep(3000);
		// Click the "Reserve" button and it scrolls by default to select rooms
		WebElement reserveButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("hp_book_now_button")));
		reserveButton.click();

		// Select random room number from the dropdown
		WebElement roomDropdown = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("select[name^='nr_rooms")));
		Select myRoomselect = new Select(roomDropdown);
		int TotalRoomsOptions = roomDropdown.findElements(By.tagName("option")).size();
		int randomRoom = rand.nextInt(1, TotalRoomsOptions);
		myRoomselect.selectByIndex(randomRoom);
		System.out.println(TotalRoomsOptions);

		Thread.sleep(3000);

		// Click "I'll reserve" button
		WebElement illReserveBtn = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//button[contains(@class,'hp_rt_input px--fw-cta js-reservation-button')]")));
		illReserveBtn.click();

		// Wait for the booking form to be shown (guest details)
		WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname")));
		Assert.assertTrue(firstNameInput.isDisplayed());

		WebElement lastNameInput = driver.findElement(By.name("lastname"));
		Assert.assertTrue(lastNameInput.isDisplayed());

		WebElement emailInput = driver.findElement(By.name("email"));
		Assert.assertTrue(emailInput.isDisplayed());
	}

	@Test(priority = 7, dependsOnMethods = "bookingProcess", enabled = true)
	public void completeGuestDetailsForm() {
		// Fill first name
		WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstname")));
		firstNameInput.sendKeys(TestData.firstName);

		// Fill last name
		WebElement lastNameInput = driver.findElement(By.name("lastname"));
		lastNameInput.sendKeys(TestData.lastName);

		// Fill email (use random so Booking accepts it)
		WebElement emailInput = driver.findElement(By.name("email"));
		emailInput.sendKeys(TestData.guestEmail);

		// Confirm email is hidden! but it required! so It won't find it but also will
		// write the email, check the DevTools
		// Instead of findElement (which throws an exception), use findElements.
		// This will not throw an error — it just returns an empty list if the element
		// doesn’t exist yet.

		List<WebElement> confirmFields = driver.findElements(By.name("email_confirm"));
		if (!confirmFields.isEmpty()) {
			WebElement confirmField = confirmFields.get(0);
			confirmField.sendKeys(TestData.guestEmail);
		} else {
			System.out.println("Confirm email field not present, skipping...");
		}

		// Select country
		WebElement country = driver.findElement(By.name("cc1"));
		Select myCountryselect = new Select(country);
		myCountryselect.selectByVisibleText("Kuwait");

		// Fill phone number
		WebElement phoneInput = driver.findElement(By.name("phoneNumber"));
		phoneInput.sendKeys(TestData.phoneNumber);

		// Click "Next: Final details"
		WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[name='book']")));
		nextBtn.click();

		// Verify we moved to "Final details" step
		WebElement finalStepHeader = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//strong[normalize-space()='Finish booking']")));
		Assert.assertTrue(finalStepHeader.isDisplayed());
	}

	/*
	 * All the card fields (name, number, expiry, CVC) are inside one big <iframe
	 * title="Payment">. So Selenium can’t interact with them until you
	 * switchTo().frame(...).
	 */

	@Test(priority = 8, dependsOnMethods = "bookingProcess", enabled = true)
	public void fillPaymentFormDummy() {
		// Switch into the Payment iframe
		WebElement paymentFrame = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("iframe[title='Payment']")));
		driver.switchTo().frame(paymentFrame);

		// Cardholder's Name
		WebElement cardHolderName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));

		// Card Number
		WebElement cardNumber = driver.findElement(By.name("number"));
		cardNumber.sendKeys(TestData.VisaNumber);

		// Expiration Date
		WebElement expiryDate = driver.findElement(By.name("expirationDate"));
		expiryDate.sendKeys(TestData.VisaexpiryDate);

		// CVC
		WebElement cvc = driver.findElement(By.name("cvc"));
		cvc.sendKeys(TestData.VisaCVC);

		// Assertions
		Assert.assertEquals(cardHolderName.getAttribute("value"), TestData.CardHolderName);
		Assert.assertTrue(cardNumber.getAttribute("value").length() > 0);
		Assert.assertTrue(expiryDate.getAttribute("value").length() > 0);
		Assert.assertTrue(cvc.getAttribute("value").length() > 0);

		System.out.println("Dummy payment form filled successfully (no submit).");

		// Switch back to main page
		driver.switchTo().defaultContent();
	}

	@Test(priority = 9, enabled = true)
	public void userLogin() throws InterruptedException {
		driver.navigate().to(TestData.LoginPage);

		// Enter email
		WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		emailInput.sendKeys(TestData.LoginEmail);

		WebElement continueBtn = driver.findElement(By.cssSelector("button[type='submit']"));
		continueBtn.click();

		Thread.sleep(5000);

		WebElement allRightsReservedText = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id=\"root\"]/div/div/div[2]/div[2]/div/div/div/span[2]/span")));

		System.out.println(driver.getTitle()); // Let's make sure you're human | Booking.com

		Assert.assertTrue(allRightsReservedText.isDisplayed());
	}

	@Test(priority = 10, enabled = true)
	public void changeLanguage() {
//		driver.get("https://www.booking.com/");
		// Open language selector
		WebElement langBtn = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[@data-testid='header-language-picker-trigger']")));
		langBtn.click();
		// Choose Arabic
		WebElement arabicOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[lang='ar']")));
		arabicOption.click();
		// Verify that page html lang attribute changed
		// in the top of the HTML code lang attribute must be = ar
		String actualLang = driver.findElement(By.tagName("html")).getAttribute("lang");
		Assert.assertEquals(actualLang, "ar");

		WebElement ActualArabicHeading = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[@class='b98ba2834c f77a73f1ba f0a26771c4']")));
		System.out.println(ActualArabicHeading.getText()); // ابحث عن إقامتك التالية
		Assert.assertTrue(ActualArabicHeading.isDisplayed());
	}

	@Test(priority = 11, enabled = true)
	public void changeCurrency() {
//		driver.get("https://www.booking.com/");
		// Open currency selector
		WebElement currencyBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//button[@data-testid='header-currency-picker-trigger']//span[@class='ca2ca5203b'][normalize-space()='KWD']")));
		currencyBtn.click();
		// Choose USD (example)
		WebElement usdOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-testid='selection-item']")));
		usdOption.click();
		// Verify currency text is shown as USD
		WebElement currencyLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("button[data-testid='header-currency-picker-trigger'] span[class='ca2ca5203b']")));
		Assert.assertTrue(currencyLabel.getText().contains("USD"));
	}

	@Test(priority = 12, enabled = true)
	public void customerSupportAccess() {
		driver.get(TestData.CustomerSupportPage);

		// Verify page contains contact options
		WebElement helpCenter = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='f2d726909b f546354b44']")));
		Assert.assertTrue(helpCenter.getText().contains("Help"));
	}

	@Test(priority = 13, enabled = true)
	public void offersSectionDisplay() {
		driver.get("https://www.booking.com/");

		// Verify offers are listed
		WebElement offersList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[@class='c3bdfd4ac2 a0ab5da06c d46ff48a92 f728e61e72 d0acd69e66 c256f1a28a d726fb55bc aabe312a16 bc1c39249c']")));
		System.out.println(offersList.getText());
		Assert.assertTrue(offersList.isDisplayed());
	}

	@Test(priority = 14, enabled = true)
	public void addToWishlist() throws InterruptedException {
		driver.get("https://www.booking.com/");

		// Click the first property heart (wishlist icon)
		WebElement heartIcon = wait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-testid='wishlist-button']")));
		heartIcon.click();

		Thread.sleep(3000);
		// After clicking, it should become "active"
		String ariaPressed = heartIcon.getAttribute("aria-expanded");
		Assert.assertEquals(ariaPressed, "true");
	}

	@Test(priority = 15, enabled = false)
	public void mobileResponsiveness() {
		driver.get("https://www.booking.com/");
		// Check if hamburger menu is displayed in mobile view
		WebElement menuBtn = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[aria-label='Menu']")));
		Assert.assertTrue(menuBtn.isDisplayed());
	}
}
