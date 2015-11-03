package com.citrix.webinars;

public class WebinarObject {

	
		// TODO Auto-generated constructor stub
		
		private String title ;
		private String description ="";
		private String occurences = "";
		private int startDate= 0;
		private int startMonth=0;
		private int startYear= 0;
		private String startTime="";
		private String startTimeAMPM="";
		private int endDate= 0;
		private int endMonth= 0;
		private int endYear= 0;
		private String endTime="";
		private String endTimeAMPM="";
		
		public WebinarObject(){
			
		}
		public static WebinarObject createInstance(){
			
			return new WebinarObject();
			
			
		}
		
		public void setTitle(String title){
			this.title = title;
		
		}
		
		public void setDescription(String description){
			this.description = description;
		
		}
		
		public void setOccurences(String occurences){
			this.occurences = occurences;
		
		}
		public void setStartDate(int startDate){
			this.startDate = startDate;
		
		}
		public void setStartMonth(int startMonth){
			this.startMonth = startMonth;
		
		}
		public void setStartYear(int startYear){
			this.startYear = startYear;
		
		}
		public void setStartTime(String startTime){
			this.startTime = startTime;
		
		}
		public void setStartTimeAMPM(String startTimeAMPM){
			this.startTimeAMPM = startTimeAMPM;
		
		}
		public void setEndtDate(int endDate){
			this.endDate = endDate;
		
		}
		public void setEndMonth(int endMonth){
			this.endMonth = endMonth;
		
		}
		public void setEndYear(int endYear){
			this.endYear = endYear;
		
		}
		public void setEndTime(String endTime){
			this.endTime = endTime;
		
		}
		public void setEndTimeAMPM(String endTimeAMPM){
			this.endTimeAMPM = endTimeAMPM;
		
		}
		
		public String getTitle(){
			return this.title ;
		
		}
		
		public String getDescription(){
			return this.description; 
		
		}
		
		public String getOccurences(){
			return this.occurences; 
		
		}
		public int getStartDate(){
			return this.startDate ;
		
		}
		public int getStartMonth(){
			return this.startMonth ;
		
		}
		public int getStartYear(){
			return this.startYear;
		
		}
		public String getStartTime(){
			return this.startTime ;
		
		}
		public String getStartTimeAMPM(){
			return this.startTimeAMPM ;
		
		}
		public int getEndtDate(){
			return this.endDate;
		
		}
		public int getEndMonth(){
			return this.endMonth ;
		
		}
		public int getEndYear(){
			return this.endYear;
		
		}
		public String getEndTime(){
			return this.endTime ;
		
		}
		public String getEndTimeAMPM(){
			return this.endTimeAMPM;
		
		}
	
	

}
