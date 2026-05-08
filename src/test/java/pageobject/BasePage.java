package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
	WebDriver driver;
	public BasePage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this); //we can use findby anottation for finding the webelement withou using findelement 
	}
}
