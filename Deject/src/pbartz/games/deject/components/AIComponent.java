package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class AIComponent extends Component {

	public static final int STATE_NOT_INITED = 0;
	public static final int STATE_WORKING = 1;
	public static final int STATE_WAITING = 2;
	public static final int STATE_GAMEOVER = 3;
	public static final int STATE_STARTING = 4;
	public static final int STATE_LEVEL_COMPLETED = 5;
	public static final int STATE_SHOP = 6;
	
	float timer = 0;
	int state = STATE_NOT_INITED;
	
	
	public AIComponent() {
		
	}
	
	public boolean isNextEvent(float diff) {
		timer = Math.max(0, timer - diff);
		return timer > 0 ? false : true;
	}

	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer * 1000;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}	
	
	
}