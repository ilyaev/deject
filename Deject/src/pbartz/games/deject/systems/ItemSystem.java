package pbartz.games.deject.systems;

import android.graphics.Paint;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.ColorInterpolationComponent;
import pbartz.games.deject.components.ItemComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.PositionInterpolationComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.core.Interpolation;

public class ItemSystem extends IteratingSystem {
	
	ColorComponent color;
	PositionComponent position;
	RectDimensionComponent dimension;
	ItemComponent item;
	
	DejectSurface surface;

	@SuppressWarnings("unchecked")
	public ItemSystem(DejectSurface surface) {
		super(Family.getFamilyFor(ItemComponent.class, PositionComponent.class, ColorComponent.class, RectDimensionComponent.class));
		this.surface = surface;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		item = entity.getComponent(ItemComponent.class);
		
		if (item.isNextEvent(deltaTime)) {
			
			color = entity.getComponent(ColorComponent.class);
			position = entity.getComponent(PositionComponent.class);
			dimension = entity.getComponent(RectDimensionComponent.class);
			
			if (item.getState() == ItemComponent.NOT_INITED) {
				
				entity.add(new PositionInterpolationComponent(
					entity.getComponent(PositionComponent.class), 
					position.x,
					position.y - (dimension.getHeight()), 
					item.getSpeedUp(), 
					Interpolation.EASE_OUT	
				));
				
				color.setAlpha(255);
				
				item.setState(ItemComponent.TOSS_UP);
				item.setTimeToNextState(item.getSpeedUp());
				
			} else if (item.getState() == ItemComponent.TOSS_UP) {
				
				entity.add(new PositionInterpolationComponent(
					entity.getComponent(PositionComponent.class), 
					position.x,
					position.y + (dimension.getHeight()), 
					item.getSpeedDown(), 
					Interpolation.EASE_IN	
				));
					
				item.setState(ItemComponent.TOSS_DOWN);
				item.setTimeToNextState(item.getSpeedUp());
				
			} else if (item.getState() == ItemComponent.TOSS_DOWN) {
				
				item.setState(ItemComponent.WAITING);
				item.setTimeToNextState(item.getWaitingTime());
				
			} else if (item.getState() == ItemComponent.WAITING) {
				
				entity.add(new PositionInterpolationComponent(
					entity.getComponent(PositionComponent.class), 
					position.x,
					position.y + (dimension.getHeight()), 
					item.getSpeedDown(), 
					Interpolation.EASE_OUT	
				));
				
				Paint tmpPaint = new Paint();
				
				color.setAlpha(255);
				
				tmpPaint.set(color.getPaint());
				tmpPaint.setAlpha(0);
				
				entity.add(new ColorInterpolationComponent(
					color.getPaint(), 
					tmpPaint, 
					item.getSpeedGoingDown(), 
					Interpolation.EASE_OUT
				));
				
				item.setState(ItemComponent.FORCE_REMOVE);
				item.setTimeToNextState(item.getSpeedGoingDown());
				
			} else if (item.getState() == ItemComponent.GOING_DOWN) {			
				
				item.setState(ItemComponent.FORCE_REMOVE);
				
			}  else if (item.getState() == ItemComponent.FORCE_REMOVE) {
				
				wipeItem(entity);
				
			} else if (item.getState() == ItemComponent.BLOW_UP) {
				
				entity.add(new PositionInterpolationComponent(
					entity.getComponent(PositionComponent.class), 
					position.x,
					position.y - (dimension.getHeight()), 
					0.15f, 
					Interpolation.EASE_OUT	
				));
				
				item.setState(ItemComponent.BLOW);
				item.setTimeToNextState(0.15f);
				
			} else if (item.getState() == ItemComponent.BLOW) {
				
				EntityFactory.createBangFromItem(engine, surface, entity);
				
				item.setState(ItemComponent.FORCE_REMOVE);
			}
			
			
		}
		
	}

	private void wipeItem(Entity entity) {
		engine.getSystem(AISystem.class).getItems().put(entity.getComponent(ItemComponent.class).getPosition(), null);
		engine.removeEntity(entity);		
	}

	public void itemTouched(Entity entity) {		
		entity.getComponent(ItemComponent.class).setState(ItemComponent.BLOW_UP);

		engine.getSystem(ScoreSystem.class).
			increaseGold(entity.getComponent(ItemComponent.class).getGold());
		
		engine.getSystem(ScoreSystem.class).
			increaseLife(entity.getComponent(ItemComponent.class).getLife());
	}

}