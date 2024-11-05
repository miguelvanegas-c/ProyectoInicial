package puzzle;
import shapes.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
/**
 * Is a implementation of GeneralGlue.
 * 
 * @author Miguel Angel Vanegas Cardenas y Allan Steef Contreras 
 * @version 1.8
 */
public class Glue extends GeneralGlue{
    private boolean noMove;
    private GlueOfGlue glueOfGlue;
    private boolean isGlueOfGlue;
    /**
     * Constructor for objects of class CuadrosPegados
     * @param matriz, matriz of the board.
     * @param board, board of the puzzle.
     * @param row, row of the midle
     * @param colum, colum of the midle
     */
    public Glue(char[][] matriz, Tile[][] board,int row,int colum, int height, int width,Puzzle puzzle){
        this.puzzle = puzzle;
        this.height = height;
        this.width = width;
        gluedMidle = board[row][colum];
        glueMatriz = new char[height][width];
        glueBoard = new Tile[height][width];
        isGlueOfGlue = false;
        Set<Tile> gluedMidleOld = new HashSet<>();
        for (int fila = row - 1;  fila <= row + 1 ; fila ++){
            for (int columna = colum - 1; columna <= colum + 1 ; columna ++){
                if(fila >= 0 && fila < height && columna >= 0 && columna < width && matriz[fila][columna]!= '.' && !(board[fila][columna] instanceof FreelanceTile) && !(board[fila][columna] instanceof FlyingTile)){
                    glueMatriz[fila][columna] = matriz[fila][columna];   
                    glueBoard[fila][columna] = board[fila][columna];  
                    if (board[fila][columna].isGlued()) gluedMidleOld.add(board[fila][columna].getGluedMidleTile());
                    if (board[fila][columna] instanceof RoughTile) noMove = true;
                    board[fila][columna].makeGlued();
                    board[fila][columna].setGluedMidleTile(gluedMidle);
                    board[fila][columna].changeSize(49,49);
                }
            }
        }
        
        if (gluedMidle.getGlue() != null && gluedMidle.getGlue().isGlueOfGlue()){
            Tile midle = gluedMidle.getGluedMidleTile();
            midle.getGlueOfGlue().add(this);
            setGlueOfGlue(midle.getGlueOfGlue());
            makeIsGlueOfGlue();
            System.out.println(1);
        }
        gluedMidle.setGlue(this);
        if (gluedMidleOld.size() > 0) createGlueOfGlue(gluedMidleOld);
    }
    
    public char[][] getGlueMatriz(){
        return glueMatriz;
    }
    
