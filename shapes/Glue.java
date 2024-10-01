import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
/**
 * Write a description of class CuadrosPegados here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Glue{
    private Puzzle puzzle;
    private char[][] glueMatriz;
    private Tile[][] glueBoard;
    private Tile gluedMidle;
    private int xPosition;
    private int yPosition;
    private int height;
    private int width;
    private GlueOfGlue glueOfGlue;
    private boolean isGlueOfGlue;
    /**
     * Constructor for objects of class CuadrosPegados
     * @param, matriz of the board.
     * @param, board.
     * @param, row of the midle
     * @param, colum of the midle
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
                if(fila >= 0 && fila < height && columna >= 0 && columna < width && matriz[fila][columna]!= '.'){
                    glueMatriz[fila][columna] = matriz[fila][columna];   
                    glueBoard[fila][columna] = board[fila][columna];                    
                    if (board[fila][columna].isGlued()){
                        gluedMidleOld.add(board[fila][columna].getGluedMidleTile());
                    }
                    board[fila][columna].makeGlued();
                    board[fila][columna].setGluedMidleTile(gluedMidle);
                    board[fila][columna].changeSize(49,49);
                }
            }
        }
        gluedMidle.setGlue(this);
        if (gluedMidleOld.size() > 0){
            createGlueOfGlue(gluedMidleOld);
        }
        
    }
    
    private void createGlueOfGlue(Set<Tile> gluedMidleOld){
        boolean banderaFirstOperation = true; 
        for (Tile midle:gluedMidleOld){
            if (banderaFirstOperation){
                if(midle.getGlueOfGlue() == null){
                        this.glueOfGlue = new GlueOfGlue(puzzle, this, midle.getGlue());
                }else{
                        midle.getGlueOfGlue().add(this);
                        setGlueOfGlue(midle.getGlueOfGlue());
                }
                banderaFirstOperation = false;
                makeIsGlueOfGlue();
            }else{
                if (midle.getGlueOfGlue() == null){
                    this.getGlueOfGlue().add(midle.getGlue());
                }else{
                    this.getGlueOfGlue().join(midle.getGlueOfGlue());
                }
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
    /*
     * find the left border of a glue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    private ArrayList<Integer[]> leftPositionTiles(){
        ArrayList<Integer[]> leftPositionTiles = new ArrayList<>();
        for (int fila = 0;  fila < height ; fila ++){
            for (int columna = 0; columna < width ; columna ++){
                if ((columna == 0 & glueBoard[fila][columna] != null)||(glueBoard[fila][columna] != null && columna-1 >= 0 && glueBoard[fila][columna-1] == null)){
                    Integer filaInt = fila;
                    Integer columnaInt = columna;
                    Integer[] arreglo ={filaInt,columnaInt};
                    leftPositionTiles.add(arreglo);
                }
                    
            }
        }
        return leftPositionTiles;
    }
    /*
     * find the right border of a glue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    private ArrayList<Integer[]> rightPositionTiles(){
        ArrayList<Integer[]> rightPositionTiles = new ArrayList<>();
        for (int fila = 0;  fila < height ; fila ++){
            for (int columna = 0; columna < width ; columna ++){
                if ( (columna == width - 1 & glueBoard[fila][columna] != null) ||(glueBoard[fila][columna] != null && columna+1 < width && glueBoard[fila][columna+1] == null)){
                    Integer filaInt = fila;
                    Integer columnaInt = columna;
                    Integer[] arreglo ={filaInt,columnaInt};
                    rightPositionTiles.add(arreglo);
                }    
            }
        }
        return rightPositionTiles;
    }
    /*
     * find the down border of a glue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    private ArrayList<Integer[]> downPositionTiles(){
        ArrayList<Integer[]> downPositionTiles = new ArrayList<>();
        for (int fila = 0;  fila < height ; fila ++){
            for (int columna = 0; columna < width ; columna ++){
                if ((fila == height -1 & glueBoard[fila][columna] != null) ||(glueBoard[fila][columna] != null && fila+1 < height && glueBoard[fila+1][columna] == null)){
                    Integer filaInt = fila;
                    Integer columnaInt = columna;
                    Integer[] arreglo ={filaInt,columnaInt};
                    downPositionTiles.add(arreglo);
                }
            }
        }
        return downPositionTiles;
    }
    /*
     * find the up border of a glue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    private ArrayList<Integer[]> upPositionTiles(){
        ArrayList<Integer[]> upPositionTiles = new ArrayList<>();
        for (int fila = 0;  fila < height ; fila ++){
            for (int columna = 0; columna < width ; columna ++){
                if ((fila == 0 & glueBoard[fila][columna] != null)||(glueBoard[fila][columna] != null && fila-1 >= 0 && glueBoard[fila-1][columna] == null)){
                    Integer filaInt = fila;
                    Integer columnaInt = columna;
                    Integer[] arreglo ={filaInt,columnaInt};
                    upPositionTiles.add(arreglo);
                }
                    
            }
        }
        return upPositionTiles;
    }
    /**
     * if is posible move the glue to the left with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    public boolean isPosibleLeftTilt(){
        if (isGlueOfGlue()){
            return glueOfGlue.isPosibleLeftTilt();
        }else{
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
    public boolean isPosibleDownTilt(){
        if (isGlueOfGlue()){
            return glueOfGlue.isPosibleDownTilt();
        }else{
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
    public boolean isPosibleUpTilt(){
        if (isGlueOfGlue()){
            return glueOfGlue.isPosibleUpTilt();
        }else{
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
    public boolean isPosibleRightTilt(){
        if (isGlueOfGlue()){
            return glueOfGlue.isPosibleRightTilt();
        }else{
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
    public void tiltLeft(){
        if (isGlueOfGlue()){
            glueOfGlue.tiltLeft();
        }else{
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
        }
    }
    /**
     * tilt the glue to down.
     */
    public void tiltDown(){
        if (isGlueOfGlue()){
            glueOfGlue.tiltDown();
            System.out.println(1);
        }else{
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
        }
    } 
    /**
     * tilt the glue to up.
     */
    public void tiltUp(){
        if (isGlueOfGlue()){
            glueOfGlue.tiltUp();
        }else{
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
        }
    }
    /**
     * tilt the glue to right.
     */
    public void tiltRight(){
        if (isGlueOfGlue()){
            glueOfGlue.tiltRight();
        }else{
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
        }
    }
    

}
