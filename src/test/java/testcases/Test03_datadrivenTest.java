package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobject.HomePage;
import pageobject.LoginPage;
import pageobject.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/* Data valid login success test pass logout
   Data valid login failed test failed
   Data invalid login success test failed logout
   Data invalid login failed test pass 
 */
public class Test03_datadrivenTest extends BaseClass{

	@Test(dataProvider="LoginData" ,dataProviderClass=DataProviders.class ,groups="Datadriven")
	public void verify_login(String email,String password,String exp) {
		//Homepage
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Login Page
		LoginPage lp=new LoginPage(driver);
		lp.SetUsername(email);
		lp.SetPassword(password);
		lp.clicklogin();
		
		//Validation
		MyAccountPage mp=new MyAccountPage(driver);
		boolean targetpage=mp.IsMyAccountPageExists();
		
		if(exp.equalsIgnoreCase("Vaild")) {
			if(targetpage==true) {
				mp.logout();
				Assert.assertTrue(true);
			}
			else {
				Assert.assertTrue(false);
			}
		}
		
		if(exp.equalsIgnoreCase("Invalid")) {
			if(targetpage==true) {
				mp.logout();
				Assert.assertTrue(false);
			}
			else {
				Assert.assertTrue(true);
			}
		}
	}catch(Exception e) {
		Assert.fail();
	}
}
}
