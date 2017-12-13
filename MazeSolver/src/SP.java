
public class SP {
	public State state;
	public double probablility, reward;
	
	public SP(State state, Double probablility, Double reward){
		this.state = state;
		this.probablility = probablility;
		this.reward = reward;
	}

	public double getReward() {
		return reward;
	}

	public void setReward(double reward) {
		this.reward = reward;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public double getProbablility() {
		return probablility;
	}

	public void setProbablility(double probablility) {
		this.probablility = probablility;
	}
}
