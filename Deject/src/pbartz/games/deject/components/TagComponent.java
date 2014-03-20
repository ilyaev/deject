package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class TagComponent extends Component {
	
	String tag = "";
	
	public TagComponent(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}