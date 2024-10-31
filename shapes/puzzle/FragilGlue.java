package puzzle;
import shapes.*;
import java.util.ArrayList;
/**
 * It is a glue that just can move one time.
 * 
 * @author Miguel Angel Vanegas Cardenas y Allan Steef Contreras
 * @version 1.0
 */
public class FragilGlue extends Glue{
    private boolean canMove;
    private ArrayList<String> colors;
    
    /**
     * Constructor for objects of class CuadrosPegados
     * @param, matriz of the board.
     * @param, board.
     * @param, row of the midle
     * @param, colum of the midle
     */
    public FragilGlue(char[][] matriz, Tile[][] board,int row,int colum, int height, int width,Puzzle puzzle){
        super(matriz,board, row, colum, height, width, puzzle);
        canMove = true;
    }
    
    /**
     * tilt to the left
     */
    @Override
    public void tiltLeft(){
        super.tiltLeft();
        roughGlue();
    }
    
    /**
     * tilt to the down.
     */
    @Override
    public void tiltDown(){
        super.tiltDown();
        roughGlue();
    }
    
    /**
     * tilt to the up
     */
    @Override
    public void tiltUp(){
        super.tiltUp();
        roughGlue();
    }
    
    /**
     * tilt to the right
     */
    @Override
    public void tiltRight(){
        super.tiltRight();
        roughGlue();
    }
    
    /**
     * is posible make a right tilt.
     */
    @Override
    public boolean isPosibleRightTilt(){
       if(canMove){
           return super.isPosibleRightTilt();
       }
       return false;
    }
    
    /**
     * is posible make a left tilt.
     */
    @Override
    public boolean isPosibleLeftTilt(){
       if(canMove){
           return super.isPosibleLeftTilt();
       }
       return false;
    }
    
    /**
     * is posible make a up tilt.
     */
    @Override
    public boolean isPosibleUpTilt(){
       if(canMove){
           return super.isPosibleUpTilt();
       }
       return false;
    }
    
    /**
     * is posible make a down tilt.
     */
    @Override
    public boolean isPosibleDownTilt(){
       if(canMove){
           return super.isPosibleDownTilt();
       }
       return false;
    }
    
    /**
     * change the state of the fragil glue when it can´t move more. 
     */
    public void roughGlue(){
        for(int row = 0; row < height ; row++){
            for(int col = 0 ; col < width ; col ++){
                if (glueBoard[row][col] != null) glueBoard[row][col].changeColor("white");
            }
        }
    }
    
    /**
     * make that the glue can´t move.
     */
    public void makeNoMove(){
        canMove = false;
    }
    
}
