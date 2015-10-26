package com.example.anf.db;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.anf.R;
import com.example.anf.entity.Button;
import com.example.anf.entity.Promotion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PromoManager {
	private DBManager dbManager;
	private Context context;

	public PromoManager(Context context, DBManager dbManager){
		this.context = context;
		this.dbManager = dbManager;
	}

	public synchronized Promotion getPromoByID(long promoId){
		Promotion promo = new Promotion();
		
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(context.getString(R.string.sql_table_promo));
		
		SQLiteDatabase db = dbManager.getReadableDatabase();
		if(db == null){
			return promo;
		}
		if(!db.isOpen()){
			dbManager.openDB(db);
			db = dbManager.getReadableDatabase();
		}

		String[] values = {};
		String query = context.getString(R.string.sql_select_promo) + " WHERE idPromo=" + promoId + ";";
		Cursor cursor = db.rawQuery(query, values); 
		
	    if (cursor == null) {
	    	if (db != null && db.isOpen()){
	    		db.close();
	    	}
	        return promo;
	    } else if (!cursor.moveToFirst()) {
	        cursor.close();
	    	if (db != null && db.isOpen()){
	    		db.close();
	    	}
	        return promo;
	    }

	    int idPromoIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_idPromo));
	    int descriptionIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_description));
	    int footerIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_footer));
	    int butonTargetIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_button_target));
	    int butonTitleIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_button_title));
	    int titleIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_title));
	    int imageURLIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_imageURL));
	    int imageIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_image));

	    do{
		    //Promotion promo = new Promotion();
		    promo.setId(cursor.getLong(idPromoIndex));
		    promo.setDescription(cursor.getString(descriptionIndex));
		    promo.setFooter(cursor.getString(footerIndex));
		    promo.setButton(new Button(cursor.getString(butonTargetIndex), cursor.getString(butonTitleIndex)));
		    promo.setTitle(cursor.getString(titleIndex));
		    promo.setImage(cursor.getString(imageURLIndex));
		    byte[] imageByteArray = cursor.getBlob(imageIndex);

		    Bitmap image = null;
		    if(imageByteArray != null){
			    ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
			    image = BitmapFactory.decodeStream(imageStream);
			    imageStream = null;
		    }
		    promo.setImageBtmp(image);
		    
		    //list.add(promo);
	    } while(cursor.moveToNext());
	    
	    cursor.close();
    	if (db != null && db.isOpen()){
    		db.close();
    	}
		
		return promo;
	}

	public synchronized ArrayList<Promotion> getPromosList(){
		ArrayList<Promotion> list = new ArrayList<Promotion>();
		
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(context.getString(R.string.sql_table_promo));
		
		SQLiteDatabase db = dbManager.getReadableDatabase();
		if(db == null){
			return list;
		}
		if(!db.isOpen()){
			dbManager.openDB(db);
			db = dbManager.getReadableDatabase();
		}

		String[] values = {};
		Cursor cursor = db.rawQuery(context.getString(R.string.sql_select_all_promos), values); 
		
		//System.out.println(context.getString(R.string.sql_select_all_promos));
		//System.out.println(cursor.getCount());
	    if (cursor == null) {
	    	if (db != null && db.isOpen()){
	    		db.close();
	    	}
	        return list;
	    } else if (!cursor.moveToFirst()) {
	        cursor.close();
	    	if (db != null && db.isOpen()){
	    		db.close();
	    	}
	        return list;
	    }

	    int idPromoIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_idPromo));
	    int descriptionIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_description));
	    int footerIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_footer));
	    int butonTargetIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_button_target));
	    int butonTitleIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_button_title));
	    int titleIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_title));
	    int imageURLIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_imageURL));
	    int imageIndex = cursor.getColumnIndex(context.getString(R.string.sql_column_promo_image));

	    do{
		    Promotion promo = new Promotion();
		    promo.setId(cursor.getLong(idPromoIndex));
		    promo.setDescription(cursor.getString(descriptionIndex));
		    promo.setFooter(cursor.getString(footerIndex));
		    promo.setButton(new Button(cursor.getString(butonTargetIndex), cursor.getString(butonTitleIndex)));
		    promo.setTitle(cursor.getString(titleIndex));
		    promo.setImage(cursor.getString(imageURLIndex));
		    byte[] imageByteArray = cursor.getBlob(imageIndex);

		    Bitmap image = null;
		    if(imageByteArray != null){
			    ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
			    image = BitmapFactory.decodeStream(imageStream);
			    imageStream = null;
		    }
		    promo.setImageBtmp(image);
		    
		    list.add(promo);
	    } while(cursor.moveToNext());
	    
	    cursor.close();
    	if (db != null && db.isOpen()){
    		db.close();
    	}
		
		return list;
	}

	public synchronized void deletePromosList(){
		SQLiteDatabase db = dbManager.getReadableDatabase();
		if(db == null){
			return;
		}
		if(!db.isOpen()){
			dbManager.openDB(db);
			db = dbManager.getReadableDatabase();
		}

		String[] values = {};
		db.delete(context.getString(R.string.sql_table_promo), "", values); 
		
		if (db != null && db.isOpen()){
    		db.close();
    	}
	}
	
    
    public void savePromosList(List<Promotion> list){
    	deletePromosList();
    	for(Promotion promo : list){
    		savePromo(promo);
    	}
    }
    
	private synchronized void savePromo(Promotion promo){
	    SQLiteDatabase db = dbManager.getWritableDatabase();
	    if(db == null){
	        return;
	    }
	    ContentValues dataToInsert = new ContentValues();
	    dataToInsert.put(context.getString(R.string.sql_column_promo_idPromo), promo.getId());
	    dataToInsert.put(context.getString(R.string.sql_column_promo_description), promo.getDescription());
	    dataToInsert.put(context.getString(R.string.sql_column_promo_footer), promo.getFooter());
	    dataToInsert.put(context.getString(R.string.sql_column_promo_button_target), promo.getButton().getTarget());
	    dataToInsert.put(context.getString(R.string.sql_column_promo_button_title), promo.getButton().getTitle());
	    dataToInsert.put(context.getString(R.string.sql_column_promo_title), promo.getTitle());
	    dataToInsert.put(context.getString(R.string.sql_column_promo_imageURL), promo.getImage());
	
	    if(!db.isOpen()){
		    dbManager.openDB(db);
		    db = dbManager.getWritableDatabase();
	    }
	    if(db == null){
	    	return;
	    }
	    db.insertWithOnConflict(context.getString(R.string.sql_table_promo), null, dataToInsert,
	            SQLiteDatabase.CONFLICT_IGNORE);
	    if (db != null && db.isOpen()){
	    	db.close();
	    }
	}
	
	public synchronized void saveImg(Promotion promo, byte[] imageByteArray){
		SQLiteDatabase db = dbManager.getWritableDatabase();
		if(db == null){
			return;
		}
		ContentValues dataToInsert = new ContentValues();
		dataToInsert.put(context.getString(R.string.sql_column_promo_image), imageByteArray);
		String[] whereValues = /*{"0"};/*/ {String.valueOf(promo.getId())};

		if(!db.isOpen()){
			dbManager.openDB(db);
			db = dbManager.getWritableDatabase();
		}
		if(db == null){
			return;
		}
		db.update(context.getString(R.string.sql_table_promo), dataToInsert, 
				context.getString(R.string.sql_update_promo_image_whereclause), whereValues);
    	if (db != null && db.isOpen()){
    		db.close();
    	}
	}
}
