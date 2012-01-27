package com.saantayokakainandroid;

import java.util.List;

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
	private List<Restaurant> kainan;
	private Intent iUwi, aScreen;
	private Button home, a, b, c;
	private int buttonClick;
	private int today, thisMonth;
	public static final int ALERT_DIALOG1 = 2;

	RestaurantStorage storage;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view3);
		storage = new RestaurantStorage(this);
		kainan = storage.getAllRestaurants();
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
