package pbartz.games.deject.core;

/**
 * Base class for all Components. A Component is intended as a data holder and provides data to be processed
 * in a {@link EntitySystem}. But do as you wish.
 * 
 * @author Stefan Bachmann
 */
public class Component {
	
	private int timeToAppear = 0;

	public void setTimeToAppear(int timeToAppear) {
		this.timeToAppear = timeToAppear;
	}
	
	public boolean isTimeToAppear(int diff) {
		timeToAppear = Math.max(0, timeToAppear - diff);
		return timeToAppear == 0 ? true : false;
	}
	
}
