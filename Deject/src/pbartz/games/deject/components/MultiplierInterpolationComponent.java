package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.core.Interpolation;
import pbartz.games.deject.utils.Pool.Poolable;

public class MultiplierInterpolationComponent extends Component implements Poolable {

	
	float start_multiplier = 0;
	float end_multiplier = 0;

	float speed = 0;
	
	float time = 0;
	
	int type = Interpolation.LINEAR;
	
	public void init(float start_multiplier, float end_multiplier, float speed, int easing) {
		
		this.start_multiplier = start_multiplier;
		this.end_multiplier = end_multiplier;
		
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

	public float getStart_multiplier() {
		return start_multiplier;
	}

	public void setStart_multiplier(float start_multiplier) {
		this.start_multiplier = start_multiplier;
	}

	public float getEnd_multiplier() {
		return end_multiplier;
	}

	public void setEnd_multiplier(float end_multiplier) {
		this.end_multiplier = end_multiplier;
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

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
