package pbartz.games.deject.config;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.utils.ObjectMap;

import android.content.res.AssetManager;

public class GameConfig {

	public static JSONObject config = null;
	
	static ObjectMap<Integer, LevelConfig> levels = new ObjectMap<Integer, LevelConfig>();
	static ObjectMap<String, CreepConfig> creeps = new ObjectMap<String, CreepConfig>();
	static ObjectMap<String, ItemConfig> items = new ObjectMap<String, ItemConfig>();
	
	
	public static boolean loadConfig(DejectSurface surface) {
		
		AssetManager assets = surface.context.getAssets();
		
		String jsonString = "";
		
		try {

			InputStream input = assets.open("json/config.json");
	        
			int size = input.available();
	        byte[] buffer = new byte[size];
	        input.read(buffer);
	        input.close();
	        
	        jsonString = new String(buffer);
	        
	        config = new JSONObject(jsonString);

		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
			
		} catch (JSONException e) {
			
			e.printStackTrace();
			return false;
			
		}
		
		return true;
		
	}
	
	public static CreepConfig getCreepConfig(String creep) {
		
		CreepConfig result = creeps.get(creep);
		
		if (result == null) {
			
			result = new CreepConfig(creep);
			creeps.put(creep, result);
			
		}
		
		return result;
		
	}
	
	public static LevelConfig getLevelConfig(int level) {
		
		LevelConfig result = levels.get(level);
		
		if (result == null) {
			
			result = new LevelConfig(level);
			levels.put(level, result);
			
		}
		
		return result;
		
	}
	
	public static ItemConfig getItemConfig(String item) {
		
		ItemConfig result = items.get(item);
		
		if (result == null) {
			
			result = new ItemConfig(item);
			items.put(item, result);
			
		}
		
		return result;
		
	}
	
	
}