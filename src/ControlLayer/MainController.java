package ControlLayer;

import javax.swing.SwingUtilities;

import ModelLayer.World;
import ViewLayer.PaintingFrame;

public class MainController {

	private PaintingFrame paintingFrame;
	private World world;

	public MainController() {
		world = new World();
		paintingFrame = new PaintingFrame(world);
		Runnable thread = new Runnable() {
			@Override
			public void run() {
				paintingFrame.create();
			}
		};
		SwingUtilities.invokeLater(thread);
	}

}
