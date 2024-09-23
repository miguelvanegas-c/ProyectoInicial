import java.util.ArrayList;
/**
 * Write a description of class GlueOfGlue here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GlueOfGlue{
    private Puzzle puzzle;
    private ArrayList<Glue> glues = new ArrayList<>();
    private char[][] glueOfGlueMatriz;
    private Tile[][] glueOfGlueBoard;
    private int height;
    private int width;

    /**
     * Constructor for objects of class GlueOfGlue
     */
    public GlueOfGlue(Puzzle puzzle, Glue oldGlue,Glue newGlue){
        this.puzzle = puzzle;
        height = oldGlue.getHeight();
        width = oldGlue.getWidth();
        glueOfGlueMatriz = new char[height][width];
        glueOfGlueBoard = new Tile[height][width];
        glues.add(oldGlue);
        glues.add(newGlue);
        oldGlue.setGlueOfGlue(this);
        newGlue.setGlueOfGlue(this);
        oldGlue.makeIsGlueOfGlue();
        newGlue.makeIsGlueOfGlue();
        Tile[][] oldBoard = oldGlue.getGlueBoard();
        char[][] oldMatriz = oldGlue.getGlueMatriz();
        char[][] newMatriz = newGlue.getGlueMatriz();
        Tile[][] newBoard = newGlue.getGlueBoard();
        for (int row = 0; row < height; row++){
            for (int colum = 0; colum < width; colum++){
                if (oldBoard[row][colum] != null){ 
                    glueOfGlueBoard[row][colum] = oldBoard[row][colum]; 
                    glueOfGlueMatriz[row][colum] = oldMatriz[row][colum];
                }
                if(newBoard[row][colum] != null){
                    glueOfGlueBoard[row][colum] = newBoard[row][colum];
                    glueOfGlueMatriz[row][colum] = newMatriz[row][colum];
                }
            }
        }
    }
    /**
     * delete a glue of glues.
     * @param Glue, the glue that will be eliminated.
     */
    public void deleteOfGlues(Glue deleteGlue){
        if (glues.size() == 2){
            for(Glue g: glues){
                g.makeNoIsGlueOfGlue();
                g.setGlueOfGlue(null);
            }
        }else{
            glues.remove(deleteGlue);
            for (int row = 0; row < height; row++){
                for (int colum = 0; colum < width; colum++){
                    if(!isTileInOtherGlue(glueOfGlueBoard[row][colum])){
                        glueOfGlueBoard[row][colum] = null;
                    }
                }
            }   
        }
    }
    /*
     * analice if one tile is in anothe glue of glues.
     * @param the tile.
     * @return boolean, true id the tile is in one glue, false if not.
     */
    private boolean isTileInOtherGlue(Tile tile){
        for(Glue g: glues){
            for (int row = 0; row < height; row++){
                for (int colum = 0; colum < width; colum++){
                    if(g.getGlueBoard()[row][colum].equals(tile)){
                        return true;
                    }
                }
            }         
        }
        return false;
    }
    public ArrayList<Glue> getGlues(){
        return glues;
    }

    /**
     * add a Glue in the glues
     * @param newGlue that will be added.
     */
    public void add(Glue newGlue){
        glues.add(newGlue);
        char[][] newMatriz = newGlue.getGlueMatriz();
        Tile[][] newBoard = newGlue.getGlueBoard();
        for (int row = 0; row < height; row++){
            for (int colum = 0; colum < width; colum++){
                if(newBoard[row][colum] != null){
                    glueOfGlueBoard[row][colum] = newBoard[row][colum];
                    glueOfGlueMatriz[row][colum] = newMatriz[row][colum];
                }
            }
        }
    }
    /*
     * find the left border of a glueOfGlue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    private ArrayList<Integer[]> leftPositionTiles(){
        ArrayList<Integer[]> leftPositionTiles = new ArrayList<>();
        for (int fila = 0;  fila < height ; fila ++){
            for (int columna = 0; columna < width; columna ++){
                if ((columna == 0 & glueOfGlueBoard[fila][columna] != null)||(glueOfGlueBoard[fila][columna] != null && columna-1 >= 0 && glueOfGlueBoard[fila][columna-1] == null)){
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
     * find the right border of a glueOfGlue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    private ArrayList<Integer[]> rightPositionTiles(){
        ArrayList<Integer[]> rightPositionTiles = new ArrayList<>();
        for (int fila = 0;  fila < height ; fila ++){
            for (int columna = 0; columna < width ; columna ++){
                if ( (columna == width - 1 & glueOfGlueBoard[fila][columna] != null) ||(glueOfGlueBoard[fila][columna] != null && columna+1 < width && glueOfGlueBoard[fila][columna+1] == null)){
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
     * find the down border of a glueOfGlue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    private ArrayList<Integer[]> downPositionTiles(){
        ArrayList<Integer[]> downPositionTiles = new ArrayList<>();
        for (int fila = 0;  fila < height ; fila ++){
            for (int columna = 0; columna < width ; columna ++){
                if ((fila == height -1 & glueOfGlueBoard[fila][columna] != null) ||(glueOfGlueBoard[fila][columna] != null && fila+1 < height && glueOfGlueBoard[fila+1][columna] == null)){
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
     * find the up border of a glueOfGlue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    private ArrayList<Integer[]> upPositionTiles(){
        ArrayList<Integer[]> upPositionTiles = new ArrayList<>();
        for (int fila = 0;  fila < height ; fila ++){
            for (int columna = 0; columna < width ; columna ++){
                if (  (fila == 0 & glueOfGlueBoard[fila][columna] != null)||(glueOfGlueBoard[fila][columna] != null && fila-1 >= 0 && glueOfGlueBoard[fila-1][columna] == null)){
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
     * if is posible move the glueOfGLue to the left with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    public boolean isPosibleLeftTilt(){
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
    /**
     * if is posible move the glueOfGlue to the down with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    public boolean isPosibleDownTilt(){
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
    /**
     * if is posible move the glueOfGlue to the up with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    public boolean isPosibleUpTilt(){
        ArrayList<Integer[]> upPositionTiles = upPositionTiles();
        int row;
        int col;
        Tile [][] board = puzzle.getBoard();
        for (Integer [] positions: upPositionTiles){
            row = positions[0];
            col = positions[1];
            System.out.println(row);
            System.out.println(col);
            if (row <= 0 ||  board[row-1][col] != null){
                return false;
            }
        }
        return true;
    }
    /**
     * if is posible move the glueOfGlue to the right with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    public boolean isPosibleRightTilt(){
        ArrayList<Integer[]> rightPositionTiles = rightPositionTiles();
        int row;
        int col;
        Tile [][] board = puzzle.getBoard();
        for (Integer [] positions: rightPositionTiles){
            row = positions[0];
            col = positions[1];
            System.out.println(row);
            System.out.println(col);
            if (col >= width-1 || board[row][col+1] != null){
                return false;
            }
        }
        return true;
    }
    /**
     * tilt the glueOfGlue to left.
     */
    public void tiltLeft(){
        Tile [][] board = puzzle.getBoard(); 
        char [][] startingMatriz = puzzle.getStartingMatriz();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (glueOfGlueBoard[row][col] != null) {
                    for(Glue g:glues){
                        char[][] glueMatriz = g.getGlueMatriz();
                        Tile[][] glueBoard = g.getGlueBoard();
                        glueBoard[row][col-1] = glueBoard[row][col];
                        glueBoard[row][col] = null;
                        glueMatriz[row][col - 1] = puzzle.colorToChar(board[row][col].getColor());
                        glueMatriz[row][col] = '\u0000';
                    }
                    startingMatriz[row][col - 1] = puzzle.colorToChar(board[row][col].getColor());
                    startingMatriz[row][col] = '.';
                    glueOfGlueMatriz[row][col - 1] = puzzle.colorToChar(board[row][col].getColor());
                    glueOfGlueMatriz[row][col] = '\u0000';
                    board[row][col - 1] = board[row][col];
                    glueOfGlueBoard[row][col - 1] = board[row][col];
                    glueOfGlueBoard[row][col] = null;
                    board[row][col] = null;
                    board[row][col - 1].puzzleMoveHorizontal(-1);
                }
            }
        }
    }
    /**
     * tilt the glueOfGlue to down.
     */
    public void tiltDown(){
        Tile [][] board = puzzle.getBoard(); 
        char [][] startingMatriz = puzzle.getStartingMatriz();
        for (int row = height-1; row >= 0; row--) {
            for (int col = 0; col < width; col++) {
                if (glueOfGlueBoard[row][col] != null) {
                    for(Glue g:glues){
                        char[][] glueMatriz = g.getGlueMatriz();
                        Tile[][] glueBoard = g.getGlueBoard();
                        glueBoard[row+1][col] = glueBoard[row][col];
                        glueBoard[row][col] = null;
                        glueMatriz[row+1][col] = puzzle.colorToChar(board[row][col].getColor());
                        glueMatriz[row][col] = '\u0000';
                    }
                    startingMatriz[row+1][col] = puzzle.colorToChar(board[row][col].getColor());
                    startingMatriz[row][col] = '.';
                    glueOfGlueMatriz[row+1][col] = puzzle.colorToChar(board[row][col].getColor());
                    glueOfGlueMatriz[row][col] = '\u0000';
                    board[row+1][col] = board[row][col];
                    glueOfGlueBoard[row+1][col] = board[row][col];
                    glueOfGlueBoard[row][col] = null;
                    board[row][col] = null;
                    board[row+1][col].puzzleMoveVertical(1);
                }
            }
        }
    } 
    /**
     * tilt the glueOfGlue to up.
     */
    public void tiltUp(){
        Tile [][] board = puzzle.getBoard(); 
        char [][] startingMatriz = puzzle.getStartingMatriz();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (glueOfGlueBoard[row][col] != null) {
                    for(Glue g:glues){
                        char[][] glueMatriz = g.getGlueMatriz();
                        Tile[][] glueBoard = g.getGlueBoard();
                        glueBoard[row-1][col] = glueBoard[row][col];
                        glueBoard[row][col] = null;
                        glueMatriz[row-1][col] = puzzle.colorToChar(board[row][col].getColor());
                        glueMatriz[row][col] = '\u0000';
                    }
                    startingMatriz[row-1][col] = puzzle.colorToChar(board[row][col].getColor());
                    startingMatriz[row][col] = '.';
                    glueOfGlueMatriz[row-1][col] = puzzle.colorToChar(board[row][col].getColor());
                    glueOfGlueMatriz[row][col] = '\u0000';
                    board[row-1][col] = board[row][col];
                    glueOfGlueBoard[row-1][col] = board[row][col];
                    glueOfGlueBoard[row][col] = null;
                    board[row][col] = null;
                    board[row-1][col].puzzleMoveVertical(-1);
                }
            }
        }
    }
    /**
     * tilt the glueOfGlue to right.
     */
    public void tiltRight(){
        Tile [][] board = puzzle.getBoard(); 
        char [][] startingMatriz = puzzle.getStartingMatriz();
        for (int row = 0; row < height; row++) {
            for (int col = width-1; col >= 0; col--) {
                if (glueOfGlueBoard[row][col] != null) {
                    for(Glue g:glues){
                        char[][] glueMatriz = g.getGlueMatriz();
                        Tile[][] glueBoard = g.getGlueBoard();
                        glueBoard[row][col+1] = glueBoard[row][col];
                        glueBoard[row][col] = null;
                        glueMatriz[row][col + 1] = puzzle.colorToChar(board[row][col].getColor());
                        glueMatriz[row][col] = '\u0000';
                    }
                    startingMatriz[row][col + 1] = puzzle.colorToChar(board[row][col].getColor());
                    startingMatriz[row][col] = '.';
                    glueOfGlueMatriz[row][col+1] = puzzle.colorToChar(board[row][col].getColor());
                    glueOfGlueMatriz[row][col] = '\u0000';
                    board[row][col + 1] = board[row][col];
                    glueOfGlueBoard[row][col + 1] = board[row][col];
                    glueOfGlueBoard[row][col] = null;
                    board[row][col] = null;
                    board[row][col + 1].puzzleMoveHorizontal(1);
                }
            }
        }
    }
}
