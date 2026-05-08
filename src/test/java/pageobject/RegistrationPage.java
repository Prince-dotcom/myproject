package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class RegistrationPage extends BasePage{

	public RegistrationPage(WebDriver driver) {
		super(driver);
	}
	
	
	@FindBy(xpath="//input[@id='input-firstname']") WebElement InFirstName;
	@FindBy(xpath="//input[@id='input-lastname']") WebElement InLastName;
	@FindBy(xpath="//input[@id='input-email']") WebElement InEmail;
	@FindBy(xpath="//input[@id='input-telephone']") WebElement InTelephone;
	@FindBy(xpath="//input[@id='input-password']") WebElement InPass;
	@FindBy(xpath="//input[@id='input-confirm']") WebElement InConfPass;
	@FindBy(xpath="//input[@name='agree']") WebElement chkPolicy;
	@FindBy(xpath="//input[@value='Continue']") WebElement btncontinue;
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']") WebElement msg;
	
	public void setFirstName(String Fname) {
		InFirstName.sendKeys(Fname);
	}
	
	public void setLastName(String Lname) {
		InLastName.sendKeys(Lname);
	}
	
	public void setEmail(String email) {
		InEmail.sendKeys(email);
	}
	
	public void setTelephone(String number) {
		InTelephone.sendKeys(number);
	}
	
	public void setPassword(String pass) {
		InPass.sendKeys(pass);
	}
	
	public void setConfPass(String confpass) {
		InConfPass.sendKeys(confpass);
	}
	
	public void clickpolicy() {
		chkPolicy.click();
	}
	
	public void clickcontinue() {
		btncontinue.click();
	}

	public String confmsg() {
		try {
			return (msg.getText());
		}catch(Exception e) {
			return e.getMessage();
		}
	}
}
