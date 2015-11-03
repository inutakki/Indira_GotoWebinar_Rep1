package com.citrix.webinars;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.citrix.webinars.UI.LoginPage;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author: Indira Nutakki
 * Two test cases, first one for simple login
 * second test case depends on first method and it schedules a webinar using Data driven  object model
 * 
 */
public class WebinarTest 
    
{
	public WebDriver driver = null;
	Properties prop = new Properties();
	WebDriverWait wait = null;
	
	
	/**
	 * Before class initialize the chrome driver
	 * username and password is loaded using different properties for stage and Production
	 * Loading those values in properties in init method
	 * 
	 */
   @BeforeClass(alwaysRun=true)
   public void init()throws Exception{
	   System.setProperty("webdriver.chrome.driver", "C:\\Eclipse_workspaces\\demo_workspace\\schedule-webinars\\resources\\chromedriver\\chromedriver.exe");
	   driver = new ChromeDriver();
	   wait = new WebDriverWait(driver,30);
	   driver.get("http://www.gotomeeting.com/online/webinar");
	   FileInputStream fi = new FileInputStream("conf\\stage.properties");
	   prop.load(fi);
	   fi.close();
   }
   
   
   @AfterClass
   public void finish()throws Exception{
	   
	 driver.quit();
   }
   /*
    * read data from CSv file using openCSV.jar
    * returns two dimensional WebinarObjects 
    * 
    */
   @DataProvider(name = "webinarData")
   public Object[][] getData() throws Exception{
	   ArrayList<HashMap<String,String>> list = createDataObject("data\\webinarSchedule.csv");
	    WebinarObject[][] webinarTestObject = convertToWebinarObjects(list);
	    return webinarTestObject;
	   
	   
   }

