package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class GalaxyEmitterComponent extends Component {
	
	float centerX;
	float centerY;
	float width;
	float height;
	
	float baseSpeed = 7f;
	
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
	

}