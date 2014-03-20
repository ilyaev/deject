package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class PositionComponent extends Component {
	public float x, y;
	public float originalX, originalY;

	public PositionComponent(float x, float y){
		this.x = x;
		this.y = y;
		
		originalX = x;
		originalY = y;
	}

	public void setPosition(float x2, float y2) {
		this.x = x2;
		this.y = y2;		
	}

	public float getOriginalX() {
		return originalX;
	}

	public float getOriginalY() {
		return originalY;
	}
	
}