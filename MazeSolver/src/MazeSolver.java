import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Ben McAdams
 * 
 * MaseSolver is the driver of this program. It creates the initial grid and sets up the policy evaluation/iteration
 *
 */
public class MazeSolver {
	static ArrayList<Integer> targets;
	
	public static void main(String[] args) {
		String[][] grid = null;
		targets = new ArrayList<Integer>();
		
		int c = Integer.parseInt(args[1]);
		if(args.length == 2 && args[0].contains(".txt") && c > 0){
			System.out.println("Loading file \""+ args[0]+ "\"\n");
			grid = buildGrid(args[0], c);	
		}else{
			System.out.println("Invalid parameter. Try again. ");
			return;
		}
		
		System.out.println("Deterministic MDP. \n");
		printTargets();
		printArray(grid);
		new Solve(grid, targets, false); //Deterministic
		System.out.println("_________________________________________________________________________\n");
		//System.out.println("Stochastic MDP. \n");
		//printTargets();
		//printArray(grid);
		//new Solve(grid, targets, true); //stochastic
		//System.out.println("_________________________________________________________________________");
	}
	
	
	/**
	 * @param filename - the name of the user defined file
	 * @param target_max - the amount of items we are looking for
	 * 
	 * @return the grid of items we will be earching through
	 */
	private static String[][] buildGrid(String filename, int target_max){
		String[][] grid = null;
		int height, width = 0;
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String line = br.readLine();
			
			//get width and height
			String[] dimensions = line.split(" ");
			height = Integer.parseInt(dimensions[0]);
			width = Integer.parseInt(dimensions[1]);
			grid = new String[height][width];
			
			//build grid
			for(int i = 0; i < height; i++){
				line = br.readLine();
				String[] tiles = line.trim().split("\\s+");
				for(int j = 0; j < width; j++){
					if(!tiles[j].equals("1") && !tiles[j].equals("-1") && !tiles[j].equals("0")){
						targets.add(Integer.parseInt(tiles[j]));
					}
					grid[i][j] = tiles[j];
				}
			}
	
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//remove items in target we dont need
		Collections.sort(targets, Collections.reverseOrder());
		for(int i = 0; i < targets.size(); i++){
			if(target_max == 0){
				targets.remove(i);
			}else{
				target_max--;
			}
		}
				
		return grid;
	}
	
	private static void printArray(String matrix[][]) {
		for (String[] row : matrix) 
			System.out.println(Arrays.toString(row));       
	}
	
	private static void printTargets(){
		 System.out.print("Targets: [ ");
		for(Integer s: targets){
			System.out.print(s + " ");
		}
		System.out.println("]");
		System.out.println();
	}
		
}
