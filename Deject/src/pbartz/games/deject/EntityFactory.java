package pbartz.games.deject;

import java.util.Random;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import pbartz.games.deject.components.AIComponent;
import pbartz.games.deject.components.BitmapComponent;
import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.ColorInterpolationComponent;
import pbartz.games.deject.components.CreepComponent;
import pbartz.games.deject.components.CreepShieldComponent;
import pbartz.games.deject.components.ExpireComponent;
import pbartz.games.deject.components.ItemComponent;
import pbartz.games.deject.components.LevelInfoComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.PositionInterpolationComponent;
import pbartz.games.deject.components.RotateComponent;
import pbartz.games.deject.components.RotateInterpolationComponent;
import pbartz.games.deject.components.ScoreComponent;
import pbartz.games.deject.components.ScreenOverlayComponent;
import pbartz.games.deject.components.TagComponent;
import pbartz.games.deject.components.TextComponent;
import pbartz.games.deject.components.TouchComponent;
import pbartz.games.deject.components.ZoomComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.config.CreepConfig;
import pbartz.games.deject.config.GameConfig;
import pbartz.games.deject.config.ItemConfig;
import pbartz.games.deject.core.Engine;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Interpolation;
import pbartz.games.deject.core.PooledEngine;
import pbartz.games.deject.systems.AISystem;
import pbartz.games.deject.systems.ScoreSystem;

public class EntityFactory {

	static float controlPanelHeightPart = 2.8f;
	static float infoPanelHeightPart = 10f;
	static float controlPanelHeight = 0f;
	public static float infoPanelHeight = 0f;
	static float creepHeight = 0f;
	static float creepWidth = 0f;
	static float creepRealHeight;
	static float creepRealWidth;
	
	public static float pbarWidth = 0f;
	public static float pbarHeight = 0f;
	public static float pbarX = 0f;
	public static float pbarY = 0f;
	
	public static float levelPanelX = 0f;
	public static float levelPanelY = 0f;
	public static float levelPanelWidth = 0f;
	public static float levelPanelHeight = 0f;
	
	static float lifeOffsetX;
	static float lifeHeight;
	private static float lifeWidth;
	
	public static void caculateMetrics(DejectSurface surface) {
		
		infoPanelHeight = surface.heightDp / infoPanelHeightPart;
		controlPanelHeight = surface.heightDp / controlPanelHeightPart;	
		
		creepRealHeight = (surface.heightDp - (((surface.heightDp - 1) / controlPanelHeightPart)) - ((surface.heightDp - 1) / infoPanelHeightPart)) / 3;
		creepRealWidth = (surface.widthDp - 1) / 3;
		
		creepHeight = Math.min(creepRealHeight, creepRealWidth);
		creepWidth = Math.min(creepRealHeight, creepRealWidth);	
		
		lifeOffsetX = infoPanelHeight + 1;
		lifeHeight = (infoPanelHeight / 2) - 1;
		lifeWidth = lifeHeight;
		
		levelPanelX = surface.widthDp / 2;
		levelPanelY = surface.heightDp / 2 + surface.heightDp / 4;
		
		levelPanelWidth = (surface.widthDp / 100) * 90;
		levelPanelHeight = levelPanelWidth / 1.5f;
		
		
	}
	
	

