package com.saantayokakainandroid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FilteredActivity extends Activity{
	private ArrayList<Restaurant> kainan;
	private Intent iUwi, aScreen;
	private Button home, a, b, c;
    private int buttonClick;
    private int today, thisMonth;
	public static final int ALERT_DIALOG1 = 2;
	public static final String RESTAURANTFILE = "/sdcard/Restaurant.txt";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view3);
        kainan = new ArrayList<Restaurant>();
        home = (Button) findViewById(R.id.uwiB);
        a = (Button) findViewById(R.id.chosenA);
        b = (Button) findViewById(R.id.chosenB);
        c = (Button) findViewById(R.id.chosenC);
        buttonClick = 0;
        iUwi = new Intent(this, SaanTayoKakainAndroidActivity.class);
        aScreen = new Intent(this, AchieveActivity.class);
        home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(iUwi);
				getApplication().stopService(getIntent());
			}
		});
        populate();
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
	}
	
    public void populate()
    {
    	BufferedReader buf;
		try
		{
			buf = new BufferedReader(new FileReader(RESTAURANTFILE));
			String line = buf.readLine();
			while (line != null)
			{
				String[] temp = line.split("\\|");
				int m = kainan.size();
				boolean is = temp[7].equals("true");
				kainan.add(new Restaurant(temp[0], Double.parseDouble(temp[1])));
				kainan.get(m).setRestaurant(temp[0], Double.parseDouble(temp[1]),
						temp[2], temp[3], temp[4],
						temp[5], temp[6], is);
				kainan.get(m).setParams(temp[8], Integer.parseInt(temp[9]), Integer.parseInt(temp[10]));
				kainan.get(m).setCost(Integer.parseInt(temp[11]));
				line = buf.readLine();
			}
			buf.close();
		}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
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
		        	    	kainan.get(0).setLastDate(dateToday);
		        	    }
		        	    else if (buttonClick == 2)
		        	    {
		        	    	kainan.get(1).setLastDate(dateToday);
		        	    }
		        	    else
		        	    {
		        	    	kainan.get(2).setLastDate(dateToday);
		        	    }
		                dialog.dismiss();
		                removeDialog(ALERT_DIALOG1);
		                save();
		                startActivity(aScreen);
		           }
		       })
		       .setNegativeButton("Baka next time", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) 
		           {
		        	   if (buttonClick == 1)
		        	    {
		        		   kainan.get(0).incDeclinedC();
		        	    }
		        	    else if (buttonClick == 2)
		        	    {
		        	    	kainan.get(1).incDeclinedC();
		        	    }
		        	    else
		        	    {
		        	    	kainan.get(2).incDeclinedC();
		        	    }
		                dialog.dismiss();
		                save();
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
    
    public void save()
    {
    	FileWriter out;
    	try
    	{
    	out = new FileWriter(RESTAURANTFILE, false);
    	out.write("");
    	out = new FileWriter(RESTAURANTFILE, true);
    	for (int z = 0; z<kainan.size(); z++)
    	{
    		String s = kainan.get(z).getName()+"|"+ ""+kainan.get(z).getLocation()+"|" +kainan.get(z).getCuisine()+
    				"|" +kainan.get(z).getPeopleMax()+"|" +kainan.get(z).getWorth()+"|" +kainan.get(z).getOT()+
        			"|" +kainan.get(z).getCT()+"|" +""+kainan.get(z).getIsO()+"|" +kainan.get(z).getLastDate()+"|" +
    				""+kainan.get(z).getKainC()+"|" +""+kainan.get(z).getDeclinedC()+"|"+""+kainan.get(z).getCost();
    		out.write(s+"\n");
    	}
    	out.close();
    	}
    	catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
