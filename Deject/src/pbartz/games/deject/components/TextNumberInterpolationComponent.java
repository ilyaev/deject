package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.core.Interpolation;

public class TextNumberInterpolationComponent extends Component {

	int startNumber = 0;
	int endNumber = 0;
	
	float speed = 0;
	int type = Interpolation.LINEAR;
	float time = 0;
	
	public TextNumberInterpolationComponent(int startNumber, int endNumber, float speed, int easing) {
		
		this.startNumber = startNumber;
		this.endNumber = endNumber;
		
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

	public int getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}

	public int getEndNumber() {
		return endNumber;
	}

	public void setEndNumber(int endNumber) {
		this.endNumber = endNumber;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}
	
	
	
}