package com.example.anf.task;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.anf.PromosAct;
import com.example.anf.db.DBManager;
import com.example.anf.db.PromoManager;
import com.example.anf.entity.Promotion;
import com.example.anf.net.ConnectionManager;
import com.example.anf.util.Constants;
import com.example.anf.util.JsonParser;
import com.example.anf.util.SharedPreferencesGestor;

/**
 *
 */
public class DownloadPromosListTask extends AsyncTask<Void, Void, Void> {
	private DBManager dbManager;
	private PromosAct activity;
	private List<Promotion> promoList;
	
	boolean appStart;
	boolean dataFromServer;
	
	private ConnectionManager connectionManager;
	
	public DownloadPromosListTask(PromosAct activity, DBManager dbManager) {
		this.activity = activity;
		this.dbManager = dbManager;
		promoList = new ArrayList<Promotion>();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		JsonParser parser = new JsonParser();
		readValues();
		
        connectionManager = new ConnectionManager(activity, "http://www.abercrombie.com/anf/nativeapp/Feeds/promotions.json");
        
        if(connectionManager.isNetworkAvailable() && appStart){
        	//Toast.makeText(activity, "Downloading data from server", Toast.LENGTH_SHORT).show();
        	activity.showMess("Downloading data from server");
        	// ** If the Json from the URL is correctly formed the next line makes the parsing to the PromoData object 
        	// ** instead of make the Parser validating element by element of the sason as it is made in the class JsonReader
	        //PromoData promoData = (PromoData) GsonUtil.gsonStringToObject(connectionManager.getJson(), new PromoData());
	        
	        promoList = parser.parsePromotionList(connectionManager.requestJson());
			connectionManager.closeConnection();
			
			//promoList = promoData.getPromotions();
			long i = 1;
			for(Promotion promo: promoList){
				promo.setId(i);
				i++;
			}
			PromoManager promoManager = new PromoManager(activity, dbManager);
			promoManager.savePromosList(promoList);
			if(!promoList.isEmpty()){
				dataFromServer = true;
				SharedPreferencesGestor.saveBooleanValue(activity, Constants.KEY_DATA_FROM_SERVER, true);
			}
        } else {
        	//Toast.makeText(activity, "Loading saved data", Toast.LENGTH_SHORT).show();
        	activity.showMess("Loading saved data");
        	PromoManager promoManager = new PromoManager(activity, dbManager);
        	promoList = promoManager.getPromosList();
        }
        
		return null;
	}

    protected void onPostExecute(Void result) {
    	if(dataFromServer){
    		activity.updatePromosList(promoList);
    	}
    	else {
    		activity.showOffline();
    	}
    }
	
	public void readValues(){
		appStart = SharedPreferencesGestor.readBooleanValue(activity, Constants.KEY_APP_START, false);
		dataFromServer = SharedPreferencesGestor.readBooleanValue(activity, Constants.KEY_DATA_FROM_SERVER, false);
	}
}
