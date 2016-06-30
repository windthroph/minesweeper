import javax.swing.*; 
import java.awt.*;

/* This class represents a single button with various 
   attributes and methods.                           */

public class SingleButton extends JButton{

    /* Private Attributes of one button */
    
    private int row;
    private int column;
    private boolean mine;
    private boolean flag;
    private boolean checked;
    
    /* Constructor for a button */
    
    public SingleButton(int r, int c) {
        row = r;
        column = c;
        mine = false;
        flag = false;
        checked = false; 
		setMargin(new Insets(0,0,0,0));
		setPreferredSize(new Dimension(30,30));
    }
    
    /* Getter and Setter Methods */
    
    public int getRow(){
        return row;
    }   
    public int getColumn(){
        return column;
    }   
    public boolean getMine() {
        return mine;
    }   
    public boolean getFlag() {
        return flag;
    }    
    public boolean getChecked() {
        return checked;
    }
    
    public void setMine(boolean m){
        mine = m;
    }
    public void setFlag(boolean f){
        flag = f;
    }
    public void setChecked(boolean c){
        checked = c;
    }
}