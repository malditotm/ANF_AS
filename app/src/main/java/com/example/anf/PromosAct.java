package com.example.anf;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anf.adapter.PromoAdapter;
import com.example.anf.db.DBManager;
import com.example.anf.entity.Button;
import com.example.anf.entity.PromoData;
import com.example.anf.entity.Promotion;
import com.example.anf.task.DownloadPromosListTask;
import com.example.anf.util.Constants;
import com.example.anf.util.GsonUtil;
import com.example.anf.util.SharedPreferencesGestor;

public class PromosAct extends Activity {
	PromosAct thisRef = this;
	
	DBManager dbManager;

	boolean appStart;
	String userName;
	List<Promotion> promosList;
	Handler handler;
	Runnable updateResults;
	Handler messageHandler;
	
	GridView promosGV;
	PromoAdapter promoAdapter;
	ProgressBar progressBar;
	ProgressDialog loadDialog;
	TextView welcomeLbl;
    TextView userNameVal;
    RelativeLayout mainLyt;
    RelativeLayout offlineLyt;
    
    public int currentImageIndex = 0;
    Timer timer;
    TimerTask task;
    ImageView slidingImage;
    ImageView headerIV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_promos);
		initialize();
	}

	public void initialize(){
		dbManager = new DBManager(thisRef);
		readValues();
		
		//createDummyData();
    	messageHandler = new Handler();
    	
		welcomeLbl = (TextView) findViewById(R.id.welcomeLbl);
	    userNameVal = (TextView) findViewById(R.id.userNameVal);
	    progressBar = (ProgressBar) findViewById(R.id.progressBar);
	    mainLyt = (RelativeLayout) findViewById(R.id.mainLyt);
		
		promoAdapter = new PromoAdapter(thisRef, promosList, dbManager);
		promosGV = (GridView) findViewById(R.id.promosGV);
		promosGV.setAdapter(promoAdapter);
		
		if(userName != null && !userName.equals("")){
			setTitle("Welcome " + userName);
			//userNameVal.setText(userName);
			//welcomeLbl.setText("Welcome again ");
		}
		
		loadDialog = new ProgressDialog(thisRef);

	    promosGV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
			    PromoAdapter adapter = (PromoAdapter) promosGV.getAdapter();
			    Promotion item = (Promotion) adapter.getItem(position);
			    
				Intent intent = new Intent();
				intent.setClass(thisRef, PromoDetailAct.class);
				intent.putExtra(Constants.KEY_PROMOS_SELECTED_PROMO, item.getId()/*GsonUtil.objectToGsonString(item)*/);
				startActivity(intent);
				overridePendingTransition(R.anim.grow_from_middle,R.anim.shrink_to_middle);
			}
		});
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new DownloadPromosListTask(thisRef, dbManager).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.promos, menu);
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
	
	public void updatePromosList(List<Promotion> list){
		promosList = list;
		promoAdapter.updateList(promosList);
		setAccess();
		progressBar.setVisibility(View.INVISIBLE);
		loadDialog.dismiss();

		if(offlineLyt != null){
			mainLyt.removeView(offlineLyt);
			handler.removeCallbacks(updateResults);
		}
	}
	
	public void showOffline(){
		LayoutInflater inflater = thisRef.getLayoutInflater();
		offlineLyt = (RelativeLayout) inflater.inflate(R.layout.offline, mainLyt, false);
		mainLyt.addView(offlineLyt);
		
		handler = new Handler();		 
        // Create runnable for posting
        updateResults = new Runnable() {
            public void run() { 
                AnimateandSlideShow();
            }
        };
        int delay = 1000; 
        int period = 8000;
        Timer timer = new Timer(); 
        timer.scheduleAtFixedRate(new TimerTask() { 
        public void run() { 
            	handler.post(updateResults);
        	}
        }, delay, period);
	}

    private void AnimateandSlideShow() { 
    	if(offlineLyt != null && slidingImage != null){
    		try{
		        slidingImage = (ImageView)findViewById(R.id.slideIV);
		        slidingImage.setImageResource(thumbIds[currentImageIndex%thumbIds.length]);
				headerIV = (ImageView) offlineLyt.findViewById(R.id.headerIV);
		        headerIV.setImageResource(headerIds[currentImageIndex%thumbIds.length]);
		 
		        currentImageIndex++;
		 
		        Animation anim = AnimationUtils.loadAnimation(this, R.anim.grow_from_middle);
		        slidingImage.startAnimation(anim);
    		} catch (Exception e){
    			// I need more time to detect the failure due the moment when the network is enabled
    			// and some variables gone to null due the removal of the offline layer.
    		}
    	}
    }
    
    public void showMess(String message){
    	messageHandler.postDelayed(new ShowMessage(message), 2);
    }

    public class ShowMessage implements Runnable {
    	String message;
    	ShowMessage(String message){
    		this.message = message;
    	}
        public void run() { 
        	Toast.makeText(thisRef, message, Toast.LENGTH_SHORT).show();
        	//showMessage(message);
        }
    };
	
	public void createDummyData(){
		PromoData dummyData = new PromoData();
		dummyData.setPromotions(new ArrayList<Promotion>());
		Promotion promo = new Promotion();
		promo.setButton(new Button("Pringle", "Surf"));
		promo.setDescription("AllBrands");
		promo.setFooter("In stores & online. Exclusions apply. <a href=\"https://www.abercrombie.com/anf/media/legalText/viewDetailsText20150618_Shorts25_US.html\" class=\"legal promo-details\">See details</a>");
		//promo.setId(1);
		promo.setTitle("Cup");
		promo.setImage("http://en.kosmosmalabares.com/wp-content/uploads/2012/01/Bombin_rojo_1_web.jpg");
		dummyData.getPromotions().add(promo);
		promo = new Promotion();
		promo.setButton(new Button("Stern", "Runt"));
		promo.setDescription("Crown");
		promo.setFooter("Fur");
		//promo.setId(2);
		promo.setTitle("part");
		promo.setImage("http://mlm-s1-p.mlstatic.com/10-sombreros-copa-bombin-para-fiesta-batucada-animacion-13381-MLM3058883047_082012-O.jpg");
		dummyData.getPromotions().add(promo);
		
		System.out.println(GsonUtil.objectToGsonString(dummyData.getPromotions()));
	}
	
	public void setAccess(){
		SharedPreferencesGestor.saveBooleanValue(thisRef, Constants.KEY_APP_START, false);
	}
	
	public void readValues(){
		appStart = SharedPreferencesGestor.readBooleanValue(thisRef, Constants.KEY_APP_START, false);
		userName = SharedPreferencesGestor.readStrValue(thisRef, Constants.KEY_USER);
	}

    int[] thumbIds = {
            R.drawable.product5,
            R.drawable.product9,
            R.drawable.product2,
            R.drawable.product6,
            R.drawable.product10,
            R.drawable.product3,
            R.drawable.product7,
            R.drawable.product11,
            R.drawable.product4,
            R.drawable.product8,
            R.drawable.product12,
            R.drawable.product1
     };

    int[] headerIds = {
            R.drawable.girl_header,
            R.drawable.man_header,
            R.drawable.woman_header,
            R.drawable.girl_header,
            R.drawable.man_header,
            R.drawable.woman_header,
            R.drawable.boy_header,
            R.drawable.man_header,
            R.drawable.woman_header,
            R.drawable.boy_header,
            R.drawable.man_header,
            R.drawable.woman_header
     };

	public List<Promotion> getPromosList() {
		return promosList;
	}
	public void setPromosList(List<Promotion> promosList) {
		this.promosList = promosList;
	}
}
