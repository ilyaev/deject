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
import pbartz.games.deject.components.ZoomInterpolationComponent;
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
		
		if (r.nextInt(100) < emitter.getBurstChance()) {
			stars = emitter.getBurstStars();
		}
		
		if (r.nextInt(100) < emitter.getNewStarChance()) {
			
			for (int i = 0 ; i < stars ; i++ ) { 
			
				float distance = r.nextFloat() / 10;
				
				float armOffsetMax = 0.5f;
				
				float angle = (float) (r.nextFloat() * 2 * Math.PI);
				
				float armOffset = r.nextFloat() * armOffsetMax;
				armOffset = armOffset - armOffsetMax / 2;
				
				Entity star = new Entity();
				
				int sSize = (int) (EntityFactory.starBaseWidth + r.nextFloat() * EntityFactory.starBaseWidth - EntityFactory.starBaseWidth / 2);
				
				star.add(new PositionComponent(-100, -100));
				
				
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
				
				if (stars == 1 && r.nextInt(100) < emitter.getColorStarChance()) {
					
					sSize = (int) (EntityFactory.starBaseWidth / 2);
					star.add(new RectDimensionComponent(sSize, sSize));
					
					float startT = emitter.getBaseSpeed() / 1.2f - r.nextFloat() * emitter.getBaseSpeed() / 1.5f;
					
					star.add(new ZoomInterpolationComponent(
						sSize,
						sSize,
						(int)(EntityFactory.starBaseWidth * 2.5f),						
						(int)(EntityFactory.starBaseWidth * 2.5f),
						1f,
						Interpolation.EASE_IN						
					), startT);
					
					star.add(new ZoomInterpolationComponent(
						(int)(EntityFactory.starBaseWidth * 2.5f),						
						(int)(EntityFactory.starBaseWidth * 2.5f),
						sSize,
						sSize,						
						1f,
						Interpolation.EASE_IN						
					), startT + 1f);
					
					String particle = "particle_star";
					if (r.nextInt(100) < 30) {
						particle += "_red";
					} else if (r.nextInt(100) < 30) {
						particle += "_blue";
					} else {
						particle += "_green";
					}
					
					
					star.add(new ColorComponent(r.nextInt(255), 255, 0, 0));
					star.add(new BitmapComponent(BitmapLibrary.getBitmap(particle)));
					
				} else {
					star.add(new RectDimensionComponent(sSize, sSize));
				}
				
				
				engine.addEntity(star);
			}
			
		}
		
	}

}
