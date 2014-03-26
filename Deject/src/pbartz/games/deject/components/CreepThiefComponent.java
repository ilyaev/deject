package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;




public class CreepThiefComponent extends Component {

	public static final int EMPTY = 0;
	public static final int STOLEN = 1;
	
	int state = EMPTY;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
	
}