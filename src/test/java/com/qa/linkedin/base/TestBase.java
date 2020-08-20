package com.qa.linkedin.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	private static Logger log=Logger.getLogger(TestBase.class);
	public static WebDriver driver;
	public WebDriverWait wait;
	
    public String readPropertyValue(String key) throws IOException {
    	log.debug("Fetching the Properties from config.properties file");
    	log.debug("create object for Properties class");
    	Properties prop=new Properties();
    	FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\com\\qa\\linkedin\\config\\config.properties");
    	log.debug("load all the properties");
    	prop.load(fis);
    	return prop.getProperty(key);
    }
	
	@BeforeSuite
	public void setup() throws IOException {
		log.debug("fetch the browser to run the test");
		String browserName=readPropertyValue("browser");
		if(browserName.equalsIgnoreCase("firefox")) {
			log.debug("launching the firefox browser");
			WebDriverManager.firefoxdriver().setup();
					driver = new FirefoxDriver();
		}else if(browserName.equalsIgnoreCase("chrome")) {
			Reporter.log("set CHROME_DRIVER_SILENT_OUTPUT_PROPERTY to avoid TimeOut log in console");
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			Reporter.log("launching Chromebrowser");
			WebDriverManager.chromedriver().setup();
			//interface refvar = new implementingclass();
			driver = new ChromeDriver();
		
		}else if(browserName.equalsIgnoreCase("ie")) {
			log.debug("launching ie browser");
			WebDriverManager.iedriver().setup();
					//launch the IE browser
				 driver = new InternetExplorerDriver();
		}
		log.debug("maximize the window");
		driver.manage().window().maximize();
	    log.debug("add implicit wait");
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			log.debug("create object for webdriverwait class");
			wait=new WebDriverWait(driver,30);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("open the url: "+readPropertyValue("url"));
		driver.get(readPropertyValue("url"));
		driver.manage().deleteAllCookies();
		
		
	}
	
	@AfterSuite
	public void tearDown() {
		log.debug("close the browser");
		if(driver!=null) {
			driver.quit();
		}
		
	}
	
	
	
}
