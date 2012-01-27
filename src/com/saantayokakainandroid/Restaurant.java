package com.saantayokakainandroid;

public class Restaurant implements Comparable<Restaurant> {

	public String name;
	public double location;
	public String cuisine;
	public Integer maxPeople;
	public String worth;
	public String openingTime;
	public String closingTime;
	public boolean isOpen;
	public String lastDate;
	public int kainCounter;
	public int declinedCounter;
	public Double score;
	public int cost;
	
	public Restaurant(String n, double loc){
		name = n;
		location = loc;
		isOpen = true;
		kainCounter = declinedCounter = 0;
		lastDate = "none provided";
	}
	
	public void setRestaurant(String n, double loc, String c, Integer p, String w, String oT, String cT, boolean isO)
	{
		name = n;
		location = loc;
		cuisine = c;
		maxPeople = p;
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
	
	public Integer getMaxPeople()
	{
		return maxPeople;
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