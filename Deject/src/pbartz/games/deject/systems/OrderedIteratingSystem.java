package pbartz.games.deject.systems;

import android.util.Log;
import pbartz.games.deject.core.Engine;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.EntitySystem;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.utils.Array;
import pbartz.games.deject.utils.IntMap;
import pbartz.games.deject.utils.IntMap.Keys;

public abstract class OrderedIteratingSystem extends EntitySystem {

	private Family family;
	private IntMap<Entity> entities;
	
	public OrderedIteratingSystem(Family family){
		this(family, 0);
	}
	
	public OrderedIteratingSystem(Family family, int priority){
		super(priority);
		
		this.family = family;
	}
		
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(family);
		
		if (this.engine == null) {
			this.engine = engine;
		}
	}

	@Override
	public void removedFromEngine(Engine engine) {
		entities = null;
	}

	@Override
	public void update(float deltaTime) {

		Keys keys = entities.keys();
		
		Array<Integer> orders = new Array<Integer>();
		
		while(keys.hasNext) {
			int order = entities.get(keys.next()).order;
			if (orders.indexOf(order, true) == -1) {
				orders.add(order);
			}
		}
		
		if (orders.size > 1) {
			orders.sort();
		}
		
		for(int i = 0 ; i < orders.size ; i++) {

			int zIndex = orders.get(i);

			keys.reset();
			
			while(keys.hasNext){
				Entity entity = entities.get(keys.next());
				if (entity.order == zIndex) {
					processEntity(entity, deltaTime);
				}
			}
		}
		
	}

	public abstract void processEntity(Entity entity, float deltaTime);
}
