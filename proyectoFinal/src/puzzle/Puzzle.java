package puzzle;
import shapes.*;

import javax.swing.*;
import java.util.ArrayList;
/**
 * A puzzle that can be manipulated, and created with a lot of configurations.
 * 
 * @author Miguel Angel Vanegas Cardenas y Allan Steef Contreras
 * @version 5.0
 */

public class Puzzle
{
    private Tile base;
    private Tile baseEnding;
    private Tile [][] board;
    private Tile [][] ending;
    private char [][] endingMatriz;
    private char [][] startingMatriz;
    private int height;
    private int width;
    private boolean isVisible;
    private boolean [][] matrizPegados;
    private Tile [][] matrizHole;
    /**
     * Constructor for objects of class puzzle with the height and the width.
     * @param h the height of the board.
     * @param w the width of the board.
     */
    public Puzzle(int h, int w){
        //creacion base board
        startingMatriz = new char[h][w];
        endingMatriz = new char[h][w];
        base = new Tile();
        base.changeSize(h*50,w*50);
        base.changeXPosition(-1);
        base.changeYPosition(-1);
        height = h;
        width = w;
        board = new Tile[h][w];
        baseEnding = new Tile();
        matrizHole = new Tile[h][w];
        baseEnding.changeSize(h*50,w*50);
        baseEnding.moveVertical(h*50+50);
        baseEnding.changeXPosition(-1);
        baseEnding.changeYPosition(-1);
        ending = new Tile[h][w];
        isVisible = false;
        matrizPegados = createMatrizPegados();
    }
    /**
     * Constructor for objects of class puzzle with the height and the width.
     * @param ending puzzle final.
     */
    public Puzzle(char[][] ending){
        endingMatriz = ending;
        int h = ending.length;
        int w = ending[0].length;
        startingMatriz = new char[h][w];
        //creacion base board
        base = new Tile();
        base.changeSize(h*50,w*50);
        base.changeXPosition(-1);
        base.changeYPosition(-1);
        //creacion y desplazamiento base ending
        baseEnding = new Tile();
        baseEnding.changeSize(h*50,w*50);
        baseEnding.moveVertical(h*50+50);
        baseEnding.changeXPosition(-1);
        baseEnding.changeYPosition(-1);
        height = h;
        width = w;
        board = new Tile[h][w];
        this.ending = new Tile[h][w];
        matrizHole = new Tile [h][w];
        isVisible = false;
        //creacion de las baldozas
        for(int row = 0; row < h; row+=1){
            for(int colum = 0; colum < w; colum +=1){
                startingMatriz[row][colum]='.';
                if (ending[row][colum] == '.') this.ending[row][colum] = null;
                else{ 
                    String color = charToColor(ending[row][colum]);
                    addTileEnding(row+1,colum+1,color);
                }
            }
        }
        matrizPegados = createMatrizPegados();
    }
    /**
     * Constructor for objects of class puzzle 
     * @param starting config de starting
     * @param ending config de ending
     */
    public Puzzle(char[][] starting, char[][] ending){
        int h = ending.length;
        int w = ending[0].length;
        
        try {
            valideBoardSize(h,w,starting);
        }catch(PuzzleException e) {
            JOptionPane.showMessageDialog(null, 
                                          e.getMessage(), 
                                          "ERROR", 
                                           JOptionPane.ERROR_MESSAGE);
            System.exit(0);    
        }
    
        //creacion base board
        base = new Tile();
        base.changeSize(h*50,w*50);
        base.changeXPosition(-1);
        base.changeYPosition(-1);
        //creacion y desplazamiento base ending
        baseEnding = new Tile();
        baseEnding.changeSize(h*50,w*50);
        baseEnding.moveVertical(h*50+50);
        baseEnding.changeXPosition(-1);
        baseEnding.changeYPosition(-1);
        endingMatriz = ending;
        startingMatriz = starting;
        height = h;
        width = w;
        board = new Tile[h][w];
        this.ending = new Tile[h][w];
        matrizHole = new Tile[h][w];
        isVisible = false;
        //creacion de las baldozas
        for(int row = 0; row < h; row+=1){
            for(int colum = 0; colum < w; colum +=1){
                //config board
                if (starting[row][colum] == '.') board[row][colum] = null;
                else{ 
                    String color = charToColor(starting[row][colum]);
                    addTile(row+1,colum+1,color,1);
                }
                //config ending
                if (ending[row][colum] == '.') this.ending[row][colum] = null;
                else{ 
                    String color = charToColor(ending[row][colum]);
                    addTileEnding(row+1,colum+1,color);
                }
            }
        }
        matrizPegados = createMatrizPegados();
    }
    
    
    
    public boolean[][] getMatrizPegados(){
        return matrizPegados;
    } 
    
