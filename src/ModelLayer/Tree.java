package ModelLayer;

public class Tree {

	private TreeSize size;
	private TreeType type;
	private double relX;
	private double relY;

	public Tree(String size, String type, double relX, double relY) {
		// using valueOf to set the enumarations
		this.size = TreeSize.valueOf(size);
		this.type = TreeType.valueOf(type);
		this.relX = relX;
		this.relY = relY;
	}

	public TreeSize getSize() {
		return size;
	}

	public void setSize(TreeSize size) {
		this.size = size;
	}

	public TreeType getType() {
		return type;
	}

	public void setType(TreeType type) {
		this.type = type;
	}

	public double getRelX() {
		return relX;
	}

	public int getRelXint() {
		// use Math.round to make a int from the double
		return (int) Math.round(relX);
	}

	public void setRelX(double relX) {
		this.relX = relX;
	}

	public double getRelY() {
		return relY;
	}

	public int getRelYint() {
		// use Math.round to make a int from the double
		return (int) Math.round(relY);
	}

	public void setRelY(double relY) {
		this.relY = relY;
	}

	public void move() {
		// using a formula to set the speed
		// the bigger relY is the faster they go
		double speed = (20 + 3.6 * (getRelY() - 50)) / 200;
		relX = relX + speed * 1.2;
		if (relX > 110) {
			relX = -10;
		}
	}

}
