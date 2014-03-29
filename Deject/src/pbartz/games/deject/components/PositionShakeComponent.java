package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.utils.Pool.Poolable;

public class PositionShakeComponent extends Component implements Poolable {

	float shiftX;
	float shiftY;
	
	float speed = 0;
	
	float time = 0;
	
	public void init(float shiftX, float shiftY, float speed) {
		this.shiftX = shiftX;
		this.shiftY = shiftY;
		this.speed = speed * 1000;
		time = 0;
	}
	
	public void increaseTime(float diff) {
		this.time += diff;
	}

	public boolean isCompleted() {
		if (this.time >= this.speed) return true;
		return false;
	}
	
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}


	public float getShiftX() {
		return shiftX;
	}


	public void setShiftX(float shiftX) {
		this.shiftX = shiftX;
	}


	public float getShiftY() {
		return shiftY;
	}


	public void setShiftY(float shiftY) {
		this.shiftY = shiftY;
	}
	
}
