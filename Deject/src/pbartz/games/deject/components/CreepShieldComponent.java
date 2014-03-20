package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class CreepShieldComponent extends Component {
	
	public static final int STATE_OPEN = 0;
	public static final int STATE_CLOSED = 1;
	
	float shield_interval = 0f;
	float shield_duration = 0f;
	
	int state = 0;
	
	float timeToNextState = 0f;
	
	public CreepShieldComponent(float shield_interval, float shield_duration) {
		
		this.shield_duration = shield_duration;
		this.shield_interval = shield_interval;
		
		this.setState(STATE_OPEN);
		
	}
	
	public void setState(int newState) {
		state = newState;
		
		if (state == STATE_OPEN) {
			timeToNextState = shield_interval * 1000;
		}
		
		if (state == STATE_CLOSED) {
			timeToNextState = shield_duration * 1000;
		}
	}
	
	public boolean isNextEvent(float diff) {

		timeToNextState = Math.max(0, timeToNextState - diff);
		
		return timeToNextState == 0;
		
	}

	public int getState() {
		return state;
	}
	

}