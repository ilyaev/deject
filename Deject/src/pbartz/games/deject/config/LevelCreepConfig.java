package pbartz.games.deject.config;

import org.json.JSONException;
import org.json.JSONObject;

public class LevelCreepConfig {

	String type = "";
	int chance = 0;
	
	
	public LevelCreepConfig(JSONObject obj) {
		
		try {
			
			type = obj.getString("type");
			chance = obj.getInt("chance");
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getChance() {
		return chance;
	}


	public void setChance(int chance) {
		this.chance = chance;
	}
	
	
}