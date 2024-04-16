package kirin.barcodescanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BarcodescannerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BarcodescannerApplication.class, args);
	}

	public void run(String... args){

	}

//	@Override
//	public void run(String... args) throws InterruptedException {
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--lang=ko");
//		options.addArguments("--start-maximized");
//		System.setProperty("webdriver.chrome.driver", "src/main/resources/static/chromedriver_win32/chromedriver.exe");
//		WebDriver driver = new ChromeDriver(options);
//		String url = "https://cu.bgfretail.com/product/product.do?category=product&depth2=4&depth3=1";
//		driver.get(url);
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		wait.until(ExpectedConditions.urlToBe(url));
//
//		cuCrawler(6, "간편식사", driver);
//
//		((JavascriptExecutor) driver).executeScript("gomaincategory('20',2)");
//		Thread.sleep(1000);
//		cuCrawler(2, "즉석조리", driver);
//
//		((JavascriptExecutor) driver).executeScript("gomaincategory('30',3)");
//		Thread.sleep(1000);
//		cuCrawler(39, "과자류", driver);
//
//		((JavascriptExecutor) driver).executeScript("gomaincategory('40',4)");
//		Thread.sleep(1000);
//		cuCrawler(8, "아이스크림", driver);
//
//		((JavascriptExecutor) driver).executeScript("gomaincategory('50',5)");
//		Thread.sleep(1000);
//		cuCrawler(68, "식품", driver);
//
//		((JavascriptExecutor) driver).executeScript("gomaincategory('60',6)");
//		Thread.sleep(1000);
//		cuCrawler(27, "음료", driver);
//
//		((JavascriptExecutor) driver).executeScript("gomaincategory('70',7)");
//		Thread.sleep(1000);
//		cuCrawler(34, "생활용품", driver);
//
//
//		driver.quit();
//
//	}

	private static void clickMoreButton(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("nextPage(1)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void dataProcessing(List<WebElement> prodTextList, List<String> productNames, List<String> productPrices, List<String> barcodeNumbers, List<String> discounts ) {
		for (WebElement prodElement : prodTextList) {
			String productName = prodElement.findElement(By.cssSelector("div.prod_text > div.name > p")).getText().trim();
			productNames.add(productName);

			String productPrice = prodElement.findElement(By.cssSelector("div.prod_text > div.price > strong")).getText().trim();
			productPrices.add(productPrice);

			String alt = prodElement.findElement(By.cssSelector("div.prod_img img")).getAttribute("alt");
			String barcodeNumber = alt.replace(".jpg", "").replace(".png", "");

			barcodeNumbers.add(barcodeNumber);

			WebElement badgeElement = prodElement.findElement(By.cssSelector("div.badge"));
			String badgeText = badgeElement.getText().trim();
			if (badgeText.isEmpty()) {
				badgeText = "0";
			}
			discounts.add(badgeText);
		}
	}

	public void cuCrawler(int clickButtonNum, String category, WebDriver driver) throws InterruptedException {
		List<String> productNames = new ArrayList<>();
		List<String> productPrices = new ArrayList<>();
		List<String> barcodeNumbers = new ArrayList<>();
		List<String> discounts = new ArrayList<>();

		for (int i = 0; i < clickButtonNum; i++) {
			clickMoreButton(driver);
		}

		List<WebElement> prodTextList1 = driver.findElements(By.cssSelector("li.prod_list"));
		dataProcessing(prodTextList1, productNames, productPrices, barcodeNumbers, discounts);
		saveData(barcodeNumbers, productNames, productPrices, category, discounts);
	}

	@Autowired
	private kirin.barcodescanner.repository.jdbcSaveRepository jdbcSaveRepository;
	public void saveData(List<String> barcodeNumbers, List<String> productNames, List<String> productPrices, String category,List<String> discounts) {
		jdbcSaveRepository.saveProduct(barcodeNumbers, productNames, productPrices, category, discounts);
	}
}