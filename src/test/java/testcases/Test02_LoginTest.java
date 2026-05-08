package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobject.HomePage;
import pageobject.LoginPage;
import pageobject.MyAccountPage;
import testBase.BaseClass;

public class Test02_LoginTest extends BaseClass{

	@Test(groups={"Sanity","Master"})
	public void Test02() {
		
		try {
		logger.info("***** Starting Test case *****");
		HomePage hp=new HomePage(driver);
		logger.info("***** Clicked on my account *****");
		hp.clickMyAccount();
		logger.info("***** Clicked on login*****");
		hp.clickLogin();
		
		LoginPage lp=new LoginPage(driver);
		logger.info("*****Entered the user details *****");
		lp.SetUsername(p.getProperty("username"));
		lp.SetPassword(p.getProperty("password"));
		lp.clicklogin();
		
		logger.info("***** Validating the my account page *****");
		MyAccountPage mp=new MyAccountPage(driver);
		Assert.assertTrue(mp.IsMyAccountPageExists());
		}
		catch(Exception e) {
			Assert.fail();
		}
		
		logger.info("***** finished *****");
	}
}
