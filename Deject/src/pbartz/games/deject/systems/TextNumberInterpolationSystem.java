package pbartz.games.deject.systems;

import pbartz.games.deject.components.FontSizeInterpolationComponent;
import pbartz.games.deject.components.TextComponent;
import pbartz.games.deject.components.TextNumberInterpolationComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.core.Interpolation;

public class TextNumberInterpolationSystem extends IteratingSystem {
	
	TextComponent text = null;
	TextNumberInterpolationComponent interpolation = null;

	@SuppressWarnings("unchecked")
	public TextNumberInterpolationSystem() {
		super(Family.getFamilyFor(TextComponent.class, TextNumberInterpolationComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		text = entity.getComponent(TextComponent.class);
		interpolation = entity.getComponent(TextNumberInterpolationComponent.class);

		interpolation.increaseTime(deltaTime);
		
		float t = interpolation.getTime();
		float b = interpolation.getStartNumber();
		float c = (interpolation.getEndNumber() - interpolation.getStartNumber());
		float d = interpolation.getSpeed();
		
		float newNum = Interpolation.calculateCurrentValue(interpolation.getType(), t, b, c, d);
		
		text.setText(Integer.toString(Math.round(newNum)));
		
		if (interpolation.isCompleted()) {
			
			text.setText(Integer.toString(interpolation.getEndNumber()));
			
			entity.remove(TextNumberInterpolationComponent.class);
			
		}
		
		
	}

}
