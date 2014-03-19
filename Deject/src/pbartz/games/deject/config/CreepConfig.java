package pbartz.games.deject.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CreepConfig {

	float speedUp = 0f;
	float speedDown = 0f;
	float speedWaiting = 0f;
	
	String image;
	
	String[] components = {};
	
	float shield_interval = 0f;
	float shield_duration = 0f;
	
	
	int minHit = 1;
	int life = 1;
	int score = 0;
	
	String type = "";
	
	List<CreepItemConfig> items = new ArrayList<CreepItemConfig>();
	
	
	public CreepConfig(String creepType) {
		
		try {
				
			JSONObject defObj = GameConfig.config.getJSONObject("creeps_default_properties");

			
			for(int i = 0 ; i < GameConfig.config.getJSONArray("creeps").length() ; i++) {
				
				JSONObject obj = GameConfig.config.getJSONArray("creeps").getJSONObject(i);
				
				if (obj.getString("type").equalsIgnoreCase(creepType)) {
			
					type = obj.getString("type");
					life = obj.getInt("life");		
					image = obj.getString("image");
					score = obj.getInt("score");
					
					try {
						
						String tmp = new String(obj.getString("components"));
						if (tmp != "") {
							 components = tmp.split(",");
						}
						
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					
					try {
						
						shield_interval = Float.valueOf(obj.getString("shield_interval"));
						shield_duration = Float.valueOf(obj.getString("shield_duration"));
						
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					
					try {
						minHit = Integer.valueOf(obj.getString("min_hit"));
					} catch (JSONException e) {
						try {
							minHit = Integer.valueOf(defObj.getString("min_hit"));
						}catch (JSONException e1) {
							e1.printStackTrace();
						}
					}
					
				
				
					try {
						speedUp = Float.valueOf(obj.getString("speed_up"));
					} catch (JSONException e) {
						try {
							speedUp = Float.valueOf(defObj.getString("speed_up"));
						}catch (JSONException e1) {
							e1.printStackTrace();
						}
					}
					
					try {
						speedDown = Float.valueOf(obj.getString("speed_down"));
					} catch (JSONException e) {
						try {
							speedDown = Float.valueOf(defObj.getString("speed_down"));
						}catch (JSONException e1) {
							e1.printStackTrace();
						}
					}
					
					try {
						speedWaiting = Float.valueOf(obj.getString("speed_waiting"));
					} catch (JSONException e) {
						try {
							speedWaiting = Float.valueOf(defObj.getString("speed_waiting"));
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					}
					
					
					JSONArray aItems = obj.getJSONArray("items");
					for(int j = 0 ; j < aItems.length() ; j++) {
						items.add(new CreepItemConfig(aItems.getJSONObject(j)));
					}
				}
			}
			
		} catch (JSONException e2) {

			e2.printStackTrace();
		}
		
	}


	public float getSpeedUp() {
		return speedUp;
	}


	public void setSpeedUp(float speedUp) {
		this.speedUp = speedUp;
	}


	public float getSpeedDown() {
		return speedDown;
	}


	public void setSpeedDown(float speedDown) {
		this.speedDown = speedDown;
	}


	public float getSpeedWaiting() {
		return speedWaiting;
	}


	public void setSpeedWaiting(float speedWaiting) {
		this.speedWaiting = speedWaiting;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public int getMinHit() {
		return minHit;
	}


	public void setMinHit(int minHit) {
		this.minHit = minHit;
	}


	public int getLife() {
		return life;
	}


	public void setLife(int life) {
		this.life = life;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public List<CreepItemConfig> getItems() {
		return items;
	}


	public void setItems(List<CreepItemConfig> items) {
		this.items = items;
	}


	public String findNextItem() {
		
		Random r = new Random();
		
		for(int i = 0 ; i < items.size() ; i++) {
			
			CreepItemConfig item = items.get(i);
			
			if (r.nextInt(101) <= item.getChance()) {
				return item.getType();
			}
			
		}
		
		return null;
	}

	
	public int getComponentsCount() {
		return components.length;
	}

	public String[] getComponents() {
		return components;
	}

	public float getShield_interval() {
		return shield_interval;
	}


	public void setShield_interval(float shield_interval) {
		this.shield_interval = shield_interval;
	}


	public float getShield_duration() {
		return shield_duration;
	}


	public void setShield_duration(float shield_duration) {
		this.shield_duration = shield_duration;
	}


	public String getComponent(int i) {
		return components[i];
	}
	
	

}