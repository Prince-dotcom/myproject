package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {

	public static WebDriver driver;	
	public Logger logger;
	public Properties p;
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException {
		
		//Loading properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		logger=LogManager.getLogger(this.getClass());
		
		//this piece of code is used for selenium grid here we are selecting whether test case will execute local or remote
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities cap=new  DesiredCapabilities();
			
			//selecting operating system
			switch(os.toLowerCase()){
				case "windows" : cap.setPlatform(Platform.WIN11); break;
				case "linux" : cap.setPlatform(Platform.LINUX); break;
				case "mac": cap.setPlatform(Platform.MAC); break;
				default : System.out.println("No matching os"); return;
			}
			//selecting browser
			switch(br.toLowerCase()) {
				case "chrome" : cap.setBrowserName("chrome"); break;
				case "firefox" : cap.setBrowserName("firefox"); break;
				case "edge" : cap.setBrowserName("MicrosoftEdge"); break;
				default : System.out.println("No matching browser"); return;
			}
			
			//selecting the webdriver and passing the selenium grid hub url cap variable 
			driver = new RemoteWebDriver(new URL("http://192.168.1.6:4444/wd/hub"),cap);
			
		}
		//selecting the local environment to run test case
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch(br) {
			case "chrome": driver=new ChromeDriver(); break;
			case "edge": driver=new EdgeDriver(); break;
			default : System.out.println("Invalid browser"); return;
			}
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appurl")); //reading url from properties file
		driver.manage().window().maximize();	
		
	}
	
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown() {
		driver.quit();
	}
	
	public String RandomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}
	
	public String RandomPassword() {
		return RandomStringUtils.randomAlphanumeric(6);
	}
	public String RandomNumber() {
		return RandomStringUtils.randomNumeric(10);
	}
	
	public String captureScreen (String tname) throws IOException {
		
		String timestamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot=(TakesScreenshot) driver;
		File sourcefile=takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_"+ timestamp + ".png";
		File targetfile=new File(targetFilePath);
		
		sourcefile.renameTo(targetfile);
		
		return targetFilePath;
	}
	
}
