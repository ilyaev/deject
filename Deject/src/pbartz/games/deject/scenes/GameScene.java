package pbartz.games.deject.scenes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import pbartz.games.deject.BitmapLibrary;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.AIComponent;
import pbartz.games.deject.config.GameConfig;
import pbartz.games.deject.config.LevelConfig;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.signals.Listener;
import pbartz.games.deject.signals.Signal;
import pbartz.games.deject.systems.AISystem;
import pbartz.games.deject.systems.ColorInterpolationSystem;
import pbartz.games.deject.systems.CreepShieldSystem;
import pbartz.games.deject.systems.CreepSystem;
import pbartz.games.deject.systems.ExpireSystem;
import pbartz.games.deject.systems.InterpolationSystem;
import pbartz.games.deject.systems.ItemSystem;
import pbartz.games.deject.systems.RectInterpolationSystem;
import pbartz.games.deject.systems.RotateInterpolationSystem;
import pbartz.games.deject.systems.ScoreSystem;
import pbartz.games.deject.systems.TouchSystem;
import pbartz.games.deject.systems.ZoomInterpolationSystem;
import pbartz.games.deject.systems.renderer.DimensionRenderingSystem;

public class GameScene extends BasicScene {
	
	public MotionEvent touchEvent = null;
	public int touchEventType = -1;
	private Paint whitePaint;
	
	public GameScene(DejectSurface surface) {
		super(surface);
		
		whitePaint = new Paint();
		whitePaint.setARGB(255, 255, 255, 255);
		whitePaint.setTextSize(40);
	}
	
	public void initScene() {
		
		if (isInited()) {
			return;
		}
		
		// score
		engine.addSystem(new ScoreSystem());
		
		// control
		engine.addSystem(new TouchSystem(this.surface));
		
		// AI
		engine.addSystem(new AISystem(this.surface));
		engine.addSystem(new CreepSystem());
		engine.addSystem(new CreepShieldSystem());
		engine.addSystem(new ItemSystem(this.surface));
		
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

		// Configuration file parse
		GameConfig.loadConfig(surface);		
		
		// pre load asset images
		BitmapLibrary.loadAssets(surface);
		
		EntityFactory.caculateMetrics(surface);		
		
		EntityFactory.createControlPanel(engine, surface);
		
		EntityFactory.createScorePanel(engine, surface);
		
		EntityFactory.createAIEntity(engine);		
		
		
		initListeners();
		
		setInited(true);

	}
	
	public void completeScene() {
		
	}

	private void initListeners() {
		
		engine.getSystem(TouchSystem.class).entityTouchedDown.add( new Listener<Entity>() {

			@Override
			public void receive(Signal<Entity> signal, Entity object) {
				entityTouchedDown(object);
			}			
			
		});
		
		engine.getSystem(ExpireSystem.class).entityExpired.add(new Listener<Entity>() {
			
			public void receive(Signal<Entity> signal, Entity entity) {
				entityExpired(entity);
			}

		});
		
		engine.getSystem(ScoreSystem.class).gameOverSignal.add(new Listener<Entity>() {
			
			public void receive(Signal<Entity> signal, Entity entity) {
				gameOverSignal(entity);
			}			
			
		});
		
		engine.getSystem(AISystem.class).levelCompletedSignal.add(new Listener<LevelConfig>() {
			
			public void receive(Signal<LevelConfig> signal, LevelConfig level) {
				levelCompletedSignal(level);
			}
			
		});
		
	}
	
	private void levelCompletedSignal(LevelConfig level) {
		// ToDo: Level completed
		//engine.getSystem(AISystem.class).getAi().setState(AIComponent.STATE_LEVEL_COMPLETED);
		
	}

	private void gameOverSignal(Entity score) {
		// ToDo: Game Over
		engine.getSystem(AISystem.class).getAi().setState(AIComponent.STATE_GAMEOVER);
		
	}
	
	private void entityExpired(Entity entity) {
			
	}	

	protected void entityTouchedDown(Entity entity) {		
		
	}
	
	
	public void update(Canvas canvas, float timeDiff) {
		//canvas.drawARGB(255, 110, 74, 112);
		canvas.drawARGB(255, 212, 212, 212);
		engine.update(timeDiff);
		drawDebug(canvas);
	}

	private void drawDebug(Canvas canvas) {
		
		//canvas.drawText("FPS: " + Integer.toString(surface.fps), 0, 80, whitePaint);
		//canvas.drawText("Entities: " + Integer.toString(engine.getEntitiesCount()), 0, 80, whitePaint);
		//canvas.drawText("Removed: " + Integer.toString(engine.getRemovedEntitiesCount()), 0, 120, whitePaint);
		
//		canvas.drawText("DIP_W: " + Float.toString(surface.widthDp), 0, 80, whitePaint);
//		canvas.drawText("DIP_H: " + Float.toString(surface.heightDp), 0, 120, whitePaint);
	}
	
}