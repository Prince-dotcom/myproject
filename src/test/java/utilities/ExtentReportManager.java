package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;



public class ExtentReportManager implements ITestListener {
		
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public String repName;
	
	public void onStart(ITestContext context) {
		
		/*
		SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);
		*/
		
		String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName="Test-Report-" + timestamp + ".html";
		
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Report");
		sparkReporter.config().setReportName("Opencart Funtional testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Application","opencart");
		extent.setSystemInfo("Module ","Admin");
		extent.setSystemInfo("Sub module","Customers");
		extent.setSystemInfo("User Name",System.getProperty("user.name"));
		extent.setSystemInfo("Environment","QA");
		
		String os= context.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating system", os);
		
		String browser= context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includeGroups= context.getCurrentXmlTest().getIncludedGroups();
		if(!includeGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includeGroups.toString());
		}
		
	}
	
	public void onTestSuccess(ITestResult result) {

		test=extent.createTest(result.getTestClass().getName()); // create a new entry in report
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS,result.getName()+" got Successfully executed"); //update status as pass/fail/skip
		
	}
	
	public void onTestFailure (ITestResult result) { //result contains the detail of pass/fail/skipped case
		
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName() +" got failed" );
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		try {
		    BaseClass b=new BaseClass();
			String imgpath=b.captureScreen(result.getName());;
			test.addScreenCaptureFromPath(imgpath);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		public void onTestSkipped(ITestResult result) {
			test = extent.createTest(result.getTestClass().getName());
			test.assignCategory(result.getMethod().getGroups());
			test.log(Status.SKIP, "Test case SKIPPED is:" + result.getName());
			test.log(Status.INFO,result.getThrowable().getMessage());
			
		}
		
		public void onFinish(ITestContext context) {

			extent.flush();
			
			String pathofExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
			File extentReport=new File(pathofExtentReport);
			
			try {
				Desktop.getDesktop().browse(extentReport.toURI());
			}catch (IOException e) {
				e.printStackTrace();
			}
			/* 
			try {
				URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
				
				ImageHtmlEmail email=new ImageHtmlEmail();
				email.setDataSourceResolver(new DataSourceUrlResolver(url));
				email.setHostName("smtp.googlemail.com");
				email.setSmtpPort(465);
				email.setAuthenticator(new DefaultAuthenticator("pavantraining@gmail.com","password"));
				email.setSSLOnConnect(true);
				email.setFrom("pavanoltraining@gmail.com");
				email.setSubject("Test Results");
				email.setMsg("Please find Attached report");
				email.addTo("pk8076224246@gmail.com");
				email.attach(url,"extent report","Please check report");
				email.send();
			}catch (Exception e){
				e.printStackTrace();
			}
			*/
		}
}
