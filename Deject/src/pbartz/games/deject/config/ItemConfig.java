package pbartz.games.deject.config;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemConfig {
	
	String type = "";
	String image = "";
	
	int life = 0;
	int score = 0;
	int gold = 0;
	
	int cost = 0;
	
	float waitingTime = 2f;
	
	
	public ItemConfig(String itemType) {
		
		try {
			
			JSONObject defObj = GameConfig.config.getJSONObject("items_default_properties");
			
			for(int i = 0 ; i < GameConfig.config.getJSONArray("items").length() ; i++) {
				
				JSONObject obj = GameConfig.config.getJSONArray("items").getJSONObject(i);
				
				if (obj.getString("type").equalsIgnoreCase(itemType)) {
			
					type = obj.getString("type");
					image = obj.getString("image");
					
					try {
						waitingTime = Float.valueOf(obj.getString("waitingTime"));
					} catch (JSONException e) {
						try {
							waitingTime = Float.valueOf(defObj.getString("waitingTime"));
						}catch (JSONException e1) {
							e1.printStackTrace();
						}
					}
					
					try {
						life = Integer.valueOf(obj.getString("life"));
					} catch (JSONException e) {
						try {
							life = Integer.valueOf(defObj.getString("life"));
						}catch (JSONException e1) {
							e1.printStackTrace();
						}
					}
					
					try {
						score = Integer.valueOf(obj.getString("score"));
					} catch (JSONException e) {
						try {
							score = Integer.valueOf(defObj.getString("score"));
						}catch (JSONException e1) {
							e1.printStackTrace();
						}
					}	
					
					try {
						gold = Integer.valueOf(obj.getString("gold"));
					} catch (JSONException e) {
						try {
							gold = Integer.valueOf(defObj.getString("gold"));
						}catch (JSONException e1) {
							e1.printStackTrace();
						}
					}
					
					
					try {
						cost = Integer.valueOf(obj.getString("cost"));
					} catch (JSONException e) {
						try {
							cost = Integer.valueOf(defObj.getString("cost"));
						}catch (JSONException e1) {
							e1.printStackTrace();
						}
					}
					
				}
			}
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public float getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(float waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	
	
}