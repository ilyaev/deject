package pbartz.games.deject.systems;

import android.graphics.Paint;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.ColorInterpolationComponent;
import pbartz.games.deject.components.CreepComponent;
import pbartz.games.deject.components.CreepShieldComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.PositionInterpolationComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.core.Interpolation;

public class CreepSystem extends IteratingSystem {
	
	CreepComponent creep;
	ColorComponent color;
	PositionComponent position;
	RectDimensionComponent dimension;

	@SuppressWarnings("unchecked")
	public CreepSystem() {
		super(Family.getFamilyFor(CreepComponent.class, PositionComponent.class, ColorComponent.class, RectDimensionComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		creep = entity.getComponent(CreepComponent.class);
		
		if (creep.isNextEvent(deltaTime)) {
			
			color = entity.getComponent(ColorComponent.class);
			position = entity.getComponent(PositionComponent.class);
			dimension = entity.getComponent(RectDimensionComponent.class);
			
			if (creep.getState() == CreepComponent.NOT_INITED) {
				
				Paint tmpPaint = new Paint();
				
				color.setAlpha(0);
				
				tmpPaint.set(color.getPaint());
				tmpPaint.setAlpha(255);
				
				entity.add(new ColorInterpolationComponent(
					color.getPaint(), 
					tmpPaint, 
					creep.getSpeedUp(), 
					Interpolation.EASE_IN
				));
				
				entity.add(new PositionInterpolationComponent(
					new PositionComponent(position.x, position.y + (dimension.getHeight())), 
					position.x, 
					position.y, 
					creep.getSpeedUp(), 
					Interpolation.EASE_IN
				));
				
				creep.setTimeToNextState(creep.getSpeedUp());
				creep.setState(CreepComponent.GOING_UP);
				
				
			} else if (creep.getState() == CreepComponent.GOING_UP) {
				
				color.setAlpha(255);
				
				creep.setTimeToNextState(creep.getWaitingTime());
				creep.setState(CreepComponent.WAITING);
				
			} else if (creep.getState() == CreepComponent.WAITING) {
				
				Paint tmpPaint = new Paint();
				
				color.setAlpha(255);
				
				tmpPaint.set(color.getPaint());
				tmpPaint.setAlpha(0);
				
				entity.add(new ColorInterpolationComponent(
					color.getPaint(), 
					tmpPaint, 
					creep.getSpeedDown(), 
					Interpolation.EASE_IN
				));
				
				entity.add(new PositionInterpolationComponent(
					position, 
					position.x, 
					position.y + (dimension.getHeight()), 
					creep.getSpeedDown(), 
					Interpolation.EASE_IN
				));
				
				creep.setTimeToNextState(creep.getSpeedDown());
				creep.setState(CreepComponent.GOING_DOWN);
				
			} else if (creep.getState() == CreepComponent.GOING_DOWN) {
				
				creepMissed(entity);
				
				creep.setState(CreepComponent.FORCE_REMOVE);				
				
				
			} else if (creep.getState() == CreepComponent.FORCE_REMOVE) {
				
				wipeCreep(entity);
				
			}
			
			
		}	
		
	}
	
	private void wipeCreep(Entity entity) {
		engine.getSystem(AISystem.class).getCreeps().put(entity.getComponent(CreepComponent.class).getPosition(), null);
		engine.removeEntity(entity);
	}
	
	public void creepTouched(Entity entity, DejectSurface surface) {
		
		boolean canBeHit = true;
		
		CreepShieldComponent shield = entity.getComponent(CreepShieldComponent.class);
		
		if (shield != null) {
			
			if (shield.getState() == CreepShieldComponent.STATE_CLOSED) {
				
				canBeHit = false;
				
			}
			
		}
		
		int lifeLose = 0;
		
		if (entity.getComponent(CreepComponent.class).getMinHit() > engine.getSystem(ScoreSystem.class).getStength()) {

			lifeLose = 1;
			
		}
			
		
		if (canBeHit) {
		
			EntityFactory.spawnHammerHit(engine, surface, entity.getComponent(CreepComponent.class).getPosition());	
			
			int str = engine.getSystem(ScoreSystem.class).getStength();
			
			boolean isKilled = entity.getComponent(CreepComponent.class).decreasHealth(str);
			
			if (isKilled) {
				
				engine.getSystem(AISystem.class).generateItem(entity);
				
				EntityFactory.createBangFromCreep(engine, surface, entity);
				
				entity.getComponent(CreepComponent.class).setState(CreepComponent.FORCE_REMOVE);
				engine.getSystem(ScoreSystem.class).increaseScore(entity.getComponent(CreepComponent.class).getScore());
			}
			
		} else {
			
			EntityFactory.spawnHammerMiss(engine, surface, entity.getComponent(CreepComponent.class).getPosition());
			
		}
		
		if (lifeLose > 0) {
			engine.getSystem(ScoreSystem.class).increaseLife(-lifeLose);
		}
	}

	public void creepMissed(Entity entity) {
		if (entity != null && entity.getComponent(CreepComponent.class).getMinHit() <= engine.getSystem(ScoreSystem.class).getStength()) {
			engine.getSystem(ScoreSystem.class).increaseLife(-1);
		}
	}

}