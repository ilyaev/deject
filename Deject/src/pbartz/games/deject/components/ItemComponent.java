package pbartz.games.deject.components;

import pbartz.games.deject.config.ItemConfig;
import pbartz.games.deject.core.Component;

public class ItemComponent extends Component {
	
	public static final int NOT_INITED = 0;
	public static final int TOSS_UP = 1;
	public static final int TOSS_DOWN = 2;
	public static final int WAITING = 3;
	public static final int GOING_DOWN = 4;
	public static final int BLOW_UP = 5;
	public static final int BLOW = 6;
	
	
	public static final int FORCE_REMOVE = 10;
	
	public static final int TYPE_COIN = 0;
	
	
	ItemConfig config = null;

	int gold = 1;
	int life = 0;
	
	int state = NOT_INITED;
	int position = 0;
	int type = TYPE_COIN;
	
	float speedUp = 0.3f;
	float speedDown = 0.3f;
	float waitingTime = 2f;
	float speedGoingDown = 0.5f;
	
	float timeToNextState = 0;
	
	
	public ItemComponent(int position, int weight) {
		this.gold = weight;
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
	public boolean isNextEvent(float diff) {

		timeToNextState = Math.max(0, timeToNextState - diff);
		
		return timeToNextState == 0;
		
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		
		if (state == FORCE_REMOVE || state == BLOW_UP) {
			setTimeToNextState(0);
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

	public float getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(float waitingTime) {
		this.waitingTime = waitingTime;
	}

	public float getTimeToNextState() {
		return timeToNextState;
	}

	public void setTimeToNextState(float timeToNextState) {
		this.timeToNextState = timeToNextState * 1000;
	}

	public float getSpeedGoingDown() {
		return speedGoingDown;
	}

	public void setSpeedGoingDown(float speedGoingDown) {
		this.speedGoingDown = speedGoingDown;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public void setConfig(ItemConfig itemConfig) {
		config = itemConfig;
		gold = itemConfig.getGold();
		life = itemConfig.getLife();
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	
	
	
	
}