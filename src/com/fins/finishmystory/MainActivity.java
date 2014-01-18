package com.fins.finishmystory;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button play_button;
	Button spectate_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*
		tvDeviceName = (TextView) findViewById(R.id.textView_device_name);
        tvRobotStatus = (TextView) findViewById(R.id.textView_status);
        etToUsb = (EditText) findViewById(R.id.editText_to_usb);
        etToUsb.setFocusable(true);
        */
        
        play_button = (Button) findViewById(R.id.play_button);
        
        play_button.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {  
        		Intent gameIntent = new Intent(MainActivity.this, Game.class);
        		startActivity(gameIntent);
        	}
        });
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
