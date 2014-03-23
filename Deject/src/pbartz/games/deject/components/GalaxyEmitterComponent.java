package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class GalaxyEmitterComponent extends Component {
	
	float centerX;
	float centerY;
	float width;
	float height;
	
	float baseSpeed = 7f;
	int newStarChance = 30;
	int burstChance = 5;
	int burstStars = 10;
	int colorStarChance = 5;
	int arms = 3;
	
	public GalaxyEmitterComponent(float centerX, float centerY, float width, float height) {
		
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.height = height;
		
	}

	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getBaseSpeed() {
		return baseSpeed;
	}

	public void setBaseSpeed(float baseSpeed) {
		this.baseSpeed = baseSpeed;
	}

	public int getArms() {
		return arms;
	}

	public void setArms(int arms) {
		this.arms = arms;
	}

	public int getNewStarChance() {
		return newStarChance;
	}

	public void setNewStarChance(int newStarChance) {
		this.newStarChance = newStarChance;
	}

	public int getBurstChance() {
		return burstChance;
	}

	public void setBurstChance(int burstChance) {
		this.burstChance = burstChance;
	}

	public int getBurstStars() {
		return burstStars;
	}

	public void setBurstStars(int burstStars) {
		this.burstStars = burstStars;
	}

	public int getColorStarChance() {
		return colorStarChance;
	}

	public void setColorStarChance(int colorStarChance) {
		this.colorStarChance = colorStarChance;
	}
	
	
	

}