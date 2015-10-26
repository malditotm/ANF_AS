package com.example.anf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anf.db.DBManager;
import com.example.anf.db.PromoManager;
import com.example.anf.entity.Promotion;
import com.example.anf.util.Constants;

public class PromoDetailAct extends Activity {
	PromoDetailAct thisRef = this;
	
	DBManager dbManager;
	
	long promoID;
	String promoLinkURL;
	Promotion promo;
	
	Button promoLinkBtn;
	ImageView promoIV;
	TextView descriptionTV;
	TextView footerTV;
	RelativeLayout mainLyt;
	RelativeLayout webLyt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_promo_detail);
		
		initialize();
	}
	
	public void initialize(){
		dbManager = new DBManager(thisRef);
		readValues(getIntent());
		
		mainLyt = (RelativeLayout) findViewById(R.id.mainLyt);
		promoLinkBtn = (Button) findViewById(R.id.promoLinkBtn);
		promoIV = (ImageView) findViewById(R.id.promoIV);
		descriptionTV = (TextView) findViewById(R.id.descriptionTV);
		footerTV = (TextView) findViewById(R.id.footerVL);

		
		setTitle(promo.getTitle());
		promoLinkURL = promo.getButton().getTarget();
		promoLinkBtn.setText(promo.getButton().getTitle());
		descriptionTV.setText(promo.getDescription());
		footerTV.setText(promo.getFooter());
		if(promo.getImageBtmp() != null){
			promoIV.setImageBitmap(promo.getImageBtmp());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.promo_detail, menu);
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
	
	public void followLinkOnClick(View view){
		LayoutInflater inflater = thisRef.getLayoutInflater();
		webLyt = (RelativeLayout) inflater.inflate(R.layout.web_view_lyt, mainLyt, false);
		WebView webView = (WebView) webLyt.findViewById(R.id.webView);
		final ProgressBar progressBar = (ProgressBar) webLyt.findViewById(R.id.progressBar);
		mainLyt.addView(webLyt);
		 webView.setWebViewClient(new WebViewClient() {
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                view.loadUrl(url);
	                return true;
	            }

	            public void onPageFinished(WebView view, String url) {
	                if (progressBar.isShown()) {
	                    progressBar.setVisibility(View.INVISIBLE);
	                }
	            }
		 });
		 webView.loadUrl(promoLinkURL);
	}
	
	public void closeWebViewOnClick(View view){
		mainLyt.removeView(webLyt);
	}
	
    public void readValues(Intent fromIntent){
    	promoID = Long.valueOf(fromIntent.getExtras().get(Constants.KEY_PROMOS_SELECTED_PROMO).toString());
    	promo = new PromoManager(thisRef, dbManager).getPromoByID(promoID);
    }

	public String getPromoLinkURL() {
		return promoLinkURL;
	}
	public void setPromoLinkURL(String promoLinkURL) {
		this.promoLinkURL = promoLinkURL;
	}
	public Promotion getPromo() {
		return promo;
	}
	public void setPromo(Promotion promo) {
		this.promo = promo;
	}
	public RelativeLayout getWebLyt() {
		return webLyt;
	}
	public void setWebLyt(RelativeLayout webLyt) {
		this.webLyt = webLyt;
	}
}
