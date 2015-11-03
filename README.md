# Indira_GotoWebinar_Rep1

Citrix QE Selenium Automation Task (Senior/Staff):

This project automates all the test steps mentioned in 'Citrix QE Selenium Automation Task challenge'. Tried to accommodate all the suggested requirements,
This intializes chrome webdriver and also reads the properties file and assign properies object for usename and password  in the before class test method.

Uploaded are two project folders:

1. schedule-webinars: 
Month.java - enum class for month, it has methods to return days in month per month name, Month number per month name

WebinarObject.java - Created Webinar Object with all the member fields of Schedule a webinar 

WebinarTest.java - Test class with two test cases test_Login_To_Citrix,testScheduleWebinar test methods

test_Login_To_Citrix - just simple login and assert title

testScheduleWebinar - It depends on login method, if second method will run only test_Login_To_Citrix  method is successful, 
and it reads the data values from excel file using data provider.

POM.xml - maveb config,

2. UIElements: Has one java File. Contains all the required UI element Ids.

TODO: Further Automation possiblities:
This project can be further enhanced by automating following test cases:

1. Verify validation messages, if user doesn't provide the Title and decription, if those are mandatory fields and length limitations on those fields.

2. Verify selecting different options for occurs,webinar timezone, webinar language drop down and make sure  user selection appears correctly in the selection box and also after clicking on schedule button in my webinars page by data driven method using excel file.

3. Verify validtaioon message if user gives start date in the past(in this user is not able to select it is not a link) and end date is less than start date and end time less than start time.

4. Verify user is able to type in start and end date text fields (here only able to select using  calendar icon).

5. Verify user is able to select webinar, that is previously created from copy Webinar drop down and make sure  values are getting populated are same as originally created one.

6. Edit the copied webinar and save it and verify the edited values are getting saved.

7. Verify deleted webinars should not be dispyed in copy webinar dropdown.

8. Verify user is able to create webinar with same title, same date and time schedule that got deleted before.

9. Verify running same test case in multiple browsers.

10. Verify clicking on different links are properly landing on correct pages.

11. Verify browser warning message for different system requirements and verify user clicks on X mark on that warning message should close.

12. Verify Logout should invalidtae the session, using browesr back button shouldn't go to the previous page the user visited after logout.


Build and Run Instructions:
- Open the projects folders in Eclipse
- open the ScheduleWebinar project -> src->test-> java -> com->citrix->webinars->WebinarTest.java and right click and run as Testngtest



