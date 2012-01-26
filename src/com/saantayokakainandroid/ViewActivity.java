package com.saantayokakainandroid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewActivity extends Activity{
	private ArrayList<Restaurant> kainan;
	private LinearLayout here;
	private ArrayList<TextView> tekusutoBiyu;
	private TextView tv1;
	private Intent iUwi;
	private Button home;
	public static final int ALERT_DIALOG1 = 2;
	public static final String RESTAURANTFILE = "/sdcard/Restaurant.txt";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewall);
        kainan = new ArrayList<Restaurant>();
        tekusutoBiyu = new ArrayList<TextView>();
        tv1 = (TextView) findViewById(R.id.textView1);
        home = (Button) findViewById(R.id.uwiB);
        here = (LinearLayout) findViewById(R.id.kainDitoLL);
        iUwi = new Intent(this, SaanTayoKakainAndroidActivity.class);
        home.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(iUwi);
				getApplication().stopService(getIntent());
			}
		});
        populate();
        fill();
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
	
	public void fill()
	{
		for (int x = 0; x < kainan.size(); x++)
		{
			tekusutoBiyu.add(new TextView(this));
			String message = "";
			message = "Name of Restaurant: \n" + kainan.get(x).getName() + "\nLocation of Restaurant: \nArea " +
					""+kainan.get(x).getLocation() + "\nType of food served: \n" + kainan.get(x).getCuisine() + "\nOpening Time: " +
					kainan.get(x).getOT()+" \nClosing Time: " + kainan.get(x).getCT() + "\nRating: " +""+kainan.get(x).getWorth() +
					"\nDate of last visit: \n";
			message += kainan.get(x).getLastDate() + "\n";
			message += "How much you should have: ";
			if (kainan.get(x).getCost() == 0)
				message += "Not provided\n\n";
			else
				message += "P"+kainan.get(x).getCost() + "\n\n";
			tekusutoBiyu.get(x).setText(message);
			tekusutoBiyu.get(x).setTypeface(Typeface.MONOSPACE);
			tekusutoBiyu.get(x).setTextSize(tv1.getTextSize());
			//tekusutoBiyu.get(x).setTextAppearance(getApplicationContext(), 1);
			here.addView(tekusutoBiyu.get(x), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
	    			ViewGroup.LayoutParams.WRAP_CONTENT));
		}
	}
}
