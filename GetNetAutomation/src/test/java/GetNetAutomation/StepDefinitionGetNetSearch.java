package GetNetAutomation;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

public class StepDefinitionGetNetSearch {
	
	private WebDriver driver;
	private WebDriverWait wait;
	private long timeoutInSeconds = 15L;
		
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
    	driver.manage().window().maximize();
    	driver.manage().timeouts().implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, timeoutInSeconds);
	}
	
	@After
	public void tearDown() {
		driver.close();
	}
	
    @Given("I access the GetNet homepage at {string}")
    public void openHomepage(String url) {
    	// open the homepage and await for it to be fully loaded
    	driver.get(url);
    	wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));   	
    }
    
    @When("I search for {string}")
    public void search(String query) {
    	// click the search button to open the search bar
    	wait.until(ExpectedConditions.elementToBeClickable(By.id("search-trigger")));
    	driver.findElement(By.id("search-trigger")).click();
    	
    	// find the search bar	
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("global-search-input")));
    	WebElement searchBar = driver.findElement(By.id("global-search-input"));
    	
        // input the query in the search bar        
        searchBar.sendKeys(query);
        
        // submit the query
        searchBar.submit();
    }
    
    @And("I select the {string} from the search result")
    public void selectSearchResult(String result) {	    	
    	// click the search result
    	wait.until(ExpectedConditions.elementToBeClickable(By.linkText(result)));
    	driver.findElement(By.linkText(result)).click();    	
    }
    
    @Then("a popup with the title {string} is displayed")
    public void checkPopupTitle(String popupTitle) {
    	// wait for the popup to open
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.switchTo().activeElement().isDisplayed();
            }
        });
        
        // check the title against the scenario example value
        WebElement element = driver.switchTo().activeElement().findElement(By.className("o-modal__title"));
        assertTrue(element.getText().contentEquals(popupTitle));
    }
}
