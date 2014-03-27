package pbartz.games.deject.systems.renderer;

import android.graphics.Canvas;
import android.graphics.Rect;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.components.BitmapComponent;
import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.RotateComponent;
import pbartz.games.deject.components.TextComponent;
import pbartz.games.deject.components.ZoomComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.systems.IteratingSystem;
import pbartz.games.deject.systems.OrderedIteratingSystem;

public class DimensionRenderingSystem extends OrderedIteratingSystem {

	public Canvas canvas = null;
	
	DejectSurface surface;
	
	RectDimensionComponent dimension;
	PositionComponent position;
	ColorComponent color;
	TextComponent text;
	BitmapComponent bitmap;
    RotateComponent rotate = null;
    ZoomComponent zoom = null;
	
	@SuppressWarnings("unchecked")
	public DimensionRenderingSystem(DejectSurface surface) {
		super(Family.getFamilyFor(PositionComponent.class, RectDimensionComponent.class, ColorComponent.class));
		
		this.surface = surface;		
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		canvas = surface.getCanvas();
		
		position = entity.getComponent(PositionComponent.class);
		dimension = entity.getComponent(RectDimensionComponent.class);
		color = entity.getComponent(ColorComponent.class);
		
		bitmap = entity.getComponent(BitmapComponent.class);
		
		if (entity.hasComponent(RotateComponent.class)) {		
			rotate = entity.getComponent(RotateComponent.class);
		} else {
			rotate = null;
		}
		
//		if (entity.hasComponent(ZoomComponent.class)) {
//			zoom = entity.getComponent(ZoomComponent.class);
//		} else {
//			zoom = null;
//		}
		
		if (bitmap != null) {

			renderBitmap();
//			
//			if (hasText) {
//				
//				text = entity.getComponent(TextComponent.class);
//				renderText();
//			
//			}
			
		} else if (entity.hasComponent(TextComponent.class)) {
			
			text = entity.getComponent(TextComponent.class);
			renderText();
		
		} else {			
			
			renderRect();
		}
		
		
		
	}

	private void renderBitmap() {
		
		if (bitmap.getBitmap() == null) return;
		
 		Rect rect = dimension.getZeroRect();
		canvas.save();
		canvas.translate(position.x, position.y);
		
		if (rotate != null) {
			canvas.rotate(rotate.getAngle());
		}
		
		canvas.scale((float)dimension.getWidth() / (float)bitmap.getBitmap().getWidth(), (float)dimension.getHeight() / (float)bitmap.getBitmap().getHeight(), rect.left, rect.top);
		
		
//		if (zoom != null) {
//			canvas.scale(zoom.getZoomX(), zoom.getZoomY(), rect.left, rect.top);
//		}
//		
		canvas.drawBitmap(bitmap.getBitmap(), rect.left, rect.top, color.getPaint());
		canvas.restore();
	}

	private void renderText() {
		
		color.getPaint().setTextSize(text.getHeight() > 0 ? text.getHeight() : dimension.getHeight());
		canvas.drawText(text.getText(), position.x - (dimension.getWidth() / 2), position.y + (dimension.getHeight() / 2), color.getPaint());		
	
	}

	private void renderRect() {
		
		Rect tmpRect = dimension.getZeroRect();
		
		canvas.save();
		canvas.translate(position.x, position.y);
		if (rotate != null) {			
			canvas.rotate(rotate.getAngle());
		}
		
		if (color.getBorderPaint() == null) {
			
			if (zoom != null) {
				canvas.scale(zoom.getZoomX(), zoom.getZoomY(), tmpRect.left, tmpRect.top);
			}
			
			canvas.drawRect(tmpRect, color.getPaint());
			
		} else {
			
			int shift = surface.dp2px(1f);
			
			tmpRect.set(tmpRect.left + shift, tmpRect.top + shift, tmpRect.right - shift, tmpRect.bottom - shift);
			
			if (zoom != null) {
				canvas.scale(zoom.getZoomX(), zoom.getZoomY(), tmpRect.left, tmpRect.top);
			}
			
			canvas.drawRect(((RectDimensionComponent) dimension).getZeroRect(), color.getBorderPaint());
			canvas.drawRect(tmpRect, color.getPaint());
		}
		
		canvas.restore();
	}


}