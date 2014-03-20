package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;



public class TouchComponent extends Component {

	private int status;
	
	public final static int UNTOUCHED = 0;
	public final static int TOUCHED = 1;
	
	public TouchComponent() {
		this.status = TouchComponent.UNTOUCHED;
	}
	
	public void setStatus(int new_status) {
		this.status = new_status;
	}
	
	public int getStatus() {
		return this.status;
	}	
	
	
}