	public static void createScorePanel(Engine engine, DejectSurface surface) {
		
		Entity scoreEntity = new Entity();
		scoreEntity.add(new ScoreComponent());
		
		engine.addEntity(scoreEntity);
		engine.getSystem(ScoreSystem.class).setScoreEntity(scoreEntity);
		engine.getSystem(AISystem.class).setScore(scoreEntity.getComponent(ScoreComponent.class));
		
		
		// Gold icon
		
		Entity gold_icon = new Entity();
		
		float sX = lifeOffsetX + (lifeWidth / 2);
		float sY = lifeHeight + lifeHeight / 2;
		
		gold_icon.add(new PositionComponent(surface.dp2px(sX), surface.dp2px(sY)));
		gold_icon.add(new RectDimensionComponent(surface.dp2px(lifeWidth), surface.dp2px(lifeHeight)));
		gold_icon.add(new ColorComponent(255, 255, 0, 0));
		gold_icon.add(new BitmapComponent(BitmapLibrary.getBitmap("coin_gold_small")));
		
		engine.addEntity(gold_icon);
		
		// gold value
		
		Entity goldValue = new Entity();
		
		float iconsPanelWidth = ((surface.widthDp - lifeOffsetX) / 3);
		float valuePanelWidth = iconsPanelWidth - lifeWidth; 
		
		float gvX = lifeOffsetX + lifeWidth + (valuePanelWidth / 2) + 1;
		float gvY = sY - 2;
		
		goldValue.add(new PositionComponent(surface.dp2px(gvX), surface.dp2px(gvY)));
		goldValue.add(new RectDimensionComponent(surface.dp2px(valuePanelWidth), surface.dp2px(lifeHeight)));
		goldValue.add(new ColorComponent(255, 255, 255, 255));
		goldValue.add(new TextComponent("0", lifeHeight * 1.5f));
		
		engine.getSystem(ScoreSystem.class).setLabelGoldEntity(goldValue);
		
		engine.addEntity(goldValue);
		
		// progress bar
		
		float pbWidth = surface.widthDp - (valuePanelWidth + lifeOffsetX + lifeWidth); 
		
		float pbX = (valuePanelWidth + lifeOffsetX + lifeWidth) + pbWidth / 2;
		float pbY = gvY + 2;
		
		Entity pbEntity = new Entity();
		
		pbEntity.add(new PositionComponent(surface.dp2px(pbX), surface.dp2px(pbY)));
		pbEntity.add(new RectDimensionComponent(surface.dp2px(pbWidth), surface.dp2px(lifeHeight)));
		pbEntity.add(new ColorComponent(255, 255, 0, 0));
		
		
		pbarWidth = surface.dp2px(pbWidth);
		pbarHeight = surface.dp2px(lifeHeight);
		pbarX = surface.dp2px(pbX);
		pbarY = surface.dp2px(pbY);
		
		pbEntity.add(new BitmapComponent(BitmapLibrary.getBitmap("pbar")));
		
		engine.addEntity(pbEntity);
		
		engine.getSystem(ScoreSystem.class).setProgressBarEntity(pbEntity);
		engine.getSystem(AISystem.class).setProgressBarEntity(pbEntity);
		
		
		// Score value
		
		Entity entity = new Entity();
		
		entity.add(new TextComponent("0", infoPanelHeight / 2));
		
		float pX = 0 + surface.widthDp / 3 - ((surface.widthDp / 3) / 2);
		float pY = 0 + infoPanelHeight / 2;
		
		entity.add(new PositionComponent(surface.dp2px(pX), surface.dp2px(pY)));
		
		
		entity.add(new ColorComponent(255, 255, 255, 255));
		
		entity.add(new RectDimensionComponent(
			surface.dp2px(surface.widthDp / 3),
			surface.dp2px(infoPanelHeight)
		));
		
		entity.add(new TagComponent("score_score"));
		
		engine.getSystem(ScoreSystem.class).setLabelScoreEntity(entity);
		
		//engine.addEntity(entity);
		
		
		// weapon icon
		
		
		Entity wepEntity = new Entity();
		
		wepEntity.add(new PositionComponent(surface.dp2px(infoPanelHeight / 2), surface.dp2px(infoPanelHeight / 2)));
		wepEntity.add(new RectDimensionComponent(surface.dp2px(infoPanelHeight), surface.dp2px(infoPanelHeight)));
		wepEntity.add(new ColorComponent(255, 255, 0, 0));
		
		wepEntity.add(new BitmapComponent(BitmapLibrary.getBitmap("hammer")));
		
		engine.addEntity(wepEntity);
		
		
		// hearts panel
		for(int i = 0 ; i < 8; i++) {
			
			Entity heart = new Entity();
			
			float hX = lifeOffsetX + (lifeWidth / 2) + i*lifeWidth + 1*i;
			float hY = lifeHeight / 2;
			
			heart.add(new PositionComponent(surface.dp2px(hX), surface.dp2px(hY)));
			heart.add(new RectDimensionComponent(surface.dp2px(lifeWidth), surface.dp2px(lifeHeight)));
			heart.add(new ColorComponent(255, 255, 0, 0));
			heart.add(new BitmapComponent(BitmapLibrary.getBitmap("heart_small")));
			
			engine.getSystem(ScoreSystem.class).setHeart(i, heart);
			
			engine.addEntity(heart);
			
		}
		
	}
	
