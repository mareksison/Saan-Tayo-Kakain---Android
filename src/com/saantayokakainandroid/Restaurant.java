package com.saantayokakainandroid;

public class Restaurant implements Comparable<Restaurant> {

	public String name;
	public float location;
	public String cuisine;
	public Integer maxPeople;
	public String worth;
	public String openingTime;
	public String closingTime;
	public String last_eating_date;
	public int approval_rate;
	public int decline_rate;
	public Double score;
	public int cost;
	
	public Restaurant(String n, float loc){
		name = n;
		location = loc;
		approval_rate = decline_rate = 0;
		last_eating_date = "none provided";
	}
	
	public void setRestaurant(String n, float loc, String c, Integer p, String w, String oT, String cT)
	{
		name = n;
		location = loc;
		cuisine = c;
		maxPeople = p;
		worth = w;
		openingTime = oT;
		closingTime = cT;
	}
	
	public void setLastDate(String d)
	{
		last_eating_date = d;
		approval_rate++;
	}
	
	public void setParams(String ld, int k, int d)
	{
		last_eating_date = ld;
		approval_rate = k;
		decline_rate = d;
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
		decline_rate++;
	}
	
	public int getKainC()
	{
		return approval_rate;
	}
	
	public int getDeclinedC()
	{
		return decline_rate;
	}
	
	public String getLastDate()
	{
		return last_eating_date;
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
		return false;
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