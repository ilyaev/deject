package pbartz.games.deject.scenes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import pbartz.games.deject.BitmapLibrary;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.AIComponent;
import pbartz.games.deject.components.LevelInfoComponent;
import pbartz.games.deject.components.MultiplierComponent;
import pbartz.games.deject.components.MultiplierInterpolationComponent;
import pbartz.games.deject.components.PositionInterpolationComponent;
import pbartz.games.deject.components.ScoreComponent;
import pbartz.games.deject.components.TagComponent;
import pbartz.games.deject.config.GameConfig;
import pbartz.games.deject.config.LevelConfig;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Interpolation;
import pbartz.games.deject.signals.Listener;
import pbartz.games.deject.signals.Signal;
import pbartz.games.deject.systems.AISystem;
import pbartz.games.deject.systems.BitmapAnimationSystem;
import pbartz.games.deject.systems.ColorInterpolationSystem;
import pbartz.games.deject.systems.CreepShieldSystem;
import pbartz.games.deject.systems.CreepSystem;
import pbartz.games.deject.systems.ExpireSystem;
import pbartz.games.deject.systems.FontSizeInterpolationSystem;
import pbartz.games.deject.systems.GalaxyEmitterSystem;
import pbartz.games.deject.systems.InterpolationSystem;
import pbartz.games.deject.systems.ItemSystem;
import pbartz.games.deject.systems.LevelInfoSystem;
import pbartz.games.deject.systems.MovementSystem;
import pbartz.games.deject.systems.GalaxyCoordinateTranslateSystem;
import pbartz.games.deject.systems.MultiplierInterpolationSystem;
import pbartz.games.deject.systems.PositionShakeSystem;
import pbartz.games.deject.systems.RadialPositionInterpolationSystem;
import pbartz.games.deject.systems.RectInterpolationSystem;
import pbartz.games.deject.systems.RotateInterpolationSystem;
import pbartz.games.deject.systems.ScoreSystem;
import pbartz.games.deject.systems.ScreenOverlaySystem;
import pbartz.games.deject.systems.TextNumberInterpolationSystem;
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
		whitePaint.setTextSize(surface.dp2px(EntityFactory.infoPanelHeight / 2));
	}
	
	public void initScene() {
		
		if (isInited()) {
			EntityFactory.createFadeIn(engine, surface);
			return;
		}
		
		// score
		engine.addSystem(new ScoreSystem(this.surface));
		
		// control
		engine.addSystem(new TouchSystem(this.surface));
		
		// AI
		engine.addSystem(new AISystem(this.surface));
		engine.addSystem(new CreepSystem(this.surface));
		engine.addSystem(new CreepShieldSystem());
		engine.addSystem(new ItemSystem(this.surface));
		
		// Miscellaneous
		engine.addSystem(new LevelInfoSystem(this.surface));
		engine.addSystem(new MovementSystem(surface.dp2px(surface.heightDp * 3.3f)));
		engine.addSystem(new GalaxyCoordinateTranslateSystem());
		engine.addSystem(new GalaxyEmitterSystem());
		engine.addSystem(new PositionShakeSystem());
		engine.addSystem(new BitmapAnimationSystem());
		
		// interpolation
		engine.addSystem(new InterpolationSystem());
		engine.addSystem(new ColorInterpolationSystem());
		engine.addSystem(new RotateInterpolationSystem());
		engine.addSystem(new ZoomInterpolationSystem());
		engine.addSystem(new RectInterpolationSystem());
		engine.addSystem(new RadialPositionInterpolationSystem());
		engine.addSystem(new MultiplierInterpolationSystem());
		engine.addSystem(new FontSizeInterpolationSystem());
		engine.addSystem(new TextNumberInterpolationSystem());
		
		// expiration
		engine.addSystem(new ExpireSystem());
		
		// rendering
		engine.addSystem(new DimensionRenderingSystem(this.surface));
		engine.addSystem(new ScreenOverlaySystem(this.surface));

		// Configuration file parse
		GameConfig.loadConfig(surface);		
		
		// pre load asset images
		BitmapLibrary.loadAssets(surface);
		
		EntityFactory.caculateMetrics(surface);		
		
		EntityFactory.createControlPanel(engine, surface);
		
		EntityFactory.createScorePanel(engine, surface);
		
		EntityFactory.createAIEntity(engine);		
		
		EntityFactory.createMultiplierEntity(engine);
		
		EntityFactory.createGalaxy(engine, surface);
		
		initListeners();
		
		setInited(true);
		
		EntityFactory.createFadeIn(engine, surface);
		
		Entity bg = engine.createEntity();
		bg.add(EntityFactory.getReusableBitmapComponent(engine, "bg_pixel_city"));
		bg.add(EntityFactory.getColorComponent(engine, 255, 255, 0, 0));
		bg.add(EntityFactory.getPositionComponent(engine, surface.dp2px(surface.widthDp / 2), surface.dp2px(surface.heightDp / 2)));
		bg.add(EntityFactory.getRectComponent(engine, surface.dp2px(surface.widthDp + 2), surface.dp2px(surface.heightDp + 2)));
		bg.setOrder(-3);
		
		engine.addEntity(bg);

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
		EntityFactory.clearSlowMo();
		engine.getSystem(AISystem.class).getAi().setState(AIComponent.STATE_LEVEL_COMPLETED);
		EntityFactory.animateButtonsDown(engine);
		
	}

	private void gameOverSignal(Entity score) {
		
		if (engine.getSystem(AISystem.class).getAi().getState() != AIComponent.STATE_GAMEOVER) {
			EntityFactory.clearSlowMo();
			engine.getSystem(AISystem.class).getAi().setState(AIComponent.STATE_GAMEOVER);
			EntityFactory.spawnGameOver(engine, surface);
			EntityFactory.animateButtonsDown(engine);
			
		}
	}
	
	private void entityExpired(Entity entity) {
		
		if (entity.getComponent(TagComponent.class) == null) return;
		
		String tag = entity.getComponent(TagComponent.class).getTag();
		
		if (tag == "exit_shop") {
			
			if (engine.getSystem(AISystem.class).getAi().getState() == AIComponent.STATE_SHOP) {
				
				engine.getSystem(AISystem.class).getAi().setState(AIComponent.STATE_WORKING);
				engine.getSystem(AISystem.class).resumeProgressBar();
			}
			
		}
	}	

	protected void entityTouchedDown(Entity entity) {		
		String tag = entity.getComponent(TagComponent.class).getTag();
		
		if (tag == "levelInfo") {
			
			if (engine.getSystem(LevelInfoSystem.class).getLevelInfo().getState() == LevelInfoComponent.STATE_WAITING) {
				engine.getSystem(AISystem.class).startLevel();
			}
			
		} else if (tag == "btn_play") {
			if (EntityFactory.btnPlay != null && EntityFactory.btnPlay.getComponent(PositionInterpolationComponent.class) == null) {
				engine.getSystem(AISystem.class).starGame();
				
				EntityFactory.hideGameOverPanel(engine, surface);
				engine.removeEntity(EntityFactory.scoreValueEntity);
				
			}
		} else if (tag == "playfield") {
			
			float timeFrom = 1f;
			float timeTo = 0;
			
			if (EntityFactory.timeScale.getComponent(MultiplierComponent.class).getMultiplier() < 1) {
				
				timeFrom = 0;
				timeTo = 1f;
				
				EntityFactory.hidePause(engine, surface);
				
			} else {
				
				EntityFactory.showPause(engine, surface);
				
			}
			
			if (timeTo == 0) {
			
				EntityFactory.timeScale.getComponent(MultiplierComponent.class).setMultiplier(0);
				
			} else {
			
				MultiplierInterpolationComponent timeScale = engine.createComponent(MultiplierInterpolationComponent.class);
				timeScale.init(timeFrom, timeTo, 1f, Interpolation.EASE_IN);
				
				EntityFactory.timeScale.add(timeScale);				
				
			}
			
		} else if (tag == "btn_snake" || tag == "btn_rate") {
			
			EntityFactory.processAdButton(engine, surface, entity);
			
		}
	}
	
	
	public void update(Canvas canvas, float timeDiff) {
	//	canvas.drawARGB(255, 0, 0, 0);
		
		float timeScale = EntityFactory.timeScale.getComponent(MultiplierComponent.class).getMultiplier();
		
		engine.update(timeDiff * timeScale, timeDiff);
		
		drawDebug(canvas);
	}

	private void drawDebug(Canvas canvas) {		
		//canvas.drawText("FPS: " + Integer.toString(surface.fps), 0, surface.dp2px(EntityFactory.infoPanelHeight + 15), whitePaint);
	}

	@Override
	public void transitOut(BasicScene tScene) {
		// TODO Auto-generated method stub
		
	}
	
	
}