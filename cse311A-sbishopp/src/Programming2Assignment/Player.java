package Programming2Assignment;

import java.util.Comparator;

public class Player {
	String type; //Possible types = T4T, G, AC, AD
	String move; //Possible moves = Cooperate == "C", Defect == "D"
	int cumulativePayoff;
	
	public Player(String type) {
		this.type = type;
		this.move = "";
		this.cumulativePayoff = 0;
	}
	
	public void addPayoff(int payoff) {
		this.cumulativePayoff += payoff; 
	}
}