    public Tile[][] getGlueBoard(){
        return glueBoard;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getWidth(){
        return width;
    }
    
    public boolean isGlueOfGlue(){
        return isGlueOfGlue;
    }
    
    public GlueOfGlue getGlueOfGlue(){
        return glueOfGlue;
    }
    
    public void setGlueOfGlue(GlueOfGlue glueOfGlue){
        this.glueOfGlue = glueOfGlue;
    }
    
    public Tile getGluedMidle(){
        return gluedMidle;
    }
    
    /**
     * Create a glueOfGlue with this and another glue.
     * @param gluedMidleOld, gluedmidle of the othe glue.
     */
    private void createGlueOfGlue(Set<Tile> gluedMidleOld){
        boolean banderaFirstOperation = true; 
        for (Tile midle:gluedMidleOld){
            if (banderaFirstOperation){
                if(isGlueOfGlue()){
                    if(midle.getGlueOfGlue() == null){
                        getGlueOfGlue().add(midle.getGlue());
                    }else{
                        midle.getGlueOfGlue().join(getGlueOfGlue());
                        midle.getGlue().setGlueOfGlue(getGlueOfGlue());
                    }
                    banderaFirstOperation = false;
                }else{
                    if(midle.getGlueOfGlue() == null) this.glueOfGlue = new GlueOfGlue(puzzle, this, midle.getGlue());
                    else{
                        midle.getGlueOfGlue().add(this);
                        setGlueOfGlue(midle.getGlueOfGlue());
                    }
                    banderaFirstOperation = false;
                    makeIsGlueOfGlue();
                }
            }else{
                if (midle.getGlueOfGlue() == null) this.getGlueOfGlue().add(midle.getGlue());
                else this.getGlueOfGlue().join(midle.getGlueOfGlue());
            }    
        }
    }
    /**
     * delete the glue.
     */
    public void deleteGlue(){
        if (isGlueOfGlue){
            getGlueOfGlue().deleteOfGlues(this);
        }else{
            for (int fila = 0;  fila < height ; fila ++){
                for (int columna = 0; columna < width ; columna ++){
                    if(glueBoard[fila][columna] != null){   
                        glueBoard[fila][columna].makeNoGlued();
                        glueBoard[fila][columna].changeSize(48,48);
                        glueBoard[fila][columna].setGluedMidleTile(null);
                        if (glueBoard[fila][columna].isGluedMidle()){
                            glueBoard[fila][columna].setGlue(null);
                            glueBoard[fila][columna].makeNoGluedMidle();
                        }
                    }
                }
            }
        }
    }
    
    /**
     * make part of GlueOfGlue.
     */
    public void makeIsGlueOfGlue(){
        if (!isGlueOfGlue()){
            isGlueOfGlue = true;
        }
    }
    /**
     * make part of not GlueOfGlue.
     */
    public void makeNoIsGlueOfGlue(){
        if (isGlueOfGlue()){
            isGlueOfGlue = false;
        }
    }
    
    /**
     * if is posible move the glue to the left with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    @Override
    public boolean isPosibleLeftTilt(){
        if (isGlueOfGlue()) return glueOfGlue.isPosibleLeftTilt();
        else{
            if (noMove) return false;
            ArrayList<Integer[]> leftPositionTiles = leftPositionTiles();
            int row;
            int col;
            Tile [][] board = puzzle.getBoard();
            for (Integer [] positions: leftPositionTiles){
                row = positions[0];
                col = positions[1];
                if (col <= 0 || board[row][col-1] != null){
                    return false;
                }
            }
            return true;
        }
    }
    /**
     * if is posible move the glue to the down with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    @Override
    public boolean isPosibleDownTilt(){
        if (isGlueOfGlue()) return glueOfGlue.isPosibleDownTilt();
        else{
            if (noMove) return false;
            ArrayList<Integer[]> downPositionTiles = downPositionTiles();
            int row;
            int col;
            Tile [][] board = puzzle.getBoard();
            for (Integer [] positions: downPositionTiles){
                row = positions[0];
                col = positions[1];
                if (row >= height-1 || board[row+1][col] != null){
                    return false;
                }
            }
            return true;
        }
    }
    /**
     * if is posible move the glue to the up with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    @Override
    public boolean isPosibleUpTilt(){
        if (isGlueOfGlue()) return glueOfGlue.isPosibleUpTilt();
        else{
            if (noMove) return false;
            ArrayList<Integer[]> upPositionTiles = upPositionTiles();
            int row;
            int col;
            Tile [][] board = puzzle.getBoard();
            for (Integer [] positions: upPositionTiles){
                row = positions[0];
                col = positions[1];
                if (row <= 0 ||  board[row-1][col] != null){
                    return false;
                }
            }
            return true;
        }
    }
    /**
     * if is posible move the glue to the right with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    @Override
    public boolean isPosibleRightTilt(){
        if (isGlueOfGlue()) return glueOfGlue.isPosibleRightTilt();
        else{
            if (noMove) return false;
            ArrayList<Integer[]> rightPositionTiles = rightPositionTiles();
            int row;
            int col;
            Tile [][] board = puzzle.getBoard();
            for (Integer [] positions: rightPositionTiles){
                row = positions[0];
                col = positions[1];
                if (col == width-1 || board[row][col+1] != null){
                    return false;
                }
            }
            return true;
        }
    }
    /**
     * tilt the glue to left.
     */
    @Override
    public void tiltLeft(){
        if (isGlueOfGlue()) glueOfGlue.tiltLeft();
        else{
            Tile [][] board = puzzle.getBoard(); 
            char [][] startingMatriz = puzzle.getStartingMatriz();
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    if (glueBoard[row][col] != null) {
                        startingMatriz[row][col - 1] = puzzle.colorToChar(board[row][col].getColor());
                        startingMatriz[row][col] = '.';
                        glueMatriz[row][col - 1] = puzzle.colorToChar(board[row][col].getColor());
                        glueMatriz[row][col] = '\u0000';
                        board[row][col - 1] = board[row][col];
                        glueBoard[row][col - 1] = board[row][col];
                        glueBoard[row][col] = null;
                        board[row][col] = null;
                        board[row][col - 1].puzzleMoveHorizontal(-1);
                    }
                }
            }
            glueDeleteInHole();
        }
    }
    /**
     * tilt the glue to down.
     */
    @Override
    public void tiltDown(){
        if (isGlueOfGlue() && getGlueOfGlue()!= null) glueOfGlue.tiltDown();
        else{
            Tile [][] board = puzzle.getBoard(); 
            char [][] startingMatriz = puzzle.getStartingMatriz();
            for (int row = height-1; row >= 0; row--) {
                for (int col = 0; col < width; col++) {
                    if (glueBoard[row][col] != null) {
                        startingMatriz[row+1][col] = puzzle.colorToChar(board[row][col].getColor());
                        startingMatriz[row][col] = '.';
                        glueMatriz[row+1][col] = puzzle.colorToChar(board[row][col].getColor());
                        glueMatriz[row][col] = '\u0000';
                        board[row+1][col] = board[row][col];
                        glueBoard[row+1][col] = board[row][col];
                        glueBoard[row][col] = null;
                        board[row][col] = null;
                        board[row+1][col].puzzleMoveVertical(1);
                    }
                }
            }
            glueDeleteInHole();
        }
    } 
    /**
     * tilt the glue to up.
     */
    @Override
    public void tiltUp(){
        if (isGlueOfGlue()) glueOfGlue.tiltUp();
        else{
            Tile [][] board = puzzle.getBoard(); 
            char [][] startingMatriz = puzzle.getStartingMatriz();
            for (int row = 0; row< height; row++) {
                for (int col = 0; col < width; col++) {
                    if (glueBoard[row][col] != null) {
                        startingMatriz[row-1][col] = puzzle.colorToChar(board[row][col].getColor());
                        startingMatriz[row][col] = '.';
                        glueMatriz[row-1][col] = puzzle.colorToChar(board[row][col].getColor());
                        glueMatriz[row][col] = '\u0000';
                        board[row-1][col] = board[row][col];
                        glueBoard[row-1][col] = board[row][col];
                        glueBoard[row][col] = null;
                        board[row][col] = null;
                        board[row-1][col].puzzleMoveVertical(-1);
                    }
                }
            }
            glueDeleteInHole();
        }
    }
    /**
     * tilt the glue to right.
     */
    @Override
    public void tiltRight(){
        if (isGlueOfGlue()) glueOfGlue.tiltRight();
        else{
            Tile [][] board = puzzle.getBoard(); 
            char [][] startingMatriz = puzzle.getStartingMatriz();
            for (int row = 0; row < height; row++) {
                for (int col = width-1; col >= 0; col--) {
                    if (glueBoard[row][col] != null) {
                        startingMatriz[row][col + 1] = puzzle.colorToChar(board[row][col].getColor());
                        startingMatriz[row][col] = '.';
                        glueMatriz[row][col+1] = puzzle.colorToChar(board[row][col].getColor());
                        glueMatriz[row][col] = '\u0000';
                        board[row][col + 1] = board[row][col];
                        glueBoard[row][col + 1] = board[row][col];
                        glueBoard[row][col] = null;
                        board[row][col] = null;
                        board[row][col + 1].puzzleMoveHorizontal(1);
                    }
                }
            }
            glueDeleteInHole();
        }
    }
    
}
