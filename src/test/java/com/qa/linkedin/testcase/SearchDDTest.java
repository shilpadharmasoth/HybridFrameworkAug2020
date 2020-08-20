package com.qa.linkedin.testcase;

import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.qa.linkedin.base.TestBase;
import com.qa.linkedin.page.LinkedinHomePage;
import com.qa.linkedin.page.LinkedinLoggedInPage;
import com.qa.linkedin.page.SearchResultsPage;
import com.qa.linkedin.util.ExcelUtils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterClass;

public class SearchDDTest extends TestBase {
	private static Logger log=Logger.getLogger(SearchDDTest.class);
	private String path=System.getProperty("user.dir")+"\\src\\test\\java\\com\\qa\\linkedin\\data\\searchData.xlsx";
	
	LinkedinHomePage lHmPg=null;
	LinkedinLoggedInPage llPg=null;
	SearchResultsPage srPg=null;
	
	
	
  @Test(dataProvider = "dp")
  public void searchPeopleTest(String s) throws InterruptedException {
	  log.debug("started executing the search for: "+s);
	  //llPg.verifyProfileRailCard();
	 // llPg.doPeopleSearch(s);
	  srPg=llPg.doPeopleSearch(s);
	  Thread.sleep(3000);
	  long count=srPg.getResultsCount();
	  log.debug("results count for "+s+" heyword is: "+count);
	  srPg.clickHomeTab();
	  
  }

  @DataProvider
  public Object[][] dp() throws InvalidFormatException, IOException {
    Object [][] data=new ExcelUtils().getTestData(path, "Sheet1");
	  return data;
    
  }
  @BeforeClass
  public void beforeClass() throws IOException, Exception {
	  log.debug("initializing the page objects");
	  lHmPg=new LinkedinHomePage();
	  llPg=new LinkedinLoggedInPage();
	  srPg=new SearchResultsPage();
	  lHmPg.verifyLinkedinHomePageTitle();
	  lHmPg.verifyLinkedinLogo();
	  lHmPg.clickSigninLink();
	  lHmPg.verifyLinkedinLoginPageTitle();
	  lHmPg.verifyWelcomeBackHeaderText();
	  llPg=lHmPg.doLogin(readPropertyValue("userName"),readPropertyValue("passwd"));
	  Thread.sleep(3000);
	  
  }

  @AfterClass
  public void afterClass() throws InterruptedException {
	  log.debug("logout from application");
	  llPg.doLogout();
	  lHmPg.verifyLinkedinHomePageTitle();
  }

}
