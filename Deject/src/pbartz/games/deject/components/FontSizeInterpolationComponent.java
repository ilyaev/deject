package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.core.Interpolation;

public class FontSizeInterpolationComponent extends Component {

	float startSize = 0;
	float endSize = 0;

	float speed = 0;
	
	float time = 0;
	
	int type = Interpolation.LINEAR;
	
	
	public FontSizeInterpolationComponent(float startSize, float endSize, float speed, int easing) {
		
		this.startSize = startSize;
		this.endSize = endSize;
		
		this.speed = speed * 1000;		
		this.type = easing;
		
		time = 0;
		
	}
	
	public void increaseTime(float diff) {
		this.time += diff;
	}
	
	public boolean isCompleted() {
		if (this.time >= this.speed) return true;
		return false;
	}

	public float getStartSize() {
		return startSize;
	}

	public void setStartSize(float startSize) {
		this.startSize = startSize;
	}

	public float getEndSize() {
		return endSize;
	}

	public void setEndSize(float endSize) {
		this.endSize = endSize;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	

}
