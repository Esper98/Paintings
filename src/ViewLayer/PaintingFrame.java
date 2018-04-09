package ViewLayer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import ModelLayer.Tree;
import ModelLayer.World;

@SuppressWarnings("serial")
public class PaintingFrame extends JFrame {

	private PaintingPanel paintingPanel;
	private World world;
	private Thread worldThread = new Thread(world);
	private JMenuBar menuBar;

	private JFileChooser fileChooser;

	private BufferedReader bufferedReader;

	private JMenu file;
	private JMenu menu;
	private JMenu autograph;
	private JMenu movie;

	private JMenuItem loadPainting;
	private JMenuItem savePainting;
	private JMenuItem exit;

	private JMenuItem addLeafTree;
	private JMenuItem addPineTree;
	private JMenuItem add100Trees;
	private JMenuItem clear;

	private JRadioButtonMenuItem arial;
	private JRadioButtonMenuItem courier;
	private JRadioButtonMenuItem helvetica;
	private JRadioButtonMenuItem timesNewRoman;

	private ButtonGroup fonts;

	private JCheckBoxMenuItem play;

	private String errorMessage;

	public PaintingFrame(World world) {
		this.world = world;
		this.setTitle("Esper van den Heuvel - Painting");
		paintingPanel = new PaintingPanel(world);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(paintingPanel);
		worldThread = new Thread(world);
		// set the default directory to the Paintings map
		fileChooser = new JFileChooser("Paintings/");
		// make a filter so the fileChooser can only open painting files
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("painting files", "painting");
		fileChooser.addChoosableFileFilter(filter);
		errorMessage = "Can't load that file";
		createMenu();
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}

	public void create() {
		worldThread.start();
	}

	public void createMenu() {
		// making all the menu items and actionListeners
		menuBar = new JMenuBar();

		file = new JMenu("File");

		loadPainting = new JMenuItem("Load Painting");
		savePainting = new JMenuItem("Save Painting");
		exit = new JMenuItem("Exit");

		loadPainting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getPainting();
			}
		});

		savePainting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				savePainting();
			}
		});

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		file.add(loadPainting);
		file.add(savePainting);
		file.add(exit);

		menu = new JMenu("Tree");
		movie = new JMenu("Movie");
		autograph = new JMenu("Autograph");
		addLeafTree = new JMenuItem("add Leaf Tree");
		addLeafTree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				world.addRandomTree("LEAF");
				repaint();
			}
		});

		addPineTree = new JMenuItem("add Pine Tree");
		addPineTree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				world.addRandomTree("PINE");
				repaint();
			}
		});

		add100Trees = new JMenuItem("Add 100 Tree's");
		add100Trees.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 100; i++) {
					world.addRandomTree("random");
					repaint();
				}
			}
		});

		clear = new JMenuItem("Clear All Trees");
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				world.clearTrees();
				repaint();
			}
		});

		play = new JCheckBoxMenuItem("Play");

		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				world.start();
			}
		});

		arial = new JRadioButtonMenuItem("Arial");
		arial.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paintingPanel.setFont(new Font("Arial", Font.PLAIN, 20));
				repaint();
			}
		});
		courier = new JRadioButtonMenuItem("courier");

		courier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paintingPanel.setFont(new Font("Courier", Font.PLAIN, 20));
				repaint();

			}
		});
		helvetica = new JRadioButtonMenuItem("helvetica");
		helvetica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paintingPanel.setFont(new Font("helvetica", Font.PLAIN, 20));
				repaint();
			}
		});

		timesNewRoman = new JRadioButtonMenuItem("timesNewRoman");
		timesNewRoman.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paintingPanel.setFont(new Font("newRoman", Font.PLAIN, 20));
				repaint();
			}
		});

		fonts = new ButtonGroup();
		fonts.add(arial);
		fonts.add(courier);
		fonts.add(helvetica);
		fonts.add(timesNewRoman);

		autograph.add(arial);
		autograph.add(courier);
		autograph.add(helvetica);
		autograph.add(timesNewRoman);

		arial.setSelected(true);

		movie.add(play);

		menu.add(clear);
		menu.add(addLeafTree);
		menu.add(addPineTree);
		menu.add(add100Trees);

		menuBar.add(file);
		menuBar.add(menu);
		menuBar.add(autograph);
		menuBar.add(movie);
		this.setJMenuBar(menuBar);

	}

	public void getPainting() {
		try {
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();

				bufferedReader = new BufferedReader(new FileReader(file));
				String line;
				String[] part;
				world.clearTrees();
				while ((line = bufferedReader.readLine()) != null) {
					part = line.split(":");
					if (!part[0].isEmpty() && !part[1].isEmpty() && !part[2].isEmpty() && !part[3].isEmpty()) {
						String type = part[0].toUpperCase();
						String size = part[1].toUpperCase();
						int relX = Integer.parseInt(part[2]);
						int relY = Integer.parseInt(part[3]);
						// check if the file is a usable .painting file
						if (type.matches("LEAF|PINE") && size.matches("S|M|L|XL|XXL") && relX >= 0 && relX <= 100
								&& relY >= 50 && relY <= 100) {
							world.addTree(type, size, relX, relY);
						} else {
							// show error message
							JOptionPane.showMessageDialog(null, errorMessage);
							world.clearTrees();
							repaint();
							return;
						}
					} else {
						// show error message
						JOptionPane.showMessageDialog(null, errorMessage);
						world.clearTrees();
						repaint();
						return;

					}
				}
				repaint();
			}

		} catch (

		Exception e) {
		}
	}

	public void savePainting() {
		// sho the save dialog
		int result = fileChooser.showSaveDialog(this);

		if (result == fileChooser.APPROVE_OPTION) {

			File targetFile = fileChooser.getSelectedFile();

			// if target file not exist make a new file
			try {
				if (!targetFile.exists()) {
					targetFile.createNewFile();
				}

				FileWriter fw = new FileWriter(targetFile);
				BufferedWriter bw = new BufferedWriter(fw);

				// write the info of the trees from the trees arrayList in the
				// file
				for (Tree t : world.getTrees()) {

					bw.write(t.getType() + ":" + t.getSize() + ":" + t.getRelXint() + ":" + t.getRelYint());
					bw.newLine();

				}
				bw.close();
			} catch (IOException e) {
			}
		}
	}

}
