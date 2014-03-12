package pbartz.games.deject.core;

import java.util.BitSet;

import pbartz.games.deject.utils.ObjectMap;

/**
 * A family represents a group of components. It is used to describe what entities a system
 * should process. 
 * 
 * Example: Family.getFamilyFor(PositionComponent.class, VelocityComponent.class)
 * 
 * Families can't be instantiate directly but must be accessed via Family.getFamilyFor(), this is
 * to avoid duplicate families that describe the same components.
 * 
 * @author Stefan Bachmann
 */
public class Family {
	/** The hashmap holding all families */
	private static ObjectMap<String, Family> families = new ObjectMap<String, Family>();
	private static int familyIndex = 0;
	
	/** A bitset used for quick comparison between families & entities */
	private final BitSet bits;
	/** Each family has a unique index, used for bitmasking */
	private final int index;
	
	/** Private constructor, use static method Family.getFamilyFor() */
	private Family(BitSet bits){
		this.bits = bits;
		this.index = familyIndex++;
	}
	
	/**
	 * Returns this family's unique index
	 */
	public int getFamilyIndex(){
		return this.index;
	}
	
	/**
	 * Checks if the passed entity matches this family's requirements.
	 * @param entity The entity to check for matching
	 * @return Whether the entity matches or not
	 */
	public boolean matches(Entity entity){
		BitSet entityComponentBits = entity.getComponentBits();
		
		if(entityComponentBits.isEmpty())
			return false;
		
		for (int i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i+1)){
			if(!entityComponentBits.get(i))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Returns a family with the passed componentTypes as a descriptor. Each set of component types will
	 * always return the same Family instance.
	 * @param componentTypes The components to describe the family
	 * @return The family
	 */
	@SafeVarargs
	public static Family getFamilyFor(Class<? extends Component> ...componentTypes){
		BitSet bits = new BitSet();
		
		for(int i=0; i<componentTypes.length; i++){
			bits.set(ComponentType.getIndexFor(componentTypes[i]));	
		}
		
		String hash = bits.toString();
		Family family = families.get(hash, null);
		if(family == null){
			family = new Family(bits);
			families.put(hash, family);
		}
		
		return family;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Family){
			if(obj == this)
				return true;
			else return bits.equals(((Family)obj).bits);
		}
		return false;
	}
}