    public void setMatrizPegados(boolean [][] newMatrizPegados){
        matrizPegados = newMatrizPegados;
        for(int row = 0; row < height; row++){
            for(int column = 0; column < width; column++){
                if(matrizPegados[row][column]) addGlue(row,column,1);
            }
        }
    }
    
    public Tile[][] getMatrizHole(){
        return matrizHole;
    }
    
    public Tile[][] getBoard(){
        return board;
    }
    
    public char[][] getStartingMatriz(){
        return startingMatriz;
    }
    
    public char[][] getEndingMatriz(){
        return endingMatriz;
    }
    
    /*
     * create matrizPegados, matriz with true if there is a gluedMidle and false if there isnt.
     */
    private boolean [][] createMatrizPegados(){
        boolean [][] newMatrizPegados = new boolean[height][width];
        for (int row = 0; row < height; row++){
            for(int column = 0; column < width; column++){
                if (board[row][column] != null){
                    if(board[row][column].isGluedMidle()) newMatrizPegados[row][column] = true;
                }else newMatrizPegados[row][column] = false;
            }
        }
        return newMatrizPegados;
    }
    
    /**
     * Valide that the size of the starting was the same of the ending in the constructor.
     * @param h, height of the ending.
     * @param w, width of the ending.
     * @param starting, the startingMatriz of the constructor.
     * @throws PuzzleException, if the size of the ending is different to the starting.
     */
    public void valideBoardSize(int h, int w, char[][] starting) throws PuzzleException{
        if (h != starting.length || w != starting[0].length) throw new PuzzleException(PuzzleException.ERROR_BOARD_SIZE);
    }
    
    /**  
     * Valide that the row or the column are out of the puzzle range.
     * @param row, row to valide.
     * @param colum, column to valide.
     * @throws PuzzleException, if the row or column are out of range.
     */
    public void valideOutOfRange(int row, int colum) throws PuzzleException{
        if (!(row >= 0 & colum >= 0 & row < height & colum < width)) throw new PuzzleException (PuzzleException.ERROR_OUT_RANGE);
    }
    
    /**
     * Valide that the space of the ending is empty.
     * @param row, row of the space.
     * @param colum, column of the space.
     * @throws PuzzleException, if the ending in the space is not empty.
     */
    
    private void valideEmptySpaceInEnding(int row, int colum) throws PuzzleException{
        if (!(ending[row][colum] == null)) throw new PuzzleException(PuzzleException.ERROR_SPACE_NO_EMPTY);
    }
    
    /**
     * Valide that the space is empty.
     * @param row, row of the space.
     * @param colum, column of the space.
     * @throws PuzzleException, if the space is not empty.
     */
    public void valideEmptySpace(int row, int colum) throws PuzzleException{
        if (!(board[row][colum] == null && matrizHole[row][colum] == null)) throw new PuzzleException(PuzzleException.ERROR_SPACE_NO_EMPTY);
    }                           
    
    /**
     * Valide that exist a tile to delete.
     * @param row, row of the tile.
     * @param colum, column of the tile.
     * @throws PuzzleException, if the tile doesn´t exist.
     */
    public void valideTileToDelete(int row, int colum ) throws PuzzleException {
        if (board[row][colum] == null) throw new PuzzleException(PuzzleException.ERROR_NO_TILE_DELETE);
    }
    
    /**
     * Valide that the tile is not glued.
     * @param row, row of the tile.
     * @param colum, column of the tile.
     * @throws PuzzleException, if the tile is glued.
     */
    public void valideTileNoGlued(int row, int colum ) throws PuzzleException {
        if (board[row][colum].isGlued()) throw new PuzzleException(PuzzleException.ERROR_TILE_GLUED);
    }
    
    /**
     * Valide that the tile is not fixed.
     * @param row, row of the tile.
     * @param colum, column of the tile.
     * @throws PuzzleException, if the tile is fixed.
     */
    public void valideTileNoFixed(int row, int colum ) throws PuzzleException {
        if ((board[row][colum] instanceof FixedTile)) throw new PuzzleException(PuzzleException.ERROR_TILE_FIXED);
    }
    
    /**
     * Valide that the space is not empty.
     * @param row, row of the space.
     * @param colum, column of the space.
     * @throws PuzzleException, if the space is empty.
     */
    public void valideNotEmptySpace(int row, int colum) throws PuzzleException {
        if (board[row][colum] == null) throw new PuzzleException(PuzzleException.ERROR_SPACE_EMPTY);
    }
    
    /**
     * Valide that the tile is not gluedMidle.
     * @param row, row of the tile.
     * @param colum, column of the tile.
     * @throws PuzzleException, if the tile is gluedMidle.
     */
    public void valideTileNotIsGluedMidle(int row, int colum) throws PuzzleException {
        if(board[row][colum].isGluedMidle()) throw new PuzzleException(PuzzleException.ERROR_TILE_GLUED_MIDLE);
    }
    
