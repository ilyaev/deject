package pbartz.games.deject;

import android.content.SharedPreferences;

public class Storage {

	static SharedPreferences src = null;
	
	public static void setSource(SharedPreferences prefs) {
		
		src = prefs;
		
	}
	
	public static int getIntValue(String key, int defValue) {
		
		if (src == null) return defValue;
		
		return src.getInt(key, defValue);
		
	}
	
	public static String getValue(String key, String defValue) {
		
		if (src == null) return defValue;
		
		return src.getString(key, defValue);		
		
	}
	
	public static void setValue(String key, String value) {
		
		if (src == null) return;
		
		SharedPreferences.Editor editor = src.edit();
		editor.putString(key, value);
		editor.commit();
		
	}	
	
	public static void setIntValue(String key, int value) {
		
		if (src == null) return;
		
		SharedPreferences.Editor editor = src.edit();
		editor.putInt(key, value);
		editor.commit();
		
	}
	
	

}
