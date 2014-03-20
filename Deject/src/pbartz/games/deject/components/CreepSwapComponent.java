package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class CreepSwapComponent extends Component {

	
	int touchKey = 0;
	
	
	public CreepSwapComponent(int key) {
		touchKey = key;
	}


	public int getTouchKey() {
		return touchKey;
	}


	public void setTouchKey(int touchKey) {
		this.touchKey = touchKey;
	}
	
	
}