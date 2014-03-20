package pbartz.games.deject.components;

import pbartz.games.deject.config.CreepConfig;
import pbartz.games.deject.core.Component;

public class CreepComponent extends Component {
	
	public static final int NOT_INITED = 0;
	public static final int GOING_UP = 1;
	public static final int WAITING = 2;
	public static final int GOING_DOWN = 3;
	public static final int FORCE_REMOVE = 4;
	public static final int HIT = 5;
	public static final int HIT_END = 6;
	
	
	int position = -1;
	
	int state = NOT_INITED;
	
	float speedUp = 0;
	float speedDown = 0;
	float waitingTime = 0;
	
	float timeToNextState = 0;
	
	int health = 1;
	int score = 0;
	int minHit = 1;
	
	int oldState = NOT_INITED;
	int oldTimeToNextState = 0;
	
	String type = "";
	
	private CreepConfig config;
	
	public CreepComponent(int position) {
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
		
		if (state == FORCE_REMOVE) {
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

	public float getLifeTime() {
		return speedUp + speedDown + waitingTime;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public boolean decreasHealth(int diff) {
		this.health = Math.max(0, this.health - diff);
		return this.health == 0;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setConfig(CreepConfig creepConfig) {
		config = creepConfig;		
		speedDown = config.getSpeedDown();
		speedUp = config.getSpeedUp();
		waitingTime = config.getSpeedWaiting();
		health = config.getLife();
		score = config.getScore();
		minHit = config.getMinHit();
		type = config.getType();
	}
	
	public CreepConfig getConfig() {
		return config;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMinHit() {
		return minHit;
	}

	public void setMinHit(int minHit) {
		this.minHit = minHit;
	}

	public String getNextItem() {
		return config.findNextItem();
	}

	
}