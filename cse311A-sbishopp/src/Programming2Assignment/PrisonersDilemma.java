/**
 * @author sophiebishopp
 *
 */
package Programming2Assignment;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PrisonersDilemma{
	
	public static void playGame(ArrayList<Player> players, int m, float p, int k) {
		int numGen = 1;
		
		//Loop for the number of generations: 
		for(int j = 0; j < k; ++j) {
			
			//Resetting payoffs between generations
			for(int in = 0; in < players.size(); ++in) {
				players.get(in).cumulativePayoff = 0; 
			}
			
			// Loop for each player playing every other player 
			for(int p1 = 0; p1 < (players.size()-1); ++p1) {
				for(int p2 = (p1+1); p2 < (players.size()); ++p2) {
					
					//Players for this round:
					Player player1 = players.get(p1);
					Player player2 = players.get(p2);
					
					//Keeping track of grudging
					Boolean p1Grudge = false; //if p2 defected --> true 
					Boolean p2Grudge = false; //if p1 defected --> true 
					
					//Keeping track of previous moves - from round before 
					String player1PrevMove = "";
					String player2PrevMove = "";
					
					//Number of games each pair of players will play 
					for(int g = 0; g < m; ++g) {
						
						//store previous moves
						if(g != 0) {
							player1PrevMove = player1.move;
							player2PrevMove = player2.move;
						}
						
						//Check if first game 
						if(g == 0) {
							if(player1.type == "T4T") {
								player1.move = "C"; 
							}
							if(player2.type == "T4T") {
								player2.move = "C";
							}
						}else {
							if(player1.type == "T4T") {
								player1.move = player2PrevMove;
							}
							if(player2.type == "T4T") {
								player2.move = player1PrevMove;
							}
						}
						
						//Player 1 possible types 
						if(player1.type == "G") {
							if(p1Grudge) {
								player1.move = "D";
								p2Grudge = true;
							}else {
								player1.move = "C";
							}
						}
						if(player1.type == "AC") {
							player1.move = "C";
						}
						if(player1.type == "AD") {
							player1.move = "D";
							p2Grudge = true;
						}
						
						//Player 2 possible types 
						if(player2.type == "G") {
							if(p2Grudge) {
								player2.move = "D";
								p1Grudge = true; 
							}else {
								player2.move = "C";
							}
						}
						if(player2.type == "AC") {
							player2.move = "C";
						}
						if(player2.type == "AD") {
							player2.move = "D";
							p1Grudge = true;
						}
						
						//Add payoffs for each game to cumulative payoff 
						int[] gamePayoff = getPayoff(player1.move, player2.move);
						player1.addPayoff(gamePayoff[0]);
						player2.addPayoff(gamePayoff[1]);
						
//						System.out.println("Game #: " + g);
//						System.out.println("Player1 Move: " + player1.move);
//						System.out.println("Player1 Payoff: " + player1.cumulativePayoff);
//						System.out.println("Player2 Move: " + player2.move);
//						System.out.println("Player2 Payoff: " + player2.cumulativePayoff);
					}
				}
			}
			//Calculate percentage/payoff of each type 
			float[] playerTypes = new float[4];
			int[] playerTypePayoffs = new int[4];
			for(int index = 0; index < players.size(); ++index) {
				if(players.get(index).type == "T4T") {
					playerTypes[0]++;
					playerTypePayoffs[0]+= players.get(index).cumulativePayoff;
				}else if(players.get(index).type == "G") {
					playerTypes[1]++;
					playerTypePayoffs[1]+= players.get(index).cumulativePayoff;
				}else if(players.get(index).type == "AC") {
					playerTypes[2]++;
					playerTypePayoffs[2]+= players.get(index).cumulativePayoff;
				}else {
					playerTypes[3]++;
					playerTypePayoffs[3]+= players.get(index).cumulativePayoff;
				}
			}
	
			//Percentages of player types 
			int percentT4T = (int)((playerTypes[0]/players.size())*100);
			int percentG = (int)((playerTypes[1]/players.size())*100);
			int percentAC = (int)((playerTypes[2]/players.size())*100);
			int percentAD = (int)((playerTypes[3]/players.size())*100);
			
			//Total Payoff per type 
			int totalPayoffT4T = playerTypePayoffs[0];
			int totalPayoffG = playerTypePayoffs[1];
			int totalPayoffAC = playerTypePayoffs[2];
			int totalPayoffAD = playerTypePayoffs[3];
			
			//Total payoffs of all types 
			int totalPayoff = playerTypePayoffs[0] + playerTypePayoffs[1] + playerTypePayoffs[2] + playerTypePayoffs[3];
			
			//Average payoff for each type of player 
			float averagePayoffT4T = playerTypePayoffs[0]/playerTypes[0];
			float averagePayoffG = playerTypePayoffs[1]/playerTypes[1];
			float averagePayoffAC = playerTypePayoffs[2]/playerTypes[2];
			float averagePayoffAD = playerTypePayoffs[3]/playerTypes[3];
			
			//Print Statements 
			System.out.println("Gen " + numGen + ": "  + "  T4T: " + percentT4T + "%" + "  G: " + percentG + "%" + "  AC: " + percentAC + "%" + "  AD: " + percentAD + "%");
			System.out.println("Gen " + numGen + ": "  + "  T4T: " + playerTypePayoffs[0] + "  G: " + playerTypePayoffs[1] + "  AC: " + playerTypePayoffs[2] + "  AD: " + playerTypePayoffs[3] + "  Total: " + totalPayoff);
			System.out.println("Gen " + numGen + ": "  + "  T4T: " + averagePayoffT4T + "  G: " + averagePayoffG + "  AC: " + averagePayoffAC + "  AD: " + averagePayoffAD);
			System.out.println();
			
			
			//--------------------Write data to files------------------------------ 
				//Code adapted from w3schools
			try {
				//Percentages of population by type 
			      FileWriter myPercentWriter = new FileWriter("PercentagesofPops250.txt", true);
			      BufferedWriter bufferedWriter = new BufferedWriter(myPercentWriter);
			      bufferedWriter.write("Gen: "+ new Integer(numGen).toString() + "\n");
			      bufferedWriter.write(new Integer(percentT4T).toString() + "\n");
			      bufferedWriter.write(new Integer(percentG).toString() + "\n");
			      bufferedWriter.write(new Integer(percentAC).toString() + "\n");
			      bufferedWriter.write(new Integer(percentAD).toString() + "\n");
			      bufferedWriter.write("\n");
			      bufferedWriter.close();
			      
			      //Total Payoff by type 
			      FileWriter myTotalPayoffByType = new FileWriter("TotalPayoffByType25.txt", true);
			      BufferedWriter bufferedWriter2 = new BufferedWriter(myTotalPayoffByType);
			      bufferedWriter2.write("Gen: "+ new Integer(numGen).toString() + "\n");
			      bufferedWriter2.write(new Integer(totalPayoffT4T).toString() + "\n");
			      bufferedWriter2.write(new Integer(totalPayoffG).toString() + "\n");
			      bufferedWriter2.write(new Integer(totalPayoffAC).toString() + "\n");
			      bufferedWriter2.write(new Integer(totalPayoffAD).toString() + "\n");
			      bufferedWriter2.write("\n");
			      bufferedWriter2.close();
			      
			      //Average Payoff By Type 
			      FileWriter myAveragePayoffByType = new FileWriter("AveragePayoffByType25.txt", true);
			      BufferedWriter bufferedWriter3 = new BufferedWriter(myAveragePayoffByType);
			      bufferedWriter3.write("Gen: "+ new Integer(numGen).toString() + "\n");
			      bufferedWriter3.write(String.valueOf(averagePayoffT4T) + "\n");
			      bufferedWriter3.write(String.valueOf(averagePayoffG) + "\n");
			      bufferedWriter3.write(String.valueOf(averagePayoffAC) + "\n");
			      bufferedWriter3.write(String.valueOf(averagePayoffAD) + "\n");
			      bufferedWriter3.write("\n");
			      bufferedWriter3.close();
			      
			      //TotalPayoff
			      FileWriter myTotalPayoff = new FileWriter("TotalPayoff25.txt", true);
			      BufferedWriter bufferedWriter4 = new BufferedWriter(myTotalPayoff);
			      bufferedWriter4.write("Gen: "+ new Integer(numGen).toString() + "\n");
			      bufferedWriter4.write(new Integer(totalPayoff).toString() + "\n");
			      bufferedWriter4.write("\n");
			      bufferedWriter4.close();
			      
			      System.out.println("Successfully wrote to files.");
			} catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			}
			//-----------------------End of writing data-----------------------------------
			
		//Set up for next generation 	
			//Remove bottom p% of players & replicate top percent 
			Collections.sort(players, new PlayerSortingComparator()); //sort players by cumulative payoff
			
			float pPercentOfPlayers = p*players.size();
			int changePopSize = (int)pPercentOfPlayers;
		
			for(int i = 0; i < changePopSize; ++i) {
				players.remove(0);	
				Player playerToCopy = players.get(players.size() - changePopSize);
				Player copy = new Player(playerToCopy.type);
				copy.cumulativePayoff = players.get(players.size() - changePopSize).cumulativePayoff;
				players.add(copy);
			}

		//End of one generation of game 
			numGen++;
		}
	} 
	
	//helper function for getting the payoff based on moves of players
	public static int[] getPayoff(String p1Move, String p2Move) {
		int[] payoffs = new int[2];
		
		if ((p1Move == "C") && (p2Move == "C")){
			payoffs[0] = 3;
			payoffs[1] = 3;
		}else if((p1Move == "C") && (p2Move == "D")) {
			payoffs[0] = 0;
			payoffs[1] = 5;
		}else if((p1Move == "D") && (p2Move == "C")) {
			payoffs[0] = 5;
			payoffs[1] = 0;
		}else {
			payoffs[0] = 1;
			payoffs[1] = 1;
		}
		return payoffs;
	}

	public static void main(String[] args) {
		//Input number of players (n), games (m) and generations (k)
		Scanner input = new Scanner(System.in);
		System.out.println("Input the number of players");
		int n = input.nextInt();
		System.out.println("Input the number of games to play");
		int m = input.nextInt();
		
		//Input number of generations 
		System.out.println("Input the number of generations you would like to play the game");
		int k = input.nextInt();
		
		//Input percentage of population you remove (p)
		System.out.println("Input percentage of population to remove each generation (in decimal)");
		float p = input.nextFloat();
			
				
		//Create an arrayList of players - percentage of population of each type = 25%
		ArrayList<Player> players = new ArrayList<Player>();
		for (int i = 0; i < n; ++i) {
			if(i%4 == 0) {
				players.add(new Player("T4T"));
			}else if (i%4 == 1) {
				players.add(new Player("G"));
			}else if(i%4 == 2) {
				players.add(new Player("AC"));
			}else {
				players.add(new Player("AD"));
			}
		}
		
		//Change Percentages of population: 
//		for(int i = 0; i < n/3; ++i) {
//			players.add(new Player("AC"));
//		}
//		for(int i = 0; i < n/3; ++i) {
//			players.add(new Player("AD"));
//		}
//		for(int i = 0; i < n/3; ++i) {
//			players.add(new Player("T4T"));
//		}
		
		playGame(players, m, p, k);
	}
}

