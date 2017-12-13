package def;
public class Driver
{
    public Driver()
    {
        clearScreen();
        AngryBirdsApp abApp = new AngryBirdsApp();
    }

    private void clearScreen()
    {
        System.out.print('\u000C');
    }    

    // PLEASE DO NOT EDIT THE FOLLOWING LINES OF CODE
    // THESE ARE MERELY THERE SO I CAN GRADE YOUR 
    // ASSIGNMENTS WITH EASE. 
    // THE FOLLOWING CODE IS BEYOND THE SCOPE OF THIS CLASS
    public static void main( String[] args )
    {
        Driver d = new Driver();
    }
}