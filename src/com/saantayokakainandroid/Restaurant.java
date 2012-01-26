package com.saantayokakainandroid;

public class Restaurant implements Comparable<Restaurant> {

	private String name;
	private double location;
	private String cuisine;
	private String peopleMax;
	private String worth;
	private String openingTime;
	private String closingTime;
	private boolean isOpen;
	private String lastDate;
	private int kainCounter;
	private int declinedCounter;
	private Double score;
	private int cost;
	
	public Restaurant(String n, double loc){
		name = n;
		location = loc;
		isOpen = true;
		kainCounter = declinedCounter = 0;
		lastDate = "none provided";
	}
	
	public void setRestaurant(String n, double loc, String c, String p, String w, String oT, String cT, boolean isO)
	{
		name = n;
		location = loc;
		cuisine = c;
		peopleMax = p;
		worth = w;
		openingTime = oT;
		closingTime = cT;
		isOpen = isO;
	}
	
	public void setLastDate(String d)
	{
		lastDate = d;
		kainCounter++;
	}
	
	public void setParams(String ld, int k, int d)
	{
		lastDate = ld;
		kainCounter = k;
		declinedCounter = d;
	}
	
	public void setCost(int x)
	{
		cost = x;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public void incDeclinedC()
	{
		declinedCounter++;
	}
	
	public int getKainC()
	{
		return kainCounter;
	}
	
	public int getDeclinedC()
	{
		return declinedCounter;
	}
	
	public String getLastDate()
	{
		return lastDate;
	}
	
	public String getName()
	{
		return name;
	}
	
	public double getLocation()
	{
		return location;
	}
	
	public String getCuisine()
	{
		return cuisine;
	}
	
	public String getPeopleMax()
	{
		return peopleMax;
	}
	
	public String getWorth()
	{
		return worth;
	}
	
	public String getOT()
	{
		return openingTime;
	}
	
	public String getCT()
	{
		return closingTime;
	}
	
	public boolean getIsO()
	{
		return isOpen;
	}
	
	public double getScore()
	{
		return score;
	}
	
	public void setScore(double s)
	{
		score = s;
	}
	
	public int compareTo(Restaurant r) {
		return this.score.compareTo(r.score);
	}
}