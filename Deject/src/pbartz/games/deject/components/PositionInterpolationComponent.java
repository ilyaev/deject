package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.core.Interpolation;
import pbartz.games.deject.utils.Pool.Poolable;

public class PositionInterpolationComponent extends Component implements Poolable {


	int type = Interpolation.LINEAR;

	public float destX = 0;
	public float destY = 0;
	
	public float startX = 0;
	public float startY = 0;
	
	float speed = 0;
	
	float time = 0;
	
	public void init(PositionComponent position, float newX, float newY, float speed, int easing) {
		this.destX = newX;
		this.destY = newY;
		this.startX = position.x;
		this.startY = position.y;
		this.speed = speed * 1000;		
		this.type = easing;
	}	
	

	public void increaseTime(float diff) {
		this.time += diff;
	}

	public boolean isCompleted() {
		if (this.time >= this.speed) return true;
		return false;
	}
	
	public float getCurrentX() {
		
		float t = time;
		float b = startX;
		float c = (destX - startX);
		float d = speed;
		
		return Interpolation.calculateCurrentValue(type, t, b, c, d);	
		 
	}
	

	public float getCurrentY() {
		float t = time;
		float b = startY;
		float c = (destY - startY);
		float d = speed;
		
		return Interpolation.calculateCurrentValue(type, t, b, c, d);
		
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
		time = 0;
	}
	
}