package com.example.anf.db;

import com.example.anf.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 *
 */
public class DBManager extends SQLiteOpenHelper{
	private static final String DB_NAME = "promoCatalogue";
	private static final int DB_VERSION = 1;
	
	private Context context;
	
	public DBManager(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
		
		testDB();
	}
	
	private void testDB(){
		SQLiteDatabase db = getReadableDatabase();
		db.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(context.getString(R.string.sql_createtable_promo));
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(context.getString(R.string.sql_droptable_promo));
		onCreate(db);
	}
	
	public void openDB(SQLiteDatabase db){
		if (!db.isOpen()) {
		    db = context.openOrCreateDatabase(context.getString(R.string.sql_db_directory), 
		    		SQLiteDatabase.OPEN_READWRITE, null);
		 }
	}

}
