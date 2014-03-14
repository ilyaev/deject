package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class LevelInfoComponent extends Component {
	
	public static final int STATE_GOING_UP = 1;
	public static final int STATE_GOING_DOWN = 2;
	public static final int STATE_NOT_INITED = 0;
	public static final int STATE_WAITING = 3;
	public static final int STATE_FORCE_REMOVE = 4;
	public static final int STATE_GO_DOWN = 5;

	int level = 1;
	int state = STATE_NOT_INITED;
	private float timeToNextState;
	
	float speedUp = 0.5f;
	float speedDown = 0.5f;
	
	public LevelInfoComponent(int level) {
		this.level = level;
	}
	
	public boolean isNextEvent(float diff) {

		timeToNextState = Math.max(0, timeToNextState - diff);
		
		return timeToNextState == 0;
		
	}
	
	public void setTimeToNextState(float timeToNextState) {
		this.timeToNextState = timeToNextState * 1000;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		if (state == STATE_GOING_UP) {
			setTimeToNextState(speedUp);
		} else if (state == STATE_GOING_DOWN) {
			setTimeToNextState(speedDown);
		}
	}	
	

	public float getSpeedUp() {
		return speedUp;
	}

	public void setSpeedUp(float speedUp) {
		this.speedUp = speedUp;
	}

	public float getSpeedDown() {
		return speedDown;
	}

	public void setSpeedDown(float speedDown) {
		this.speedDown = speedDown;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}