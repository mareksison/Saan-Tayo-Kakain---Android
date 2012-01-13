package com.saantayokakainandroid;

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
import android.widget.Spinner;
import android.widget.ViewFlipper;

public class ManageActivity extends Activity {
    /** Called when the activity is first created. */
    private Button add, home, back;
    private Intent i;
    private Spinner cuisine;
    private ViewFlipper flippie;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage);
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
    		this.finish();
    	}
    	return true;
    }
    
    public void initUI()
    {
    	add = (Button) findViewById(R.id.addB);
    	back = (Button) findViewById(R.id.Back);
    	home = (Button) findViewById(R.id.Home);
    	flippie = (ViewFlipper) findViewById(R.id.flippie);
    	i = new Intent(this, SaanTayoKakainAndroidActivity.class);
    	
    	add.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flippie.showNext();
			}
		});
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flippie.showPrevious();
			}
		});
    	home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(i);
			}
		});
    }
}