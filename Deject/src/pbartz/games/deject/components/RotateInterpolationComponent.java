package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.core.Interpolation;
import pbartz.games.deject.utils.Pool.Poolable;

public class RotateInterpolationComponent extends Component implements Poolable{
	
	float start_angle = 0;
	float end_angle = 0;

	float speed = 0;
	
	float time = 0;
	
	int type = Interpolation.LINEAR;
	
	public void init(float start_angle, float end_angle, float speed, int easing) {
			
		this.start_angle = start_angle;
		this.end_angle = end_angle;
		
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
	
	public float getCurrentAngle() {
		
		float t = time;
		float b = start_angle;
		float c = (end_angle - start_angle);
		float d = speed;
		
		return Interpolation.calculateCurrentValue(type, t, b, c, d);	
		 
	}

	public float getStart_angle() {
		return start_angle;
	}

	public void setStart_angle(float start_angle) {
		this.start_angle = start_angle;
	}

	public float getEndAngle() {
		return end_angle;
	}

	public void setEndAngle(float end_angle) {
		this.end_angle = end_angle;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	
}