package pbartz.games.deject.systems;

import android.graphics.Bitmap;
import pbartz.games.deject.BitmapLibrary;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.BitmapComponent;
import pbartz.games.deject.components.CreepComponent;
import pbartz.games.deject.components.CreepShieldComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class CreepShieldSystem extends IteratingSystem {
	
	public static final String IMAGE_PREFIX = "_shield";
	
	CreepComponent creep = null;
	CreepShieldComponent shield = null;

	@SuppressWarnings("unchecked")
	public CreepShieldSystem() {
		super(Family.getFamilyFor(CreepComponent.class, CreepShieldComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		creep = entity.getComponent(CreepComponent.class);
		shield = entity.getComponent(CreepShieldComponent.class);
		
		if (shield.isNextEvent(deltaTime)) {
			
			if (shield.getState() == CreepShieldComponent.STATE_OPEN) {
				
				Bitmap shieldBitmap = BitmapLibrary.getBitmap(creep.getConfig().getImage() + IMAGE_PREFIX); 
				
				if (shieldBitmap != null) {
					entity.add(EntityFactory.getReusableBitmapComponent(engine, creep.getConfig().getImage() + IMAGE_PREFIX));
				}
				
				shield.setState(CreepShieldComponent.STATE_CLOSED);
				
			} else if (shield.getState() == CreepShieldComponent.STATE_CLOSED) {
				
				entity.add(EntityFactory.getReusableBitmapComponent(engine, creep.getConfig().getImage()));
				shield.setState(CreepShieldComponent.STATE_OPEN);
				
			}
			
		}
	}

}