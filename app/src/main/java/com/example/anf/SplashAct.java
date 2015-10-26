package com.example.anf;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.anf.util.Constants;
import com.example.anf.util.SharedPreferencesGestor;

public class SplashAct extends Activity {
	SplashAct thisRef = this;
	
	boolean appStart;
	String userName;
	
	Button continueBtn;
	EditText userNameVl;
	ProgressBar progressBar;
	TextView insertNameLbl;
	TextView userVl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_splash);
		initialize();
	}

	private void initialize() {
		appStart = true;
		SharedPreferencesGestor.saveBooleanValue(thisRef, Constants.KEY_APP_START, appStart);
		continueBtn = (Button) findViewById(R.id.continueBtn);
		userNameVl = (EditText) findViewById(R.id.userNameVl);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		insertNameLbl = (TextView) findViewById(R.id.insertNameLbl);
		userVl = (TextView) findViewById(R.id.userVl);
	}
	
	public void mainFlow(){
		readValues();
		
		if(appStart){
			if(userName != null && !userName.equals("")){
				userVl.setText(userName);
				continueBtn.setVisibility(View.INVISIBLE);
				userNameVl.setVisibility(View.INVISIBLE);
				insertNameLbl.setVisibility(View.INVISIBLE);
				new WaitAndLoad().execute();
			}
			else {
				progressBar.setVisibility(View.INVISIBLE);
			}
		} 
		else {
			loadMain();
		}
	}
	
	public void loadMain(){
		Intent intent = new Intent();
		intent.setClass(thisRef, PromosAct.class);
		startActivity(intent);
		overridePendingTransition(R.anim.grow_from_middle,R.anim.shrink_to_middle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
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
	
	@Override
	protected void onResume() {
		super.onResume();
		mainFlow();
	}
	
	public void continueBtnOnClick(View view){
		userName = userNameVl.getText().toString().trim();
		if(userName != null && !userName.equals("")){
			saveValues();
		}
		loadMain();
	}
	
	public void readValues(){
		userName = SharedPreferencesGestor.readStrValue(thisRef, Constants.KEY_USER);
	}
	
	public void saveValues(){
		if(userName != null && !userName.equals("")){
			SharedPreferencesGestor.saveStrValue(thisRef, Constants.KEY_USER, userName);
		}
	}


    class WaitAndLoad extends AsyncTask<Void, Void, Integer> {
        
        protected void onPreExecute() {

        }

        protected Integer doInBackground(Void... params) {
        	try {
        		Thread.sleep(3000);
        	} catch (InterruptedException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	loadMain();
        	return 0;
	    }

        protected void onPostExecute(Integer result) {
        }
	}
}
