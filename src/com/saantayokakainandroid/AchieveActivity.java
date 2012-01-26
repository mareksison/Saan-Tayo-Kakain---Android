package com.saantayokakainandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AchieveActivity extends Activity {
	
	private TextView achieveTV;
	private Button achieveHomeB;
	private Intent achieveHomeI;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achieve);
        
        initUI();
    }
	
	public void initUI(){
		achieveTV = (TextView) findViewById(R.id.achieveTV);
		achieveTV.setText("Glad to be of service!");
		
		achieveHomeB = (Button) findViewById(R.id.achieveHomeB);
    	achieveHomeI = new Intent(this, SaanTayoKakainAndroidActivity.class);
    	achieveHomeB.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0){
	    		startActivity(achieveHomeI);
	    		getApplication().stopService(getIntent());
			}
		});
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
}
