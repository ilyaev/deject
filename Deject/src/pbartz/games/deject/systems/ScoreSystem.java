package pbartz.games.deject.systems;

import pbartz.games.deject.BitmapLibrary;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.AIComponent;
import pbartz.games.deject.components.BitmapComponent;
import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.ScoreComponent;
import pbartz.games.deject.components.TextComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.signals.Signal;
import pbartz.games.deject.utils.ObjectMap;

public class ScoreSystem extends IteratingSystem {
	
	Entity score = null;
	Entity labelScore = null;
	Entity labelGold = null;
	Entity progressBar = null;
	
	ObjectMap <Integer, Entity> hearts = new ObjectMap<Integer, Entity>();
	
	public Signal<Entity> gameOverSignal = new Signal<Entity>();
	private DejectSurface surface;

	@SuppressWarnings("unchecked")
	public ScoreSystem(DejectSurface surface) {
		super(Family.getFamilyFor(ScoreComponent.class));
		
		this.surface = surface;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		if (this.score == null) {
			this.score = entity;
		}
	}
	
	public int getStength() {
		if (this.score != null) {
			
			return this.score.getComponent(ScoreComponent.class).getStrength();
			
		}
		
		return 0;
	}
	
	public void increaseScore(int add) {
		if (this.score != null && this.labelScore != null) {
			
			this.score.getComponent(ScoreComponent.class).addScore(add);
			
			int curScore = this.score.getComponent(ScoreComponent.class).getScore();
			
			this.labelScore.getComponent(TextComponent.class).setText(Integer.toString(curScore));
			
		}
	}
	
	public void increaseGold(int add) {
		if (this.score != null && this.labelGold != null) {
			
			this.score.getComponent(ScoreComponent.class).addGold(add);
			
			int curGold = this.score.getComponent(ScoreComponent.class).getGold();
			
			this.labelGold.getComponent(TextComponent.class).setText(Integer.toString(curGold));
			
		}
	}
	
	public void increaseLife(int add) {
		if (this.score != null) {
			
			this.score.getComponent(ScoreComponent.class).addLife(add);
			
			int curLife = this.score.getComponent(ScoreComponent.class).getLife(); 

			syncHearts();
			
			if (curLife <= 0) {
				gameOverSignal.dispatch(this.score);
			}
			
			if (add < 0 && engine.getSystem(AISystem.class).getAi().getState() != AIComponent.STATE_GAMEOVER) {
				EntityFactory.spawnLifeLose(engine, surface, Math.round(curLife / 2));
			}
			
		}
	}
	
	private void syncHearts() {
		int curLife = this.score.getComponent(ScoreComponent.class).getLife();
		
		int full = (int) Math.floor((double)curLife / 2d);
		
		int rest = curLife - full * 2;
		
		for(int i = 0 ; i < 8 ; i++) {
			
			Entity heart = hearts.get(i);
			
			if (heart != null) {
				
				heart.remove(BitmapComponent.class);
				heart.getComponent(ColorComponent.class).setAlpha(0);
				
				if (i < full) {
					heart.getComponent(ColorComponent.class).setAlpha(255);
					heart.add(new BitmapComponent(BitmapLibrary.getBitmap("heart_small")));
				}
				
				if (i == full && rest == 1) {
					heart.getComponent(ColorComponent.class).setAlpha(255);
					heart.add(new BitmapComponent(BitmapLibrary.getBitmap("heart_small_half")));
				}
				
			}
			
			
			
		}
		
	}

	public Entity getScoreEntity() {
		return score;
	}

	public void setScoreEntity(Entity score) {
		this.score = score;
	}

	public Entity getLabelScoreEntity() {
		return labelScore;
	}

	public void setLabelScoreEntity(Entity labelScore) {
		this.labelScore = labelScore;
	}
	
	public Entity getLabelGoldEntity() {
		return labelGold;
	}

	public void setLabelGoldEntity(Entity labelGold) {
		this.labelGold = labelGold;
	}
	
	public void setProgressBarEntity(Entity pbar) {
		this.progressBar = pbar;
	}

	public void setHeart(int i, Entity heart) {
		hearts.put(i, heart);		
	}

}