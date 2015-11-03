package com.citrix.webinars.UI;

public class LoginPage {

	
		
		public static class Login{
			public static final String button_signIn = "//*[@id='content-body']//li[@class='show-for-medium-up last']"; 
			public static final String textbox_emailAddress ="emailAddress";
			public static final String textbox_password = "password";
			public static final String button_submit = "submit";
			
		}
		
		public static class Webinars{
			public static final String button_Schedule_Webinar = "//*[@id='scheduleWebinar']/div/span";
			public static final String textbox_newWebinarTitle ="name";
			public static final String textbox_descrption ="description";
			public static final String textBox_recurrence = "//*[@id='recurrenceForm_recurs_trig']/span[1]";
			public static final String list_reccurence = "//*[@id='recurrenceForm_recurs__menu']/ul/li";
			public static final String textbox_startDate ="//*[@id='webinarTimesForm.dateTimes_0.baseDate']";
			public static final String textbox_endDate  = "//*[@id='recurrenceForm.endDate']";
			public static final String icon_calendaerDatePicker = "//*[@id='dateContainer_0']/div[1]/div/img";
			public static final String icon_calendaerDatePicker_endDate = "//*[@id='recur-end-date']/div/div/img";
			public static final String textbox_startTime = "//*[@id='webinarTimesForm.dateTimes_0.startTime']";
			public static final String list_starttimeAMPM_trigger = "//*[@id='webinarTimesForm_dateTimes_0_startAmPm_trig']/span[1]/span";
			public static final String list_starttimeAMPM = "//*[@id='webinarTimesForm_dateTimes_0_startAmPm__menu']/ul/li";
			public static final String textbox_endTime = "//*[@id='webinarTimesForm.dateTimes_0.endTime']";
			public static final String list_endtimeAMPM_trigger = "//*[@id='webinarTimesForm_dateTimes_0_endAmPm_trig']/span[2]";
			public static final String list_endtimeAMPM = "//*[@id='webinarTimesForm_dateTimes_0_endAmPm__menu']/ul/li";
			public static final String icon_nextMonth = "//*[@id='ui-datepicker-div']/div[1]/a[2]/span";
			public static final String icon_prevMonth ="//*[@id='ui-datepicker-div']/div[1]/a[1]/span";
			public static final String displayMonth_OnCalender = "//*[@id='ui-datepicker-div']/div[1]/div/span[1]";
			public static final String displayYear_OnCalender = "//*[@id='ui-datepicker-div']/div[1]/div/span[2]";
			public static final String button_schedule = "//*[@id='schedule.submit.button']";
			
		}
		
		public static class MyWebinars{
			
			public static final String text_title ="//*[@id='trainingName']";
			public static final String teXt_description ="//*[@id='trainingDesc']";
			public static final String text_webinarSchdule = "//*[@id='dateTime']/p";
			public static final String logout = "//*[@id='manageWebinar']/div[2]/div[1]/div/ul/li[5]/a";

			
			
		}
	

}
