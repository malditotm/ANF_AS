package com.example.anf.adapter;

import java.io.ByteArrayInputStream;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anf.PromosAct;
import com.example.anf.R;
import com.example.anf.db.DBManager;
import com.example.anf.db.PromoManager;
import com.example.anf.entity.Promotion;
import com.example.anf.net.ConnectionManager;

public class PromoAdapter extends BaseAdapter {
	PromosAct activity;
	List<Promotion> promotions;
	DBManager dbManager;
	
	public PromoAdapter(PromosAct activity, List<Promotion> promosList, DBManager dbManager){
		this.activity = activity;
		this.promotions = promosList;
		this.dbManager = dbManager;
	}
	
	@Override
	public int getCount() {
		if(promotions == null){
			return 0;
		}
		return promotions.size();
	}

	@Override
	public Object getItem(int position) {
		return promotions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;//promotions.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		convertView = inflater.inflate(R.layout.thumb_promo, parent, false);
		
		ImageView promoImage = (ImageView) convertView.findViewById(R.id.promoIV);
		TextView promoTitle = (TextView) convertView.findViewById(R.id.promoTitleVl);
        ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);
        
        Promotion promo = promotions.get(position);
        
        promoTitle.setText(promo.getTitle());
        
        if(promo.getImageBtmp() != null){
        	promoImage.setImageBitmap(promo.getImageBtmp());
        	progressBar.setVisibility(View.INVISIBLE);
        } else {
        	promoImage.setImageResource(R.drawable.img);
        	progressBar.setVisibility(View.VISIBLE);
        	getImg(promo);
        }
        
		return convertView;
	}

	public void updateList(List<Promotion> promosList) {
		this.promotions = promosList;
		this.notifyDataSetChanged();
	}

    private void getImg(Promotion promo){
    	Toast.makeText(activity, "Downloading images", Toast.LENGTH_SHORT).show();
        if(promo.getImageBtmp() == null){
        	new GetImg(promo).execute();
        }
    }

    class GetImg extends AsyncTask<Void, Void, Integer> {
        Promotion promo;

        public GetImg(Promotion promo){
            this.promo = promo;
        }

        protected void onPreExecute() {

        }

        protected Integer doInBackground(Void... params) {
            ConnectionManager connectionManager = new ConnectionManager(activity, promo.getImage());
            byte[] imageByteArray = connectionManager.requestImage().buffer();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; 
            Bitmap imageBtmp = BitmapFactory.decodeStream(imageStream);
            promo.setImageBtmp(imageBtmp);

            PromoManager songManager = new PromoManager(activity, dbManager);
            songManager.saveImg(promo, imageByteArray);
            return 0;
	    }

        protected void onPostExecute(Integer result) {
            notifyDataSetChanged();
        }
	}
}
