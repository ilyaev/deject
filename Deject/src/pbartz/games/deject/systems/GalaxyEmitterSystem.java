package pbartz.games.deject.systems;

import java.util.Random;

import pbartz.games.deject.BitmapLibrary;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.BitmapComponent;
import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.GalaxyComponent;
import pbartz.games.deject.components.GalaxyEmitterComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.RadialPositionComponent;
import pbartz.games.deject.components.RadialPositionInterpolationComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.core.Interpolation;

public class GalaxyEmitterSystem extends IteratingSystem {

	GalaxyEmitterComponent emitter = null;
	
	@SuppressWarnings("unchecked")
	public GalaxyEmitterSystem() {
		super(Family.getFamilyFor(GalaxyEmitterComponent.class, GalaxyComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		emitter = entity.getComponent(GalaxyEmitterComponent.class);
		
		Random r = new Random();
		
		int stars = 1;
		if (r.nextInt(100) < 2) {
			stars = 10;
		}
		
		if (r.nextInt(100) < 30) {
			
			for (int i = 0 ; i < stars ; i++ ) { 
			
				float distance = r.nextFloat() / 10;
				
				float armOffsetMax = 0.5f;
				
				float angle = (float) (r.nextFloat() * 2 * Math.PI);
				
				float armOffset = r.nextFloat() * armOffsetMax;
				armOffset = armOffset - armOffsetMax / 2;
				
				Entity star = new Entity();
				
				int sSize = (int) (EntityFactory.starBaseWidth + r.nextFloat() * EntityFactory.starBaseWidth - EntityFactory.starBaseWidth / 2);
				
				star.add(new PositionComponent(-100, -100));
				star.add(new RectDimensionComponent(sSize, sSize));
				
				star.add(new RadialPositionComponent(angle, distance, armOffset));
				star.add(new RadialPositionInterpolationComponent(
					distance,
					1f,
					angle,
					angle,
					emitter.getBaseSpeed() + r.nextFloat() * 3 - 1.5f,
					Interpolation.EASE_IN
				));
				
				star.add(new ColorComponent(r.nextInt(255), 125, 125, 125));
				star.add(new BitmapComponent(BitmapLibrary.getBitmap("particle_star")));
				star.setOrder(-1);
				
				star.add(emitter);
				
				engine.addEntity(star);
			}
			
		}
		
	}

}
