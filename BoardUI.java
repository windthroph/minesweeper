/*
	Eric Whatley
	Wayne Myhre
	Chris Womack
*/

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class BoardUI extends JPanel implements MouseListener{

    private SingleButton[][] array = null;
    private int rows;
    private int columns;
    private int mines;
	private int flagsPlaced;
	private boolean lost;
    private boolean gameover;
    private Random generator;
	private JFrame main;
	private int time;
	private Timer t;

    public BoardUI(int r, int c, int m) {
        rows = r;
        columns = c;
        mines = m;
		flagsPlaced = 0;
		lost = false;
		gameover = false;
        array = new SingleButton[rows][columns];
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setLayout(new GridLayout(rows, columns));
        
        /* Creates all the buttons and adds listeners */
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                array[i][j] = new SingleButton(i, j);
                array[i][j].addMouseListener(this);
				array[i][j].setText("    ");
                add(array[i][j]);
            }
        }
        
        /* Randomly puts mines throughout the 2D array */
        for(int i = 0; i < mines; i++) {
            generator = new Random();
            int x = (generator.nextInt(rows));
            int y = (generator.nextInt(columns));
            if(array[x][y].getMine()){i--;}
            else {array[x][y].setMine(true);}
        }
	
		/* Initiaties timer */
		t = new Timer(1000, new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
					time = time+1;
		      }
		});
	}
	
	/*****Getters*****/
	public int getRows() { return rows; }
	public int getColumns() {return columns; }
	public int getMines() {return mines; }
	public int getFlagsPlaced() { return flagsPlaced; }
	public boolean getLost() {return lost; }
	public boolean getGameover() {return gameover; }
	public int getTimer(){ return time;}
		
	/* Action Event mouseClicked */
	public void mouseClicked(MouseEvent click) {
		//Time start 
		t.start();
	
		//gets the button source
		int a = click.getButton();
		SingleButton source = (SingleButton)click.getSource();
		
		//if right clicked and no flag and not checked
		if(a == MouseEvent.BUTTON3 && !source.getFlag() && !source.getChecked()) {
			source.setFlag(true);
			source.setText("");
			source.setIcon(new ImageIcon("Flag1.png"));
			flagsPlaced += 1;
			if(checkGameover(source, a)) {
				t.stop();
				gameOver();
			}
		}
		//if right clicked and flag is there it is removed
		else if(a == MouseEvent.BUTTON3 && source.getFlag()) {
			source.setFlag(false);
			source.setIcon(new ImageIcon());
			flagsPlaced -= 1;
			if(checkGameover(source, a)) {
				t.stop();
				gameOver();
			}
		}
		//if a 0 is clicked the recursive method is called
		else if(a == MouseEvent.BUTTON1 && !source.getFlag() && !source.getMine()) {
			ZeroButton(source.getRow(), source.getColumn());
			if(checkGameover(source, a)) {
				t.stop();
				gameOver();
			}
		}
		else if(checkGameover(source, a)) {
			t.stop();
			gameOver();
		}
	}
	
	public void mouseEntered (MouseEvent e) {}
    public void mouseExited (MouseEvent e) {}
    public void mousePressed (MouseEvent e) {}
    public void mouseReleased (MouseEvent e) {}
	
	
/**********************************PRIVATE FUNCTIONS**********************************/

	/* Checks for bombs in surrounding buttons and returns numbers */
	private int Number(int row, int col) {
		int bombs = 0;
		//Checks surrounding area
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				try { if(array[row + i][col + j].getMine()) bombs++; }
				catch(ArrayIndexOutOfBoundsException e) {}
			}
		}
		
		//Sets them to checked
		array[row][col].setChecked(true);
		
		//Sets colors of numbers
		switch(bombs) {
			case 0:
				array[row][col].setForeground(Color.BLACK);
				array[row][col].setBackground(Color.GRAY);
				array[row][col].setText("");
				break;
			case 1:
				array[row][col].setForeground(Color.BLUE);
				array[row][col].setBackground(Color.GRAY);
				array[row][col].setText("1");
				break;
			case 2:
				array[row][col].setForeground(Color.GREEN);
				array[row][col].setBackground(Color.GRAY);
				array[row][col].setText("2");
				break;
			case 3:
				array[row][col].setForeground(Color.RED);
				array[row][col].setBackground(Color.GRAY);
				array[row][col].setText("3");
				break;
			case 4:
				array[row][col].setForeground(new Color(200, 0, 0));
				array[row][col].setBackground(Color.GRAY);
				array[row][col].setText("4");
				break;
			case 5:
				array[row][col].setForeground(new Color(180, 0, 0));
				array[row][col].setBackground(Color.GRAY);
				array[row][col].setText("5");
				break;
			case 6:
				array[row][col].setForeground(new Color(140, 0, 0));
				array[row][col].setBackground(Color.GRAY);
				array[row][col].setText("6");
				break;
			case 7:
				array[row][col].setForeground(new Color(100, 0, 0));
				array[row][col].setBackground(Color.GRAY);
				array[row][col].setText("7");
				break;
			case 8:
				array[row][col].setForeground(new Color(80, 0, 0));
				array[row][col].setBackground(Color.GRAY);
				array[row][col].setText("8");
				break;
		}
		return bombs;
	}
	
	/* Recursion to automatically show spaces */
	private void ZeroButton(int row, int column) {
		//if the Number function returns 0, start recursion
		if (Number(row, column) == 0) {
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					try {
						//if the button isn't checked and is not a mine
						if(!array[row + i][column + j].getChecked() && !array[row + i][column + j].getMine()) 
							ZeroButton(row + i, column + j); 
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}
	
	/* Checks for the end of the game */
	private boolean checkGameover(SingleButton source, int button) {
        int uncheckedButtons = 0;
		gameover = false;
		lost = false;
        if(button == 1 && source.getMine()){
            lost = true;
			gameover = true;
            return gameover;
        } 
        else { 
            /* Check to see if the unchecked buttons/squares are equal to numOfMines */
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < columns; j++) {
                    if(!array[i][j].getChecked()) {uncheckedButtons++;}
                }
            }
            if(uncheckedButtons == mines) {
                gameover = true;
                lost = false;
            }
            return gameover;
        }
    }
	
	/* Function for ending the game */
	private void gameOver() {
		//if the game isn't lost, but game is over (won)
		if(!lost && gameover) {
			JOptionPane.showMessageDialog(main, "You Won!");
		}
		//else they lost
		//so show mines and numbers
		else {
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < columns; j++) {
					if(array[i][j].getMine()) {
						array[i][j].setText("");
						array[i][j].setIcon(new ImageIcon("mine.png"));
					}
					else Number(i, j);
				}
			}
			JOptionPane.showMessageDialog(main, "Game Over");
		}
	}
}
