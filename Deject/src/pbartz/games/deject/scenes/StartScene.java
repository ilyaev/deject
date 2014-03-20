package pbartz.games.deject.scenes;

import pbartz.games.deject.BitmapLibrary;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.TagComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.signals.Listener;
import pbartz.games.deject.signals.Signal;
import pbartz.games.deject.systems.ColorInterpolationSystem;
import pbartz.games.deject.systems.ExpireSystem;
import pbartz.games.deject.systems.InterpolationSystem;
import pbartz.games.deject.systems.RectInterpolationSystem;
import pbartz.games.deject.systems.RotateInterpolationSystem;
import pbartz.games.deject.systems.ScreenOverlaySystem;
import pbartz.games.deject.systems.TouchSystem;
import pbartz.games.deject.systems.ZoomInterpolationSystem;
import pbartz.games.deject.systems.renderer.DimensionRenderingSystem;
import android.graphics.Canvas;

public class StartScene extends BasicScene {

	public StartScene(DejectSurface surface) {
		super(surface);
		
	}

	@Override
	public void initScene() {
		
		if (isInited()) {
			return;
		}
		
		// control
		engine.addSystem(new TouchSystem(this.surface));
		
		
		// interpolation
		engine.addSystem(new InterpolationSystem());
		engine.addSystem(new ColorInterpolationSystem());
		engine.addSystem(new RotateInterpolationSystem());
		engine.addSystem(new ZoomInterpolationSystem());
		engine.addSystem(new RectInterpolationSystem());
		
		// expiration
		engine.addSystem(new ExpireSystem());
		
		// rendering
		engine.addSystem(new DimensionRenderingSystem(this.surface));
		engine.addSystem(new ScreenOverlaySystem(this.surface));
		

		// pre load asset images
		BitmapLibrary.loadAssets(surface);
		
		EntityFactory.caculateMetrics(surface);
		EntityFactory.createStartScreen(engine, surface);
		
		initListeners();
		
		setInited(true);

	}
	
	private void initListeners() {
		
		engine.getSystem(TouchSystem.class).entityTouchedDown.add( new Listener<Entity>() {

			@Override
			public void receive(Signal<Entity> signal, Entity object) {
				entityTouchedDown(object);
			}			
			
		});
		
		engine.getSystem(TouchSystem.class).entityTouchedUp.add( new Listener<Entity>() {

			@Override
			public void receive(Signal<Entity> signal, Entity object) {
				entityTouchedUp(object);
			}			
			
		});
		
		engine.getSystem(ExpireSystem.class).entityExpired.add(new Listener<Entity>() {
			
			public void receive(Signal<Entity> signal, Entity entity) {
				entityExpired(entity);
			}

		});
	}
	
	protected void entityExpired(Entity entity) {
		if (entity.getComponent(TagComponent.class) != null) {
			
			String tag = entity.getComponent(TagComponent.class).getTag();
			
			if (tag == "fade_out") {
				
				surface.setScene(nextScene);
				
			}
			
		}
	}

	protected void entityTouchedDown(Entity entity) {		
		entity.getComponent(PositionComponent.class).y += surface.dp2px(1);
	}
	
	protected void entityTouchedUp(Entity entity) {		
		entity.getComponent(PositionComponent.class).y -= surface.dp2px(1);
		
		String tag = entity.getComponent(TagComponent.class).getTag();
		
		if (tag == "btn_play") {
			
			surface.startGame();
			
		}
	}

	@Override
	public void update(Canvas canvas, float timeDiff) {
		canvas.drawARGB(255, 212, 212, 212);
		engine.update(timeDiff);
	}
	
	@Override
	public void completeScene() {
		
	}

	@Override
	public void transitOut(BasicScene tScene) {
		nextScene = tScene;		
		EntityFactory.createFadeOut(engine, surface);		
	}

}