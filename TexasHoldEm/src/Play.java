
public class Play {
	public static int hands;
	public static boolean verbose;
	public static boolean rational;
	
	public static void main(String[] args) {
		//can i just use a new deck of cards each round??
		if(!validArguments(args)){
			System.out.println("Incorrect Parameters - Options: (#) (r, n) (v, *leave blank*)");
			return;
		}
		
		play();
	}

	public static void play(){
		int p1_wins = 0, p2_wins = 0, p1_earnings = 0, p2_earnings = 0;

		for(int i = 1; i < hands + 1; i++){
			if(verbose){
				System.out.println("------------------------");
				System.out.println("--------HAND-"+ i +"----------");
				System.out.println("------------------------");
			}
			
			Expectiminimax emm = new Expectiminimax(rational, verbose, i);	

			//save some variables for the final results below
		}

		int wins = 0;
		if(String.valueOf(p1_wins).length() > String.valueOf(p2_wins).length()){
			wins = String.valueOf(p1_wins).length();
		}else{
			wins = String.valueOf(p2_wins).length();
		}

		System.out.println("------------------------");
		System.out.println("--------Results---------");
		System.out.println("------------------------");
		System.out.format("Player 1: "+ stringify(p1_wins, wins) + " wins, " + stringify(p2_wins, wins) + " loss. " + "Earnings = " + p1_earnings + "\n" );
		System.out.format("Player 2: "+ stringify(p2_wins, wins) + " wins, " + stringify(p1_wins, wins) + " loss. " + "Earnings = " + p2_earnings + "\n" );

	}


	public static boolean validArguments(String[] args){
		if(args.length != 2 && args.length != 3){
			return false;
		}

		if(isInteger(args[0])){
			hands = Integer.parseInt(args[0]);
		}else{
			return false;
		}

		if(args[1].equals("r")){
			rational = true;
		}else if(args[1].equals("n")){
			rational = false;
		}else{
			return false;
		}

		if(args.length == 3){
			if(args[2].equals("v")){
				verbose = true;
			}else{
				return false;
			}
		}

		return true;
	}

	public static boolean isInteger( String input ) {
		try {
			Integer.parseInt( input );
			return true;
		}
		catch( Exception e ) {
			return false;
		}
	}

	public static String stringify(String string, int length) {
		return String.format("%1$"+length+ "s", string);
	}

	public static String stringify(int digit, int length) {
		return String.format("%1$"+length+ "d", digit);
	}
}
