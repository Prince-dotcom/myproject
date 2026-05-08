package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageobject.HomePage;
import pageobject.RegistrationPage;
import testBase.BaseClass;

public class Test01_AccountRegistration extends BaseClass{
	
	@Test(groups={"Regression","Master"})
	public void Test01() {
		
		logger.info("***** Starting Test case *****");
		
		try {
		HomePage hp=new HomePage(driver);
		
		hp.clickMyAccount();
		logger.info("***** Clicked on my account *****");
		hp.clickRegister();
		logger.info("***** Clicked on register *****");
		
		RegistrationPage rp=new RegistrationPage(driver);
		logger.info("***** Providing the user details *****");
		rp.setFirstName(RandomString());
		rp.setLastName(RandomString());
		rp.setEmail(RandomString()+"@gmail.com");
		rp.setTelephone(RandomNumber());
		String pass=RandomPassword();
		rp.setPassword(pass);
		rp.setConfPass(pass);
		rp.clickpolicy();
		rp.clickcontinue();
		
		logger.info("***** Validating the message*****");
		String msg=rp.confmsg();
		if(msg.equals("Your Account Has Been Created!")){
			Assert.assertTrue(true);
		}else {
			Assert.assertTrue(false);
			logger.error("Test Failed..");
			logger.debug("Debug logs..");
		}
		}
		catch(Exception e) {
			Assert.fail();
		}
		logger.info("***** Finished *****");
	}
}
