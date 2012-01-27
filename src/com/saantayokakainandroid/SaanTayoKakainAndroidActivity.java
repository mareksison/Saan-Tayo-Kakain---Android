package com.saantayokakainandroid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 
 * @author mareksison
 * @version 0.0
 * @since January 11, 2012
 */
public class SaanTayoKakainAndroidActivity extends Activity {

    private Button manageB;
    private Intent manageI;
    private Button chooseB;
    private Intent chooseI;
    private Intent aScreen;
    private Button a, b, c;
    private Calendar cal;
    public static final int ALERT_DIALOG1 = 2;
    private int buttonClick;
    private List<Restaurant> kainan;
    private int today, thisMonth;
    
    RestaurantStorage storage;
	
    /** Called when the activity is first created. */
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        storage = new RestaurantStorage(this);
        kainan = storage.getAllRestaurants();
        buttonClick = 0;
        cal = Calendar.getInstance();
        today = cal.get(Calendar.DAY_OF_MONTH);
        thisMonth = cal.get(Calendar.MONTH)+1;
    	generateScores();
    	Collections.sort(kainan);
    	Collections.reverse(kainan);
        initUI();
    }
    
    public void initUI(){

    	manageB = (Button) findViewById(R.id.manageB);
    	a = (Button) findViewById(R.id.chosenA);
    	b = (Button) findViewById(R.id.chosenB);
    	c = (Button) findViewById(R.id.chosenC);
    	aScreen = new Intent(this, AchieveActivity.class);
    	a.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				buttonClick = 1;
				showDialog(ALERT_DIALOG1);
			}
		});
    	b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				buttonClick = 2;
				showDialog(ALERT_DIALOG1);
			}
		});
    	c.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				buttonClick = 3;
				showDialog(ALERT_DIALOG1);
			}
		});
    	manageI = new Intent(this, ManageActivity.class);
    	manageB.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0){
	    		startActivity(manageI);
			}
		});
    	chooseB = (Button) findViewById(R.id.chooseB);
    	chooseI = new Intent(this, FilterActivity.class);
    	chooseB.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0){
	    		startActivity(chooseI);
			}
		});
    	try
    	{
    		a.setText(kainan.get(0).getName());
    		b.setText(kainan.get(1).getName());
    		c.setText(kainan.get(2).getName());
    	}
    	catch (Exception e)
    	{
    		a.setEnabled(false);
    		b.setEnabled(false);
    		c.setEnabled(false);
    		Toast.makeText(getApplicationContext(), "Error: kulang ang restaurant's mo ser", Toast.LENGTH_SHORT).show();
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
    		getApplication().stopService(getIntent());
    		this.finish();
    	}
    	return true;
    }
    
    public void generateScores()
    {
    	for (int x = 0; x<kainan.size(); x++)
    	{
    		float score = 50;
    		score += (kainan.get(x).getWorth()*20*0.15);
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
    		//Toast.makeText(getApplicationContext(), kainan.get(x).getName()+ " "+score, Toast.LENGTH_SHORT).show();
    		kainan.get(x).setScore(score);
    	}
    }

    public Dialog onCreateDialog(int id)
    {
    	Dialog d = null;
    	switch(id)
    	{
	    	case ALERT_DIALOG1:
	    		d = createAlertDialog1();
	    		break;
    	}
    	
    	return d;
    }
    
	private Dialog createAlertDialog1() 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String message = "";
		if (buttonClick == 1)
		{
			message = "Name of Restaurant: " + kainan.get(0).getName() + "\nLocation of Restaurant: Area " +
					""+kainan.get(0).getLocation() + "\nType of food served: " + kainan.get(0).getCuisine() + "\nOpening Time: " +
					kainan.get(0).getOT()+"\nClosing Time: " + kainan.get(0).getCT() + "\nRating: " +""+kainan.get(0).getWorth() +
					"\nDate of last visit: ";
				message += kainan.get(0).getLastDate() + "\nCost: "+ ""+kainan.get(0).getCost();
		}
		else if (buttonClick == 2)
		{
			message = "Name of Restaurant: " + kainan.get(1).getName() + "\nLocation of Restaurant: Area " +
					""+kainan.get(1).getLocation() + "\nType of food served: " + kainan.get(1).getCuisine() + "\nOpening Time: " +
					kainan.get(1).getOT()+"H \nClosing Time: " + kainan.get(1).getCT() + "\nRating: " +""+kainan.get(1).getWorth() +
					"\nDate of last visit: ";
				message += kainan.get(1).getLastDate() + "\nCost: "+ ""+kainan.get(1).getCost();
		}
		else
		{
			message = "Name of Restaurant: " + kainan.get(2).getName() + "\nLocation of Restaurant: Area " +
					""+kainan.get(2).getLocation() + "\nType of food served: " + kainan.get(2).getCuisine() + "\nOpening Time: " +
					kainan.get(2).getOT()+"H \nClosing Time: " + kainan.get(2).getCT() + "\nRating: " +""+kainan.get(2).getWorth() +
					"\nDate of last visit: ";
				message += kainan.get(2).getLastDate() + "\nCost: "+ ""+kainan.get(2).getCost();
		}
		
		builder.setMessage(message)
		       .setCancelable(false)
		       .setPositiveButton("Kain dito!", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) 
		           {
		        	    String dateToday = ""+thisMonth + "/"+today;
		        	    Restaurant r;
		        	    if (thisMonth <= 9)
		        	    {
		        	    	dateToday = "0"+thisMonth + "/"+today;
		        	    }
		        	    if (today <= 9)
		        	    {
		        	    	dateToday = ""+thisMonth + "/0"+today;
		        	    }
		        	    if (thisMonth < 10 && today < 10)
		        	    {
		        	    	dateToday = "0"+thisMonth + "/0"+today;
		        	    }
		        	    if (buttonClick == 1)
		        	    {
		        	    	r = kainan.get(0);
		        	    }
		        	    else if (buttonClick == 2)
		        	    {
		        	    	r = kainan.get(1);
		        	    }
		        	    else
		        	    {
		        	    	r = kainan.get(2);
		        	    }
		                dialog.dismiss();
		                removeDialog(ALERT_DIALOG1);
		                r.setLastDate(dateToday);
		                storage.writeRestaurant(r);
		                startActivity(aScreen);
		           }
		       })
		       .setNegativeButton("Baka next time", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) 
		           {
		        	   Restaurant r;
		        	   if (buttonClick == 1)
		        	    {
		        		   r = kainan.get(0);
		        	    }
		        	    else if (buttonClick == 2)
		        	    {
		        	    	r = kainan.get(1);
		        	    }
		        	    else
		        	    {
		        	    	r = kainan.get(2);
		        	    }
		                dialog.dismiss();
		                r.incDeclinedC();
		                storage.writeRestaurant(r);
		                removeDialog(ALERT_DIALOG1);
		           }
		       });
		AlertDialog alert = builder.create();
		return alert;
	}
    
    public void onPrepareDialog(int id, Dialog d)
    {
		System.out.println("onPrepare id="+id);
    	switch(id)
    	{
	    	case ALERT_DIALOG1:
	    		break;
    	}
    }
}
