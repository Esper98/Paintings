package ViewLayer;

import java.awt.Color;
import ModelLayer.Tree;

public class LeafTreePainter extends TreePainter {

	private double x;
	private double y;
	private double sizeRatio;
	private double heigth;
	private double width;
	private Color color;
	private double treeWidth;
	private double treeHeigth;
	private double screenWidth;
	private double screenHeight;

	public void paintTree(Tree tree, double screenWidth, double screenHeight) {
		this.screenWidth = screenWidth / 100;
		this.screenHeight = screenHeight / 100;
		x = tree.getRelX() * 8;
		y = tree.getRelY() * 6;
		sizeRatio = (20 + 3.6 * (tree.getRelY() - 50)) / 200;
		switch (tree.getSize()) {
		case S:
			heigth = (50 * sizeRatio);
			width = (5 * sizeRatio);
			color = color.decode("#2fff55");
			break;
		case M:
			heigth = (80 * sizeRatio);
			width = (8 * sizeRatio);
			color = color.decode("#00ec23");

			break;
		case L:
			heigth = (100 * sizeRatio);
			width = (10 * sizeRatio);
			color = color.decode("#00ce23");
			break;
		case XL:
			heigth = (150 * sizeRatio);
			width = (15 * sizeRatio);
			color = color.decode("#008923");

			break;
		case XXL:
			heigth = (200 * sizeRatio);
			width = (20 * sizeRatio);
			color = color.decode("#005623");

			break;
		}

		x = tree.getRelX() * this.screenWidth;
		y = tree.getRelY() * this.screenHeight - heigth;
		treeWidth = width * 8;
		treeHeigth = heigth * 0.75;
	}

	public Color getColor() {
		return color;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getHeigth() {
		return heigth;
	}

	public double getWidth() {
		return width;
	}

	public double getTreeWidth() {
		return treeWidth;
	}

	public double getTreeHeigth() {
		return treeHeigth;
	}
}
