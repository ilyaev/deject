package pbartz.games.deject.components;

import android.graphics.Rect;
import pbartz.games.deject.core.Component;

public class TextComponent extends Component {
	
	String text = new String();
	String align = "left";
	float height = -1;
	
	float shiftX = 0;
	float shiftY = 0;
	
	Rect bounds = null;
	
	public TextComponent(String text) {
		this.text = text;
	}
	
	public TextComponent(String text, float height) {
		this.text = text;
		this.height = height;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
		if (bounds != null) {
			bounds = null;
		}
	}

	public float getShiftX() {
		return shiftX;
	}

	public void setShiftX(float shiftX) {
		this.shiftX = shiftX;
	}

	public float getShiftY() {
		return shiftY;
	}

	public void setShiftY(float shiftY) {
		this.shiftY = shiftY;
	}

	public void setAlign(String string) {

		align = string;
		
	}
	
	public String getAlign() {
		return align;
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds;
	}
	
	
	
	
	
}