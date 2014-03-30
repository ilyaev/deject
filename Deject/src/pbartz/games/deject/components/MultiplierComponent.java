package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class MultiplierComponent extends Component {
	
	float multiplier = 1;
	
	
	public MultiplierComponent(float multiplier) {
		this.multiplier = multiplier;
	}


	public float getMultiplier() {
		return multiplier;
	}


	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}
	
	

}