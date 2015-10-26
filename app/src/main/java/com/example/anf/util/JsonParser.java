package com.example.anf.util;

import java.io.IOException;
import java.util.ArrayList;

import android.util.JsonReader;
import android.util.JsonToken;

import com.example.anf.entity.Button;
import com.example.anf.entity.Promotion;

/**
 *
 */
public class JsonParser {
	public static final String JSON_KEY_PROMOTIONS = "promotions";
	public static final String JSON_KEY_BUTTON = "button";
	public static final String JSON_KEY_BUT_TARGET = "target";
	public static final String JSON_KEY_BUT_TITLE = "title";
	public static final String JSON_KEY_DESCRIPTION = "description";
	public static final String JSON_KEY_FOOTER = "footer";
	public static final String JSON_KEY_IMAGE = "image";
	public static final String JSON_KEY_TITLE = "title";
	
	
	public JsonParser(){
	}
	
	public ArrayList<Promotion> parsePromotionList(JsonReader jsonReader){
		ArrayList<Promotion> promoList = new ArrayList<Promotion>();
		
		try {
			jsonReader.beginObject();
		    while (jsonReader.hasNext()) {
		    	String name = jsonReader.nextName();
		    	if (name.equals(JSON_KEY_PROMOTIONS) && jsonReader.peek() != JsonToken.NULL) {
		        	 
		    		jsonReader.beginArray();
		    		while (jsonReader.hasNext()) {
		    			Promotion promo = parsePromotion(jsonReader);
		    			if(promo != null){
		    				promoList.add(promo);
		    			}
		    		}
		    		jsonReader.endArray();
			   	     
		    	} else {
		    		jsonReader.skipValue();
		    	}
		    }
		    jsonReader.endObject();
		     
		} catch (IOException e) {
		}
		
		return promoList;
	}
	
	public Promotion parsePromotion(JsonReader jsonReader){
		Promotion promo = new Promotion();
		
		try {
			jsonReader.beginObject();
			while (jsonReader.hasNext()) {
				String name = jsonReader.nextName();
				if (name.equals(JSON_KEY_BUTTON)) {
					promo.setButton(parseButton(jsonReader));
				} else if (name.equals(JSON_KEY_DESCRIPTION)) {
					promo.setDescription(jsonReader.nextString());
				} else if (name.equals(JSON_KEY_FOOTER)) {
					promo.setFooter(jsonReader.nextString());
				} else if (name.equals(JSON_KEY_TITLE)) {
					promo.setTitle(jsonReader.nextString());
				} else if (name.equals(JSON_KEY_IMAGE)) {
					promo.setImage(jsonReader.nextString());
				} else {
					jsonReader.skipValue();
				}
			}
			jsonReader.endObject();
			
		} catch (IOException e) {
		}
		
		return promo;
	}
	
	public Button parseButton(JsonReader jsonReader){
		Button button = new Button();
		
		try {
			jsonReader.beginObject();
			while (jsonReader.hasNext()) {
				String name = jsonReader.nextName();
				if (name.equals(JSON_KEY_BUT_TARGET)) {
					button.setTarget(jsonReader.nextString());
				} else if (name.equals(JSON_KEY_BUT_TITLE)) {
					button.setTitle(jsonReader.nextString());
				} else {
					jsonReader.skipValue();
				}
			}
			jsonReader.endObject();
			
		} catch (IOException e) {
		} catch (Exception e){

			try {
				jsonReader.beginArray();
				jsonReader.beginObject();
				while (jsonReader.hasNext()) {
					String name = jsonReader.nextName();
					if (name.equals(JSON_KEY_BUT_TARGET)) {
						button.setTarget(jsonReader.nextString());
					} else if (name.equals(JSON_KEY_BUT_TITLE)) {
						button.setTitle(jsonReader.nextString());
					} else {
						jsonReader.skipValue();
					}
				}
				jsonReader.endObject();
				jsonReader.endArray();
				
			} catch (IOException e2) {
			}
		}
		
		return button;
	}
	
}
