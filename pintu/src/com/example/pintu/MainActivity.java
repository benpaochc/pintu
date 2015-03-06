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

public class MainActivity extends Activity {

	private GamePintuLayout mGamePintuLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mGamePintuLayout = (GamePintuLayout) findViewById(R.id.id_gamePintuLayout);
		
		mGamePintuLayout.setOnGamePintuListener(new GamePintuListener() {
			
			@Override
			public void timeChange(int currentTime) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void nextLevel(int nextLevel) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MainActivity.this).
				setTitle("game info").setMessage("next level").
				setPositiveButton("下一局", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mGamePintuLayout.nextLevel();
					}
				}).show();
			}
			
			@Override
			public void gameOver() {
				// TODO Auto-generated method stub
				
			}
		});
		
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
