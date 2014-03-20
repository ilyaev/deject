package pbartz.games.deject.systems;

import java.util.Random;

import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.AIComponent;
import pbartz.games.deject.components.CreepComponent;
import pbartz.games.deject.components.CreepSwapComponent;
import pbartz.games.deject.components.LevelInfoComponent;
import pbartz.games.deject.components.RectInterpolationComponent;
import pbartz.games.deject.components.ScoreComponent;
import pbartz.games.deject.config.GameConfig;
import pbartz.games.deject.config.LevelConfig;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.core.Interpolation;
import pbartz.games.deject.signals.Signal;
import pbartz.games.deject.utils.ObjectMap;

public class AISystem extends IteratingSystem {

	AIComponent ai;
	
	ScoreComponent score = null;
	LevelConfig level = null;
	
	public Entity levelInfo = null;
	
	Entity progressBar = null;
	
	private DejectSurface surface;
	
	ObjectMap<Integer, Entity> creeps = new ObjectMap<Integer, Entity>();
	ObjectMap<Integer, Entity> items = new ObjectMap<Integer, Entity>();
	
	public Signal<LevelConfig> levelCompletedSignal = new Signal<LevelConfig>();
	
	@SuppressWarnings("unchecked")
	public AISystem(DejectSurface surface) {
		super(Family.getFamilyFor(AIComponent.class));
		this.surface = surface;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		ai = entity.getComponent(AIComponent.class);
		
		if (ai.getState() == AIComponent.STATE_NOT_INITED && levelInfo == null) {
			
			levelInfo = EntityFactory.spawnLevelInfoPanel(engine, surface, score.getLevel());			
			
		} else if (ai.getState() == AIComponent.STATE_LEVEL_COMPLETED && levelInfo == null) {
			
			score.increasLevel();
			levelInfo = EntityFactory.spawnLevelInfoPanel(engine, surface, score.getLevel());
			
		} else if (ai.getState() == AIComponent.STATE_STARTING) {
			
			ai.setState(AIComponent.STATE_WORKING);		
			
			level = GameConfig.getLevelConfig(score.getLevel());
			
			releaseProgressBar();
			
		} else if (ai.getState() == AIComponent.STATE_WORKING) {
		
			if (ai.isNextEvent(deltaTime) && progressBar.getComponent(RectInterpolationComponent.class) != null) {
				
				
				
				if (!isBoardFilled()) {
					int position = getNextFreeCell();
					
					String creepType = level.getNextCreep();
					
					if (creepType != null) {
						Entity newCreep = EntityFactory.spawnCellCreep(engine, surface, position, creepType);
						creeps.put(position, newCreep);
					}
				}
				
				Random r = new Random();
				ai.setTimer(0.5f + r.nextFloat());
				
			}
			
			if (progressBar.getComponent(RectInterpolationComponent.class) == null) {
				
				if (isEmptyField()) {
					levelCompletedSignal.dispatch(level);
				}
				
			}

		}
		
	}
	
	private boolean isEmptyField() {
		
		for (int i = 1 ; i <= 9 ; i++ ) {
			if (creeps.get(i, null) != null || items.get(i, null) != null) {
				return false;
			}
		}
		
		return true;
	}

	public void releaseProgressBar() {
		
		progressBar.add(new RectInterpolationComponent(
			EntityFactory.pbarWidth,
			0,			
			EntityFactory.pbarHeight,
			EntityFactory.pbarHeight,
			level.getDuration(), 
			Interpolation.LINEAR
		));
		
	}

	public ObjectMap<Integer, Entity> getCreeps() {
		return creeps;
	}
	
	public ObjectMap<Integer, Entity> getItems() {
		return items;
	}

	public AIComponent getAi() {
		return ai;
	}

	public void setAi(AIComponent ai) {
		this.ai = ai;
	}
	
	private boolean isBoardFilled() {
		for (int i = 1 ; i <= 9 ; i++ ) {
			if (creeps.get(i, null) == null || items.get(i, null) == null) {
				return false;
			}
		}		
		return true;
	}

	private int getNextFreeCell() {
		int position = 0;
		
		boolean flag = false;
		
		Random r = new Random();
		
		do {
			
			position = r.nextInt(9) + 1;
			
			if (creeps.get(position, null) == null && items.get(position, null) == null) {
				flag = true;
			}
			
		} while (!flag);
			
		
		return position;
	}

	public void generateItem(Entity entity) {
		int position = entity.getComponent(CreepComponent.class).getPosition();	
		
		String itemType = entity.getComponent(CreepComponent.class).getNextItem();
		
		if (itemType != null) {
			Entity item = EntityFactory.spawnCellItem(engine, surface, position, itemType);
			items.put(position, item);
		}
	}

	public void setScore(ScoreComponent scoreComponent) {
		score = scoreComponent;	
	}
	
	public void setProgressBarEntity(Entity pbar) {
		this.progressBar = pbar;
	}

	public void startLevel() {
		levelInfo.getComponent(LevelInfoComponent.class).setState(LevelInfoComponent.STATE_GO_DOWN);
		EntityFactory.animateButtonsUp();
		ai.setState(AIComponent.STATE_STARTING);		
	}

	public Entity getSwapCreep(int position) {
		
		for(int i = 1 ; i <= 9 ; i++) {
			
			Entity entity = creeps.get(i);
			
			if (entity != null) {
				
				CreepSwapComponent swap = entity.getComponent(CreepSwapComponent.class);
				
				if (swap != null && swap.getTouchKey() == position) {
					return entity;
				}
				
			}
		}
		
		return null;
	}

}