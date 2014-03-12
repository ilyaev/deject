package pbartz.games.deject.core;


public class Interpolation {

	final public static int LINEAR = 0;
	final public static int EASE_IN = 1;
	final public static int EASE_OUT = 2;
	
	public static float calculateCurrentValue(int type, float t, float b, float c, float d) {
		
		float factor = 0;
		
		switch (type) { 
			
			case Interpolation.LINEAR:
				factor = getLinearFactor(t, d);
				break;
				
			case Interpolation.EASE_IN:
				factor = getEaseInFactor(t, d);
				break;
				
			case Interpolation.EASE_OUT:
				factor = getEaseOutFactor(t, d);
				break;
			default: 
				factor = 0;
		}
		
		return c * factor + b;
	}
	
	public static float getEaseInFactor(float t, float d) {
		return (t/=d) * t;
	}
	
	public static float getEaseOutFactor(float t, float d) {
		return -(t/=d) * (t-2);
	}
	
	public static float getLinearFactor(float t, float d) {
		return (t / d);
	}

	
}