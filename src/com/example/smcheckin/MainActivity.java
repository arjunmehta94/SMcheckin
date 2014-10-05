package com.example.smcheckin;

import java.net.URLEncoder;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	//final String myTag = "DocsUpload";
	private Button butt;
	//private Chronometer chrono;
	private long lastPause = 0;
	private boolean set = false;
	private boolean checkedOut = false;
	private String firstEntry = "entry_1832334365";
	private String secondEntry = "entry_963522509";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.i(myTag, "OnCreate()");
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
public void onUploadComplete(String response){
		
	}

    public void postData(String s1, String entry){
    	final String sA = s1;
    	final String entry1 = entry;
    	Thread t = new Thread(new Runnable(){
        	@Override
        	public void run() {
        		String fullUrl = "https://docs.google.com/a/berkeley.edu/forms/d/1GnIbnQxhcrNwbbxFwMGbJ1s-l9ghsm3GXxityIxvTIc/formResponse";
            	HttpRequest mReq = new HttpRequest();
            	String data = entry1 + "="+ URLEncoder.encode(sA);
            	String response = mReq.sendPost(fullUrl, data);
            	onUploadComplete(response);
        		
        	}
        	
        });
        t.start();
    	
    	//Log.i(myTag, response);
    	
    }
    public void click(View v){
    	Calendar c = Calendar.getInstance();
    	final TextView t = (TextView)findViewById(R.id.textView1);
    	final Button s = (Button)findViewById(R.id.button2);
    	final Chronometer chrono = (Chronometer)findViewById(R.id.chronometer1);
    	if(checkedOut){
    		
    		finish();
    		System.exit(0);
    	
    	}
    	
    	if(!set){
    		//checkin
    		String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    		t.setText("SM Checked in at: " + mydate);
    		s.setText("Check-out");
    		chrono.setBase(SystemClock.elapsedRealtime());
    		chrono.start();
    		set = true;
    		postData(mydate,firstEntry);
    		
    	}
    	else{
    		//checkout
    		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Are you sure you want to check-out?");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            		t.setText("SM Checked out at: " + mydate);
            		chrono.stop();
                	checkedOut = true;
                    dialog.cancel();
                    s.setText("Close");
                    postData(mydate, secondEntry);
                }
            });
            builder1.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();
    		
    		
    		
    	}
    }

}
