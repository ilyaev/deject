package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class TextComponent extends Component {
	
	String text = new String();
	float height = -1;
	
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
}