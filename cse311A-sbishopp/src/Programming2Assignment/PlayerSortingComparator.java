package Programming2Assignment;

import java.util.Comparator;

//Code below adapted from geeksforgeeks.org/comparator-interface-java/
public class PlayerSortingComparator implements Comparator<Player>{

	@Override
	public int compare(Player p1, Player p2) {
		return p1.cumulativePayoff - p2.cumulativePayoff;
	}

}
