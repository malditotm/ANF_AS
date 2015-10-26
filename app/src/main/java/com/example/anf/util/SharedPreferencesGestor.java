package com.example.anf.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Uses SharedPreferencesGestor to manage the application settings file.
 **/
public class SharedPreferencesGestor{
	
	/** Called for saving the new user ID token from the application settings file. */
	public static synchronized void saveStrValue(Context context, String idObject, String value) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Save sessionID
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(idObject, value);
		editor.commit();
	}
	
	/** Called for reading the Str Values settings file. */
	public static synchronized String readStrValue(Context context, String idObject) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Get sessionID
		String valueStr = settings.getString(idObject, "");
		
		return valueStr;
	}

	/** Called for saving the new user ID token from the application settings file. */
	public static synchronized void saveIntValue(Context context, String idObject, int value) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Save sessionID
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(idObject, value);
		editor.commit();
	}
	
	/** Called for reading the Str Values settings file. */
	public static synchronized int readIntValue(Context context, String idObject) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Get sessionID
		int valueStr = settings.getInt(idObject, -1);
		
		return valueStr;
	}	
	
	public static synchronized void saveBooleanValue(Context context, String idObject, boolean value) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Save sessionID
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(idObject, value);
		editor.commit();
	}
	
	public static synchronized boolean readBooleanValue(Context context, String idObject, boolean defalutValue) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Get sessionID
		boolean valueStr = settings.getBoolean(idObject, defalutValue);
		
		return valueStr;
	}	
	
	/** Called for reading the current user ID token from the application settings file. */
	/*public static synchronized String readUserID(Context context) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Get sessionID
		String userID = String.valueOf(settings.getInt(Constants.KEY_USER_ID, 0));
		
		return userID;
	}
	
	/** Called for saving the new user ID token from the application settings file. */
	/*public static synchronized void saveUserID(Context context, String userID) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Save sessionID
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Constants.KEY_USER_ID, userID);
		editor.commit();
	}
	
	/** Called for reading the current user's session ID token from the application settings file. */
	/*public static synchronized String readSessionID(Context context) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Get sessionID
		String sessionID = settings.getString(Constants.KEY_SESSION_ID, "0");
		
		return sessionID;
	}
	
	/** Called for saving the new user's session ID token from the application settings file. */
	/*public static synchronized void saveSessionID(Context context, String sessionID) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Save sessionID
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Constants.KEY_SESSION_ID, sessionID);
		editor.commit();
	}
	
	/** Called for reading the boolean from the application settings file used for knowing whether this device has been registered. */
	/*public static synchronized boolean readDeviceRegistrationProcessComplete(Context context) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Get deviceRegistrationProcessComplete
		boolean deviceRegistrationProcessComplete = settings.getBoolean(Constants.KEY_DEVICE_REGISTRATION_PROCESS_COMPLETE, false);
		
		return deviceRegistrationProcessComplete;
	}
	
	/** Called for saving the boolean from the application settings file used for indicating whether the device has been registered. */
	/*public static synchronized void saveDeviceRegistrationProcessComplete(Context context, boolean deviceRegistrationProcessComplete) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Save deviceRegistrationProcessComplete
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(Constants.KEY_DEVICE_REGISTRATION_PROCESS_COMPLETE, deviceRegistrationProcessComplete);
		editor.commit();
	}
	
	/** Called for reading the current user ID token from the application settings file. */
	/*public static synchronized int readDefaultAutoID(Context context) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Get sessionID
		int defaultAutoID = settings.getInt(Constants.KEY_DEFAULT_AUTO_ID, 0);
		
		return defaultAutoID;
	}
	
	/** Called for saving the new user ID token from the application settings file. */
	/*public static synchronized void saveDefaultAutoID(Context context, int defaultAutoID) {
		
		// Get SharedPreferences file
		SharedPreferences settings = context.getSharedPreferences(Constants.SETTINGS_FILENAME, Context.MODE_PRIVATE);
		
		// Save sessionID
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(Constants.KEY_DEFAULT_AUTO_ID, defaultAutoID);
		editor.commit();
	}*/
}
