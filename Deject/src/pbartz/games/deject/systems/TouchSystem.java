package pbartz.games.deject.systems;

import android.view.MotionEvent;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.AIComponent;
import pbartz.games.deject.components.CreepComponent;
import pbartz.games.deject.components.CreepSwapComponent;
import pbartz.games.deject.components.MultiplierComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.PositionInterpolationComponent;
import pbartz.games.deject.components.TagComponent;
import pbartz.games.deject.components.TouchComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.signals.Signal;

public class TouchSystem extends IteratingSystem {
	
	DejectSurface surface;
	
	public MotionEvent event = null;
	public int eventType = -1;
	
	RectDimensionComponent dimension;
	PositionComponent position;
	TagComponent tag;
	
	public Signal<Entity> entityTouchedDown = new Signal<Entity>();
	public Signal<Entity> entityTouchedUp = new Signal<Entity>();

	@SuppressWarnings("unchecked")
	public TouchSystem(DejectSurface surface) {
		super(Family.getFamilyFor(TouchComponent.class, RectDimensionComponent.class, PositionComponent.class));
		this.surface = surface;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		if (surface.touchEventType == -1) return;
		
		dimension = entity.getComponent(RectDimensionComponent.class);	
		position = entity.getComponent(PositionComponent.class);
		
		
		if (surface.touchEventType == MotionEvent.ACTION_DOWN) {
			
			tag = entity.getComponent(TagComponent.class);
			
			if (tag == null || !isIntercect()) {
				return;
			}
			
			entityTouchedDown.dispatch(entity);
			
			if (!entity.hasComponent(PositionInterpolationComponent.class) && !tag.getTag().equalsIgnoreCase("levelInfo")) {
				
				EntityFactory.spawnGenericTouchReaction(engine, surface, entity);
				
			}
			
			int position = 0;

			try {
				position = Integer.valueOf(tag.getTag());
			} catch (java.lang.NumberFormatException e) {
				position = 0;
			}	
			
			
			if (position > 0 && EntityFactory.timeScale.getComponent(MultiplierComponent.class).getMultiplier() != 0) {
				
				if (engine.getSystem(AISystem.class).getAi().getState() != AIComponent.STATE_WORKING && engine.getSystem(AISystem.class).getAi().getState() != AIComponent.STATE_SHOP) {
					return;
				}
				
				
				Entity swapCreep = engine.getSystem(AISystem.class).
						getSwapCreep(position);
				
				Entity creep = engine.getSystem(AISystem.class).
						getCreeps().get(position);
				
				Entity item = engine.getSystem(AISystem.class).
						getItems().get(position);
				
				if (swapCreep != null) {
					
					creep = swapCreep;
					position = creep.getComponent(CreepComponent.class).getPosition();
					
				} else if (creep != null) {
					
					CreepSwapComponent swap = creep.getComponent(CreepSwapComponent.class);
					
					if (swap != null && position != swap.getTouchKey()) {
						creep = null;
					}
					
				}
				
				
				
				if (creep != null) {
					
					engine.getSystem(CreepSystem.class).creepTouched(creep, surface);					
					
				} else if (item != null) {
					
					if (engine.getSystem(ItemSystem.class).itemTouched(item)) {
						
						EntityFactory.spawnHammerHit(engine, surface, position);
						
					} else {
						
						EntityFactory.spawnHammerMiss(engine, surface, position);
					}
					
				} else {					
					
					engine.getSystem(ScoreSystem.class).increaseLife(-1);
					EntityFactory.spawnHammerMiss(engine, surface, position);
					
				}			
				
			}	
			
			surface.touchEventType = -1;
			
			
		} else if (surface.touchEventType == MotionEvent.ACTION_UP) {

			if (!isIntercect()) {
				return;
			}
			
			entityTouchedUp.dispatch(entity);
			surface.touchEventType = -1;
			
		} else if (surface.touchEventType == MotionEvent.ACTION_MOVE) {
			
			surface.touchEventType = -1;
			
		}

	}
	
	public Boolean isIntercect() {
		return dimension.isIntercect((int)surface.touchEvent.getX(), (int)surface.touchEvent.getY(), position);
	}

}