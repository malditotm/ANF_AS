package com.example.anf.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.JsonReader;

public class ConnectionManager {
	private HttpURLConnection httpConnection = null;
	private URL url = null;
	private InputStream is = null;
	private JsonReader jsonReader = null;
	
	private Context context;

	public ConnectionManager(Context context, String requestURL){
		this.context = context;
		
		try {
			url = new URL(requestURL);
			
		} catch (MalformedURLException e) {
		}
	}
	
	public JsonReader requestJson(){
		try {
			jsonReader = new JsonReader(new InputStreamReader(request(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
		
		return jsonReader;
	}
	
	public String getJson(){
		try {
			String string = IOUtils.toString(request(), "UTF-8");
			/*int indexA, indexB;
			indexA = string.indexOf("<a");
			indexB = string.indexOf("a>") + 2;
			string = string.substring(0, indexA) + string.substring(indexB);
			indexA = string.indexOf("\"promotions\"") + 13;
			indexB = string.lastIndexOf("}");
			string = string.substring(indexA,indexB);*/
			
			//System.out.println(string);
			return string;
		} catch (IOException e) {
		} 
		return null;
	}
	
	public InputStream request(){
	    try {
	        httpConnection = (HttpURLConnection) url.openConnection();

	        int responseCode = httpConnection.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            is = httpConnection.getInputStream();	            
	        }
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    }
	    return is;
	}
	public ByteArrayBuffer requestImage(){
		HttpURLConnection httpConnection = null;
		ByteArrayBuffer baf = new ByteArrayBuffer(1024);
		BufferedInputStream bis = null;

		if(!isNetworkAvailable()){
			return null;
		}
		
	    try {
	        httpConnection = (HttpURLConnection) url.openConnection();
	        
	        int responseCode = httpConnection.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	        	bis = new BufferedInputStream(httpConnection.getInputStream(), 1024);

				int current = 0;
				while ((current = bis.read()) != -1) {
					baf.append((byte) current);
				}
	            
	        } 
	        
	    } catch (Exception ex) {

	    }
	    //System.out.println(url.toString());
	    return baf;
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	public void closeConnection(){
	    try{
	    	if(is != null){
	    		is.close();
	    	}
	    	if(httpConnection != null){
	    		httpConnection.disconnect();
	    	}
		} catch(Exception e){
		}
	}
}
