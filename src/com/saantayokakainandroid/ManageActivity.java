package com.saantayokakainandroid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
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
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ManageActivity extends Activity {
    /** Called when the activity is first created. */
    private Button add, home, back, location, oTime, cTime, ayos, view;
    private Intent i, iAchieve, iView;
    private Spinner cuisine;
    private ViewFlipper flippie;
    private EditText name, barkada, cost;
    private RatingBar worth;
    private static int timeCounter, selection, screenCounter;
	public static final int TIME_DIALOG = 1;
	public static final int ALERT_DIALOG1 = 2;
	private int today, thisMonth;
	private Calendar cal;
	public static final String RESTAURANTFILE = "/sdcard/Restaurant.txt";
	public ArrayList<Restaurant> kainan;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage);
        screenCounter = 1;
        timeCounter = 0;
        selection = 100;
        cal = Calendar.getInstance();
        today = cal.get(Calendar.DAY_OF_MONTH);
        thisMonth = cal.get(Calendar.MONTH)+1;
        kainan = new ArrayList<Restaurant>();
        populate();
        initUI();
        cuisine = (Spinner) findViewById(R.id.CuisineS);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.cuisineArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisine.setAdapter(adapter);        
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
    		//must find out how to close entire app and not just this
    		this.finish();
    	}
    	return true;
    }
    
    public void initUI()
    {
    	add = (Button) findViewById(R.id.addB);
    	view = (Button) findViewById(R.id.ViewB);
    	iView = new Intent(this, ViewActivity.class);
    	ayos = (Button) findViewById(R.id.AyosB);
    	name = (EditText) findViewById(R.id.NameET);
    	barkada = (EditText) findViewById(R.id.BarkadaET);
    	cost = (EditText) findViewById(R.id.CostET);
    	worth = (RatingBar) findViewById(R.id.WorthRB);
    	iAchieve = new Intent(this, AchieveActivity.class);
    	ayos.setOnClickListener(new OnClickListener() {
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (name.getText().toString().equals(""))
					Toast.makeText(getApplicationContext(), "Error: Lagyan mo naman ng pangalan", Toast.LENGTH_SHORT).show();
				else if(location.getText().toString().equals("Required"))
					Toast.makeText(getApplicationContext(), "Error: Mejo kailangan kong malaman kung saan itong gusto mong idagdag", Toast.LENGTH_SHORT).show();
				else if(check(name.getText().toString())!=140)
				{
					Toast.makeText(getApplicationContext(), "Error: May ganyan na", Toast.LENGTH_SHORT).show();
				}
				else
				{
					resAdd();
					BufferedReader buf;
					try
					{
						buf = new BufferedReader(new FileReader(RESTAURANTFILE));
						String line = buf.readLine();
						while (line != null)
						{
							line = buf.readLine();
						}
						buf.close();
					}
					catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					startActivity(iAchieve);
				}
			}
		});
    	
    	location = (Button) findViewById(R.id.LocB);
    	location.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(ALERT_DIALOG1);
			}
		});
    	oTime = (Button) findViewById(R.id.openingTimeB);
    	oTime.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				timeCounter = 1;
				showDialog(TIME_DIALOG);
			}
		});
    	cTime = (Button) findViewById(R.id.closingTimeB);
    	cTime.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				timeCounter = 2;
				showDialog(TIME_DIALOG);
			}
		});
    	back = (Button) findViewById(R.id.Back);
    	home = (Button) findViewById(R.id.Home);
    	flippie = (ViewFlipper) findViewById(R.id.flippie);
    	i = new Intent(this, SaanTayoKakainAndroidActivity.class);
    	
    	add.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flippie.showNext();
				screenCounter++;
				back.setEnabled(true);
			}
		});
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flippie.showPrevious();
				screenCounter--;
				if (screenCounter == 1)
					back.setEnabled(false);
			}
		});
    	home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(i);
				getApplication().stopService(getIntent());
			}
		});
    	
    	view.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				generateScores();
				recursion();
				startActivity(iView);
			}
		});
    }
    
    public Dialog onCreateDialog(int id)
    {
    	Dialog d = null;
    	Calendar cal = Calendar.getInstance();
    	switch(id)
    	{
    	
	    	case TIME_DIALOG:
	    		System.out.println("onCreate id="+id);
	    		d = createTimeDialog(cal);
	    		break;
	    		
	    	case ALERT_DIALOG1:
	    		d = createAlertDialog1();
	    		break;
    	}
    	return d;
    }
    
    private Dialog createAlertDialog1() 
	{
    	final CharSequence[] items = {"Area 2", "Area 2.5", "Area 3", "Area 3.5", "Area 4"};
    	//populate this
    	final CharSequence[] itemCounterparts = {"McDonalds hanggang Starbucks, kasama doon ang Wok Dis Way",
    			"MushroomBurger hanggang BBB, kasama doon ang Greenwich",
    			"Sizzling Pepper Steak hanggang Hap Chan, kasama doon and Flaming Wings",
    			"Pancake House hanggang Brother's Burger, kasama doon ang Kentucky Fried Chicken",
    			"Ilocos Empanada hanggang Mang Inasal, kasama doon and Bo's Coffee"};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Saan kaya?");
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        Toast.makeText(getApplicationContext(), itemCounterparts[item], Toast.LENGTH_SHORT).show();
		        selection = item;
		    }
		});
		builder.setNeutralButton("Ayt", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				location.setText(items[selection]);
				removeDialog(ALERT_DIALOG1);
			}
		});
		AlertDialog alert = builder.create();		
		return alert;
	}
    
    private Dialog createTimeDialog(Calendar cal) 
	{
		Dialog d;
		OnTimeSetListener timeCallBack = new OnTimeSetListener() 
		{
			@Override
			public void onTimeSet(TimePicker view, int hour, int minute)
			{
				String s = ""+hour + ""+minute;
				if (minute<10)
					s = ""+hour  + "0"+minute;
				if (hour<10)
					s = "0"+hour + ""+minute;
				if (timeCounter == 1)
				{
					oTime.setText(s);
				}
				else if (timeCounter == 2)
				{
					cTime.setText(s);
				}
			}
		};

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		d = new TimePickerDialog(this, timeCallBack, hour, minute, true);
		return d;
	}

    public void onPrepareDialog(int id, Dialog d)
    {
		System.out.println("onPrepare id="+id);
    	switch(id)
    	{

	    	case TIME_DIALOG:
	    		break;
	    		
	    	case ALERT_DIALOG1:
	    		break;
    	}
    	
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
    
    public int check(String n)
    {
    	for (int x=0; x<kainan.size(); x++)
    	{
    		if (n.equalsIgnoreCase(kainan.get(x).getName()))
    		{
    			return x;
    		}
    	}
    	return 140;
    }
    
    public void resAdd()
    {
    	String aye = location.getText().toString().substring(5);
    	String s = name.getText().toString()+"|"+aye+"|"+cuisine.getSelectedItem().toString();
    	if (barkada.getText().toString().equals(""))
    	{
    		s=s+"|none provided";
    	}
    	else
    	{
    		s=s+"|"+barkada.getText().toString();
    	}
    	s=s+"|"+Float.toString(worth.getRating());
    	if (oTime.getText().toString().equals("Click to add"))
    	{
    		s=s+"|none provided";
    	}
    	else
    	{
    		s=s+"|"+oTime.getText().toString();
    	}
    	if (cTime.getText().toString().equals("Click to add"))
    	{
    		s=s+"|none provided";
    	}
    	else
    	{
    		s=s+"|"+cTime.getText().toString();
    	}
    	s=s+"|true|none provided|0|0";
    	s=s+""+cost.getText().toString();
    	
    	FileWriter out;
    	try
    	{
    	out = new FileWriter(RESTAURANTFILE, true);
    	out.write(s+"\n");
    	out.close();
    	String[] temp = s.split("\\|");
		int m = kainan.size();
		boolean is = true;
		kainan.add(new Restaurant(temp[0], Double.parseDouble(temp[1])));
		kainan.get(m).setRestaurant(temp[0], Double.parseDouble(temp[1]),
				temp[2], temp[3], temp[4],
				temp[5], temp[6], is);
    	}
    	catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    
    public void generateScores()
    {
    	for (int x = 0; x<kainan.size(); x++)
    	{
    		double score = 50;
    		score += ((Double.parseDouble(kainan.get(x).getWorth())*20)*0.15);
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
    		kainan.get(x).setScore(score);
    	}
    }
    
    public void recursion()
    {
    	//this used to be recursion but I found out that this one is simpler logic
    	for (int j = (kainan.size()-1); j>0; j--)
    	{
    		for (int i = (j-1); i>=0; i--)
    		{
    			if (kainan.get(i).getScore() < kainan.get(j).getScore())
    			{
    				swap(i,j);
    			}
    		}
    	}
    }
    
    
    public void swap(int a, int b)
    {
    	Restaurant u = new Restaurant(kainan.get(b).getName(), kainan.get(b).getLocation());
    	u.setRestaurant(u.getName(), u.getLocation(), kainan.get(b).getCuisine(), kainan.get(b).getPeopleMax(),
    			kainan.get(b).getWorth(), kainan.get(b).getOT(), kainan.get(b).getCT(), kainan.get(b).getIsO());
    	u.setParams(kainan.get(b).getLastDate(), kainan.get(b).getKainC(), kainan.get(b).getDeclinedC());
    	u.setScore(kainan.get(b).getScore());
    	u.setCost(kainan.get(b).getCost());
    	kainan.get(b).setRestaurant(kainan.get(a).getName(), kainan.get(a).getLocation(), kainan.get(a).getCuisine(),
    			kainan.get(a).getPeopleMax(), kainan.get(a).getWorth(), kainan.get(a).getOT(),
    			kainan.get(a).getCT(), kainan.get(a).getIsO());
    	kainan.get(b).setParams(kainan.get(a).getLastDate(), kainan.get(a).getKainC(), kainan.get(a).getDeclinedC());
    	kainan.get(b).setScore(kainan.get(a).getScore());
    	kainan.get(b).setCost(kainan.get(a).getCost());
    	kainan.get(a).setRestaurant(u.getName(), u.getLocation(), u.getCuisine(),
    			u.getPeopleMax(), u.getWorth(), u.getOT(),
    			u.getCT(), u.getIsO());
    	kainan.get(a).setParams(u.getLastDate(), u.getKainC(), u.getDeclinedC());
    	kainan.get(a).setScore(u.getScore());
    	kainan.get(a).setCost(u.getCost());
    }
}