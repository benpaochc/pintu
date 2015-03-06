package com.example.pintu;

import com.example.pintu.view.GamePintuLayout;
import com.example.pintu.view.GamePintuLayout.GamePintuListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	private GamePintuLayout mGamePintuLayout;
	private TextView mLevel;
	private TextView mCurrentTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mGamePintuLayout = (GamePintuLayout) findViewById(R.id.id_gamePintuLayout);
		mLevel = (TextView) findViewById(R.id.id_level);
		mCurrentTime = (TextView) findViewById(R.id.id_currentTime);
		
		mGamePintuLayout.setTimeEnabled(true);
		mGamePintuLayout.setOnGamePintuListener(new GamePintuListener() {
			
			@Override
			public void timeChange(int currentTime) {
				// TODO Auto-generated method stub
//				mLevel.setText(""+mGamePintuLayout.lev);
				mCurrentTime.setText(""+currentTime);
			}
			
			@Override
			public void nextLevel(final int nextLevel) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MainActivity.this).
				setTitle("game info").setMessage("next level").
				setPositiveButton("下一局", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mGamePintuLayout.nextLevel();
						mLevel.setText(""+nextLevel);
					}
				}).show();
			}
			
			@Override
			public void gameOver() {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MainActivity.this).
				setTitle("game info").setMessage("next level").
				setPositiveButton("RESTART", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mGamePintuLayout.reStart();
					}
				}).setNegativeButton("QUIT", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				}).show();
			}
		});
		
	}

	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mGamePintuLayout.resume();
		
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mGamePintuLayout.pause();
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
}
