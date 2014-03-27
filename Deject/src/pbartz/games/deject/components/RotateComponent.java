package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.utils.Pool.Poolable;

public class RotateComponent extends Component implements Poolable {
	
	float angle = 0;
	
	public void init(float angle) {
		this.angle = 0;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float next) {
		angle = next;
		if (angle > 360) {
			angle = angle - 360;
		}
		
		if (angle < 0) {
			angle = 360 + angle;
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
}