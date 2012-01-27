package com.saantayokakainandroid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FilterActivity extends Activity {

	private Button filterB;
	private Intent filterI;
	private EditText barkadaET;
	private Button filterHomeB;
	private Intent filterHomeI;
	private Spinner locationS;
	private Spinner costS;
	private Spinner cuisineS;
	private ArrayList<Restaurant> kainan;
	private Calendar cal;
    private int today, thisMonth;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);
        kainan = new ArrayList<Restaurant>();
        cal = Calendar.getInstance();
        today = cal.get(Calendar.DAY_OF_MONTH);
        thisMonth = cal.get(Calendar.MONTH)+1;
        initUI();
    }
    
    public void generateScores()
    {
    	for (int x = 0; x<kainan.size(); x++)
    	{
    		float score = 0;
    		
    		//cost check
    		float tempCost = kainan.get(x).getCost();
    		if (costS.getSelectedItem().toString().equalsIgnoreCase("100 pababa"))
    		{
    			if (tempCost<=100)
    			{
    				score += 20.0;
    			}
    			else if (tempCost < 200 && tempCost > 100)
    			{
    				score += 15.0;
    			}
    			else if (tempCost > 200)
    			{
    				score += 10.0;
    			}
    		}
    		else if (costS.getSelectedItem().toString().equalsIgnoreCase("100 to 150"))
    		{
    			if (tempCost<=150)
    			{
    				score += 20.0;
    			}
    			else if (tempCost < 250 && tempCost > 150)
    			{
    				score += 15.0;
    			}
    			else if (tempCost > 250)
    			{
    				score += 10.0;
    			}
    		}
    		else if (costS.getSelectedItem().toString().equalsIgnoreCase("150 to 250"))
    		{
    			if (tempCost<=250)
    			{
    				score += 20.0;
    			}
    			else if (tempCost < 350 && tempCost > 250)
    			{
    				score += 15.0;
    			}
    			else if (tempCost > 350)
    			{
    				score += 10.0;
    			}
    		}
    		else if (costS.getSelectedItem().toString().equalsIgnoreCase("250 to 300"))
    		{
    			if (tempCost<=300)
    			{
    				score += 20.0;
    			}
    			else if (tempCost < 400 && tempCost > 300)
    			{
    				score += 15.0;
    			}
    			else if (tempCost > 400)
    			{
    				score += 10.0;
    			}
    		}
    		else
    		{
    			score += 20.0;
    		}
    		
    		
    		//pwede pang dagdagan
    		//check if restaurant can accomodate number of people
    		int tempBark = kainan.get(x).getMaxPeople();
    		if (tempBark>=Integer.parseInt(barkadaET.getText().toString()))
    		{
    			score += 10.0;
    		}
    		else if ((tempBark - Integer.parseInt(barkadaET.getText().toString()) == 1))
    		{
    			score += 7.5;
    		}
    		else
    		{
    			score += 5.0;
    		}
    		
    		//pwede pang dagdagan
    		//check if cuisine match
    		String tempCui = kainan.get(x).getCuisine();
    		if (tempCui.equalsIgnoreCase(cuisineS.getSelectedItem().toString()))
    		{
    			score += 10.0;
    		}
    		else if (cuisineS.getSelectedItem().toString().equalsIgnoreCase("kahit ano"))
    		{
    			score += 10.0;
    		}
    		else
    		{
    			score += 5.0;
    		}
    		
    		//location check
    		Double tempLoc = kainan.get(x).getLocation();
    		if (locationS.getSelectedItem().toString().equalsIgnoreCase("Area 2"))
    		{
    			if (tempLoc == 4)
    			{
    				score += 5.0;
    			}
    			else if (tempLoc == 3.5)
    			{
    				score += 7.5;
    			}
    			else
    			{
    				score += 10.0;
    			}
    		}
    		else if (locationS.getSelectedItem().toString().equalsIgnoreCase("Area 2.5"))
    		{
    			if (tempLoc == 4 || tempLoc == 3.5)
    			{
    				score += 7.5;
    			}
    			else
    			{
    				score += 10.0;
    			}
    		}
    		else if (locationS.getSelectedItem().toString().equalsIgnoreCase("Area 3"))
    		{
    			score += 10.0;
    		}
    		else if (locationS.getSelectedItem().toString().equalsIgnoreCase("Area 3.5"))
    		{
    			if (tempLoc == 2 || tempLoc == 2.5)
    			{
    				score += 7.5;
    			}
    			else
    			{
    				score += 10.0;
    			}
    		}
    		else if (locationS.getSelectedItem().toString().equalsIgnoreCase("Area 4"))
    		{
    			if (tempLoc == 2)
    			{
    				score += 5.0;
    			}
    			else if (tempLoc == 2.5)
    			{
    				score += 7.5;
    			}
    			else
    			{
    				score += 10.0;
    			}
    		}
    		else
    		{
    			score += 10.0;
    		}
    		
    		score += (kainan.get(x).getWorth())*20*0.15;
    		score += (100-(kainan.get(x).getDeclinedC()*4))*0.15;
    		String last = kainan.get(x).getLastDate();
    		try
    		{
    			String[] mo = last.split("/");
    			int da = Integer.parseInt(mo[1]);
    			if ((Integer.parseInt(mo[0])- thisMonth) == 0)
    			{
    				score += (((today - da)/today)*70 + (30))*0.20;
    			}
    			else if ((thisMonth - Integer.parseInt(mo[0])) == 1)
    			{
    				if (thisMonth == 1 || thisMonth == 3 || thisMonth == 5 || thisMonth == 7 || thisMonth == 8 || thisMonth == 10
    						|| thisMonth == 12)
    				{
    					score += ((((today+31) - da)/today)*70 + (30))*0.20;
    				}
    				else if (thisMonth == 4 || thisMonth == 6 || thisMonth == 9 || thisMonth == 11)
    				{
    					score += ((((today+30) - da)/today)*70 + (30))*0.20;
    				}
    				else
    				{
    					score += ((((today+29) - da)/today)*70 + (30))*0.20;    					
    				}
    			}
    			else
    			{
    				score += 20;
    			}
    				
    		}
    		catch (Exception e)
    		{
    			score += 20;
    		}
    		try
    		{
    			int open = Integer.parseInt(kainan.get(x).getOT());
    			int close = Integer.parseInt(kainan.get(x).getCT());
    			int hour = cal.get(Calendar.HOUR_OF_DAY);
    			int minute = cal.get(Calendar.MINUTE);
    			String s = ""+hour + ""+minute;
				if (minute<10)
					s = ""+hour  + "0"+minute;
				if (hour<10)
					s = "0"+hour + ""+minute;
				int timeToday = Integer.parseInt(s);
				if (timeToday < open || timeToday > close)
				{
					score = 0;
				}
    		}
    		catch (Exception e)
    		{
    			
    		}
    		kainan.get(x).setScore(score);
    	}
    }

	public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.special_menu, menu);
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
    	
    	if (item.getItemId() == R.id.quit)
    	{
    		this.finish();
    	}
    	return true;
    }
	
	public void initUI(){
		locationS = (Spinner) findViewById(R.id.locationS);
		barkadaET = (EditText) findViewById(R.id.brkadaET);
		cuisineS = (Spinner) findViewById(R.id.cuisineS);
    	costS = (Spinner) findViewById(R.id.costS);

        ArrayAdapter<CharSequence> locationSAdapter = ArrayAdapter.createFromResource(this, R.array.locationArray, android.R.layout.simple_spinner_item);
        locationSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationS.setAdapter(locationSAdapter);
        ArrayAdapter<CharSequence> costSAdapter = ArrayAdapter.createFromResource(this, R.array.costArray, android.R.layout.simple_spinner_item);
        costSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        costS.setAdapter(costSAdapter);
        ArrayAdapter<CharSequence> cuisineSAdapter = ArrayAdapter.createFromResource(this, R.array.cuisineArray, android.R.layout.simple_spinner_item);
        cuisineSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineS.setAdapter(cuisineSAdapter);

    	filterB = (Button) findViewById(R.id.filterB);
    	filterI = new Intent(this, FilteredActivity.class);
    	filterB.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0){
				generateScores();
		    	Collections.sort(kainan);
		    	Collections.reverse(kainan);
	    		startActivity(filterI);
			}
		});
    	
    	filterHomeB = (Button) findViewById(R.id.filterBackB);
    	filterHomeI = new Intent(this, SaanTayoKakainAndroidActivity.class);
    	filterHomeB.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0){
	    		startActivity(filterHomeI);
	    		getApplication().stopService(getIntent());
			}
		});
	}
}
