package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.core.Interpolation;
import pbartz.games.deject.utils.Pool.Poolable;

public class RadialPositionInterpolationComponent extends Component implements Poolable{
	
	int type = Interpolation.LINEAR;
	
	float startDistance = 0;
	float endDistance = 0;
	
	float startAngle = 0;
	float endAngle = 0;
	
	float speed = 0;
	
	float time = 0;
	
	public void init(float startDistance, float endDistance, float startAngle, float endAngle, float speed, int easing) {
		
		this.startDistance = startDistance;
		this.endDistance = endDistance;
		
		this.startAngle = startAngle;
		this.endAngle = endAngle;
		
		this.type = easing;
		
		this.speed = speed * 1000;	
		
	}
	
	public void increaseTime(float diff) {
		this.time += diff;
	}

	public boolean isCompleted() {
		if (this.time >= this.speed) return true;
		return false;
	}
	
	public int getType() {
		return type;
	}

	public float getStartDistance() {
		return startDistance;
	}

	public float getEndDistance() {
		return endDistance;
	}

	public float getStartAngle() {
		return startAngle;
	}

	public float getEndAngle() {
		return endAngle;
	}

	public float getSpeed() {
		return speed;
	}

	public float getTime() {
		return time;
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
