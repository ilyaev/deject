package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class TextComponent extends Component {
	
	String text = new String();
	float height = -1;
	
	float shiftX = 0;
	float shiftY = 0;
	
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
	
	
	
	
}