import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * @author Ben McAdams
 * 
 *	The Solve class is where my final calculations are done. The policy iteration and policy evaluation is done here
 *	
 */
public class Solve {
	public ArrayList<State> states;
	public ArrayList<Integer> targets;
	public boolean stochastic;
	public ArrayList<ArrayList<Integer>> powerSet;
	public String[][] grid;

	/**
	 * @param grid - the user defined grid of tiles
	 * @param targets - the integers we are trying to find
	 * @param stochastic - T if stochastic / F if deterministic
	 */
	public Solve(String[][] grid, ArrayList<Integer> targets, boolean stochastic){
		this.targets = targets;
		this.stochastic = stochastic;
		this.grid = grid;
		states = new ArrayList<State>();

		powerSet = powerset(targets);
		ArrayList<State> states = createStates(grid); //contain row, column, and empty carry section 
		HashMap<String, Action> state_to_action = new HashMap<String, Action>();
		HashMap<String, Double> state_to_util = new HashMap<String, Double>();

		ArrayList<Action> policy = new ArrayList<Action>();

		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++){
				policy.add(Action.left); //change to random
			}
		}

		for(ArrayList<Integer> set: powerSet){
			int policy_count = 0;
			for(State state: states){
				State s = new State(state.getRow(), state.getCol(), set);
				state_to_action.put(s.hashCode1(), policy.get(policy_count));
				state_to_util.put(s.hashCode1(), 0.0);
				this.states.add(s);
				policy_count++;
			}
		}
		
		for(String s: state_to_util.keySet()){
			System.out.println(s);
		}

		state_to_action = policyIteration(state_to_action, state_to_util);

		traversePolicy(grid, state_to_action, states);
	}

	/**
	 * @param grid - the user defined elements
	 * @param state_to_action - my policy table
	 * @param states - all the possible states we can be in
	 * 
	 * Prints out the best path to find every target we are looking for
	 */
	public void traversePolicy(String[][] grid, HashMap<String, Action> state_to_action, ArrayList<State> states){
		int row = 0, col = 0;
		ArrayList<Integer> carry = new ArrayList<Integer>();

		//get start state
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				if(grid[i][j].equals("1")){
					row = i;
					col = j;
					break;
				}
			}
		}

		int count = 0;

		System.out.println("Final Policy: ");
		for(State s: states){
			System.out.print(state_to_action.get(s.hashCode1()) + " ");

			if(count == grid[0].length - 1){
				System.out.println();
				count = 0;
			}else{
				count++;
			}
		}

		System.out.println();
		System.out.println("Start Coordinates: " + row + " " + col + "\n");
		while(!carry.containsAll(targets)){
			State state = new State(row, col, carry);
			Action direction = state_to_action.get(state.hashCode1());

			System.out.println(direction);

			if(direction.equals(Action.up)){
				row++;
			}else if(direction.equals(Action.down)){
				row--;
			}else if(direction.equals(Action.left)){
				col--;
			}else {
				col++;
			}

			if(targets.contains(Integer.parseInt(grid[row][col]))){
				carry.add(Integer.parseInt(grid[row][col]));
			}
		}
	}

	/**
	 * @param state_to_action - policy table all random
	 * @param state_to_util - utility table all set to 0
	 * @return - an updated utility table after delta converges
	 * 
	 * Calculates the best utility when given a policy.. 
	 */
	
	public HashMap<String, Action> policyIteration(HashMap<String, Action> state_to_action, HashMap<String, Double> state_to_util){
		boolean changed = true;
		int policyCount = 0;
		while(changed){
			state_to_util = policyEvaluation(state_to_action, state_to_util);
			changed = false;
			for(State state: states){
				double u = state_to_util.get(state.hashCode1());
				Action direction = state_to_action.get(state.hashCode1()); //policy defined direction
				ArrayList<SP> spList = possibleActions(state, direction); //list of possible actions and rewards if taken

				ArrayList<Action> actions = new ArrayList<Action>(); //list is used to go over all possible action.. order matters!!
				actions.add(Action.up);
				actions.add(Action.down);
				actions.add(Action.left);
				actions.add(Action.right);

				int action_index = 0;
				double up = 0;
				double down = 0;
				double left = 0;
				double right = 0;
				double u_prime = 0;
				for(SP sp: spList){
					u_prime = findUPrime(actions.get(action_index), u, state, state_to_util);
					//if(u_prime != -100000){
						if(actions.get(action_index).equals(Action.up)){
							up = (sp.getProbablility() * (sp.getReward() + (.9)*u_prime)); 
						}else if(actions.get(action_index).equals(Action.down)){
							down = (sp.getProbablility() * (sp.getReward() + (.9)*u_prime));
						}else if(actions.get(action_index).equals(Action.left)){
							left = (sp.getProbablility() * (sp.getReward() + (.9)*u_prime));
						}else{
							right = (sp.getProbablility() * (sp.getReward() + (.9)*u_prime));
						}	
					//}
					action_index++;
				}
				
				Action new_direction = null;
				double max = Math.max(up, Math.max(down, Math.max(left, right)));
				
				if(max == up){
					new_direction = Action.up;
				}else if(max == down){
					new_direction = Action.down;
				}else if(max == left){
					new_direction = Action.left;
				}else if(max == right){
					new_direction = Action.right;
				}
				
				if(!new_direction.equals(direction)){
					changed = true;
				}
				
				state_to_action.put(state.hashCode1(), new_direction);
			}
			
			int count = 0;
			for(State s: states){
				System.out.print(state_to_action.get(s.hashCode1()) + " ");

				if(count == grid[0].length - 1){
					System.out.println();
					count = 0;
				}else{
					count++;
				}
			}
			System.out.println("-----");
			
			policyCount++;
			
			if(policyCount == 100){ //not supposed to be here... loop never stops
				break;
			}
		}
		System.out.println("Policys created: " + policyCount);
		
		return state_to_action;
	}
	
	//helper method to create a clean temporary utility table
	private HashMap<String, Action> createTemp( HashMap<String, Action> state_to_action){
		HashMap<String, Action> temp = new HashMap<String, Action>();
		for(String i: state_to_action.keySet()){
			temp.put(i, null);
		}

		return temp;
	}

	/**
	 * @param state_to_action - policy table
	 * @param state_to_util - utility table
	 * 
	 * @return- a policy
	 * 
	 * Computes the best policy table and returns in so we can find the best path
	 */
	public HashMap<String, Double> policyEvaluation(HashMap<String, Action> state_to_action, HashMap<String, Double> state_to_util){
		double delta = 1, theta = .1;
		int count = 0;

		//while(count < 20){
		while(delta >= theta){
			HashMap<Integer, Double> temp = new HashMap<Integer, Double>(); //createTemp(state_to_util);
			delta = 0;
			count++;
			for(State state: states){
				double u = state_to_util.get(state.hashCode1()); //u
				double u_new = 0.0; //U(s)
				Action direction = state_to_action.get(state.hashCode1()); //policy defined direction
				ArrayList<SP> spList = possibleActions(state, direction); //list of possible actions and rewards if taken

				ArrayList<Action> actions = new ArrayList<Action>(); //list is used to go over all possible action.. order matters!!
				actions.add(Action.up);
				actions.add(Action.down);
				actions.add(Action.left);
				actions.add(Action.right);

				int action_index = 0;
				double u_prime = 0;
				for(SP sp: spList){
					u_prime = findUPrime(actions.get(action_index), u, state, state_to_util);
					//if(u_prime != -100000){
						u_new += (sp.getProbablility() * (sp.getReward() + (.9)*u_prime)); 
						//System.out.println("new u: " + u_new + " Pro");
					//}
					action_index++;
				}

				state_to_util.put(state.hashCode1(), u_new);
				delta = Math.max(delta, Math.abs(u_new - u)); 

			}
			
			//System.out.println("Delta: " + delta); //prints out biggest change
			//System.out.println();
		}

		return state_to_util;
	}

	/**
	 * @param state - a single state element
	 * @param direction - direction of given state
	 * @param state_to_action - policy table
	 * @param state_to_util - utility table
	 * 
	 * @return a single probability
	 * 
	 * This class is run 4 times, one for each direction. 
	 * It doesnt actually compare anything but is only used to find the best one of the 4 runs
	 */

	private double findUPrime(Action direction, double u,State state, HashMap<String, Double> state_to_util){
		double u_prime = 0.0;
		int row = state.getRow();
		int col = state.getCol();

		if(direction.equals(Action.up)){
			row++;
		}else if(direction.equals(Action.down)){
			row--;
		}else if(direction.equals(Action.left)){
			col--;
		}else {
			col++;
		}

		if(col < 0 || row < 0 || col > grid[0].length - 1|| row > grid.length - 1){ //hit wall
			return u;
		}else{
			//find the next utility 
			State s = new State(row, col, state.getCarry());
			u_prime = state_to_util.get(s.hashCode1());				
		}

		return u_prime;
	}

	//can choose between a stochastic and a deterministic run
	private ArrayList<SP> possibleActions(State state, Action direction){
		ArrayList<SP> spList = new ArrayList<SP>();

		if(stochastic){
			spList = chooseVersion(state, direction, 0.7, 0.1);
		}else{
			spList = chooseVersion(state, direction, 1.0, 0.0);
		}

		return spList;
	}

	//depending on the direction the policy says, set the high probability this way and the lows the other ways
	private ArrayList<SP> chooseVersion(State state, Action direction, double high, double low){
		ArrayList<SP> spList = new ArrayList<SP>();

		if(direction.equals(Action.up)){
			spList.add(new SP(state, high, findReward(state, Action.up)));
			spList.add(new SP(state, low, findReward(state, Action.down)));
			spList.add(new SP(state, low, findReward(state, Action.left)));
			spList.add(new SP(state, low, findReward(state, Action.right)));
		}else if(direction.equals(Action.down)){
			spList.add(new SP(state, low, findReward(state, Action.up)));
			spList.add(new SP(state, high, findReward(state, Action.down)));
			spList.add(new SP(state, low, findReward(state, Action.left)));
			spList.add(new SP(state, low, findReward(state, Action.right)));
		}else if(direction.equals(Action.left)){
			spList.add(new SP(state, low, findReward(state, Action.up)));
			spList.add(new SP(state, low, findReward(state, Action.down)));
			spList.add(new SP(state, high, findReward(state, Action.left)));
			spList.add(new SP(state, low, findReward(state, Action.right)));
		}else{
			spList.add(new SP(state, low, findReward(state, Action.up)));
			spList.add(new SP(state, low, findReward(state, Action.down)));
			spList.add(new SP(state, low, findReward(state, Action.left)));
			spList.add(new SP(state, high, findReward(state, Action.right)));
		}

		return spList;
	}

	//my reward function
	private double findReward(State state, Action direction){

		int row = state.getRow();
		int col = state.getCol();
		String value = grid[row][col];

		if(state.getCarry().equals(targets)){
			return 0;
		}else{

			if(direction.equals(Action.up)){
				row++;
			}else if(direction.equals(Action.down)){
				row--;
			}else if(direction.equals(Action.left)){
				col--;
			}else {
				col++;
			}

			if(col < 0 || row < 0 || col > grid[0].length - 1|| row > grid.length - 1){ //hit wall
				return -1;
			}

			value = grid[row][col];
			if(value.equals("1") || value.equals("0")){ //empty tile
				return -1;
			}else if(value.equals("-1")){ //hit obstacle
				return -1;
			}else if(targets.contains(Integer.parseInt(value))){ //we found a potential target
				if(state.getCarry().contains(Integer.parseInt(value))){ //find target we already have
					return -1;
				}else{  //find target we don't have
					return 10; 
				}
			}
		}

		return -1; // we are on an object we don't care about
	}

	//creates all of the possible states when given a grid
	public ArrayList<State> createStates(String[][] grid){
		ArrayList<State> states = new ArrayList<State>();

		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				states.add(new State(i, j, new ArrayList<Integer>()));
			}
		}

		return states;	
	}

	//creates the power set of the targets we can possibly find
	public static ArrayList<ArrayList<Integer>> powerset(ArrayList<Integer> list) {
		ArrayList<ArrayList<Integer>> ps = new ArrayList<ArrayList<Integer>>();
		ps.add(new ArrayList<Integer>());   // add the empty set

		// for every item in the original list
		for (Integer item : list) {
			ArrayList<ArrayList<Integer>> newPs = new ArrayList<ArrayList<Integer>>();

			for (ArrayList<Integer> subset : ps) {
				// copy all of the current powerset's subsets
				newPs.add(subset);

				// plus the subsets appended with the current item
				ArrayList<Integer> newSubset = new ArrayList<Integer>(subset);
				newSubset.add(item);
				newPs.add(newSubset);
			}

			// powerset is now powerset of list.subList(0, list.indexOf(item)+1)
			ps = newPs;
		}
		return ps;
	}

	//creates the policy table
	public ArrayList<Action> createActions(String[][] grid){
		ArrayList<Action> actions = new ArrayList<Action>();

		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				int random = (int) Math.floor(Math.random() * 4);
				if(random == 0){
					actions.add(Action.up);
				}else if(random == 1){
					actions.add(Action.down);
				}else if(random == 2){
					actions.add(Action.left);
				}else{
					actions.add(Action.right);
				}	
			}
		}
		return actions;	
	}

	//calculates a random action for the initial policy
	public Action randomAction(){

		int random = (int) Math.floor(Math.random() * 4);
		if(random == 0){
			return Action.up;
		}else if(random == 1){
			return Action.down;
		}else if(random == 2){
			return Action.left;
		}
		return Action.right;
	}

	//creates the utility table... not using anymore
	public Hashtable<ArrayList<Integer>, ArrayList<Double>> createUtilities(String[][] grid){

		Hashtable<ArrayList<Integer>, ArrayList<Double>> ht = new Hashtable<ArrayList<Integer>, ArrayList<Double>>();
		for(ArrayList<Integer> set: powerSet){
			ht.put(set, emptyUtilTable(grid));
		}

		return ht;
	}

	//creates a utility table... not using anymore
	private ArrayList<Double> emptyUtilTable(String[][] grid){
		ArrayList<Double> utilities = new ArrayList<Double>();

		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				utilities.add(0.0);
			}
		}

		return utilities;
	}

}
