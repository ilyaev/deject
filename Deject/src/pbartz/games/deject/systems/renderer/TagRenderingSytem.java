package pbartz.games.deject.systems.renderer;

import android.graphics.Paint;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.TagComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.systems.IteratingSystem;

public class TagRenderingSytem extends IteratingSystem {

	private DejectSurface surface;
	PositionComponent position;
	TagComponent tag;
	private Paint paint;

	@SuppressWarnings("unchecked")
	public TagRenderingSytem(DejectSurface surface) {
		super(Family.getFamilyFor(PositionComponent.class, TagComponent.class));
		
		this.surface = surface;
		
		paint = new Paint();
		paint.setARGB(255, 255, 255, 255);
		paint.setTextSize(20);
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		tag = entity.getComponent(TagComponent.class);
		position = entity.getComponent(PositionComponent.class);
		
		this.surface.getCanvas().drawText(tag.getTag(), position.x, position.y, paint);

	}

}
