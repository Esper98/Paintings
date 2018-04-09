package ViewLayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import ModelLayer.Tree;
import ModelLayer.World;

public class PaintingPanel extends JPanel implements Observer, ComponentListener {

	private World world;
	private LeafTreePainter leafTreePainter;
	private PineTreePainter pineTreePainter;
	private String name;
	private Font font;
	private int fontSize;
	private Color sky;
	private Color ground;


	public PaintingPanel(World world) {
		this.setPreferredSize(new Dimension(800, 600));
		leafTreePainter = new LeafTreePainter();
		pineTreePainter = new PineTreePainter();
		this.world = world;
		world.addObserver(this);
		this.addComponentListener(this);
		name = "Esper van den Heuvel";
		fontSize = 20;
		font = new Font("TimesRoman", Font.PLAIN, fontSize);
		sky = new Color(0xD0FFF4);
		ground = new Color(207, 143, 7);


	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;		
		g.setColor(sky);
		g.fillRect(0, 0, this.getWidth(), this.getHeight() / 2);
		g.setColor(ground);
		g.fillRect(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
		sortArrayList();
		for (Tree t : world.getTrees()) {
			g.setColor(Color.decode("#7f6a1f"));

			if (t.getType().name().equals("LEAF")) {
				leafTreePainter.paintTree(t,  this.getWidth(), this.getHeight());
				// painting the trunk of the tree
				g.fillRect((int) (leafTreePainter.getX()), (int) (leafTreePainter.getY() + leafTreePainter.getHeigth() / 2),
						(int) leafTreePainter.getWidth(), (int) (leafTreePainter.getHeigth() / 2));
				// painting the border
				g.setColor(Color.BLACK);
				g.drawRect((int) (leafTreePainter.getX()), (int) (leafTreePainter.getY() + leafTreePainter.getHeigth() / 2),
						(int) leafTreePainter.getWidth(), (int) (leafTreePainter.getHeigth() / 2));

				// painting the leafs
				g.setColor(leafTreePainter.getColor());
				g.fillOval((int) (leafTreePainter.getX() - (leafTreePainter.getWidth() * 3.5)), (int) leafTreePainter.getY(),
						(int) leafTreePainter.getTreeWidth(), (int) (leafTreePainter.getTreeHeigth()));

				// painting the border
				g.setColor(Color.BLACK);
				g.drawOval((int) (leafTreePainter.getX() - (leafTreePainter.getWidth() * 3.5)), (int) leafTreePainter.getY(),
						(int) leafTreePainter.getTreeWidth(), (int) (leafTreePainter.getTreeHeigth()));
			}
			if (t.getType().name().equals("PINE")) {
				pineTreePainter.paintTree(t, this.getWidth(), this.getHeight());
				g.fillRect((int) (pineTreePainter.getX()), (int) (pineTreePainter.getY() + pineTreePainter.getHeigth() / 2),
						(int) pineTreePainter.getWidth(), (int) (pineTreePainter.getHeigth() / 2));
				g.setColor(Color.BLACK);
				g.drawRect((int) (pineTreePainter.getX()), (int) (pineTreePainter.getY() + pineTreePainter.getHeigth() / 2),
						(int) pineTreePainter.getWidth(), (int) (pineTreePainter.getHeigth() / 2));

				g.setColor(Color.black);
				g2.fillArc((int) (pineTreePainter.getX() - (pineTreePainter.getWidth() * 3.5)),
						(int) ((int) pineTreePainter.getY() - pineTreePainter.getTreeHeigth() / 2),
						(int) pineTreePainter.getTreeWidth(), (int) (pineTreePainter.getTreeHeigth()), -120, 60);
				g.setColor(pineTreePainter.getColor());
				g2.fillArc((int) (pineTreePainter.getX() + 4 - (pineTreePainter.getWidth() * 3.5)),
						(int) ((int) pineTreePainter.getY() + 5 - pineTreePainter.getTreeHeigth() / 2),
						(int) pineTreePainter.getTreeWidth() - 8, (int) (pineTreePainter.getTreeHeigth()) - 6 , -120, 60);

			}

		}
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(name, this.getWidth() - 10 - g.getFontMetrics().stringWidth(name), 
				this.getHeight() - 10);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	public void sortArrayList() {
		// Sorting the tree array on relY
		Collections.sort(world.getTrees(), (tree1, tree2) -> (int) (tree1.getRelY() - tree2.getRelY()));

	}
	
	public void setFont(Font font){
		this.font = font;
	}
}