    /**
     * Valide that the tile is not Freelance or flying.
     * @param row, row of the tile.
     * @param colum, column of the tile.
     * @throws PuzzleException, if the tile is Freelance or flying.
     */
    public void valideTileNotFreelanceOrFlying(int row, int colum) throws PuzzleException{
        if(board[row][colum] instanceof FreelanceTile || board[row][colum] instanceof FlyingTile ) throw new PuzzleException(PuzzleException.ERROR_TILE_FREELANCE_FLYING);
    }
    
    /**
     * Valide that the tile is gluedMidle.
     * @param row, row of the tile.
     * @param colum, column of the tile.
     * @throws PuzzleException, if the tile is not gluedMidle.
     */
    public void valideTileIsGluedMidle(int row, int colum) throws PuzzleException {
        if(!board[row][colum].isGluedMidle()) throw new PuzzleException(PuzzleException.ERROR_TILE_NOT_GLUED_MIDLE);
    }
    
    /** 
     * Valide that the direction to tilt exist.
     * @param direction, the direction to valide.
     * @throws PuzzleException, if the direction doesn´t exist.
     */
    public void valideDirection(char direction) throws PuzzleException {
        if(direction != 'u' && direction != 'd' && direction != 'l' && direction != 'r') throw new PuzzleException(PuzzleException.ERROR_WRONG_DIRECTION); 
    }
    
    /**
     * Valide the finalMovement in the intelligent tilt.
     * @param finalMovement, direction of the finalMovement.
     * @throws PuzzleException, if the finalMovement is 'i'.
     */
    public void valideFinalMovement(char finalMovement) throws PuzzleException{
        if (finalMovement == 'i') throw new PuzzleException(PuzzleException.ERROR_NOT_INTELLIGENT_TILT);
    }
    
