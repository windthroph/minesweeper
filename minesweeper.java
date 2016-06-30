// Krystyn Gatewood
// Aaron Rudolph

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class minesweeper extends JFrame implements ActionListener {
	private JPanel panel;
	private JButton options;
	private JLabel mineCounter;
	private JLabel timer;
	private JMenu menu;
	private JMenuBar bar;
	private JMenuItem newGame;
	private JMenuItem loadGame;
	private JMenuItem saveGame;
	private Timer t;
	private BoardUI board;
	
	//constructor implements base settings and passes them into board settings
	public minesweeper(int rows, int cols, int flags){
		board = new BoardUI(rows,cols,flags);
		mineCounter = new JLabel("0");
		startNewGame(board, mineCounter);
	}	
	
	public void setTimer(){
		timer.setText(String.valueOf(board.getTimer()));
	}
	
	public void setFlags(){
		mineCounter.setText((String.valueOf(board.getFlagsPlaced())));
	}
	
	//has board settings
	public void startNewGame(BoardUI board, JLabel mineCounter) {
		getContentPane().removeAll();
		setTitle ("Minesweeper");
		setLayout (new GridBagLayout());
		GridBagConstraints P = new GridBagConstraints();
		GridBagConstraints B = new GridBagConstraints();
		P.gridy=0;
		B.gridy=1;	
		P.anchor = GridBagConstraints.NORTH;
		B.anchor = GridBagConstraints.SOUTH;
		this.board = board;
		panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.setSize(100, 50);
		board.setSize(1000,1000);
		add(panel, P);
		add(board, B);
		options = new JButton("Options");
		options.addActionListener(this);
		timer = new JLabel(String.valueOf(board.getTimer()));
		t = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				setTimer();
				setFlags();
			}
		});
		t.start();
		panel.add(new JLabel("Timer:  "));
		panel.add(timer);
		panel.add(new JLabel("Flags:  "));
		panel.add(mineCounter);
		bar = new JMenuBar();
		setJMenuBar(bar);
		menu = new JMenu("Options");
		bar.add(menu);
		newGame = new JMenuItem("New Game");
		loadGame = new JMenuItem("Load Game");
		saveGame = new JMenuItem("Save Game");
		newGame.addActionListener(this);
		loadGame.addActionListener(this);
		saveGame.addActionListener(this);
		menu.add(newGame);
		menu.add(loadGame);
		menu.add(saveGame);
		repaint();
	}
	
    //save, load, and new functions
	public void saveToFile() {
		try {
			File file = new File("minesweeper.sav");
			file.delete();
			file.createNewFile();
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
			outputStream.writeObject(board);
			outputStream.writeObject(mineCounter);
			outputStream.close();
		}
		catch (IOException i) {
			try {
				File file = new File("minesweeper.sav");
				file.createNewFile();
			}
			catch (IOException j) {
				JOptionPane.showMessageDialog(this,"Unable to create save file.");
			}
		}
	}
	
	public void loadFromFile() {
		try {
			FileInputStream file = new FileInputStream("minesweeper.sav");
			ObjectInputStream in = new ObjectInputStream(file);
			board = (BoardUI) in.readObject();
			mineCounter = (JLabel)in.readObject();
			in.close();
			file.close();
			revalidate();
			repaint();
			startNewGame(board, mineCounter);
			pack();
			revalidate();
			repaint();
		}
		catch (IOException i) {
			JOptionPane.showMessageDialog(this,"Save File not Found.");
		}
		catch (ClassNotFoundException v) {
			JOptionPane.showMessageDialog(this,"Save File Corrupted.");
		}
	}
	
	//starts a new game with the default settings, saves game to file if clicked, and loads saved game if clicked.
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JMenuItem){
			JMenuItem item = (JMenuItem) e.getSource();
			if(item.getText().equalsIgnoreCase("New Game")){
				JOptionPane.showMessageDialog(this,"I'm starting a new game now with the default settings.");
				dispose();
				minesweeper window = new minesweeper(8,8,10);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setVisible(true);
				window.pack();
			}
			if(item.getText().equalsIgnoreCase("Save Game")) {
				saveToFile();
			}
			if(item.getText().equalsIgnoreCase("Load Game")) {
				loadFromFile();
			}
		}
	}
	
	//End Save, Load, and New Functions
	public static void main(String[] args) {
		// creates board with default settings unless user enters other settings through command prompt
		if(args.length==0){
			minesweeper window = new minesweeper(8,8,10);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setVisible(true);
			window.pack();
		}
		else{
			minesweeper window = new minesweeper(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setVisible(true);
		}
	}
}
