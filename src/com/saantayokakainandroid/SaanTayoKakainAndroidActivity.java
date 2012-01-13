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

/**
 * 
 * @author mareksison
 * @version 0.0
 * @since January 11, 2012
 */
public class SaanTayoKakainAndroidActivity extends Activity {

    private Button manageB;
    private Intent intent;
	private Button filterB;
	private Spinner costS;
	private Spinner cuisineS;
	
    /** Called when the activity is first created. */
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initUI();
    }
    
    public void initUI(){

    	/*cuisineS = (Spinner) findViewById(R.id.cuisineS);
    	costS = (Spinner) findViewById(R.id.costS);

        ArrayAdapter<CharSequence> cuisineSAdapter = ArrayAdapter.createFromResource(this, R.array.cuisineArray, android.R.layout.simple_spinner_item);
        cuisineSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineS.setAdapter(cuisineSAdapter);
        ArrayAdapter<CharSequence> costSAdapter = ArrayAdapter.createFromResource(this, R.array.costArray, android.R.layout.simple_spinner_item);
        costSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        costS.setAdapter(costSAdapter);

    	filterB = (Button) findViewById(R.id.filterB);
    	*/
    	manageB = (Button) findViewById(R.id.manageB);
    	intent = new Intent(this, ManageActivity.class);
    	manageB.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0)
			{
	    		startActivity(intent);
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