	public static void createControlPanel(Engine engine, DejectSurface surface) {

		float btnHeight = ((surface.heightDp - 1) / controlPanelHeightPart) / 3;
		float btnWidth = (surface.widthDp - 1) / 3;
		
		int tag = 1;
		
		for(int y = 0 ; y < 3 ; y++) {
			float centerY = surface.heightDp - (surface.heightDp / controlPanelHeightPart) + btnHeight / 2 + btnHeight * y;
			
			for(int x = 0 ; x < 3 ; x++) {
				float centerX = btnWidth / 2 + btnWidth * x;
				
				Entity entity = new Entity();
				
				entity.add(new PositionComponent(surface.dp2px(centerX), surface.dp2px(centerY)));
				entity.add(new RectDimensionComponent(surface.dp2px(btnWidth), surface.dp2px(btnHeight)));
				entity.add(new TouchComponent());
				entity.add(new TagComponent(Integer.toString(tag)));
				
				entity.add(new BitmapComponent(BitmapLibrary.getBitmap("btn_blank")));
				
				ColorComponent color = new ColorComponent(255, 0, 0, 0);
				
				Paint borderPaint = createPaint(255, 255, 255, 255);
				borderPaint.setStyle(Paint.Style.STROKE);
				color.setBorderPaint(borderPaint);
				
				entity.add(color);
				
				engine.addEntity(entity);
				
				tag += 1;
			}
		}
	}
	
	public static Entity spawnHammerHit(Engine engine, DejectSurface surface, int position) {
		
		float life = 0.25f;
		
		Entity entity = prepareCellEntity(surface, position);
		
		ColorComponent color = new ColorComponent(0, 255, 0, 0);
		color.getPaint().setAntiAlias(false);
		color.getPaint().setFilterBitmap(false);
		
		entity.add(color);
		
		Paint tmpColor = createPaint(255, 255, 0, 0);
		
		entity.add(new RotateComponent(0));
		entity.add(new RotateInterpolationComponent(0, -90, life, Interpolation.EASE_OUT));
		entity.add(new ColorInterpolationComponent(color.getPaint(), tmpColor, life, Interpolation.EASE_OUT));
		
		entity.add(new BitmapComponent(BitmapLibrary.getBitmap("hammer")));
		
		entity.add(new ExpireComponent(life + 0.05f));
		
		engine.addEntity(entity);
		
		return entity;
		
	}
	
	public static Entity spawnHammerMiss(Engine engine, DejectSurface surface, int position) {
		
		float life = 0.25f;
		
		Entity entity = prepareCellEntity(surface, position);
		
		ColorComponent color = new ColorComponent(0, 255, 0, 0);
		color.getPaint().setAntiAlias(false);
		color.getPaint().setFilterBitmap(false);
		
		entity.add(color);
		
		Paint tmpColor = createPaint(255, 255, 0, 0);
		
		entity.add(new RotateComponent(0));
		entity.add(new RotateInterpolationComponent(0, -40, life, Interpolation.EASE_OUT));
		entity.add(new ColorInterpolationComponent(color.getPaint(), tmpColor, life, Interpolation.EASE_OUT));
		
		entity.add(new BitmapComponent(BitmapLibrary.getBitmap("hammer")));
		
		entity.add(new ExpireComponent(life + 0.05f));
		
		engine.addEntity(entity);
		
		return entity;
		
	}

	public static Entity spawnCellCreep(Engine engine, DejectSurface surface, int position, String creepType) {

		Entity entity = prepareCellEntity(surface, position);
		
		ColorComponent color = new ColorComponent(255, 255, 0, 0);
		color.getPaint().setAntiAlias(false);
		color.getPaint().setFilterBitmap(false);
		
		entity.add(color);
		
		CreepConfig creepConfig = GameConfig.getCreepConfig(creepType);
		
		entity.add(new BitmapComponent(BitmapLibrary.getBitmap(creepConfig.getImage())));
		
		CreepComponent creep = new CreepComponent(position);
		
		creep.setConfig(creepConfig);		
				
		entity.add(creep);		
		
		entity.add(new ZoomComponent(1f,1f));
		
		if (creepConfig.getComponentsCount() > 0) {
			
			for(int i = 0 ; i < creepConfig.getComponentsCount() ; i++) {
				
				String component = creepConfig.getComponent(i);
				
				if (component.equalsIgnoreCase("shield")) {
					
					CreepShieldComponent creepShield = new CreepShieldComponent(creepConfig.getShield_interval(), creepConfig.getShield_duration());
					
					entity.add(creepShield);
				}
				
			}
			
		}
		
		engine.addEntity(entity);
		
		return entity;
	}
	
