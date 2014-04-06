package pbartz.games.deject;

import java.util.Random;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;
import pbartz.games.deject.components.AIComponent;
import pbartz.games.deject.components.BitmapAnimationComponent;
import pbartz.games.deject.components.BitmapComponent;
import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.ColorInterpolationComponent;
import pbartz.games.deject.components.CreepComponent;
import pbartz.games.deject.components.CreepShieldComponent;
import pbartz.games.deject.components.CreepSwapComponent;
import pbartz.games.deject.components.CreepThiefComponent;
import pbartz.games.deject.components.ExpireComponent;
import pbartz.games.deject.components.FontSizeInterpolationComponent;
import pbartz.games.deject.components.GalaxyComponent;
import pbartz.games.deject.components.GalaxyEmitterComponent;
import pbartz.games.deject.components.ItemComponent;
import pbartz.games.deject.components.LevelInfoComponent;
import pbartz.games.deject.components.MovementComponent;
import pbartz.games.deject.components.MultiplierComponent;
import pbartz.games.deject.components.MultiplierInterpolationComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.PositionInterpolationComponent;
import pbartz.games.deject.components.PositionShakeComponent;
import pbartz.games.deject.components.RadialPositionComponent;
import pbartz.games.deject.components.RadialPositionInterpolationComponent;
import pbartz.games.deject.components.RectInterpolationComponent;
import pbartz.games.deject.components.RotateComponent;
import pbartz.games.deject.components.RotateInterpolationComponent;
import pbartz.games.deject.components.ScoreComponent;
import pbartz.games.deject.components.ScreenOverlayComponent;
import pbartz.games.deject.components.TagComponent;
import pbartz.games.deject.components.TextComponent;
import pbartz.games.deject.components.TextNumberInterpolationComponent;
import pbartz.games.deject.components.TouchComponent;
import pbartz.games.deject.components.ZoomComponent;
import pbartz.games.deject.components.ZoomInterpolationComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.config.CreepConfig;
import pbartz.games.deject.config.GameConfig;
import pbartz.games.deject.config.ItemConfig;
import pbartz.games.deject.core.Component;
import pbartz.games.deject.core.Engine;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.core.Interpolation;
import pbartz.games.deject.core.PooledEngine;
import pbartz.games.deject.systems.AISystem;
import pbartz.games.deject.systems.LevelInfoSystem;
import pbartz.games.deject.systems.ScoreSystem;
import pbartz.games.deject.utils.Array;
import pbartz.games.deject.utils.IntMap;
import pbartz.games.deject.utils.ObjectMap;
import pbartz.games.deject.utils.IntMap.Keys;

public class EntityFactory {

	static float controlPanelHeightPart = 2.8f;
	static float infoPanelHeightPart = 10f;
	static float controlPanelHeight = 0f;
	public static float infoPanelHeight = 0f;
	public static float creepHeight = 0f;
	public static float creepWidth = 0f;
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
	
	public static Array<Entity> buttons = new Array<Entity>();
	public static float btnHeight;
	public static float btnWidth;
	private static float surfaceHeightDp;
	
	public static float goPanelWidth;
	public static float goPanelHeight;
	public static float goPanelcX;
	public static float goPanelcY;
	private static Entity gameOverEntity = null;
	public static Entity btnPlay = null;
	private static Entity btnScore = null;
	public static float starBaseWidth;
	
	public static GalaxyEmitterComponent galaxyEmitter = null;
	public static Entity scoreValueEntity = null;
	public static Entity highScoreValueEntity = null;
	
	public static Random r = new Random();
	
	
	static ObjectMap<String, Component> reusableComponents = new ObjectMap<String, Component>();
	
	static ObjectMap<String, String> adButtonsConfig = new ObjectMap<String, String>();
	
	
	public static Entity gameOverTitleEntity = null;
	public static Entity timeScale;
	public static Entity boardButton;
	private static Entity pausedEntity;
	public static Entity wepEntity;
	public static Entity rateBtnEntity = null;
	public static Entity snakeBtnEntity = null;
	public static Entity btnLevelPlay = null;
	public static boolean isTutorial = true;
	
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
		levelPanelY = surface.heightDp / 2 - surface.heightDp / 6;
		
		levelPanelWidth = (surface.widthDp / 100) * 90;
		levelPanelHeight = levelPanelWidth / 1.5f;
		
		btnHeight = ((surface.heightDp - 1) / controlPanelHeightPart) / 3;
		btnWidth = (surface.widthDp - 1) / 3;
		
		
		surfaceHeightDp = surface.heightDp;
		
		
		goPanelWidth = (surface.widthDp / 100) * 90;
		goPanelHeight = goPanelWidth / 1.5f;
		
		
		goPanelcX = surface.widthDp / 2;
		goPanelcY = surface.heightDp / 2;
		
		
		starBaseWidth = surface.widthPx / 10;
		
