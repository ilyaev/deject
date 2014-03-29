package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class BitmapAnimationComponent extends Component {

	String nameBase = "";
	int key = 0;
	int maxKeys = 0;
	
	int delay = 100;
	
	boolean loop = false;
	
	int time = 0;
	
	public BitmapAnimationComponent(String nameBase, int keys, int delay) {
		
		this.nameBase = nameBase;
		key = 1;
		maxKeys = keys;
		this.delay = delay;
		
	}

	public String getNameBase() {
		return nameBase;
	}

	public void setNameBase(String nameBase) {
		this.nameBase = nameBase;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getMaxKeys() {
		return maxKeys;
	}

	public void setMaxKeys(int maxKeys) {
		this.maxKeys = maxKeys;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public int getTime() {
		return time;
	}

	public void addTime(int time) {
		this.time += time;
	}
	
	public void clearTime() {
		this.time = 0;
	}
	
	
	
}