    /**
     * @return the suite of tests being tested
     */
   @Test( groups = {"LightRegressions"})
    public void test_Login_To_Citrix()throws Exception{
	   
       Assert.assertEquals(driver.getTitle(), "Webinar & Online Conference | GoToWebinar");
	   driver.findElement(By.xpath(LoginPage.Login.button_signIn)).click();
	   driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	  
	   wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(LoginPage.Login.button_submit))));
	  
	  
	   driver.findElement(By.id( LoginPage.Login.textbox_emailAddress)).sendKeys(prop.getProperty("emailaddress"));
	   driver.findElement(By.id( LoginPage.Login.textbox_password)).sendKeys(prop.getProperty("password"));
	   driver.findElement(By.id(LoginPage.Login.button_submit)).click();
	   
	  wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LoginPage.Webinars.button_Schedule_Webinar)));
	  Assert.assertEquals(driver.getTitle(), "My Webinars");
   }

    /**
     * Login to Webinar page
     * Schedule a Webinar using data reading from Excel sheet and create WebinarObject and
     * Pass it to the method using DataProvider 
     */
   @Test(dependsOnMethods = "test_Login_To_Citrix", groups = {"Regressions"}, dataProvider ="webinarData" )
    public void testScheduleWebinar(WebinarObject web)throws Exception{
	   	
	   	driver.findElement(By.xpath(LoginPage.Webinars.button_Schedule_Webinar)).click();
    	String title = web.getTitle() + (new Random().nextInt(100));
    	String description = web.getDescription();
    	driver.findElement(By.id( LoginPage.Webinars.textbox_newWebinarTitle)).sendKeys(title);
    	driver.findElement(By.id( LoginPage.Webinars.textbox_descrption)).sendKeys(description);
    	
    	/* selecting recurrence  looping through the options*/
    	/*String reoccurence = "One session";*/
    	String reoccurence =web.getOccurences();
    	driver.findElement(By.xpath(LoginPage.Webinars.textBox_recurrence)).click();
    	
    	selectOption( LoginPage.Webinars.list_reccurence, reoccurence );
      
    	
    	/* clicking on calendar date picker on selecting the correct date*/
    	driver.findElement(By.xpath( LoginPage.Webinars.icon_calendaerDatePicker)).click();
    	Date d = new Date();    	   	   	
    	int month = web.getStartMonth();
    	int date = web.getStartDate();
    	int year = web.getStartYear();
    	String startTime = web.getStartTime();
    	String startTimePeriod =web.getStartTimeAMPM();
    	String endTime = web.getEndTime();
    	String endTimePeriod =web.getEndTimeAMPM();
    			
    	boolean verifyDateafteradding = verifyDateWithInMonth(month, date +3);
    	
    	//Assign correct month  after adding 3 days to  Start date, if total no. days goes beyond the number of days of that particular Month
    	int numOfDays = Month.getDaysofMonthByNumber(month);
    	int newDate = date;
    	if(verifyDateafteradding){
    		 newDate += 3;
    	}
    	else{
    		int i =1;
    		while(i <= 3 ){
    			boolean monthChanged = false;
    			if(newDate+1 <= numOfDays){
    				newDate = newDate+1;
    			}
    			else{
    				monthChanged = true;
    				newDate = 1;
    				month = month+1;
    				if(monthChanged){
    					if(month >12){
    						month = 1;
    						year = year+1;
    					}
    				numOfDays = Month.getDaysofMonthByNumber(month);
    				}
    				
    			} i++;
    		}
    	}
    	//selecting start date from calendar icon 
    	selectDate( newDate, month , year );
    	
    	//Deleting the value in start time text box, before user enters start time and select AM or PM
    	int i = 5;
    	while(i >0){
    		driver.findElement(By.xpath(LoginPage.Webinars.textbox_startTime)).sendKeys(Keys.BACK_SPACE);
    		i--;
    	}
    	driver.findElement(By.xpath(LoginPage.Webinars.textbox_startTime)).sendKeys(startTime);
    	driver.findElement(By.xpath(LoginPage.Webinars.list_starttimeAMPM_trigger)).click();
    	selectOption(LoginPage.Webinars.list_starttimeAMPM, startTimePeriod );
    
    	
    	//Deleting the value in end time text box, before user enters end time and select AM or PM
    	i = 5;
    	while(i >0){
    		driver.findElement(By.xpath(LoginPage.Webinars.textbox_endTime)).sendKeys(Keys.BACK_SPACE);
    		i--;
    	}
    	
    	driver.findElement(By.xpath(LoginPage.Webinars.textbox_endTime)).sendKeys(endTime);
    	driver.findElement(By.xpath(LoginPage.Webinars.list_endtimeAMPM_trigger)).click();
    	selectOption(LoginPage.Webinars.list_endtimeAMPM, endTimePeriod );
    	   	
    	//below commented code is for selecting end date if user selects occurs option other than one session - need some rework   	
    	/*int endMonth = 1;
    	int endDate = 28;
    	int endYear = 2016;
    	
    	boolean isDatewithinMonth = verifyDateWithInMonth(endMonth, endDate );
    	//verifyDateafteradding = endDate+3;
    	numOfDays = Month.getDaysofMonthByNumber(endMonth);
    	//newDate = endDate;
    	if(verifyDateafteradding){
    		 newDate += 3;
    	}
    	else{
    		int x =0;
    		while(x <= numofDaysofconFerence ){
    			boolean monthChanged = false;
    			if(date+1 <= numOfDays){
    				newDate = newDate+1;
    			}
    			else{
    				monthChanged = true;
    				newDate = 1;
    				endMonth = endMonth+1;
    				if(monthChanged){
    					if(endMonth >12){
    						endMonth = 1;
    						endYear = endYear+1;
    					}
    					numOfDays = Month.getDaysofMonthByNumber(endMonth);
    					
    				}
    				
    			} x++;
    		}
    	}
    	*/
    	
    	
    	if(!reoccurence.equals("One Session")){
    		driver.findElement(By.xpath(LoginPage.Webinars.icon_calendaerDatePicker_endDate)).click();
    		//selectDate( endDate, endMonth , year ); this code is for when user selects other options of recurrence schedule
    	}
    	
    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LoginPage.Webinars.button_schedule)));
    	driver.findElement(By.xpath(LoginPage.Webinars.button_schedule)).click();
    	
    	//Assertions After Scheduling Webinar session    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='inviteButton']/div/span")));
    	Assert.assertEquals(driver.getTitle(), "Manage Webinar");
    	Assert.assertEquals(driver.findElement(By.xpath(LoginPage.MyWebinars.text_title)).getText(), title);
    	Assert.assertEquals(driver.findElement(By.xpath(LoginPage.MyWebinars.teXt_description)).getText(), description);
    	String dayLight = "";
    	if((month >= 11 && month  <= 12)||(month >=1 && month <=3)){
    		if(month == 3){
    			if( date > 13){
    				dayLight = "PDT";
    			}
    		}
    		else{
    			dayLight = "PST";
    		}
     	}
    	else{
    		 dayLight = "PDT";
    	}
    	String dateToverify= Month.getMonthNamebyNumber(month).substring(0, 3)+" "+newDate	+", "+year+ " "+startTime+ " "+startTimePeriod+
    			             " - "+endTime+" "+endTimePeriod+" "+dayLight;
    	System.out.println(dateToverify);
    	String datesonWebinarPage = driver.findElement(By.xpath(LoginPage.MyWebinars.text_webinarSchdule)).getText();
    	System.out.println(datesonWebinarPage);
    	Assert.assertTrue(datesonWebinarPage.contains(dateToverify));
     }
    
    /*
     * @parameters: date, month ,year
     * Selects the date from calendar icon
     * 
     */
    public void selectDate(int date, int month, int year) throws InterruptedException{
    	WebDriverWait wait = new WebDriverWait(driver,30);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoginPage.Webinars.displayMonth_OnCalender)));
    	String displayCurrMonth = driver.findElement(By.xpath(LoginPage.Webinars.displayMonth_OnCalender)).getText();
    	Calendar c = Calendar.getInstance();
    	String displayYear = driver.findElement(By.xpath(LoginPage.Webinars.displayYear_OnCalender)).getText();
    	Thread.sleep(300);;
    	int diffMonth = 0;
    	if(year == Integer.parseInt(displayYear)){
    		int mon = Month.getMonthNum(displayCurrMonth);
    		if(month > mon){
        	  diffMonth = month - Month.getMonthNum(displayCurrMonth);
    		}
    		else{
    			System.out.println(" wrong month");
    		}
       	}
    	else if( year > Integer.parseInt(displayYear) ){
    		int num = Month.getMonthNum(displayCurrMonth);
    		diffMonth = (12 - num) +month; 
    	}
    	else{
    		System.out.println("Year should be same or greater than current year");
    	}
    	
    	
    	
    	String next_previous_Month  ="";
    	if(diffMonth < 0){
    		
    		 next_previous_Month = LoginPage.Webinars.icon_prevMonth; 
    		diffMonth = diffMonth*(-1);
    	}
    	else{
    		 next_previous_Month = LoginPage.Webinars.icon_nextMonth;  
    	}
    	for(int i = 1; i <= diffMonth; i++){
    		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(next_previous_Month)));
    		 driver.findElement(By.xpath(next_previous_Month)).click();
    	}
    	displayCurrMonth = driver.findElement(By.xpath(LoginPage.Webinars.displayMonth_OnCalender)).getText();
    	Assert.assertEquals(Month.getMonthNamebyNumber(month), displayCurrMonth);
    	displayYear = driver.findElement(By.xpath(LoginPage.Webinars.displayYear_OnCalender)).getText();
    	Assert.assertEquals(year, Integer.parseInt(displayYear));
    	selectDateFromTable("//*[@id='ui-datepicker-div']/table", date);

    }
    
    /*
     * @param: Calender Date Table Id, date to be selected
     * This Function selects the date from calendar table
     * 
     */
    public String selectDateFromTable(String TableId, int date){
    	
    	String tableRowElements = TableId + "//tr";
    	List<WebElement> rowElements =  driver.findElements(By.xpath(tableRowElements));
    	boolean dateFound = false;
    	int index = 0;
    	for(WebElement el : rowElements ){
    		index = 1;
    		List<WebElement> dataElements =  driver.findElements(By.xpath(".//td/a"));
    		for(WebElement e : dataElements ){
    			String s = e.getText();
    				if(!s.isEmpty()){
	    				index++; //forindex o
	    				int d = Integer.parseInt(e.getText());
	    				if(d == date){
	    					System.out.println("index when selecting data element " + index);
	    					e.click();
	    					dateFound = true;
	    					break;
	    				}
	    				
	    			}
    			}
    			
    		    if(dateFound){
    			break;
    		}
    		
    	}
    	
    	System.out.println("index:"+ index);
    	String day = driver.findElement(By.xpath(String.format("//*[@id='ui-datepicker-div']/table/thead/tr/th[%s]",index%7))).getText();
    	System.out.println(day);
    	return day;
    }
    
   /*
    * @Parameters: int month, int date
    * 
    *  returns number of days in Month
    */
    
    public int getMonthAfterAddingSomeDays(int month, int date){
    	
    	if(date > Month.getDaysofMonthByNumber(month) ){
    		if((month+1) == 13)
    			return 1;
    		else{
    			return month+1;
    		}
    	}
    	else{
    		return month;
    	}

    }
    /*
     * @parameters: takes Month and date
     * verify if date is with in the month
     * returns boolean
     */
    public boolean verifyDateWithInMonth(int month, int date){
    	
    	if(date <= Month.getDaysofMonthByNumber(month)){
    		return true;
    	}
    	
    	return false;
     }
    
    /*
     * @param: file
     * returns ArrayList of hashMaps
     * Reading an input file and changing to list of HashMaps
     */
    public ArrayList<HashMap<String,String>> createDataObject(String fileName) throws Exception{
    	
    	CSVReader reader = new CSVReader(new FileReader(fileName), ',','"',0);
    	String[] colHeaders;
    	String [] data;
    	int index = 0;
    	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    	WebinarObject webinar = WebinarObject.createInstance();
    	
    		colHeaders =reader.readNext(); 
    	
    	while((data = reader.readNext())!= null){
    		HashMap<String,String> map = new HashMap<String,String>();
    		for(int i = 0; i < colHeaders.length; i++){
    			map.put(colHeaders[i], data[i]);    			
    		}
    		
    		list.add(map);
    	}
    	
    	return list;
    	
    }
    
    /**
     * @params: list of Hash Maos
     * returns two dimensional webinar objects for processing
     * 
     */
    public WebinarObject[][] convertToWebinarObjects(ArrayList<HashMap<String,String>> list){
    	
    	WebinarObject[][] webinarTestData = new WebinarObject[list.size()][1];
    		for(int j =0; j < list.size(); j++ ){
    			
    			WebinarObject web = WebinarObject.createInstance();
    			
    			if(list.get(j).get("Title") != null && !list.get(j).get("Title").isEmpty()){
    				web.setTitle(list.get(j).get("Title"));
    			}
    			if(list.get(j).get("Description") != null && !list.get(j).get("Description").isEmpty()){
    				web.setDescription(list.get(j).get("Description"));
    			}
    			if(list.get(j).get("Occurs") != null && !list.get(j).get("Occurs").isEmpty()){
    				web.setOccurences(list.get(j).get("Occurs"));
    			}
    			if(list.get(j).get("StartDate") != null && !list.get(j).get("StartDate").isEmpty()){
    				web.setStartDate(Integer.parseInt(list.get(j).get("StartDate")));
    			}
    			if(list.get(j).get("StartMonth") != null && !list.get(j).get("StartMonth").isEmpty()){
    				web.setStartMonth(Integer.parseInt(list.get(j).get("StartMonth")));
    			}
    			if(list.get(j).get("StartYear") != null && !list.get(j).get("StartYear").isEmpty()){
    				web.setStartYear(Integer.parseInt(list.get(j).get("StartYear")));
    			}
    			if(list.get(j).get("EndDate") != null && !list.get(j).get("EndDate").isEmpty()){
    				web.setEndtDate(Integer.parseInt(list.get(j).get("EndDate")));
    			}
    			if(list.get(j).get("EndMonth") != null && !list.get(j).get("EndMonth").isEmpty()){
    				web.setEndMonth(Integer.parseInt(list.get(j).get("EndMonth")));
    			}
    			if(list.get(j).get("EndYear") != null && !list.get(j).get("EndYear").isEmpty()){
    				web.setEndYear(Integer.parseInt(list.get(j).get("EndYear")));
    			}
    			if(list.get(j).get("StartTime") != null && !list.get(j).get("StartTime").isEmpty()){
    				web.setStartTime(list.get(j).get("StartTime"));
    			}
    			if(list.get(j).get("StartAMPM") != null && !list.get(j).get("StartAMPM").isEmpty()){
    				web.setStartTimeAMPM(list.get(j).get("StartAMPM"));
    			}
    			if(list.get(j).get("EndTime") != null && !list.get(j).get("EndTime").isEmpty()){
    				web.setEndTime(list.get(j).get("EndTime"));
    			}
    			if(list.get(j).get("EndAMPM") != null && !list.get(j).get("EndAMPM").isEmpty()){
    				web.setEndTimeAMPM(list.get(j).get("EndAMPM"));
    			}
    			webinarTestData[j][0] = web;
    		}
       	return webinarTestData;
    }
    
   /*
    * @params: xpath, text to be selected
    * selects the option in drop down 
    */
    public void selectOption(String xpath, String toFind){
    	List<WebElement> list = driver.findElements(By.xpath(xpath));
    	for(WebElement e: list){
    		if(e.getText().equalsIgnoreCase(toFind)){
    			System.out.println(e.getText());
    			e.click();
    			break;
    		}
    	}
    	
    }
}
