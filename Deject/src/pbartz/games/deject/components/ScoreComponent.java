package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class ScoreComponent extends Component {
	
	private int score = 0;
	private int level = 1;
	private int life = 16;
	private int strength = 1;
	private int gold = 0;

	public ScoreComponent() {
		
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore(int add) {
		setScore(getScore() + add);
	}
	
	public void addLife(int add) {
		setLife(Math.max(getLife() + add, 0));
	}
	
	public void increasLevel() {
		setLevel(getLevel() + 1);
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public void addGold(int add) {
		setGold(getGold() + add);		
	}

	private void setGold(int i) {
		gold = i;		
	}

	public int getGold() {
		return gold;
	}
	
	
	
}