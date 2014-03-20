package pbartz.games.deject.components.dimension;

import pbartz.games.deject.core.Component;

public class CircleDimensionComponent extends Component {

	int cX, cY;
	int radius;
	
	public CircleDimensionComponent(int cX, int cY, int radius) {
		this.cX = cX;
		this.cY = cY;
		this.radius = radius;
	}
	
	public boolean isIntercect(int cx, int cy) {
		
		return (Math.abs(this.cX - cx) <= this.radius)
				&& (Math.abs(this.cY - cy) <= this.radius);
		
	}

	public float getcX() {
		return cX;
	}
	
	public float getcY() {
		return cY;
	}
	
	public float getRadius() {
		return radius;
	}
}