		adButtonsConfig.put("btn_snake", Storage.getValue("adbutton_btn_snake", "show"));
		adButtonsConfig.put("btn_rate", Storage.getValue("adbutton_btn_rate", "show"));

	}
	
	

	public static void createScorePanel(PooledEngine engine, DejectSurface surface) {
		
		Entity scoreEntity = engine.createEntity();
		scoreEntity.add(new ScoreComponent());
		
		engine.addEntity(scoreEntity);
		engine.getSystem(ScoreSystem.class).setScoreEntity(scoreEntity);
		engine.getSystem(AISystem.class).setScore(scoreEntity.getComponent(ScoreComponent.class));
		
		
		// Gold icon
		
		Entity gold_icon = engine.createEntity();
		
		float sX = lifeOffsetX + (lifeWidth / 2);
		float sY = lifeHeight + lifeHeight / 2;
		
		PositionComponent posComp = engine.createComponent(PositionComponent.class);
		posComp.init(surface.dp2px(sX), surface.dp2px(sY));
		
		gold_icon.add(posComp);
		gold_icon.add(getRectComponent(engine, surface.dp2px(lifeWidth), surface.dp2px(lifeHeight)));
		gold_icon.add(getColorComponent(engine, 255, 255, 0, 0));
		gold_icon.add(getReusableBitmapComponent(engine, "coin_gold_small"));
		
		engine.addEntity(gold_icon);
		
		// gold value
		
		Entity goldValue = engine.createEntity();
		
		float iconsPanelWidth = ((surface.widthDp - lifeOffsetX) / 3);
		float valuePanelWidth = iconsPanelWidth - lifeWidth; 
		
		float gvX = lifeOffsetX + lifeWidth + (valuePanelWidth / 2) + 1;
		float gvY = sY - 2;
		
		PositionComponent posComp2 = engine.createComponent(PositionComponent.class);
		posComp2.init(surface.dp2px(gvX), surface.dp2px(gvY));
		
		goldValue.add(posComp2);
		goldValue.add(getRectComponent(engine, surface.dp2px(valuePanelWidth), surface.dp2px(lifeHeight)));
		goldValue.add(getColorComponent(engine, 255, 255, 255, 255));
		goldValue.add(new TextComponent("x0", surface.dp2px(lifeHeight)));
		goldValue.getComponent(ColorComponent.class).getPaint().setTypeface(surface.mFace);
		
		engine.getSystem(ScoreSystem.class).setLabelGoldEntity(goldValue);
		
		engine.addEntity(goldValue);
		
		// progress bar
		
		float pbWidth = surface.widthDp - (valuePanelWidth + lifeOffsetX + lifeWidth); 
		
		float pbX = (valuePanelWidth + lifeOffsetX + lifeWidth) + pbWidth / 2;
		float pbY = gvY + 2;
		
		Entity pbEntity = engine.createEntity();
		
		PositionComponent posComp3 = engine.createComponent(PositionComponent.class);
		posComp3.init(surface.dp2px(pbX), surface.dp2px(pbY));
		
		pbEntity.add(posComp3);
		pbEntity.add(getRectComponent(engine, surface.dp2px(pbWidth), surface.dp2px(lifeHeight)));
		pbEntity.add(getColorComponent(engine, 255, 255, 0, 0));
		
		
		pbarWidth = surface.dp2px(pbWidth);
		pbarHeight = surface.dp2px(lifeHeight);
		pbarX = surface.dp2px(pbX);
		pbarY = surface.dp2px(pbY);
		
		pbEntity.add(getReusableBitmapComponent(engine, "pbar"));
		
		engine.addEntity(pbEntity);
		
		engine.getSystem(ScoreSystem.class).setProgressBarEntity(pbEntity);
		engine.getSystem(AISystem.class).setProgressBarEntity(pbEntity);
		
		
		// Score value
		
		Entity entity = engine.createEntity();
		
		entity.add(new TextComponent("0", infoPanelHeight / 2));
		
		float pX = 0 + surface.widthDp / 3 - ((surface.widthDp / 3) / 2);
		float pY = 0 + infoPanelHeight / 2;
		
		
		PositionComponent posComp4 = engine.createComponent(PositionComponent.class);
		posComp4.init(surface.dp2px(pX), surface.dp2px(pY));
		
		entity.add(posComp4);
		
		
		entity.add(getColorComponent(engine, 255, 255, 255, 255));
		
		entity.add(getRectComponent(engine, 
			surface.dp2px(surface.widthDp / 3),
			surface.dp2px(infoPanelHeight)
		));
		
		entity.add(new TagComponent("score_score"));
		
		engine.getSystem(ScoreSystem.class).setLabelScoreEntity(entity);
		
		//engine.addEntity(entity);
		
		
		// weapon icon
		
		
		wepEntity = engine.createEntity();
		
		PositionComponent posComp5 = engine.createComponent(PositionComponent.class);
		posComp5.init(surface.dp2px(infoPanelHeight / 2), surface.dp2px(infoPanelHeight / 2));
		
		wepEntity.add(posComp5);
		wepEntity.add(getRectComponent(engine, surface.dp2px(infoPanelHeight), surface.dp2px(infoPanelHeight)));
		wepEntity.add(getColorComponent(engine, 255, 255, 0, 0));
		
		wepEntity.add(getReusableBitmapComponent(engine, engine.getSystem(ScoreSystem.class).getHammerBitmapName()));
		
		engine.addEntity(wepEntity);
		
		
		// hearts panel
		for(int i = 0 ; i < 8; i++) {
			
			Entity heart = engine.createEntity();
			
			float hX = lifeOffsetX + (lifeWidth / 2) + i*lifeWidth + 1*i;
			float hY = lifeHeight / 2;
			
			PositionComponent posComp6 = engine.createComponent(PositionComponent.class);
			posComp6.init(surface.dp2px(hX), surface.dp2px(hY));
			
			heart.add(posComp6);
			heart.add(getRectComponent(engine, surface.dp2px(lifeWidth), surface.dp2px(lifeHeight)));
			heart.add(getColorComponent(engine, 255, 255, 0, 0));
			heart.add(getReusableBitmapComponent(engine, "heart_small"));
			
			engine.getSystem(ScoreSystem.class).setHeart(i, heart);
			
			engine.addEntity(heart);
			
		}
		
	}
	
	public static float getButtonCenterY(int position) {
		Point point = getCellPoint(position);
		return surfaceHeightDp - (surfaceHeightDp / controlPanelHeightPart) + btnHeight / 2 + btnHeight * (point.y - 1);
	}
	
	public static float getButtonCenterX(int position) {
		Point point = getCellPoint(position);
		return btnWidth / 2 + btnWidth * (point.x - 1);
	}
	
	public static void createControlPanel(PooledEngine engine, DejectSurface surface) {

		int tag = 1;
		
		for(int y = 0 ; y < 3 ; y++) {
			float centerY = surface.heightDp - (surface.heightDp / controlPanelHeightPart) + btnHeight / 2 + btnHeight * y;
			
			for(int x = 0 ; x < 3 ; x++) {
				float centerX = btnWidth / 2 + btnWidth * x;
				
				Entity entity = engine.createEntity();
				
				PositionComponent posComp = engine.createComponent(PositionComponent.class);
				posComp.init(surface.dp2px(centerX), surface.dp2px(centerY));
				
				entity.add(posComp);
				entity.add(getRectComponent(engine, surface.dp2px(btnWidth), surface.dp2px(btnHeight)));
				entity.add(new TouchComponent());
				entity.add(new TagComponent(Integer.toString(tag)));
				
				entity.add(getReusableBitmapComponent(engine, "btn_blank"));
				
				ColorComponent color = getColorComponent(engine, 255, 0, 0, 0);
				
				entity.add(color);
				
				color.getPaint().setTypeface(surface.mFace);
				
				TextComponent text = new TextComponent(Integer.toString(tag), surface.dp2px(btnHeight / 2.5f)); 
				
				text.setShiftX(surface.dp2px(btnWidth / 2.25f));
				text.setShiftY( - surface.dp2px(btnHeight / 2.5f));
				
				entity.add(text);
				
				engine.addEntity(entity);
				
				buttons.add(entity);
				
				tag += 1;
			}
		}
		
		Entity board = engine.createEntity();
		board.add(getRectComponent(engine,
			surface.dp2px(surface.widthDp), 
			surface.dp2px((surface.heightDp - controlPanelHeight - infoPanelHeight) * 0.8f)
		));
		
		PositionComponent posComp = engine.createComponent(PositionComponent.class);

		posComp.init(
			surface.dp2px(surface.widthDp / 2), 
			surface.dp2px(infoPanelHeight + (surface.heightDp - controlPanelHeight - infoPanelHeight) / 2)
		);
		
		board.add(posComp);
		board.add(new TouchComponent());
		board.add(new TagComponent("playfield"));
		
		boardButton = board;
		
		engine.addEntity(board);
	}
	
	public static Entity spawnHammerHit(PooledEngine engine, DejectSurface surface, int position) {
		
		float life = 0.25f;
		
		Entity entity = prepareCellEntity(engine, surface, position);
		
		ColorComponent color = getColorComponent(engine, 0, 255, 0, 0);
		color.getPaint().setAntiAlias(false);
		color.getPaint().setFilterBitmap(false);
		
		entity.add(color);
		
		Paint tmpColor = createPaint(255, 255, 0, 0);
		
		entity.add(getRotateComponent(engine, 0));
		entity.add(getRotateInterpolationComponent(engine, 0, -90, life, Interpolation.EASE_OUT));
		entity.add(getColorInterpolationComponent(engine, color.getPaint(), tmpColor, life, Interpolation.EASE_OUT));
		
		entity.add(getReusableBitmapComponent(engine, engine.getSystem(ScoreSystem.class).getHammerBitmapName()));
		
		entity.add(getExpireComponent(engine, life + 0.05f));
		
		entity.setOrder(3);
		
		engine.addEntity(entity);
		
		spawnHammerBlowAnimation(engine, surface, position, 0.15f);
		
		return entity;
		
	}
	
	public static Entity spawnHammerBlowAnimation(PooledEngine engine, DejectSurface surface, int position, float delay) {

		float keyDelay = 0.045f;
		
		float life = keyDelay * 7;		
		
		Entity entity = prepareCellEntity(engine, surface, position);
		
		ColorComponent color = getColorComponent(engine, 255, 255, 0, 0);
		color.getPaint().setAntiAlias(false);
		color.getPaint().setFilterBitmap(false);
		
		entity.add(color);
		
		entity.add(getReusableBitmapComponent(engine, "blow_f1"));
		
		entity.add(new BitmapAnimationComponent(
			"blow_f",
			6,
			(int)(keyDelay * 1000)
		));
		
		entity.add(getExpireComponent(engine, life));
		entity.setOrder(4);
		engine.addEntity(entity, delay);
		
		return entity;
		
	}
	
	public static Entity spawnHammerMiss(PooledEngine engine, DejectSurface surface, int position) {
		
		float life = 0.25f;
		
		Entity entity = prepareCellEntity(engine, surface, position);
		
		ColorComponent color = getColorComponent(engine, 0, 255, 0, 0);
		color.getPaint().setAntiAlias(false);
		color.getPaint().setFilterBitmap(false);
		
		entity.add(color);
		
		Paint tmpColor = createPaint(255, 255, 0, 0);
		
		entity.add(getRotateComponent(engine, 0));
		entity.add(getRotateInterpolationComponent(engine, 0, -40, life, Interpolation.EASE_OUT));
		entity.add(getColorInterpolationComponent(engine, color.getPaint(), tmpColor, life, Interpolation.EASE_OUT));
		
		entity.add(getReusableBitmapComponent(engine, engine.getSystem(ScoreSystem.class).getHammerBitmapName()));
		
		entity.add(getExpireComponent(engine, life + 0.05f));
		
		entity.setOrder(3);
		
		entity.add(getPositionShakeComponent(engine, (float)surface.dp2px(EntityFactory.creepWidth / 6), (float)surface.dp2px(EntityFactory.creepWidth / 6), life / 2), life / 2);
		
		engine.addEntity(entity);
		
//		@SuppressWarnings("unchecked")
//		IntMap<Entity> allpos = engine.getEntitiesFor(Family.getFamilyFor(PositionComponent.class));
//		
//		Keys keys = allpos.keys();
//		
//		while(keys.hasNext){
//			
//			Entity ent = allpos.get(keys.next());
//			ent.add(getPositionShakeComponent(engine, (float)surface.dp2px(EntityFactory.creepWidth / 6), (float)surface.dp2px(EntityFactory.creepWidth / 6), life), life / 2);
//			
//		}
		
		return entity;
		
	}

	public static Entity spawnCellCreep(PooledEngine engine, DejectSurface surface, int position, String creepType) {

		Entity entity = prepareCellEntity(engine, surface, position);
		
		ColorComponent color = getColorComponent(engine, 255, 255, 255, 255);
		color.getPaint().setAntiAlias(false);
		color.getPaint().setFilterBitmap(false);
		
		entity.add(color);
		
		CreepConfig creepConfig = GameConfig.getCreepConfig(creepType); 
		
		
		entity.add(getReusableBitmapComponent(engine, creepConfig.getImage()));
		
		CreepComponent creep = new CreepComponent(position);
				
		creep.setConfig(creepConfig);		
		creep.applyMultiplier(getCurrentCreepSpeedMultiplier(engine));
				
		entity.add(creep);		
		
		if (creepConfig.getComponentsCount() > 0) {
			
			for(int i = 0 ; i < creepConfig.getComponentsCount() ; i++) {
				
				String component = creepConfig.getComponent(i);
				
				if (component.equalsIgnoreCase("shield")) {
					
					CreepShieldComponent creepShield = new CreepShieldComponent(creep.getShieldInterval(), creep.getShieldDuration());
					
					entity.add(creepShield);
				}
				
				if (component.equalsIgnoreCase("buttonswap")) {
					
					
					
					int swapNumber = r.nextInt(9) + 1;
					
					CreepSwapComponent swap = new CreepSwapComponent(swapNumber);
					
					entity.add(swap);
					
					
					entity.getComponent(ColorComponent.class).getPaint().setTypeface(surface.mFace);
					TextComponent text = new TextComponent(Integer.toString(swapNumber), creepHeight / 3); 
					
					text.setShiftX(surface.dp2px(creepWidth / 2.25f));
					text.setShiftY( - surface.dp2px(creepWidth / 1.75f));
					
					entity.add(text);
					
				}
				
				if (component.equalsIgnoreCase("thief")) {
					
					CreepThiefComponent thief = new CreepThiefComponent();
					entity.add(thief);
					
				}
				
			}
			
		}
		
		engine.addEntity(entity);
		
		Entity button = getPositionButton(position);
		
		if (button != null && isTutorial) {
			
			spawnGenericTouchReaction(engine, surface, button, "green");
			spawnArrowAnimation(engine, surface, position);
			
		}
		
		
		return entity;
	}
	
	private static float getCurrentCreepSpeedMultiplier(PooledEngine engine) {
		float multi = GameConfig.getLevelConfig(
			engine.getSystem(AISystem.class).getScore().getLevel()
		).getSpeedMultiplier();

		return multi;
	}


	public static Entity getPositionButton(int position) {
		
		for(int i = 0 ; i < buttons.size ; i++) {
			
			Entity button = buttons.get(i);
			String tag = button.getComponent(TagComponent.class).getTag();
			
			if (tag.equals(Integer.toString(position))) {
				
				return button;
				
			}
			
		}
		
		return null;
		
	}
	
	public static Entity prepareCellEntity(PooledEngine engine, DejectSurface surface, int position) {
		Point cellPoint = getCellPoint(position);
		
		float creepHeight = EntityFactory.creepHeight;
		float creepWidth = EntityFactory.creepWidth;
		
		float centerX = EntityFactory.creepRealWidth / 2 + EntityFactory.creepRealWidth * (cellPoint.x - 1);
		float centerY = infoPanelHeight + EntityFactory.creepRealHeight / 2 + EntityFactory.creepRealHeight * (cellPoint.y - 1) - 1;
		
		Entity entity = engine.createEntity();
		
		
		PositionComponent posComp = engine.createComponent(PositionComponent.class);
		posComp.init(surface.dp2px(centerX), surface.dp2px(centerY));
		
		entity.add(posComp);
		entity.add(getRectComponent(engine, surface.dp2px(creepWidth), surface.dp2px(creepHeight)));
		
		return entity;
	}

	public static void createAIEntity(PooledEngine engine) {
		
		Entity entity = engine.createEntity();
		AIComponent ai = new AIComponent();
		
		ai.setState(AIComponent.STATE_NOT_INITED);
		
		ai.setTimer(r.nextFloat() * 2);
		
		entity.add(ai);
		
		engine.getSystem(AISystem.class).setAi(ai);
		
		engine.addEntity(entity);		
		
	}
	
	public static void createBangFromItem(PooledEngine engine, DejectSurface surface, Entity entity) {
		
		createBangFromCreep(engine, surface, entity, "item_particle");
		
	}

	public static void createBangFromCreep(PooledEngine engine, DejectSurface surface, Entity creep, String bmPrefix) {
		PositionComponent position = creep.getComponent(PositionComponent.class);
		RectDimensionComponent dimension = creep.getComponent(RectDimensionComponent.class);
		
		float duration = 0.5f;
		
		
		Rect rect = dimension.getRect(position);
		
		int parts = 5;
		float partW = dimension.getWidth() / parts;
		float partH = dimension.getHeight() / parts;
		
		
		for(int x = 0 ; x < parts ; x++) {
			for(int y = 0 ; y < parts ; y++) {
				
				float px = rect.left + (x * partW) + (partW / 2);
				float py = rect.top + (y * partH) + (partH / 2);
				
				Entity entity = engine.createEntity();
				
				PositionComponent posComp = engine.createComponent(PositionComponent.class);
				posComp.init(px, py);
				
				entity.add(posComp);
				entity.add(getRectComponent(engine, (int)(partW * 1.5f  - r.nextFloat() * partW), (int)(partH * 1.5f - r.nextFloat() * partH)));
				entity.add(getColorComponent(engine, 255, 255, 0, 0));
				
				entity.add(getPositionInterpolationComponent(engine, 
					entity.getComponent(PositionComponent.class), 
					(float)surface.dp2px(r.nextFloat() * surface.widthDp), 
					(float)surface.dp2px(r.nextFloat() * surface.heightDp), 
					duration, 
					Interpolation.EASE_OUT
				));
				
				entity.add(getColorInterpolationComponent(engine, 
					entity.getComponent(ColorComponent.class).getPaint(),
					createPaint(0, 255, 0, 0), 
					duration, 
					Interpolation.EASE_IN
				));
				
				entity.add(getExpireComponent(engine, duration));
				
				String bmName = bmPrefix;
				
				if (r.nextInt(100) < 30) {
					bmName += "1";
				} else if (r.nextInt(100) < 30) {
					bmName += "2";
				}
				
				entity.add(getReusableBitmapComponent(engine, bmName));
				entity.add(getRotateComponent(engine, 0));
				entity.add(getRotateInterpolationComponent(engine, 
					0,
					r.nextFloat() * 720 - 360,
					duration,
					Interpolation.EASE_IN
				));
				
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



	public static Entity spawnCellItem(PooledEngine engine, DejectSurface surface, int position, String itemType, float delay) {
		Entity entity = prepareCellEntity(engine, surface, position);
		
		ColorComponent color = getColorComponent(engine, 0, 255, 0, 0);
		color.getPaint().setAntiAlias(false);
		color.getPaint().setFilterBitmap(false);
		
		entity.add(color);
		
		ItemConfig itemConfig = GameConfig.getItemConfig(itemType);
		
		ItemComponent item = new ItemComponent(position, 1);
		item.setConfig(itemConfig);
		
		entity.add(item);
		entity.add(getReusableBitmapComponent(engine, itemConfig.getImage()));
		
		if (delay > 0) {
			engine.addEntity(entity, delay);
		} else {
			engine.addEntity(entity);
		}
		
		return entity;
	}



	public static void createStartScreen(PooledEngine engine, DejectSurface surface, float offsetY) {
		
		// leader board button

		
		Entity btnScore = engine.createEntity();
		
		btnScore.add(getRectComponent(engine, surface.dp2px(creepWidth), surface.dp2px(creepWidth)));
		btnScore.add(getColorComponent(engine, 255, 255, 0, 0));
		
		btnScore.add(getReusableBitmapComponent(engine, "btn_leaderboard"));
		
		float btnScoreX = (surface.widthDp / 2);
		float btnScoreY = (surface.heightDp / 2);
		
		
		PositionComponent posComp = engine.createComponent(PositionComponent.class);
		posComp.init(surface.dp2px(btnScoreX), surface.dp2px(btnScoreY + offsetY));
		
		btnScore.add(posComp);
		
		btnScore.add(new TouchComponent());
		btnScore.add(new TagComponent("btn_leaderboard"));
		
		btnScore.add(getPositionInterpolationComponent(engine, 
			btnScore.getComponent(PositionComponent.class), 
			surface.dp2px(surface.widthDp / 2 + surface.widthDp / 4),
			surface.dp2px(surface.heightDp / 2 + offsetY),
			0.7f,
			Interpolation.EASE_OUT
		));
		
		btnScore.setOrder(3);
		engine.addEntity(btnScore, 0.7f);
		
		// play button;
		
		Entity btnPlay = engine.createEntity();
		
		btnPlay.add(getRectComponent(engine, surface.dp2px(creepWidth), surface.dp2px(creepWidth)));
		btnPlay.add(getColorComponent(engine, 255, 255, 0, 0));
		
		btnPlay.add(getReusableBitmapComponent(engine, "btn_play"));
		
		float btnX = (surface.widthDp / 2);
		float btnY = (surface.heightDp);
		
		
		PositionComponent posComp2 = engine.createComponent(PositionComponent.class);
		posComp2.init(surface.dp2px(btnX), surface.dp2px(btnY));
		
		btnPlay.add(posComp2);
		
		btnPlay.add(new TouchComponent());
		btnPlay.add(new TagComponent("btn_play"));
		
		btnPlay.add(getPositionInterpolationComponent(engine, 
			btnPlay.getComponent(PositionComponent.class), 
			surface.dp2px(btnX),
			surface.dp2px(surface.heightDp / 2 + offsetY),
			0.7f,
			Interpolation.EASE_OUT
		));
		
		PositionComponent posComp3 = engine.createComponent(PositionComponent.class);
		posComp3.init(surface.dp2px(btnX), surface.dp2px(surface.heightDp / 2 + offsetY));
		
		btnPlay.add(getPositionInterpolationComponent(engine, 
			posComp3, 
			surface.dp2px(surface.widthDp / 2 - surface.widthDp / 4),
			surface.dp2px(surface.heightDp / 2 + offsetY),
			0.7f,
			Interpolation.EASE_OUT
		), 0.7f);
		
		btnPlay.setOrder(4);
		engine.addEntity(btnPlay);
		
		EntityFactory.btnPlay = btnPlay;
		EntityFactory.btnScore = btnScore;
		
	}



	public static void createFadeOut(PooledEngine engine, DejectSurface surface) {
		
		Entity entity = engine.createEntity();
		
		entity.add(new ScreenOverlayComponent());
		
		entity.add(getColorComponent(engine, 0, 0, 0, 0));
		
		entity.add(getColorInterpolationComponent(engine, 				
			entity.getComponent(ColorComponent.class).getPaint(),
			createPaint(255, 0, 0, 0),
			0.5f,
			Interpolation.EASE_OUT			
		));	
		
		entity.add(getExpireComponent(engine, 0.8f));
		
		engine.addEntity(entity);
		
		Entity exp = engine.createEntity();
		
		exp.add(getExpireComponent(engine, 0.5f));
		exp.add(new TagComponent("fade_out"));
		
		engine.addEntity(exp);
		
	}
	
	public static void createFadeIn(PooledEngine engine, DejectSurface surface) {
		
		Entity entity = engine.createEntity();
		
		entity.add(new ScreenOverlayComponent());
		
		entity.add(getColorComponent(engine, 255, 0, 0, 0));
		
		entity.add(getColorInterpolationComponent(engine, 				
			entity.getComponent(ColorComponent.class).getPaint(),
			createPaint(0, 0, 0, 0),
			0.5f,
			Interpolation.EASE_IN				
		));
		
		entity.add(getExpireComponent(engine, 0.5f));
		entity.add(new TagComponent("fade_in"));
		
		engine.addEntity(entity);
		
	}


	public static void btnPlayLevelUp(PooledEngine engine, DejectSurface surface) {
		
		btnLevelPlay.add(getPositionInterpolationComponent(engine, 
				
				btnLevelPlay.getComponent(PositionComponent.class),
				btnLevelPlay.getComponent(PositionComponent.class).x,
				surface.dp2px(-creepHeight * 2.5f),
				0.5f,
				Interpolation.EASE_IN
				
		));
		
		btnLevelPlay.add(getExpireComponent(engine, 0.6f));
	}

	public static Entity spawnLevelInfoPanel(PooledEngine engine,	DejectSurface surface, int level) {
		btnLevelPlay = engine.createEntity();
		
		btnLevelPlay.add(getRectComponent(engine, surface.dp2px(creepWidth), surface.dp2px(creepHeight)));
		
		btnLevelPlay.add(getColorComponent(engine, 255, 255, 0, 0));
		
		btnLevelPlay.add(getPositionComponent(engine, surface.dp2px(surface.widthDp / 2), surface.dp2px(-creepHeight * 2)));
		
		btnLevelPlay.add(getReusableBitmapComponent(engine, "btn_play"));
		
		btnLevelPlay.add(new TagComponent("levelInfo"));
		
		btnLevelPlay.add(new TouchComponent());
		
		btnLevelPlay.setOrder(5);
		
		engine.addEntity(btnLevelPlay);		
		
		btnLevelPlay.add(getPositionInterpolationComponent(engine, 
			
				btnLevelPlay.getComponent(PositionComponent.class),
				btnLevelPlay.getComponent(PositionComponent.class).x,
				surface.dp2px(surface.heightDp / 2 + surface.heightDp / 4),
				0.5f,
				Interpolation.EASE_OUT
				
		));
		
		
		Entity entity = engine.createEntity();
		
		
		PositionComponent posComp3 = engine.createComponent(PositionComponent.class);
		posComp3.init(surface.dp2px(levelPanelX), surface.dp2px(levelPanelY + levelPanelHeight));
		
		entity.add(posComp3);
		
		entity.add(getRectComponent(engine, 
			surface.dp2px(levelPanelWidth),
			surface.dp2px(levelPanelHeight)
		));
		
		entity.add(getColorComponent(engine, 255, 255, 0, 0));
		
		entity.add(new LevelInfoComponent(level));
		
		entity.add(new TouchComponent());
		entity.add(new TagComponent("levelInfo"));
		
		Bitmap levelBitmap = BitmapLibrary.getBitmap("level" + Integer.toString(level));
		
		if (levelBitmap != null) {
			entity.add(getBitmapComponent(engine, levelBitmap));
		}
		
		entity.setOrder(1);
		
		engine.addEntity(entity);		
		
		animateButtonsDown(engine);
		
		return entity;
	}
	
	public static void animateButtonsDown(PooledEngine engine) {
		
		for(int i = 0 ; i < buttons.size ; i++) {
			
			Entity button = buttons.get(i);
			
			PositionComponent position = button.getComponent(PositionComponent.class);
			RectDimensionComponent dimension = button.getComponent(RectDimensionComponent.class);
			
			button.add(getPositionInterpolationComponent(engine, 
				button.getComponent(PositionComponent.class),
				position.x,
				position.getOriginalY() + dimension.getHeight() * 3,
				0.5f,
				Interpolation.EASE_IN					
			), r.nextFloat() / 5);
			
		}
	}
	
	public static void animateButtonsUp(PooledEngine engine) {

		for(int i = 0 ; i < buttons.size ; i++) {
			
			Entity button = buttons.get(i);
			
			PositionComponent position = button.getComponent(PositionComponent.class);
			
			button.add(getPositionInterpolationComponent(engine, 
				button.getComponent(PositionComponent.class),
				position.x,
				position.getOriginalY(),
				0.5f,
				Interpolation.EASE_OUT					
			), r.nextFloat() / 5);
			
		}
	}



	public static void spawnLifeLose(PooledEngine engine, DejectSurface surface, int hPos) {
		
		float rX = lifeOffsetX + (lifeWidth / 2) + hPos*lifeWidth + 1*hPos;
		float rY = 0;
		
		for(int i = 0 ; i < 50 ; i++) {
			Entity entity = engine.createEntity();
			
			PositionComponent posComp = engine.createComponent(PositionComponent.class);
			posComp.init(surface.dp2px(rX), surface.dp2px(rY));
			
			entity.add(posComp);
			entity.add(getColorComponent(engine, 255, 255, 0, 0));
			entity.add(getRectComponent(engine, surface.dp2px(lifeWidth / 2 - r.nextFloat() * (lifeWidth / 3)), surface.dp2px(lifeHeight / 3)));
			
			
			PositionComponent posComp2 = engine.createComponent(PositionComponent.class);
			posComp2.init(surface.dp2px(rX + (r.nextInt((int)lifeWidth) - (lifeWidth / 2))), surface.dp2px(rY));
			
			entity.add(getPositionInterpolationComponent(engine, 
				posComp2,
				surface.dp2px(rX + (r.nextInt((int)lifeWidth) - (lifeWidth / 2))),
				surface.dp2px(rY + surface.heightDp / 2 - r.nextInt((int)surface.heightDp / 5)),
				0.5f,
				Interpolation.EASE_OUT
			));
			
			Paint tmpColor = createPaint(0, 255, 0, 0);
			
			entity.add(getColorInterpolationComponent(engine, 
				entity.getComponent(ColorComponent.class).getPaint(),
				tmpColor,
				0.5f,
				Interpolation.EASE_OUT
			));
			
			
			entity.add(getExpireComponent(engine, 0.5f));
		
			engine.addEntity(entity, r.nextFloat() / 2);
			
		}
		
	}


	
	public static void spawnGenericTouchReaction(PooledEngine engine, DejectSurface surface, Entity entity, String colorTxt) {
		
		if (! entity.hasComponent(ColorComponent.class)) return;
		
		float lifeTime = 0.3f;
		float zoomFactor = 1.5f;
		
		PositionComponent position = entity.getComponent(PositionComponent.class);
		RectDimensionComponent dimension = entity.getComponent(RectDimensionComponent.class);
		
		Entity proxy = engine.createEntity();
		
		PositionComponent pPos = engine.createComponent(PositionComponent.class);
		pPos.init(position.x, position.y);
		
		proxy.add(pPos);
		
		proxy.add(getRectComponent(engine, dimension.getWidth(), dimension.getHeight()));
		
		
		if (colorTxt.equalsIgnoreCase("red")) {
			proxy.add(getColorComponent(engine, 150, 255, 0, 0));
		} else {
			proxy.add(getColorComponent(engine, 150, 0, 255, 0));
		}
		
		proxy.add(new ZoomComponent(1f, 1f));
		
		proxy.add(new ZoomInterpolationComponent(
				dimension.getWidth(), 
				dimension.getHeight(), 
				dimension.getWidth() * zoomFactor, 
				dimension.getHeight() * zoomFactor, 
				lifeTime, 
				Interpolation.EASE_IN
		));
		
		proxy.add(getColorInterpolationComponent(engine, 
			proxy.getComponent(ColorComponent.class).getPaint(), 
			(colorTxt.equalsIgnoreCase("red") ? createPaint(0, 255, 0, 0) : createPaint(0, 0, 255, 0)), 
			lifeTime, 
			Interpolation.EASE_IN
		));
		
		proxy.add(getExpireComponent(engine, lifeTime));
		proxy.setOrder(4);
		
		if (colorTxt.equalsIgnoreCase("green")) {
			
			engine.addEntity(proxy, 0.5f);
			
		} else {
		
			engine.addEntity(proxy);
			
		}
		
	}
	
	public static void showPause(PooledEngine engine, DejectSurface surface) {
		

		Entity title = engine.createEntity();
		
		PositionComponent tposComp = engine.createComponent(PositionComponent.class);
		tposComp.init(surface.dp2px(goPanelcX), surface.dp2px(goPanelcY - goPanelcY / 5f));
		
		title.add(tposComp);
		title.add(getRectComponent(engine, surface.dp2px((goPanelWidth / 3) * 2), surface.dp2px(goPanelHeight / 3)));
		
		title.add(getColorComponent(engine, 255, 0, 0, 0));
		
		title.add(getReusableBitmapComponent(engine, "paused"));	
		

		title.setOrder(5);
		
		engine.addEntity(title);
		
		pausedEntity = title;
	}
	
	public static void hidePause(PooledEngine engine, DejectSurface surface) {
		
		if (pausedEntity != null) {
			engine.removeEntity(pausedEntity);
		}
		
	}
	
	public static void spawnGameOver(PooledEngine engine, DejectSurface surface) {
		Entity entity = engine.createEntity();
		
		PositionComponent posComp = engine.createComponent(PositionComponent.class);
		posComp.init(surface.dp2px(goPanelcX), surface.dp2px(surface.heightDp + goPanelHeight / 2));
		
		entity.add(posComp);
		entity.add(getRectComponent(engine, surface.dp2px(goPanelWidth), surface.dp2px(goPanelHeight)));
		
		entity.add(getColorComponent(engine, 0, 0, 0, 0));
		
		entity.add(new TouchComponent());
		
		entity.add(getReusableBitmapComponent(engine, "game_over"));
		
		entity.add(getPositionInterpolationComponent(engine, 
			
				entity.getComponent(PositionComponent.class),
				surface.dp2px(goPanelcX),
				surface.dp2px(goPanelcY),
				0.8f,
				Interpolation.EASE_IN				
				
		));
		
		entity.add(getColorInterpolationComponent(engine, 
			entity.getComponent(ColorComponent.class).getPaint(),
			createPaint(255, 0, 0, 0),
			0.8f,
			Interpolation.EASE_IN
		));

		entity.setOrder(2);
		
		
		
		gameOverEntity = entity;
		
		engine.addEntity(entity);
		
		createStartScreen(engine, surface, goPanelHeight / 2 -  creepHeight / 2 - 5);
		
		// labels
		
		float sX = surface.widthDp / 2;
		float sY = goPanelcY - (goPanelHeight / 6f);
		
		float sW = goPanelWidth;
		float sH = goPanelHeight / 4f;
		
		Entity scoreText = engine.createEntity();
		
		
		PositionComponent posComp2 = engine.createComponent(PositionComponent.class);
		posComp2.init(surface.dp2px(sX), surface.dp2px(sY));
		
		scoreText.add(posComp2);
		scoreText.add(getColorComponent(engine, 255, 211, 11, 11));
		scoreText.getComponent(ColorComponent.class).getPaint().setTypeface(surface.mFace);
		
		int scoreNum = engine.getSystem(ScoreSystem.class).getScoreEntity().getComponent(ScoreComponent.class).getScore();
		
		TextComponent textComp = new TextComponent(Integer.toString(scoreNum), surface.dp2px(sH));
		textComp.setAlign("center");
		
		scoreText.add(textComp);
		
		scoreText.add(getRectComponent(engine, surface.dp2px(sW), surface.dp2px(sH)));
		
		scoreText.setOrder(3);
		
		float scoreLife = Math.max(1f, scoreNum / 500);
		
		scoreText.add(new FontSizeInterpolationComponent(textComp.getHeight() / 5, textComp.getHeight(), 1f, Interpolation.EASE_IN));
		scoreText.add(new TextNumberInterpolationComponent(0, scoreNum, scoreLife, Interpolation.EASE_IN));
				
		scoreText.add(getPositionShakeComponent(engine, goPanelHeight / 40, goPanelHeight / 40, 0.3f), scoreLife);
		
		engine.addEntity(scoreText, 0.5f);
		
		
		
		scoreValueEntity = scoreText;
		
		
		// spawn gameover title
		
		Entity title = engine.createEntity();
		
		PositionComponent tposComp = engine.createComponent(PositionComponent.class);
		tposComp.init(surface.dp2px(goPanelcX), surface.dp2px(0));
		
		title.add(tposComp);
		title.add(getRectComponent(engine, surface.dp2px(goPanelWidth), surface.dp2px(goPanelHeight / 3)));
		
		title.add(getColorComponent(engine, 0, 0, 0, 0));
		
		title.add(getReusableBitmapComponent(engine, "game_over_title"));
		
		title.add(getPositionInterpolationComponent(engine, 
			
				title.getComponent(PositionComponent.class),
				surface.dp2px(goPanelcX),
				surface.dp2px(goPanelcY - goPanelcY / 1.5f),
				0.4f,
				Interpolation.EASE_IN				
				
		));
		
		title.add(getColorInterpolationComponent(engine, 
			title.getComponent(ColorComponent.class).getPaint(),
			createPaint(255, 0, 0, 0),
			0.4f,
			Interpolation.EASE_IN
		));

		title.setOrder(2);
		
		
		
		gameOverTitleEntity = title;
		
		engine.addEntity(title);
		
		/// rate buttom
		
		Entity rate = engine.createEntity();
		
		rate.add(getPositionComponent(engine, surface.dp2px(goPanelcX), surface.dp2px(surface.heightDp + creepHeight)));
		
		rate.add(getColorComponent(engine, 0, 255, 0, 0));
		
		
		rate.add(new TagComponent("btn_rate"));
		
		
		
		if (adButtonsConfig.get("btn_rate", "show").equalsIgnoreCase("show")) {		
			rate.add(getRectComponent(engine, surface.dp2px(creepWidth), surface.dp2px(creepHeight)));
			
			rate.add(new TouchComponent());
		}
		
		rate.add(getColorInterpolationComponent(engine, 
			rate.getComponent(ColorComponent.class).getPaint(),
			createPaint(255, 255, 0, 0),
			0.4f,
			Interpolation.EASE_IN
		));
		
		rate.add(getPositionInterpolationComponent(engine, 
			rate.getComponent(PositionComponent.class),
			surface.dp2px(goPanelcX),
			surface.dp2px(surface.heightDp - creepHeight),
			0.5f,
			Interpolation.EASE_OUT
		));
		
		rate.add(getPositionInterpolationComponent(engine, 
			getPositionComponent(engine, surface.dp2px(goPanelcX), surface.dp2px(surface.heightDp - creepHeight)),
			surface.dp2px(goPanelcX - surface.widthDp / 4),
			surface.dp2px(surface.heightDp - creepHeight),
			0.5f,
			Interpolation.EASE_IN
		), 0.5f);
		
		rate.add(getReusableBitmapComponent(engine, "btn_rate_2"));		
		
		
		rateBtnEntity = rate;
		
		engine.addEntity(rate);
		
		/// snake buttom
		
		Entity snake = engine.createEntity();
		
		snake.add(getPositionComponent(engine, surface.dp2px(goPanelcX), surface.dp2px(surface.heightDp + creepHeight)));
		
		snake.add(getColorComponent(engine, 0, 255, 0, 0));
		
		
		snake.add(new TagComponent("btn_snake"));
		
		
		if (adButtonsConfig.get("btn_snake", "show").equalsIgnoreCase("show")) {
			snake.add(getRectComponent(engine, surface.dp2px(creepWidth), surface.dp2px(creepHeight)));
			snake.add(new TouchComponent());
		}
		
		snake.add(getColorInterpolationComponent(engine, 
			snake.getComponent(ColorComponent.class).getPaint(),
			createPaint(255, 255, 0, 0),
			0.4f,
			Interpolation.EASE_IN
		), 0.5f);
		
		snake.add(getPositionInterpolationComponent(engine, 
			snake.getComponent(PositionComponent.class),
			surface.dp2px(goPanelcX),
			surface.dp2px(surface.heightDp - creepHeight),
			0.5f,
			Interpolation.EASE_OUT
		), 0.5f);
		
		snake.add(getPositionInterpolationComponent(engine, 
			getPositionComponent(engine, surface.dp2px(goPanelcX), surface.dp2px(surface.heightDp - creepHeight)),
			surface.dp2px(goPanelcX + surface.widthDp / 4),
			surface.dp2px(surface.heightDp - creepHeight),
			0.5f,
			Interpolation.EASE_IN
		), 1f);
		
		snake.add(getReusableBitmapComponent(engine, "btn_snake"));
		
		
		
		snakeBtnEntity = snake;
		
		engine.addEntity(snake);
		
	}
	
	public static void hideGameOverPanel(PooledEngine engine, DejectSurface surface) {
		
		if (gameOverEntity != null) {
			
			gameOverEntity.add(getPositionInterpolationComponent(engine, 
					gameOverEntity.getComponent(PositionComponent.class),
					surface.dp2px(goPanelcX),
					surface.dp2px(surface.heightDp + goPanelHeight / 2),
					0.5f,
					Interpolation.EASE_OUT					
			));
			
			gameOverEntity.add(getExpireComponent(engine, 0.5f));
			
			gameOverTitleEntity.add(getPositionInterpolationComponent(engine, 
					gameOverTitleEntity.getComponent(PositionComponent.class),
					surface.dp2px(goPanelcX),
					surface.dp2px(-goPanelHeight / 2),
					0.5f,
					Interpolation.EASE_OUT					
			));
			
			gameOverTitleEntity.add(getExpireComponent(engine, 0.5f));
			
			btnPlay.add(getPositionInterpolationComponent(engine, 
					btnPlay.getComponent(PositionComponent.class),
					btnPlay.getComponent(PositionComponent.class).x,
					surface.dp2px(surface.heightDp + creepHeight / 2),
					0.5f,
					Interpolation.EASE_OUT					
			));
			
			btnPlay.add(getExpireComponent(engine, 0.5f));
			
			rateBtnEntity.add(getPositionInterpolationComponent(engine, 
					rateBtnEntity.getComponent(PositionComponent.class),
					rateBtnEntity.getComponent(PositionComponent.class).x,
					surface.dp2px(surface.heightDp + creepHeight),
					0.5f,
					Interpolation.EASE_IN				
			));
			
			rateBtnEntity.add(getExpireComponent(engine, 0.5f));
			
			snakeBtnEntity.add(getPositionInterpolationComponent(engine, 
					snakeBtnEntity.getComponent(PositionComponent.class),
					snakeBtnEntity.getComponent(PositionComponent.class).x,
					surface.dp2px(surface.heightDp + creepHeight),
					0.5f,
					Interpolation.EASE_IN				
			));
			
			snakeBtnEntity.add(getExpireComponent(engine, 0.5f));
			
			btnScore.add(getPositionInterpolationComponent(engine, 
					btnScore.getComponent(PositionComponent.class),
					btnScore.getComponent(PositionComponent.class).x,
					surface.dp2px(surface.heightDp + creepHeight / 2),
					0.5f,
					Interpolation.EASE_OUT					
			));
			
			btnScore.add(getExpireComponent(engine, 0.5f));

		}
		
	}



	public static void spawnDefeatAnimation(PooledEngine engine, DejectSurface surface, Entity creep) {
		
		PositionComponent position = creep.getComponent(PositionComponent.class);
		RectDimensionComponent dimension = creep.getComponent(RectDimensionComponent.class);
		
		Entity entity = engine.createEntity();
		
		PositionComponent posComp = engine.createComponent(PositionComponent.class);
		posComp.init(position.x, position.y);
		
		entity.add(posComp);
		entity.add(getRectComponent(engine, dimension.getWidth(), dimension.getHeight()));
		entity.add(getColorComponent(engine, 255, 255, 0, 0));
		
		String headBmp = creep.getComponent(CreepComponent.class).getConfig().getImage() + "_head";
		
		
		if (BitmapLibrary.getBitmap(headBmp) != null) {
			entity.add(getReusableBitmapComponent(engine, headBmp));
		}
		
		entity.add(getRotateComponent(engine, 0f));
		
		entity.add(getColorInterpolationComponent(engine, 
			entity.getComponent(ColorComponent.class).getPaint(), 
			createPaint(0, 255, 0, 0), 
			1f, 
			Interpolation.EASE_IN
		));
		
		entity.add(getRotateInterpolationComponent(engine, 
			0f,
			r.nextFloat() * 720 - 360,
			1f,
			Interpolation.EASE_IN			
		));
		
		
		entity.add(getExpireComponent(engine, 1f));
		
		entity.add(new MovementComponent(surface.dp2px(r.nextInt((int)creepWidth * 2) - (int)(creepWidth)), -surface.dp2px(surface.heightDp * 1.2f)));
		
		entity.setOrder(-1);
		
		engine.addEntity(entity);
		
	}
	
	public static void createGalaxy(PooledEngine engine, DejectSurface surface) {
		
		
		GalaxyEmitterComponent emitter = new GalaxyEmitterComponent(
				surface.widthPx / 2,
				surface.heightPx / 3,
				surface.widthPx / 2,
				surface.widthPx / 2
		);
		
		emitter.setArms(r.nextInt(10));
		
		Entity entity = engine.createEntity();
		
		entity.add(emitter);
		entity.add(new GalaxyComponent());
		
		engine.addEntity(entity);
		
		galaxyEmitter = emitter;
		
	}
	
	public static ColorComponent getColorComponent(PooledEngine engine, int a, int r, int g, int b) {
		
		ColorComponent color = engine.createComponent(ColorComponent.class);
		
		color.init(a,  r, g, b);
		
		return color;
	}
	
	public static BitmapComponent getReusableBitmapComponent(PooledEngine engine, String bitmapName) {

		String key = "bitmap_" + bitmapName;
		
		BitmapComponent component = (BitmapComponent) reusableComponents.get(key);
		
		
		
		if (component == null) {
			
			component = getBitmapComponent(engine, BitmapLibrary.getBitmap(bitmapName));

			reusableComponents.put(key, (Component) component);
			
		}
		
		return component;
		
	}
	
	public static BitmapComponent getBitmapComponent(PooledEngine engine, Bitmap bitmap) {

		BitmapComponent bm = new BitmapComponent();
		bm.init(bitmap);
		
		return bm;
	}
	
	public static RectDimensionComponent getRectComponent(PooledEngine engine, int w, int h) {
		
		RectDimensionComponent dimension = engine.createComponent(RectDimensionComponent.class);
		
		dimension.init(w, h);
		
		return dimension;
		
	}
	
	public static ExpireComponent getExpireComponent(PooledEngine engine, float time) {
		
		ExpireComponent expire = engine.createComponent(ExpireComponent.class);
		expire.init(time);
		
		return expire;
		
	}
	
	public static ColorInterpolationComponent getColorInterpolationComponent(PooledEngine engine, Paint oldPaint, Paint endPaint, float speed, int type) {
		
		ColorInterpolationComponent interpolation = engine.createComponent(ColorInterpolationComponent.class);
		interpolation.init(oldPaint, endPaint, speed, type);
		
		return interpolation;
		
	}
	
	public static PositionInterpolationComponent getPositionInterpolationComponent(PooledEngine engine, PositionComponent position, float newX, float newY, float speed, int easing) {
		
		PositionInterpolationComponent interpolation = engine.createComponent(PositionInterpolationComponent.class);
		interpolation.init(position, newX, newY, speed, easing);
		
		return interpolation;
		
	}
	
	public static RectInterpolationComponent getRectInterpolationComponent(PooledEngine engine, float start_width, float end_width, float start_height, float end_height, float speed, int easing) {
		
		RectInterpolationComponent interpolation = engine.createComponent(RectInterpolationComponent.class);
		interpolation.init(start_width, end_width, start_height, end_height, speed, easing);
		
		return interpolation;
		
	}
	
	public static RotateComponent getRotateComponent(PooledEngine engine, float angle) {
		
		RotateComponent rotate = engine.createComponent(RotateComponent.class);
		rotate.init(angle);
		
		return rotate;
		
	}
	
	public static RotateInterpolationComponent getRotateInterpolationComponent(PooledEngine engine, float start_angle, float end_angle, float speed, int easing) {
		
		RotateInterpolationComponent interpolation = engine.createComponent(RotateInterpolationComponent.class);
		interpolation.init(start_angle, end_angle, speed, easing);
		
		return interpolation;
		
	}
	
	public static PositionShakeComponent getPositionShakeComponent(PooledEngine engine, float sX, float sY, float speed) {
		
		PositionShakeComponent shake = engine.createComponent(PositionShakeComponent.class);
		shake.init(sX, sY, speed);
		
		return shake;
		
		
	}
	
	public static PositionComponent getPositionComponent(PooledEngine engine, int pX, int pY) {
		
		PositionComponent position = engine.createComponent(PositionComponent.class);
		position.init(pX, pY);
		
		return position;
		
	}



	public static void createMultiplierEntity(PooledEngine engine) {
		
		timeScale = engine.createEntity();
		timeScale.add(new MultiplierComponent(1f));
		
		engine.addEntity(timeScale);
		
	}
	
	public static MultiplierInterpolationComponent getMultiplierInterpolationComponent(PooledEngine engine, float start, float end, float speed, int easing) {
		
		MultiplierInterpolationComponent interpolation = engine.createComponent(MultiplierInterpolationComponent.class);
		
		interpolation.init(start, end, speed, easing);
		
		return interpolation;
		
	}
	
	public static void clearSlowMo() {
		
		timeScale.delayedComponents.clear();
		timeScale.getComponent(MultiplierComponent.class).setMultiplier(1f);
		
	}
	
	public static void spawnArrowAnimation(PooledEngine engine, DejectSurface surface, int pos) {
		
		float keyDelay = 0.045f;
		
		float life = keyDelay * 5;
		
		Entity button = getPositionButton(pos);
		
		PositionComponent position = button.getComponent(PositionComponent.class);
		
		Entity proxy = engine.createEntity();
		
		PositionComponent pPos = engine.createComponent(PositionComponent.class);
		pPos.init(position.getOriginalX(), position.getOriginalY() - surface.dp2px(btnHeight / 1.3f) );
		
		proxy.add(pPos);
		
		proxy.add(getRectComponent(engine, surface.dp2px(creepWidth), surface.dp2px(creepHeight)));
		
		
		ColorComponent color = getColorComponent(engine, 255, 255, 0, 0);
		color.getPaint().setAntiAlias(false);
		color.getPaint().setFilterBitmap(false);
		
		proxy.add(color);
		
		proxy.add(getReusableBitmapComponent(engine, "arrow_f1"));
		
		proxy.add(new BitmapAnimationComponent(
			"arrow_f",
			4,
			(int)(keyDelay * 1000)
		));
		
		proxy.getComponent(BitmapAnimationComponent.class).setLoop(true);
		
		proxy.add(getExpireComponent(engine, life * 3));
		proxy.setOrder(4);
		engine.addEntity(proxy);
		
	}
	
	public static void startSlowMo(PooledEngine engine) {
		
		timeScale.delayedComponents.clear();
		
		timeScale.add(getMultiplierInterpolationComponent(engine, 1f, 0.1f, 0.1f, Interpolation.EASE_IN));
		timeScale.add(getMultiplierInterpolationComponent(engine, 0.1f, 0.3f, 0.3f, Interpolation.EASE_IN), 0.5f);
		timeScale.add(getMultiplierInterpolationComponent(engine, 0.3f, 0.1f, 0.1f, Interpolation.EASE_IN), 2f);
		
		timeScale.add(getMultiplierInterpolationComponent(engine, 0.1f, 0.5f, 0.3f, Interpolation.EASE_IN), 2.5f);
		timeScale.add(getMultiplierInterpolationComponent(engine, 0.5f, 0.1f, 0.1f, Interpolation.EASE_IN), 3f);
		
		timeScale.add(getMultiplierInterpolationComponent(engine, 0.2f, 1f, 0.5f, Interpolation.EASE_IN), 5f);
		
		

		
	}



	public static void processAdButton(PooledEngine engine,	DejectSurface surface, Entity entity) {
		
		entity.add(getPositionInterpolationComponent(engine,
			
			entity.getComponent(PositionComponent.class),
			entity.getComponent(PositionComponent.class).x,
			surface.dp2px(surface.heightDp + creepHeight),
			0.8f,
			Interpolation.EASE_OUT
				
		));
		
		Storage.setValue("adbutton_" + entity.getComponent(TagComponent.class).getTag(), "hidden");
		adButtonsConfig.put(entity.getComponent(TagComponent.class).getTag(), "hidden");
		
		String packageName = surface.context.getPackageName();
		
		if (entity.getComponent(TagComponent.class).getTag().equalsIgnoreCase("btn_snake")) {
			packageName = "pbartz.games.snake";
		}
		
		Uri uri = Uri.parse("market://details?id=" + packageName);
	    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
	    try {
	    	surface.context.startActivity(goToMarket);
	    } catch (ActivityNotFoundException e) {
	       
	    }
		
	}	
	
}