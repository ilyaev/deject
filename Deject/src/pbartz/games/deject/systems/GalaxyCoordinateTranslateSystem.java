package pbartz.games.deject.systems;

import android.util.Log;
import pbartz.games.deject.components.GalaxyEmitterComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.RadialPositionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.utils.ObjectMap;

public class GalaxyCoordinateTranslateSystem extends IteratingSystem {

	RadialPositionComponent radial = null;
	PositionComponent position = null;
	GalaxyEmitterComponent emitter = null;
	
//	ObjectMap<Integer, Double> sinuses = new ObjectMap<Integer, Double>();
//	ObjectMap<Integer, Double> cosinuses = new ObjectMap<Integer, Double>();
	
	@SuppressWarnings("unchecked")
	public GalaxyCoordinateTranslateSystem() {
		super(Family.getFamilyFor(RadialPositionComponent.class, PositionComponent.class, GalaxyEmitterComponent.class));
//		
//		for(int i = 0 ; i <= 360 ; i++) {
//			
//			sinuses.put(i, Math.sin(i));
//			cosinuses.put(i, Math.cos(i));
//			
//		}
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
		
//		int newAngle = (int) angle;
//		
//		if (newAngle > 360) {
//			double tmp = Math.floor(newAngle / 360);
//			newAngle = (int) (newAngle - 360 * tmp);
//			angle = newAngle;
//		}
//		
//		if (angle < 0) {
//			double tmp = Math.floor(newAngle / 360);
//			newAngle = (int) (newAngle - 360 * tmp);
//			angle = 360 + newAngle;
//		}
//		
//		Log.v("ANGLE", Integer.toString((int)angle));
//		
//		float px = (float) (cosinuses.get((int) angle) * distance);
//		float py = (float) (sinuses.get((int)angle) * distance);
		
		
		position.setPosition(px * emitter.getWidth() + emitter.getCenterX(), py * emitter.getHeight() + emitter.getCenterY());
		
		
	}

}
