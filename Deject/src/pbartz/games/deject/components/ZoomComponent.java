package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class ZoomComponent extends Component {

	float zoomX;
	float zoomY;

	
	public ZoomComponent(float zX, float zY) {
		
		zoomX = zX;
		zoomY = zY;		
		
	}


	public float getZoomX() {
		return zoomX;
	}


	public void setZoomX(float zoomX) {
		this.zoomX = zoomX;
	}


	public float getZoomY() {
		return zoomY;
	}


	public void setZoomY(float zoomY) {
		this.zoomY = zoomY;
	}
	
}
