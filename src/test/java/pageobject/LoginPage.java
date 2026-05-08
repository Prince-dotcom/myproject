package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath="//input[@id='input-email']") WebElement namefield;
	@FindBy(xpath="//input[@id='input-password']") WebElement pswdfield;
	@FindBy(xpath="//input[@value='Login']") WebElement btnlogin;
	
	public void SetUsername(String name) {
		namefield.clear();
		namefield.sendKeys(name);
	}
	
	public void SetPassword(String psw) {
		pswdfield.clear();
		pswdfield.sendKeys(psw);
	}
	
	public void clicklogin() {
		btnlogin.click();
	}
	
}