    /*
     * add new tile in ending.
     * @param row, row of the add
     * @param colum, colum of the add
     * @param color, color of the new tile
     */
    private void addTileEnding(int row, int colum, String color){ 
        row-=1;
        colum-=1;
        
        try {
            valideOutOfRange(row,colum);
            valideEmptySpaceInEnding(row,colum);
        }catch(PuzzleException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
  
        ending[row][colum] = new Tile();
        ending[row][colum].puzzleMoveVertical(row);
        ending[row][colum].puzzleMoveHorizontal(colum);
        if (isVisible) ending[row][colum].makeVisible();
        ending[row][colum].changeColor(color);
        ending[row][colum].moveVertical(height*50+50);
        endingMatriz[row][colum] = colorToChar(color); 
    }
      
    /**
     * change char for a color
     * @param color a char. Must be 'r', 'y', 'b', 'g' and 'm'.
     * @return the nes color String. Must be "red","yellow","blue", "green" and "magenta" 
     * and "magenta".
     */
    public String charToColor(char color){
        
        if (color == 'r') return "red";
        if (color =='y') return "yellow";    
        if (color == 'b') return "blue";    
        if (color == 'g') return "green";
        if (color == 'm') return "magenta";
        return null;
    }
    
    /**
     * change color for a char
     * @param the nes color String. Must be "red","yellow","blue", "green" and "magenta".
     * @return color a char. Must be 'r', 'y', 'b', 'g' and 'm'.
     */
    public char colorToChar(String color){
        if (color == "red") return 'r';
        if (color =="yellow") return 'y';    
        if (color == "blue") return 'b';    
        if (color == "green") return 'g';
        if (color == "magenta") return 'm';
        return ' ';
    }
    
    /**
     * draw the boards on the screen
     */
    public void makeVisible(){
        if (isVisible == false){
            base.makeVisible();
            baseEnding.makeVisible();
            for(int row = 0; row < height; row+=1){
                for(int colum = 0; colum < width; colum +=1){
                    if (board[row][colum] == null) board[row][colum] = null;
                    else board[row][colum].makeVisible();
                    if (ending[row][colum] == null) ending[row][colum] = null;
                    else ending[row][colum].makeVisible();
                }
            }
            isVisible = true;
        }
    }
    
    /**
     * erase teh boards
     */
    public void makeInvisible(){
        if (isVisible){
            base.makeInvisible();
            baseEnding.makeInvisible();
            for(int row = 0; row < height; row+=1){
                for(int colum = 0; colum < width; colum +=1){
                    if (board[row][colum] == null) board[row][colum] = null;
                    else board[row][colum].makeInvisible();
                    if (ending[row][colum] == null) ending[row][colum] = null;
                    else ending[row][colum].makeInvisible();
                }
            }
            isVisible = false;
        }
    }

    /** 
     * add new tile
     * @param row, row of the add
     * @param colum, colum of the add
     * @param color, color of the new tile
     * @param typeOfTile, if 1 normal Tile, if 2 fixedTile,if 3 roughtTile, if 4 freelanceTile, if 5 flyingTile, if 6 SuperFragilTile
     */
    public void addTile(int row, int colum, String color, int typeOfTile ){ 
        row-=1;
        colum-=1;
        
        try {
            valideOutOfRange(row,colum);
            valideEmptySpace(row,colum);
        }catch(PuzzleException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(typeOfTile == 1) board[row][colum] = new Tile();
        if(typeOfTile == 2) board[row][colum] = new FixedTile();
        if(typeOfTile == 3) board[row][colum] = new RoughTile();
        if(typeOfTile == 4) board[row][colum] = new FreelanceTile();
        if(typeOfTile == 5) board[row][colum] = new FlyingTile();
        if(typeOfTile == 6) board[row][colum] = new SuperFragilTile();
        board[row][colum].puzzleMoveVertical(row);
        board[row][colum].puzzleMoveHorizontal(colum);
        if (isVisible) board[row][colum].makeVisible();
        if (!(typeOfTile == 3)) board[row][colum].changeColor(color);
        startingMatriz[row][colum] = colorToChar(color); 
        finish();
    }
    
    /**
     * delete a tile of the board
     * @param row, row of the tile
     * @param colum, colum of the tile
     */
    public void deleteTile(int row, int colum){
        row-=1;
        colum-=1;
        
        try {
            valideOutOfRange(row,colum);
            valideTileToDelete(row,colum);
            valideTileNoGlued(row,colum);
            valideTileNoFixed(row,colum);
        }catch(PuzzleException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } 
                
        board[row][colum].makeInvisible();
        board[row][colum] = null;
        startingMatriz[row][colum] = '.';        
        finish();
    }
    
    /**
     * relocate a tile
     * @param form, position of tile 
     * @param to, position of the end position of tile
     */
    public void relocateTile(int [] from, int [] to ){
        int rowFrom = from[0]-1;
        int columFrom = from[1]-1;
        int rowTo = to[0]-1;
        int columTo = to[1]-1;
        
        try {
            valideEmptySpace(rowTo,columTo);
            valideNotEmptySpace(rowFrom,columFrom);
            valideTileNoGlued(rowFrom,columFrom);
            valideTileNoFixed(rowFrom,columFrom);
        }catch(PuzzleException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } 
     
        String color = charToColor(startingMatriz[rowFrom][columFrom]);
        deleteTile(rowFrom+1,columFrom+1);
        if(board[rowFrom][columFrom] instanceof RoughTile) addTile(rowTo+1,columTo+1,color, 3);
        else if(board[rowFrom][columFrom] instanceof FreelanceTile) addTile(rowTo+1,columTo+1,color, 4);
        else addTile(rowTo+1,columTo+1,color, 1);
        finish();
    }
   
    /**
     * add glue to a tile
     * @param row, row of tile
     * @param colum, colum of tile
     * @param glueType, if 1 normalGlue, if 2 fragilGlue
     */

    public void addGlue(int row, int colum, int glueType){
        row-=1;
        colum-=1;
        
        try {
            valideOutOfRange(row,colum);
            valideNotEmptySpace(row,colum);
            valideTileNotIsGluedMidle(row,colum);
            valideTileNotFreelanceOrFlying(row, colum);
        }catch(PuzzleException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        board[row][colum].makeGluedMidle();
        Glue newGlue = null;
        if (glueType == 1) newGlue = new Glue(startingMatriz,board,row,colum,height,width,this);
        if (glueType == 2) newGlue = new FragilGlue(startingMatriz,board,row,colum,height,width,this);
        board[row][colum].setGlue(newGlue);
        matrizPegados[row][colum] = true;
           
    }
    
    /**
     * delete glue to a tile
     * @param row, row of tile
     * @param colum, colum of tile
     */
    
    public void deleteGlue(int row, int colum){
        row-=1;
        colum-=1;
        
        try {
            valideOutOfRange(row,colum);
            valideNotEmptySpace(row,colum);
            valideTileIsGluedMidle(row,colum);
        }catch(PuzzleException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } 
        
        board[row][colum].getGlue().deleteGlue(); 
        matrizPegados[row][colum] = false;
    }
    
    
    /**
     * tilt the tile
     * @param direction 'u' go up, 'd' gp dpwn, 'l' go left and 'r' go right
     */
    public void tilt(char direction) {
        
        try {
            valideDirection(direction);
        }catch(PuzzleException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (direction == 'u') tiltUp();
        if (direction == 'd') tiltDown();
        if (direction == 'l') tiltLeft();
        if (direction == 'r') tiltRight();
    }
    
    /*
     * tilt the puzzle to up.
     */
    private void tiltUp(){
        for (int row = height - 1; row >= 1; row--) {
            for (int col = 0; col < width; col++) {
                if(board[row][col] != null && !(board[row][col] instanceof RoughTile)){
                    if(board[row][col] instanceof FlyingTile && board[row - 1][col] == null && !board[row][col].isGlued()){   
                    upFlyingTilt(row,col);
                    row = height;
                    col =width; 
                    }else{
                        if (board[row - 1][col] == null && !board[row][col].isGlued()) {
                            startingMatriz[row - 1][col] = colorToChar(board[row][col].getColor());
                            startingMatriz[row][col] = '.';
                            board[row - 1][col] = board[row][col];
                            board[row][col] = null;
                            board[row - 1][col].puzzleMoveVertical(-1);
                            if(board[row-1][col] instanceof SuperFragilTile) convertInRough(row-1,col);
                            row = height;
                            col =width;
                        }else if( board[row][col].isGluedMidle() && board[row][col].getGlue().isPosibleUpTilt()){
                            board[row][col].getGlue().tiltUp();
                            row = height;
                            col = width;
                        }else if((board[row - 1][col] != null && matrizHole[row - 1][col] != null  && !board[row - 1][col].isGlued()
                                && !board[row][col].isGlued() && !(board[row - 1][col] instanceof FlyingTile))|| (board[row - 1][col] != null && matrizHole[row - 1][col] != null && 
                                board[row][col].isGlued() && !board[row - 1][col].isGlued() && !(board[row - 1][col] instanceof FlyingTile) )){
                            startingMatriz[row - 1][col] = '.';
                            board[row - 1][col].makeInvisible();
                            board[row - 1][col] = null;
                            row = height;
                            col = width;
                        }
                    }
                }else if ( board[row - 1][col] != null && matrizHole[row - 1][col] != null && !board[row - 1][col].isGlued() && !(board[row - 1][col] instanceof FlyingTile)){
                    startingMatriz[row - 1][col] = '.';
                    board[row - 1][col].makeInvisible();
                    board[row - 1][col] = null;
                    row = height;
                    col = width;    
                }
            }
        }
        makeNomovedFragilGlue();
        matrizPegados = createMatrizPegados();
        finish();
    }
    
    /*
     * tilt the puzzle to down.
     */
    private void tiltDown(){
        for (int row = 0; row < height-1; row++) {
            for (int col = 0; col < width; col++) {
                if(board[row][col] != null && !(board[row][col] instanceof RoughTile)){
                    if(board[row][col] instanceof FlyingTile && board[row + 1][col] == null && !board[row][col].isGlued()){
                        downFlyingTilt(row,col);
                        row = -1;
                        col =width;
                    }else{
                        if (board[row + 1][col] == null && !board[row][col].isGlued()) {
                            startingMatriz[row + 1][col] = colorToChar(board[row][col].getColor());
                            startingMatriz[row][col] = '.';
                            board[row + 1][col] = board[row][col];
                            board[row][col] = null;
                            board[row + 1][col].puzzleMoveVertical(1);
                            if(board[row+1][col] instanceof SuperFragilTile) convertInRough(row+1,col);
                            row = -1;
                            col =width;
                        }else if(board[row][col].isGluedMidle() && board[row][col].getGlue().isPosibleDownTilt()){
                            board[row][col].getGlue().tiltDown();
                            row = -1;
                            col = width;
                        }else if((board[row + 1][col] != null && matrizHole[row + 1][col] != null  && !board[row][col].isGlued() 
                                && !board[row][col].isGlued() && !(board[row + 1][col] instanceof FlyingTile))|| (board[row + 1][col] != null && matrizHole[row + 1][col] != null && 
                                board[row][col].isGlued() && !board[row + 1][col].isGlued() && !(board[row + 1][col] instanceof FlyingTile) )){
                            startingMatriz[row + 1][col] = '.';
                            board[row + 1][col].makeInvisible();
                            board[row + 1][col] = null;
                            row = -1;
                            col = width;
                        }
                    }
                }else if(board[row + 1][col] != null && matrizHole[row + 1][col] != null && !board[row + 1][col].isGlued() && !(board[row + 1][col] instanceof FlyingTile)){
                    startingMatriz[row + 1][col] = '.';
                    board[row + 1][col].makeInvisible();
                    board[row + 1][col] = null;
                    row = -1;
                    col = width; 
                }
            }
        }
        makeNomovedFragilGlue();
        matrizPegados = createMatrizPegados();
        finish();
    }
    
    /*
     * tilt the puzzle to left.
     */
    private void tiltLeft(){
        for (int row = 0; row < height; row++) {
            for (int col = width-1; col >= 1; col--) {
                if(board[row][col] != null && !(board[row][col] instanceof RoughTile)){
                    if(board[row][col] instanceof FlyingTile && board[row][col - 1] == null && !board[row][col].isGlued()){
                        leftFlyingTilt(row,col);
                        row = -1;
                        col =0;
                    }else{
                        if (board[row][col - 1] == null && !board[row][col].isGlued()) {
                            startingMatriz[row][col- 1] = colorToChar(board[row][col].getColor());
                            startingMatriz[row][col] = '.';
                            board[row][col - 1] = board[row][col];
                            board[row][col] = null;
                            board[row][col - 1].puzzleMoveHorizontal(- 1);
                            if(board[row][col - 1] instanceof SuperFragilTile) convertInRough(row,col-1);
                            row = -1;
                            col =0;
                        }else if(board[row][col].isGluedMidle() && board[row][col].getGlue().isPosibleLeftTilt()){
                            board[row][col].getGlue().tiltLeft();
                            row = -1;
                            col = 0;
                        }else if((board[row][col - 1] != null && matrizHole[row][col - 1] != null  && !board[row][col].isGlued()
                                && !board[row][col].isGlued() && !(board[row][col -1] instanceof FlyingTile))|| (board[row][col - 1] != null && matrizHole[row][col - 1] != null && 
                                board[row][col].isGlued() && !board[row][col - 1].isGlued() && !(board[row][col -1] instanceof FlyingTile))){
                            startingMatriz[row][col - 1] = '.';
                            board[row][col - 1].makeInvisible();
                            board[row][col - 1] = null;
                            row = -1;
                            col = 0;
                        }
                    }
                }else if (board[row][col - 1] != null && matrizHole[row][col - 1] != null && !board[row][col - 1].isGlued() && !(board[row][col -1] instanceof FlyingTile)){
                    startingMatriz[row][col - 1] = '.';
                        board[row][col - 1].makeInvisible();
                        board[row][col - 1] = null;
                        row = -1;
                        col = 0;   
                }
            }
        }
        makeNomovedFragilGlue();
        matrizPegados = createMatrizPegados();
        finish();
    }
    
    /*
     * tilt the puzzle to right.
     */
    private void tiltRight(){
        for (int row = 0; row< height; row++) {
            for (int col = 0; col < width-1; col++) {
                if (board[row][col] != null && !(board[row][col] instanceof RoughTile)){
                    if(board[row][col] instanceof FlyingTile && board[row][col + 1] == null && !board[row][col].isGlued()){ 
                        rightFlyingTilt(row,col);
                        row = -1;
                        col =width;
                    }else{
                        if (board[row][col + 1] == null && !board[row][col].isGlued()) {
                            startingMatriz[row][col + 1] = colorToChar(board[row][col].getColor());
                            startingMatriz[row][col] = '.';
                            board[row][col + 1] = board[row][col];
                            board[row][col] = null;
                            board[row][col + 1].puzzleMoveHorizontal(1);
                            if(board[row][col + 1] instanceof SuperFragilTile) convertInRough(row,col + 1);
                            row = -1;
                            col =width;
                        }else if(board[row][col].isGluedMidle() && board[row][col].getGlue().isPosibleRightTilt()){
                            board[row][col].getGlue().tiltRight();
                            row = -1;
                            col = width;
                        }else if((board[row][col + 1] != null && matrizHole[row][col + 1] != null  && !board[row][col].isGlued()
                                && !board[row][col].isGlued() && !(board[row][col +1] instanceof FlyingTile))|| (board[row][col + 1] != null && matrizHole[row][col + 1] != null && 
                                board[row][col].isGlued() && !board[row][col + 1].isGlued() && !(board[row][col +1] instanceof FlyingTile))){
                            startingMatriz[row][col + 1] = '.';
                            board[row][col + 1].makeInvisible();
                            board[row][col + 1] = null;
                            row = -1;
                            col = width;
                        }
                    }
                }else if (board[row][col + 1] != null && matrizHole[row][col + 1] != null && !board[row][col + 1].isGlued() && !(board[row][col +1] instanceof FlyingTile)){
                    startingMatriz[row][col + 1] = '.';
                    board[row][col + 1].makeInvisible();
                    board[row][col + 1] = null;
                    row = -1;
                    col = width; 
                }
            }
        }
        makeNomovedFragilGlue();
        matrizPegados = createMatrizPegados();
        finish();
    }
    /*
     * Convert a SuperFragilTile in a RoughTile.
     * @param row, row of the tile.
     * @param col, column of the tile.
     */
    private void convertInRough(int row,int col){
        deleteTile(row + 1,col + 1);
        addTile(row + 1,col + 1,"red",3);
    }
    /*
     * Make that a fragilGlue can´t move.
     */
    private void makeNomovedFragilGlue(){
        for (int row = 0; row< height; row++) {
            for (int col = 0; col < width-1; col++) {
                if (board[row][col] != null && board[row][col].isGluedMidle() && board[row][col].getGlue() instanceof FragilGlue){
                    Glue glue = board[row][col].getGlue();
                    FragilGlue fragilGlue = (FragilGlue) glue;
                    fragilGlue.makeNoMove();
                }
            }
        }
    }
    /*
     * Tilt up of flying tilt.
     */
    private void upFlyingTilt(int row, int col){
        startingMatriz[row - 1][col] = colorToChar(board[row][col].getColor());
        startingMatriz[row][col] = '.';
        board[row - 1][col] = board[row][col];
        board[row][col] = null;
        board[row - 1][col].puzzleMoveVertical(-1);
    }
    /*
     * Tilt down of flying tilt.
     */
    private void downFlyingTilt(int row, int col){
        startingMatriz[row + 1][col] = colorToChar(board[row][col].getColor());     
        startingMatriz[row][col] = '.';
        board[row + 1][col] = board[row][col];
        board[row][col] = null;
        board[row + 1][col].puzzleMoveVertical(1);    
    }
    /*
     * Tilt left of flying tilt.
     */
    private void leftFlyingTilt(int row, int col){
        startingMatriz[row][col- 1] = colorToChar(board[row][col].getColor());
        startingMatriz[row][col] = '.';
        board[row][col - 1] = board[row][col];
        board[row][col] = null;
        board[row][col - 1].puzzleMoveHorizontal(- 1);    
    }
    /*
     * Tilt right of flying tilt.
     */
    private void rightFlyingTilt(int row, int col){
        startingMatriz[row][col + 1] = colorToChar(board[row][col].getColor());
        startingMatriz[row][col] = '.';
        board[row][col + 1] = board[row][col];
        board[row][col] = null;
        board[row][col + 1].puzzleMoveHorizontal(1);    
    }
    /**
     * show the actual matriz of the board
     */
    public void actualArrangement() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (board[row][col] != null) {
                    System.out.print(board[row][col].getColor().charAt(0) + " ");
                } else {
                    System.out.print(". "); // Espacio vacío
                }
            }
            System.out.println(); 
        }
    }
    
    /**
     * show if the actual matriz has the same configuration that the ending.
     * @return false is the actual matriz is diferent tha the ending, True if is the same.
     */
    public boolean isGoal() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if ((board[row][col] == null && ending[row][col] != null) ||
                    (board[row][col] != null && ending[row][col] == null)) return false;
                
                if (board[row][col] != null && ending[row][col] != null && !(board[row][col].getColor().equals(ending[row][col].getColor()))) return false;
            }
        }
        return true;
    }
    
    /**
     * finish the program.
     */
    public void finish(){
        if (isGoal()){
            //JOptionPane.showMessageDialog(null, 
                                          //"¡Felicidades! Has encontrado la solucion del juego con éxito.", 
                                          //"¡Triunfo!", 
                                           //JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * The position that if you tilt the puzzle, they are not gonna change.
     * @return int [][] with all the position.
     */
    public int [][] fixedTiles(){
        ArrayList<Integer[]> fixed = new ArrayList<>();
        int[][] fixedInt;
        for (int row = 0; row < height; row++){
            for(int column = 0; column < width; column++){
                if (board[row][column] != null){
                    if (!isPosibleRightTilt(row, column) && !isPosibleLeftTilt(row, column) && !isPosibleDownTilt(row, column) && !isPosibleUpTilt(row, column)){
                        Integer [] arreglo = {row, column};
                        fixed.add(arreglo);
                    }
                }
            }
        }
        fixedInt = new int[fixed.size()][2];
        int count = 0; 
        for (Integer[] f:fixed){
            fixedInt[count][0] = f[0];
            fixedInt[count][1] = f[1];
            count++;
        }
        makeBlindTiles(fixedInt);
        return fixedInt;
    }
    
    /*
     * if is posible move the tile to the left with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    private boolean isPosibleLeftTilt(int row, int column){
        for (int columna = column; columna >= 0; columna--){
            if (board[row][columna] == null) return true;
            if (board[row][columna].isGlued() && !board[row][columna].getGluedMidleTile().getGlue().isPosibleLeftTilt()) return false;
        }
        return false;
    }
    
    /*
     * if is posible move the tile to the down with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    private boolean isPosibleDownTilt(int row, int column){
        for (int fila = row; fila < height; fila++){
            if (board[fila][column] == null) return true;
            if (board[fila][column].isGlued() && !board[fila][column].getGluedMidleTile().getGlue().isPosibleDownTilt()) return false;
        }
        return false;
    }
    
    /*
     * if is posible move the tile to the up with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    private boolean isPosibleUpTilt(int row, int column){
        for (int fila = row; fila >= 0; fila--){
            if (board[fila][column] == null) return true;
            if (board[fila][column].isGlued() && !board[fila][column].getGluedMidleTile().getGlue().isPosibleUpTilt()) return false;
        }
        return false;
    }
    
    /*
     * if is posible move the tile to the right with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    private boolean isPosibleRightTilt(int row, int column){
        for (int columna = column; columna < width; columna++){
            if (board[row][columna] == null) return true;
            if (board[row][columna].isGlued() && !board[row][columna].getGluedMidleTile().getGlue().isPosibleRightTilt()) return false;
        }
        return false;
    }
    
    /*
     * make blind the tiles.
     */               
    private void makeBlindTiles(int [][] blindTiles){
        for(int count = 0; count < 10; count++){
            for(int[] f:blindTiles){
                board[f[0]][f[1]].makeInvisible();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("El hilo fue interrumpido.");
            }
            for(int[] f:blindTiles){
                board[f[0]][f[1]].makeVisible();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("El hilo fue interrumpido.");
            }
        }
    }
    
    /**
     * number of tile that are misplaced.
     * @return int the number of tile that are misplaced.
     */
    public int misplacedTiles(){
        int countMisplacedTiles = 0;
        for (int row = 0; row < height; row++){
            for(int column = 0; column < width; column++){
                if (board[row][column] != null){
                    if (colorToChar(board[row][column].getColor()) != endingMatriz[row][column]) countMisplacedTiles ++;     
                }
            }
        }
        return countMisplacedTiles;
    }
    
    /**
     * do a inteligent tilt is is posible.
     */
    public void tilt(){
        char [] movement = {'r','l','u','d'};
        int minMisplacedTiles = misplacedTiles();
        int newMisplacedTiles;
        Character finalMovement = 'i';
        for (char m:movement){
            newMisplacedTiles = fakeTilt(m);
            if (minMisplacedTiles > newMisplacedTiles){
                minMisplacedTiles = newMisplacedTiles;
                finalMovement = m;
            }
        }
        try {
            valideFinalMovement(finalMovement);
        }catch(PuzzleException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (finalMovement != 'i') tilt(finalMovement);
        matrizPegados = createMatrizPegados();
        finish();
    }
    
    /*
     * simulate a tilt.
     */
    private int fakeTilt(char m){
        int newMisplacedTiles;
        char[][] newStarting = new char[startingMatriz.length][];
        for (int i = 0; i < startingMatriz.length; i++) {
            newStarting[i] = startingMatriz[i].clone();
        }
        char[][] newEnding = new char[endingMatriz.length][];
        for (int i = 0; i < endingMatriz.length; i++) {
            newEnding[i] = endingMatriz[i].clone();
        }
        Puzzle newPuzzle = new Puzzle(newStarting,newEnding);
        newPuzzle.tilt(m);
        newMisplacedTiles = newPuzzle.misplacedTiles();
        return newMisplacedTiles;
    }
    
    /**
     * Change the reference of the puzzle, starting for ending.
     */
    public void exchange(){
        makeInvisible();
        char [][] newEnding = getStartingMatriz();
        char [][] newStarting = getEndingMatriz();
        int h = newEnding.length;
        int w = newEnding[0].length;
        //creacion base board
        base = new Tile();
        base.changeSize(h*50,w*50);
        base.changeXPosition(-1);
        base.changeYPosition(-1);
        //creacion y desplazamiento base ending
        baseEnding = new Tile();
        baseEnding.changeSize(h*50,w*50);
        baseEnding.moveVertical(w*50+50);
        baseEnding.changeXPosition(-1);
        baseEnding.changeYPosition(-1);
        endingMatriz = newEnding;
        startingMatriz = newStarting;
        height = h;
        width = w;
        board = new Tile[h][w];
        this.ending = new Tile[h][w];
        matrizHole = new Tile[h][w];
        isVisible = false;
        //creacion de las baldozas
        for(int row = 0; row < h; row+=1){
            for(int colum = 0; colum < w; colum +=1){
                //config board
                if (newStarting[row][colum] == '.') board[row][colum] = null;
                else{ 
                    String color = charToColor(newStarting[row][colum]);
                    addTile(row+1,colum+1,color,1);
                }
                //config ending
                if (newEnding[row][colum] == '.') this.ending[row][colum] = null;
                else{ 
                    String color = charToColor(newEnding[row][colum]);
                    addTileEnding(row+1,colum+1,color);
                }
            }
        }
        matrizPegados = createMatrizPegados();  
        makeVisible();
    }
    
    /**
     * make a position of the board a hole.
     * @param int, row of the position.
     * @param int, column of the position.
     */
    public void makeHole(int row, int colum){
        row-=1;
        colum-=1;
        
        try {
            valideOutOfRange(row,colum);
            valideEmptySpace(row,colum);
        }catch(PuzzleException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } 
      
        matrizHole[row][colum] = new Tile();
        matrizHole[row][colum].puzzleMoveVertical(row);
        matrizHole[row][colum].puzzleMoveHorizontal(colum);
        if (isVisible) matrizHole[row][colum].makeVisible();
        matrizHole[row][colum].changeColor("white");
        matrizHole[row][colum].moveVertical(12);
        matrizHole[row][colum].moveHorizontal(12);
        matrizHole[row][colum].changeSize(25, 25);       
    }
}
