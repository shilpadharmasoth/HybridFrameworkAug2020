package com.qa.linkedin.page;

import org.apache.commons.math3.analysis.function.Exp;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.log4testng.Logger;

public class SearchResultsPage extends BasePageWeb {
	
	private static Logger log=Logger.getLogger(SearchResultsPage.class);
	//WebDriverWait wait=null;
	
	public SearchResultsPage() {
		//wait=new WebDriverWait(driver,30);
		super();
		PageFactory.initElements(driver,this);
	}

	@FindBy(xpath="//*[contains(@class,'search-results__total')]")
	private WebElement searchResultsTxt;
	
	@FindBy(xpath="//*[@id='feed-tab-icon']")
	private WebElement homeTab;
	
	String searchResultsPgTitle="Search | LinkedIn";
	
	public void validateSearchResultsTitle() {
		log.debug("waiting for the search results page title -- Search | LinkedIn"+searchResultsPgTitle);
		wait.until(ExpectedConditions.titleContains(searchResultsPgTitle));
		Assert.assertTrue(driver.getPageSource().contains(driver.getTitle()));
		
	}
	
	public long getResultsCount() {
		validateSearchResultsTitle();
		log.debug("wait for the search results text");
		wait.until(ExpectedConditions.visibilityOf(searchResultsTxt));
		log.debug("getting the results text from webpage");
		String txt=searchResultsTxt.getText();
		log.debug("results text is : "+txt);
		//txt="showing 40,938 reults";
		String[] str=txt.split(" ");
		//str[]=["showing ","40,938","results"]
		//          0           1         2
		log.debug("results count in string format is -->"+str[1]);
		String finalStringcnt=str[1].replace(",", "").trim();
		//convert the String into long format
		long count=Long.parseLong(finalStringcnt);
		//convert the String into integer format
		//int cnt=Integer.pareInt(finalStringcnt);
		return count;
	}
	
	public void clickHomeTab() throws InterruptedException {
		log.debug("wait for the home tab");
		wait.until(ExpectedConditions.visibilityOf(homeTab));
		log.debug("check whether homeTab is clickable or not");
		//wait.until(ExpectedConditions.elementToBeClickable(homeTab));
		click(homeTab);
	}
	
	
	
	
	
	
	
	
	
}
