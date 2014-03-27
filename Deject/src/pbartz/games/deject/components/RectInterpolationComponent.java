package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.core.Interpolation;
import pbartz.games.deject.utils.Pool.Poolable;

public class RectInterpolationComponent extends Component implements Poolable {
	
	float start_width = 0;
	float end_width = 0;
	
	float start_height = 0;
	float end_height = 0;

	float speed = 0;
	
	float time = 0;
	
	int type = Interpolation.LINEAR;
	
	public void init(float start_width, float end_width, float start_height, float end_height, float speed, int easing) {
		
		this.start_width = start_width;
		this.end_width = end_width;
		
		this.start_height = start_height;
		this.end_height = end_height;
				

		this.speed = speed * 1000;		
		this.type = easing;
		
		this.time = 0;
	}
	
	public void increaseTime(float diff) {
		this.time += diff;
	}
	
	public float getLeftTime() {
		
		return (this.speed - this.time) / 1000f;
		
	}
	
	public boolean isCompleted() {
		if (this.time >= this.speed) return true;
		return false;
	}
	
	public float getCurrentWidth() {
		
		float t = time;
		float b = start_width;
		float c = (end_width - start_width);
		float d = speed;
		
		return Interpolation.calculateCurrentValue(type, t, b, c, d);	
		 
	}
	
	public float getCurrentHeight() {
		
		float t = time;
		float b = start_height;
		float c = (end_height - start_height);
		float d = speed;
		
		return Interpolation.calculateCurrentValue(type, t, b, c, d);	
		 
	}

	public float getEndWidth() {
		return end_width;
	}

	public float getEndHeight() {
		return end_height;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