	public static Entity prepareCellEntity(DejectSurface surface, int position) {
		Point cellPoint = getCellPoint(position);
		
		float creepHeight = EntityFactory.creepHeight;
		float creepWidth = EntityFactory.creepWidth;
		
		float centerX = EntityFactory.creepRealWidth / 2 + EntityFactory.creepRealWidth * (cellPoint.x - 1);
		float centerY = infoPanelHeight + EntityFactory.creepRealHeight / 2 + EntityFactory.creepRealHeight * (cellPoint.y - 1) - 1;
		
		Entity entity = new Entity();
		
		entity.add(new PositionComponent(surface.dp2px(centerX), surface.dp2px(centerY)));
		entity.add(new RectDimensionComponent(surface.dp2px(creepWidth), surface.dp2px(creepHeight)));
		
		return entity;
	}

	public static void createAIEntity(PooledEngine engine) {
		
		Entity entity = new Entity();
		AIComponent ai = new AIComponent();
		
		ai.setState(AIComponent.STATE_NOT_INITED);
		
		Random r = new Random();
		ai.setTimer(r.nextFloat() * 2);
		
		entity.add(ai);
		
		engine.getSystem(AISystem.class).setAi(ai);
		
		engine.addEntity(entity);		
		
	}
	
	public static void createBangFromItem(Engine engine, DejectSurface surface, Entity entity) {
		
		createBangFromCreep(engine, surface, entity);
		
	}

	public static void createBangFromCreep(Engine engine, DejectSurface surface, Entity creep) {
		PositionComponent position = creep.getComponent(PositionComponent.class);
		RectDimensionComponent dimension = creep.getComponent(RectDimensionComponent.class);
		
		float duration = 0.5f;
		
		Random r = new Random();
		
		Rect rect = dimension.getRect(position);
		
		int parts = 5;
		float partW = dimension.getWidth() / parts;
		float partH = dimension.getHeight() / parts;
		
		
		for(int x = 0 ; x < parts ; x++) {
			for(int y = 0 ; y < parts ; y++) {
				
				float px = rect.left + (x * partW) + (partW / 2);
				float py = rect.top + (y * partH) + (partH / 2);
				
				Entity entity = new Entity();
				
				entity.add(new PositionComponent(px, py));
				entity.add(new RectDimensionComponent((int)partW, (int)partH));
				entity.add(new ColorComponent(255, 128, 189, 30));
				
				entity.add(new PositionInterpolationComponent(
					entity.getComponent(PositionComponent.class), 
					(float)surface.dp2px(r.nextFloat() * surface.widthDp), 
					(float)surface.dp2px(r.nextFloat() * surface.heightDp), 
					duration, 
					Interpolation.EASE_OUT
				));
				
				entity.add(new ColorInterpolationComponent(
					entity.getComponent(ColorComponent.class).getPaint(),
					createPaint(0, 255, 0, 0), 
					duration, 
					Interpolation.EASE_IN
				));
				
				entity.add(new ExpireComponent(duration));
				
				engine.addEntity(entity);
				
			}
		}			
		
	}
	
	public static Point getCellPoint(int position) {
		int y = Math.round((float)position / 3 + 0.5f);
		int x = position - (y - 1)*3;
		
		if (x == 0) {
			x = 3;
			y -= 1;
		}
		
		return new Point(x, y);
	}

	public static Paint createPaint(int a, int r, int g, int b) {
		Paint paint = new Paint();
		paint.setARGB(a, r, g, b);
		return paint;
	}



	public static Entity spawnCellItem(Engine engine, DejectSurface surface, int position, String itemType) {
		Entity entity = prepareCellEntity(surface, position);
		
		ColorComponent color = new ColorComponent(0, 255, 0, 0);
		color.getPaint().setAntiAlias(false);
		color.getPaint().setFilterBitmap(false);
		
		entity.add(color);
		
		ItemConfig itemConfig = GameConfig.getItemConfig(itemType);
		
		ItemComponent item = new ItemComponent(position, 1);
		item.setConfig(itemConfig);
		
		entity.add(item);
		entity.add(new BitmapComponent(BitmapLibrary.getBitmap(itemConfig.getImage())));
		
		engine.addEntity(entity);
		
		return entity;
	}



