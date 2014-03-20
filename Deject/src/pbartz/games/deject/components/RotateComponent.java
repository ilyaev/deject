package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class RotateComponent extends Component {
	
	float angle = 0;
	
	public RotateComponent(float angle) {
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
	
}