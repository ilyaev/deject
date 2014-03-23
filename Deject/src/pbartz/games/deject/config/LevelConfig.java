package pbartz.games.deject.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LevelConfig {

	int number = 0;
	float duration = 0;
	float speedMultiplier = 1;
	
	List<LevelCreepConfig> creeps = new ArrayList<LevelCreepConfig>();
	List<LevelItemConfig> items = new ArrayList<LevelItemConfig>();
	
	public LevelConfig(int level) {
		
		try {
			
			for(int i = 0 ; i < GameConfig.config.getJSONArray("levels").length() ; i++) {
				
				JSONObject obj = GameConfig.config.getJSONArray("levels").getJSONObject(i);
				
				
				if (obj.getInt("number") == level) {
					
					number = obj.getInt("number");
					duration = Float.valueOf(obj.getString("duration"));
					speedMultiplier = Float.valueOf(obj.getString("speed_multiplier"));
					
					JSONArray objects = obj.getJSONArray("creeps");
					for(int j = 0 ; j < objects.length() ; j++) {
						creeps.add(new LevelCreepConfig(objects.getJSONObject(j)));
					}
					
					JSONArray itemsObjects= obj.getJSONArray("items");
					for(int j = 0 ; j < itemsObjects.length() ; j++) {
						items.add(new LevelItemConfig(itemsObjects.getJSONObject(j)));
					}
					
										
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public float getSpeedMultiplier() {
		return speedMultiplier;
	}

	public void setSpeedMultiplier(float speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}

	public List<LevelCreepConfig> getCreeps() {
		return creeps;
	}

	public void setCreeps(List<LevelCreepConfig> creeps) {
		this.creeps = creeps;
	}

	public List<LevelItemConfig> getItems() {
		return items;
	}

	public void setItems(List<LevelItemConfig> items) {
		this.items = items;
	}

	public String getNextCreep() {
		 
		Random r = new Random();
		
		for(int i = 0 ; i < creeps.size() ; i++) {
			
			LevelCreepConfig creep = creeps.get(i);
			if (r.nextInt(101) <= creep.getChance()) {
				
				return creep.getType();
				
			}
			
		}
		
		return null;
	}

	public String getNextItem() {
		
		Random r = new Random();
		
		for(int i = 0 ; i < items.size() ; i++) {
			
			LevelItemConfig item = items.get(i);
			
			if (r.nextInt(101) <= item.getChance()) {
				
				return item.getType();
				
			}
			
		}
		
		return null;
	}
	
}