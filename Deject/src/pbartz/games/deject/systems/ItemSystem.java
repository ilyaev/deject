package pbartz.games.deject.systems;

import java.util.Random;

import android.graphics.Paint;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.ColorInterpolationComponent;
import pbartz.games.deject.components.ExpireComponent;
import pbartz.games.deject.components.ItemComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.PositionInterpolationComponent;
import pbartz.games.deject.components.ScoreComponent;
import pbartz.games.deject.components.TagComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.core.Interpolation;

public class ItemSystem extends IteratingSystem {
	
	ColorComponent color;
	PositionComponent position;
	RectDimensionComponent dimension;
	ItemComponent item;
	
	Random r = new Random();
	
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
				
				entity.add(EntityFactory.getPositionInterpolationComponent(engine, 
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
				
				entity.add(EntityFactory.getPositionInterpolationComponent(engine, 
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
				
				entity.add(EntityFactory.getPositionInterpolationComponent(engine, 
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
				
				entity.add(EntityFactory.getColorInterpolationComponent(engine, 
					color.getPaint(), 
					tmpPaint, 
					item.getSpeedGoingDown(), 
					Interpolation.EASE_OUT
				));
				
				item.setState(ItemComponent.GOING_DOWN);
				item.setTimeToNextState(item.getSpeedGoingDown());
				
			} else if (item.getState() == ItemComponent.GOING_DOWN) {			
				
				item.setState(ItemComponent.FORCE_REMOVE);
				
				
				if (entity.getComponent(ItemComponent.class).getTypeName().equalsIgnoreCase("trunk")) {
					engine.getSystem(AISystem.class).getItems().put(entity.getComponent(ItemComponent.class).getPosition(), null);
				}
				
			}  else if (item.getState() == ItemComponent.FORCE_REMOVE) {
				
				wipeItem(entity);
				
			} else if (item.getState() == ItemComponent.BLOW_UP) {
				
				entity.add(EntityFactory.getPositionInterpolationComponent(engine, 
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
		if (!entity.getComponent(ItemComponent.class).getTypeName().equalsIgnoreCase("trunk")) {
			engine.getSystem(AISystem.class).getItems().put(entity.getComponent(ItemComponent.class).getPosition(), null);
		}
		engine.removeEntity(entity);		
	}

	public boolean itemTouched(Entity entity) {
		
		int goldChange = entity.getComponent(ItemComponent.class).getGold();
		
		if (engine.getSystem(ScoreSystem.class).score.getComponent(ScoreComponent.class).getGold() < entity.getComponent(ItemComponent.class).config.getCost()) {

			return false;
			
		}
		
		goldChange -= entity.getComponent(ItemComponent.class).config.getCost();
		
		
		entity.getComponent(ItemComponent.class).setState(ItemComponent.BLOW_UP);

		engine.getSystem(ScoreSystem.class).
			increaseGold(goldChange);
		
		engine.getSystem(ScoreSystem.class).
			increaseLife(entity.getComponent(ItemComponent.class).getLife());
		
		String itemType = entity.getComponent(ItemComponent.class).getTypeName();
		
		if (itemType.equalsIgnoreCase("trunk")) {
		
			String itemName = "all_to_default";
			
			if (r.nextInt() < 30) {
				itemName = "timefreeze";
			} else if (r.nextInt() < 30) {
				itemName = "all_to_coins";
			} else if (r.nextInt() < 30) {
				itemName = "all_to_health";
			}
			
			
			
			Entity special = EntityFactory.spawnCellItem(engine, surface, entity.getComponent(ItemComponent.class).getPosition(), itemName, 0.2f);
			engine.getSystem(AISystem.class).getItems().put(entity.getComponent(ItemComponent.class).getPosition(), special);
		
		} else if (itemType.equalsIgnoreCase("all_to_coins")) {
			
			engine.getSystem(AISystem.class).turnAllToGold();
			
		} else if (itemType.equalsIgnoreCase("all_to_health")) {
			
			engine.getSystem(AISystem.class).turnAllToHealth();
			
		} else if (itemType.equalsIgnoreCase("all_to_default")) {
			
			engine.getSystem(AISystem.class).eliminateAll();
			
		} else if (itemType.equalsIgnoreCase("shop")) {
			
			engine.getSystem(AISystem.class).switchToShop();
			
		} else if (itemType.equalsIgnoreCase("shop9")) {
			
			engine.getSystem(AISystem.class).shopOut();			
			
		} else if (itemType.equalsIgnoreCase("shop1")) {
			
			engine.getSystem(ScoreSystem.class).setHammer(2);
			
		} else if (itemType.equalsIgnoreCase("shop2")) {
			
			engine.getSystem(ScoreSystem.class).setHammer(3);
			
		} else if (itemType.equalsIgnoreCase("shop3")) {
			
			engine.getSystem(ScoreSystem.class).setHammer(4);
			
		} else if (itemType.equalsIgnoreCase("shop7")) {
			
			engine.getSystem(AISystem.class).score.setOldStrength(engine.getSystem(AISystem.class).score.getStrength());
			engine.getSystem(AISystem.class).score.setStrength(10);
			engine.getSystem(AISystem.class).currentItemType = "coin_big";
			engine.getSystem(AISystem.class).rollbackHammerAfterSeconds(10f);
			
		} else if (itemType.equalsIgnoreCase("shop8")) {
			
			engine.getSystem(AISystem.class).score.setOldStrength(engine.getSystem(AISystem.class).score.getStrength());
			engine.getSystem(AISystem.class).score.setStrength(10);
			engine.getSystem(AISystem.class).currentItemType = "heart_small";
			engine.getSystem(AISystem.class).rollbackHammerAfterSeconds(10f);
			
		}
		
		return true;
		
		
	}

}