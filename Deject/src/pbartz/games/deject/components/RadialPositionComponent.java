package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.utils.Pool.Poolable;

public class RadialPositionComponent extends Component implements Poolable{
	
	float angle;
	float distance;
	float armOffset;
	
	public void init(float angle, float distance, float armOffset) {
		this.angle = angle;
		this.distance = distance;
		this.armOffset = armOffset;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getArmOffset() {
		return armOffset;
	}

	public void setArmOffset(float armOffset) {
		this.armOffset = armOffset;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	

}