package pbartz.games.deject.systems;

import pbartz.games.deject.components.GalaxyEmitterComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.RadialPositionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class GalaxyCoordinateTranslateSystem extends IteratingSystem {

	RadialPositionComponent radial = null;
	PositionComponent position = null;
	GalaxyEmitterComponent emitter = null;
	
	@SuppressWarnings("unchecked")
	public GalaxyCoordinateTranslateSystem() {
		super(Family.getFamilyFor(RadialPositionComponent.class, PositionComponent.class, GalaxyEmitterComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		radial = entity.getComponent(RadialPositionComponent.class);
		position = entity.getComponent(PositionComponent.class);
		emitter = entity.getComponent(GalaxyEmitterComponent.class);
		
		int numArms = emitter.getArms();
		
		float distance = radial.getDistance();
		
		float angle = radial.getAngle();
		
		if (numArms > 0) {
		
			float armSeparationDistance = (float) (2* Math.PI / numArms);
	
			float rotationFactor = 4;
			
			float armOffset = radial.getArmOffset();
			
			
			// dynamic
			
			distance = (float) Math.pow(distance, 2);			
			
			armOffset = armOffset * (1 / distance);
			
			float squaredArmOffset = (float) Math.pow(armOffset, 2);
			
	        if(armOffset < 0) {
	            squaredArmOffset = squaredArmOffset * -1;
	        }
	        
	        armOffset = squaredArmOffset;
	        
	        float rotation = distance * rotationFactor;
			
		    angle = (int)(angle / armSeparationDistance) * armSeparationDistance + armOffset + rotation;
		    
		}
		
		
		float px = (float) (Math.cos(angle) * distance);
		float py = (float) (Math.sin(angle) * distance);
		
		position.setPosition(px * emitter.getWidth() + emitter.getCenterX(), py * emitter.getHeight() + emitter.getCenterY());
		
		
	}

}
