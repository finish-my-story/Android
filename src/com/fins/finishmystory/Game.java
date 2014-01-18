package com.fins.finishmystory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity {

	int MAX_WORDS = 4;
	int TIMER_SECONDS = 25;

	TextView text_arena;
	TextView timer_text;
	EditText input_words;
	Button send_words;
	ProgressBar user_timer;
	ScrollView game_scroll;

	int user_timerStatus = 0;
	String colorText;
	boolean currentTurn = false;
	long timer_start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_main);

		input_words = (EditText) findViewById(R.id.input_words);
		send_words = (Button) findViewById(R.id.send_words);
		text_arena = (TextView) findViewById(R.id.text_arena);
		user_timer = (ProgressBar) findViewById(R.id.user_timer);
		game_scroll = (ScrollView) findViewById(R.id.game_scroll);
		colorText = (String) text_arena.getText();
		timer_text = (TextView) findViewById(R.id.timer_text);

		// TODO: Include this in the XML
		timer_text.setVisibility(TextView.INVISIBLE);
		input_words.setEnabled(false);

		send_words.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (currentTurn) {
					if (validEntry(input_words.getEditableText().toString())) {
						//turnStart();
						appendStory(formatEntry(input_words.getEditableText().toString()), 0x559955);
						//have to end turn as well
					} else {
						Toast.makeText(getApplicationContext(), "Illegal Entry", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		input_words.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				game_scroll.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
		
		//XXX DEBUG CODE TEMP
		currentTurn = true;
		turnStart();
		//XXX END DEBUG CODE

	}

	public void turnStart() {
		input_words.setEnabled(true);
		user_timer.setProgress(100);
		user_timer.setMax(100);
		user_timerStatus = 100;
		timer_text.setVisibility(TextView.VISIBLE);
		Toast.makeText(getApplicationContext(), "Your Turn!", Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {
			public void run() {
				timer_start = System.currentTimeMillis();
				while (100 - (int) ((System.currentTimeMillis() - timer_start) / (10 * TIMER_SECONDS)) > 0) {
					if (!currentTurn)
						break;
					runOnUiThread(new Runnable() {
						public void run() {
							timer_text.setText((TIMER_SECONDS - (System
									.currentTimeMillis() - timer_start) / 1000)
									+ " seconds left");
							user_timer.setProgress(100 - (int) ((System
									.currentTimeMillis() - timer_start) / (10 * TIMER_SECONDS)));
						}
					});
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				runOnUiThread(new Runnable() { // end of turn
					public void run() {
						input_words.setEnabled(false);
						timer_text.setVisibility(TextView.INVISIBLE);
						user_timer.setProgress(0);
					}
				});
			}
		}).start();

	}

	public void appendStory(String string, int color) {
		colorText = colorText + "<font color="
				+ String.format("#%06X>", (0xFFFFFF & color)) + string
				+ "</font>";
		runOnUiThread(new Runnable() {
			public void run() {
				text_arena.setText(Html.fromHtml(colorText));
				game_scroll.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}

	public boolean validEntry(String instring) {
		String trimmed = instring.trim();
		int words = (trimmed.length() == 0) ? 0 : trimmed.split("\\s+").length;
		if (words <= MAX_WORDS)
			return true;
		else
			return false;

	}

	public String formatEntry(String instring) {
		String trimmed = instring.trim();
		String[] split_array = trimmed.split("\\s+");
		String answer = new String();
		for (int i = 0; i < split_array.length; i++) {
			answer = answer + split_array[i];
		}
		return answer;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}