	public static void createStartScreen(Engine engine, DejectSurface surface) {
		
		// leader board button

		
		Entity btnScore = new Entity();
		
		btnScore.add(new RectDimensionComponent(surface.dp2px(creepWidth), surface.dp2px(creepWidth)));
		btnScore.add(new ColorComponent(255, 255, 0, 0));
		
		btnScore.add(new BitmapComponent(BitmapLibrary.getBitmap("btn_leaderboard")));
		
		float btnScoreX = (surface.widthDp / 2);
		float btnScoreY = (surface.heightDp / 2);
		
		btnScore.add(new PositionComponent(surface.dp2px(btnScoreX), surface.dp2px(btnScoreY)));
		
		btnScore.add(new TouchComponent());
		btnScore.add(new TagComponent("btn_leaderboard"));
		
		btnScore.add(new PositionInterpolationComponent(
			btnScore.getComponent(PositionComponent.class), 
			surface.dp2px(surface.widthDp / 2 + surface.widthDp / 4),
			surface.dp2px(surface.heightDp / 2),
			0.7f,
			Interpolation.EASE_OUT
		));
		
		engine.addEntity(btnScore, 0.7f);
		
		// play button;
		
		Entity btnPlay = new Entity();
		
		btnPlay.add(new RectDimensionComponent(surface.dp2px(creepWidth), surface.dp2px(creepWidth)));
		btnPlay.add(new ColorComponent(255, 255, 0, 0));
		
		btnPlay.add(new BitmapComponent(BitmapLibrary.getBitmap("btn_play")));
		
		float btnX = (surface.widthDp / 2);
		float btnY = (surface.heightDp);
		
		btnPlay.add(new PositionComponent(surface.dp2px(btnX), surface.dp2px(btnY)));
		
		btnPlay.add(new TouchComponent());
		btnPlay.add(new TagComponent("btn_play"));
		
		btnPlay.add(new PositionInterpolationComponent(
			btnPlay.getComponent(PositionComponent.class), 
			surface.dp2px(btnX),
			surface.dp2px(surface.heightDp / 2),
			0.7f,
			Interpolation.EASE_OUT
		));
		
		btnPlay.add(new PositionInterpolationComponent(
			new PositionComponent(surface.dp2px(btnX), surface.dp2px(surface.heightDp / 2)), 
			surface.dp2px(surface.widthDp / 2 - surface.widthDp / 4),
			surface.dp2px(surface.heightDp / 2),
			0.7f,
			Interpolation.EASE_OUT
		), 0.7f);
		
		
		engine.addEntity(btnPlay);
		
	}



	public static void createFadeOut(PooledEngine engine, DejectSurface surface) {
		
		Entity entity = new Entity();
		
		entity.add(new ScreenOverlayComponent());
		
		entity.add(new ColorComponent(0, 0, 0, 0));
		
		entity.add(new ColorInterpolationComponent(				
			entity.getComponent(ColorComponent.class).getPaint(),
			createPaint(255, 0, 0, 0),
			0.5f,
			Interpolation.EASE_OUT			
		));	
		
		entity.add(new ExpireComponent(0.8f));
		
		engine.addEntity(entity);
		
		Entity exp = new Entity();
		
		exp.add(new ExpireComponent(0.5f));
		exp.add(new TagComponent("fade_out"));
		
		engine.addEntity(exp);
		
	}
	
	public static void createFadeIn(PooledEngine engine, DejectSurface surface) {
		
		Entity entity = new Entity();
		
		entity.add(new ScreenOverlayComponent());
		
		entity.add(new ColorComponent(255, 0, 0, 0));
		
		entity.add(new ColorInterpolationComponent(				
			entity.getComponent(ColorComponent.class).getPaint(),
			createPaint(0, 0, 0, 0),
			0.5f,
			Interpolation.EASE_IN				
		));
		
		entity.add(new ExpireComponent(0.5f));
		entity.add(new TagComponent("fade_in"));
		
		engine.addEntity(entity);
		
	}



	public static Entity spawnLevelInfoPanel(Engine engine,	DejectSurface surface, int level) {
		Entity entity = new Entity();
		
		entity.add(new PositionComponent(
			surface.dp2px(levelPanelX),
			surface.dp2px(levelPanelY + levelPanelHeight)
		));
		
		entity.add(new RectDimensionComponent(
			surface.dp2px(levelPanelWidth),
			surface.dp2px(levelPanelHeight)
		));
		
		entity.add(new ColorComponent(255, 255, 0, 0));
		
		entity.add(new LevelInfoComponent(level));
		
		entity.add(new TouchComponent());
		entity.add(new TagComponent("levelInfo"));
		
		entity.add(new BitmapComponent(BitmapLibrary.getBitmap("level1")));
		
		entity.setOrder(1);
		
		engine.addEntity(entity);		
		
		return entity;
	}

	
}