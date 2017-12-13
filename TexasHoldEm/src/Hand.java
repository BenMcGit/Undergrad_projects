/*CS 110 Sample Program
 * Arrays of objects
 * determine poker hands*/

import java.util.Scanner;
import java.util.Arrays;

public class Hand
{
	public int finalScore;
	public int highCard;
	public int secondHigh;
	
  public Hand(Card[] card)
  {   
	  
	  ProbCard [] hand = new ProbCard [5];
	  highCard = 0;
	  for(int i = 0; i < 5; i++){
		  String suit = card[i].getSuit();
		  String rank = card[i].getRank();
		  int newRank = 0;
		  ProbCard.Suits s = null;
		  
		  if(suit.equals("Hearts")){
			  s = ProbCard.Suits.hearts;
		  }else if(suit.equals("Spades")){
			  s = ProbCard.Suits.spades;
		  }else if(suit.equals("Clubs")){
			  s = ProbCard.Suits.clubs;
		  }else{
			  s = ProbCard.Suits.diamonds;
		  }
		  
		  if(rank.equals("Ace")){
			  newRank = 1;
		  }else if(rank.equals("2")){
			  newRank = 2;
		  }else if(rank.equals("3")){
			  newRank = 3;
		  }else if(rank.equals("4")){
			  newRank = 4;
		  }else if(rank.equals("5")){
			  newRank = 5;
		  }else if(rank.equals("6")){
			  newRank = 6;
		  }else if(rank.equals("7")){
			  newRank = 7;
		  }else if(rank.equals("8")){
			  newRank = 8;
		  }else if(rank.equals("9")){
			  newRank = 9;
		  }else if(rank.equals("10")){
			  newRank = 10;
		  }else if(rank.equals("Jack")){
			  newRank = 11;
		  }else if(rank.equals("Queen")){
			  newRank = 12;
		  }else if(rank.equals("King")){
			  newRank = 13;
		  }
		  
		  hand[i] = new ProbCard(s,newRank);
		  if(newRank > highCard){
			  secondHigh = highCard;
			  highCard = newRank;
		  }
	  }
    
    if (royalFlush(hand)) this.finalScore = 10;
        else if (straightFlush(hand)) this.finalScore = 9;
                 else if (straight(hand)) this.finalScore = 8;
                          else if (flush(hand)) this.finalScore = 7;
                              else if (fullHouse (hand)) this.finalScore = 6;
                                  else if (fourKind(hand)) this.finalScore = 5;
                                    else if (threeKind(hand)) this.finalScore = 4;
                                       else if (twoKind(hand)) this.finalScore = 3;
                                           else this.finalScore = 1;
  }
  
  public int getFinalScore(){
	  return this.finalScore;
  }
  
  public boolean hasCardHigherThan(Hand p2){
	  if(this.highCard > p2.highCard){
		  return true;
	  }if(this.highCard == p2.highCard){
		  if(this.secondHigh > p2.secondHigh){
			  return true;
		  }if(this.secondHigh == p2.secondHigh){
			  return true; // have to change
		  }else{
			  return false;
		  }
	  }else{
		  return false;
	  }	  
  }
  
  public static boolean royalFlush (ProbCard [] hand)
  {
    boolean rf = false;
    int [] faces = new int [5];
    
    if (flush(hand))
    {
      for (int i =0; i < 5; i++)
        faces[i] = hand[i].face();
      
      Arrays.sort(faces);
      if (faces[0] == 1 && faces[1] == 10 && faces[2] ==11 && faces[3] == 12 && faces[4] == 13) rf= true;
    }
    
    return rf;
  }
  
  public static boolean straight (ProbCard [] hand)
  {   boolean st = true;
      
      int [] faces = new int[5];
      
      for (int i =0; i < 5; i++)
        faces[i] = hand[i].face();
      
      Arrays.sort(faces);
      for (int i=0; st && i<4;i++)
        if (faces[i+1]-faces[i] != 1) st = false;
      
      return st;
  }
  
  public static boolean straightFlush (ProbCard [] hand)
  {
    return (flush(hand) && straight(hand));
  }
            
  public static boolean flush (ProbCard [] hand)
  {  boolean isFlush = true;
     ProbCard firstOne = new ProbCard(hand[0].suit(), hand[0].face());
     
     for (int i = 1; isFlush && i<hand.length;i++)
       if (!firstOne.sameSuit(hand[i])) isFlush=false;
     
     return isFlush;
  }
  
  public static boolean fullHouse (ProbCard [] hand)
  {
    return (threeKind(hand) && twoKind(hand));
  }
  
  public static boolean fourKind(ProbCard [] hand)
  {
    int count =0;
    
    for (int i=0; count != 3 && i<hand.length;i++)
    { count =0;
      for (int j=0;j<hand.length;j++)
        if (hand[i].sameFace(hand[j]) && i!=j) count++;
    }
    
    return (count==3);
  }
  
  public static boolean threeKind(ProbCard [] hand)
  {
    int count =0;
    
    for (int i=0; count !=2 && i<hand.length;i++)
    { count =0;
      for (int j=0;j<hand.length;j++)
        if (hand[i].sameFace(hand[j]) && i!=j) count++;
    }
    
    return (count==2);
  }
  
  public static boolean twoKind(ProbCard [] hand)
  {
    int count =0;
    
    for (int i=0; count != 1 && i<hand.length;i++)
    { count =0;
      for (int j=0;j<hand.length;j++)
        if (hand[i].sameFace(hand[j]) && i!=j) count++;
    }
    
    return (count==1);
  }
  
}
  