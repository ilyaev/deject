package pbartz.games.deject.systems;

import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.BitmapAnimationComponent;
import pbartz.games.deject.components.BitmapComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class BitmapAnimationSystem extends IteratingSystem {
	
	BitmapAnimationComponent animation = null;

	@SuppressWarnings("unchecked")
	public BitmapAnimationSystem() {
		super(Family.getFamilyFor(BitmapAnimationComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		animation = entity.getComponent(BitmapAnimationComponent.class);
		
		animation.addTime((int)deltaTime); 
		
		if (animation.getTime() > animation.getDelay()) {
			
			int key = animation.getKey() + 1;
			
			if (key > animation.getMaxKeys()) {
				
				if (animation.isLoop()) {
					
					key = 1;
					
				} else {
					
					key = animation.getMaxKeys();
					
				}
				
			}
			
			animation.setKey(key);
			animation.clearTime();
			entity.add(EntityFactory.getReusableBitmapComponent(engine, animation.getNameBase() + Integer.toString(key)));
			
			
		}
		
		
	}

}
