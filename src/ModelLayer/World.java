package ModelLayer;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class World extends Observable implements Runnable {

	private ArrayList<Tree> trees;
	private Random random;
	private boolean play;

	public World() {
		play = false;
		random = new Random();
		trees = new ArrayList<Tree>();
	}

	public void run() {
		while (true) {
			// if play is true the trees move
			if (play) {
				for (Tree t : trees) {
					t.move();
				}
				this.setChanged();
				this.notifyObservers();
				try {
					// sleep for 42 miliseconds makes 24 fps
					Thread.sleep(42);
				} catch (InterruptedException e) {
				}
			} else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public void addRandomTree(String treeType) {
		String size = "";
		String type = treeType;
		int relX = 0;
		int relY = 0;
		int randomInt = random.nextInt(2);
		
		// give tree a random type
		if (treeType.equals("random")) {
			if (randomInt == 0) {
				type = "PINE";
			} else {
				type = "LEAF";
			}
		}
		else if (treeType.equals("PINE")) {
			type = "PINE";
		}
		else if (treeType.equals("LEAF")) {
			type = "LEAF";
		}

		// set the size
		randomInt = random.nextInt(5);
		if (randomInt == 0) {
			size = "S";
		} else if (randomInt == 1) {
			size = "M";
		} else if (randomInt == 2) {
			size = "L";
		} else if (randomInt == 3) {
			size = "XL";
		} else if (randomInt == 4) {
			size = "XXL";
		}

		randomInt = random.nextInt(101);
		relX = randomInt;

		randomInt = random.nextInt(51);
		// + 50 because the lowest possible rely = 5-
		relY = randomInt + 50;

		trees.add(new Tree(size, type, relX, relY));

	}

	public void addTree(String type, String size, int relX, int relY) {
		trees.add(new Tree(size, type, relX, relY));
	}

	// remove all the trees
	public void clearTrees() {
		trees.clear();
	}

	// start or stop the movie
	public void start() {
		if (play == true) {
			play = false;
		} else {
			play = true;
		}
	}

	public ArrayList<Tree> getTrees() {
		return trees;
	}	
	
}
