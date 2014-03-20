package pbartz.games.deject.components.dimension;

import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.core.Component;
import android.graphics.Rect;

public class RectDimensionComponent extends Component {
	
	int width = 0;
	int height = 0;
	
	float oldX = 0;
	float oldY = 0;
	
	Rect rect = null;
	private Rect zero_rect;
	
	public RectDimensionComponent(int w, int h) {
		this.width = w;
		this.height = h;
	}
	
	public boolean isIntercect(int cx, int cy, PositionComponent position) {
		
		return this.getRect(position).contains(cx, cy);
		
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public Rect getZeroRect() {
		if (zero_rect == null) {
			zero_rect = new Rect(-(int)(width/2), -(int)(height/2), (int)(width / 2), (int)(height / 2));
		};
		return zero_rect;		
	}
	
	
	public Rect getRect(PositionComponent position) {
		if (rect == null) {
			rect = new Rect();
		}
		rect.set((int)position.x - (int)width/2, (int)position.y - (int)height/2, (int)position.x + (int)width / 2, (int)position.y + (int)height / 2);
		return rect;
	}

	public void setRadius(int radius) {		
		width = radius;
		height = radius;		
	}

	public void setDimension(float currentWidth, float currentHeight) {
		width = (int) currentWidth;
		height = (int) currentHeight;
		
		if (zero_rect != null) {
			zero_rect.set(-(int)(width/2), -(int)(height/2), (int)(width / 2), (int)(height / 2));
		}
	}

}