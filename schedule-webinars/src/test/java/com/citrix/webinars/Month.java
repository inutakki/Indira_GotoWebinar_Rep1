package com.citrix.webinars;

import java.util.List;

public enum Month {
	
	JANUARY (1,"January",31),
	FEBRUARY (2, "February", 27),
	MARCH (3, "March", 31),
	APRIL (4, "April", 30),
	MAY (5, "May", 31),
	JUNE (6, "June", 30),
	JULY (7, "July", 31),
	AUGUEST (8, "Auguest", 31),
	SEPTEMBER (9, "September", 30),
	OCTOBER (10, "October", 31),
	NOVEMBER (11, "November", 30),
	DECEMBER (12, "December", 31);
	
	private final int monthNum;
	private final String monthName;
	private final int daysofMonth;
	
	Month(int num, String name, int days){
		
		this.monthNum = num;
		this.monthName = name;
		this.daysofMonth = days; 
	}
	
	public static int getMonthNum(String name){
		Month[] list =   Month.values();
		for(Month m: list){
			if(m.monthName.equalsIgnoreCase(name)){
				return m.monthNum;
			}
		}
		return 0;			
	}
	
	public static String getMonthNamebyNumber(int n){
		Month[] list =   Month.values();
		for(Month m: list){
			if(m.monthNum == n){
				return m.monthName;
			}
		}
		return null;	
		
	}
	
	
	public static int getDaysofMonthBYName(String name){
		
		Month[] list =   Month.values();
		for(Month m: list){
			if(m.monthName.equalsIgnoreCase(name)){
				return m.daysofMonth;
			}
		}
		return 0;	
	}
	
	public static int getDaysofMonthByNumber(int num){
		
		Month[] list =   Month.values();
		for(Month m: list){
			if(m.monthNum == num){
				return m.daysofMonth;
			}
		}
		return 0;	
	}

}
