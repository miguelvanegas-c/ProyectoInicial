import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
/**
 * Write a description of class GlueOfGlue here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GlueOfGlue extends GeneralGlue{
    private Set<Tile> gluedMidles = new HashSet<>();
    private ArrayList<Glue> glues = new ArrayList<>();
    
    /**
     * Constructor for objects of class GlueOfGlue
     */
    public GlueOfGlue(Puzzle puzzle, Glue oldGlue,Glue newGlue){
        this.puzzle = puzzle;
        height = oldGlue.getHeight();
        width = oldGlue.getWidth();
        glueMatriz = new char[height][width];
        glueBoard = new Tile[height][width];
        glues.add(oldGlue);
        glues.add(newGlue);
        gluedMidles.add(oldGlue.getGluedMidle());
        gluedMidles.add(newGlue.getGluedMidle());
        gluedMidle = oldGlue.getGluedMidle();
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
                    glueBoard[row][colum] = oldBoard[row][colum]; 
                    glueMatriz[row][colum] = oldMatriz[row][colum];
                }
                if(newBoard[row][colum] != null){
                    glueBoard[row][colum] = newBoard[row][colum];
                    glueMatriz[row][colum] = newMatriz[row][colum];
                }
            }
        }
    }
    /**
     * delete a glue of glues.
     * @param Glue, the glue that will be eliminated.
     */
    public void deleteOfGlues(Glue deleteGlue){
        Tile [][] deleteGlueBoard = deleteGlue.getGlueBoard();
        glues.remove(deleteGlue);
        deleteGlue.makeNoIsGlueOfGlue();
        deleteGlue.setGlueOfGlue(null);
        deleteGlue(deleteGlueBoard);   
    }
    /*
     * 
     */
    
    private void deleteGlue(Tile [][] deleteGlueBoard){
        for (int row = 0; row < height; row++){
            for (int colum = 0; colum < width; colum++){
                if(!isTileInOtherGlue(glueBoard[row][colum])){
                    glueBoard[row][colum] = null;
                    glueMatriz[row][colum] = '\u0000';
                    if (deleteGlueBoard[row][colum] != null){
                        deleteGlueBoard[row][colum].makeNoGlued();
                        deleteGlueBoard[row][colum].changeSize(48,48);
                        deleteGlueBoard[row][colum].setGluedMidleTile(null);
                    }
                    if (deleteGlueBoard[row][colum] != null && deleteGlueBoard[row][colum].isGluedMidle()){
                        deleteGlueBoard[row][colum].setGlue(null);
                        deleteGlueBoard[row][colum].makeNoGluedMidle();
                    }
                }else{
                    if (deleteGlueBoard[row][colum] != null && deleteGlueBoard[row][colum].isGluedMidle()){
                        deleteGlueBoard[row][colum].setGlue(null);
                        deleteGlueBoard[row][colum].makeNoGluedMidle();
                    }
                    assignNewGluedMidle(row,colum);
                }
            }
        } 
    }
    
    
    private void assignNewGluedMidle(int row,int colum){
        for(Glue g: glues){
            if (g.getGlueBoard()[row][colum] != null){
                g.getGlueBoard()[row][colum].setGluedMidleTile(g.getGluedMidle());
                g.getGlueBoard()[row][colum].setGlue(g);
                if(g.getGlueBoard()[row][colum].equals(g.getGluedMidle())){
                    g.getGlueBoard()[row][colum].makeGluedMidle();
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
                    if(g.getGlueBoard()[row][colum] != null && g.getGlueBoard()[row][colum].equals(tile)){
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
        newGlue.setGlueOfGlue(this);
        newGlue.makeIsGlueOfGlue();
        for (int row = 0; row < height; row++){
            for (int colum = 0; colum < width; colum++){
                if(newBoard[row][colum] != null){
                    glueBoard[row][colum] = newBoard[row][colum];
                    glueMatriz[row][colum] = newMatriz[row][colum];
                }
            }
        }
    }
    /**
     * 
     */
    public void join(GlueOfGlue newGlueOfGlue){
        ArrayList<Glue> newGlues = newGlueOfGlue.getGlues();
        int longitud = newGlues.size();
        for(int index = 0; index < longitud; index++){
            add(newGlues.get(index));
            newGlues.get(index).setGlueOfGlue(this);
            newGlues.remove(newGlues.get(index));
        }
        for(Tile t : gluedMidles){
            t.getGlue().setGlueOfGlue(this);
        }
    }
    
    /**
     * if is posible move the glueOfGLue to the left with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    @Override
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
    @Override
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
    @Override
    public boolean isPosibleUpTilt(){
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
    /**
     * if is posible move the glueOfGlue to the right with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    @Override
    public boolean isPosibleRightTilt(){
        ArrayList<Integer[]> rightPositionTiles = rightPositionTiles();
        int row;
        int col;
        Tile [][] board = puzzle.getBoard();
        for (Integer [] positions: rightPositionTiles){
            row = positions[0];
            col = positions[1];
            if (col >= width-1 || board[row][col+1] != null){
                return false;
            }
        }
        return true;
    }
    /**
     * tilt the glueOfGlue to left.
     */
    @Override
    public void tiltLeft(){
        Tile [][] board = puzzle.getBoard(); 
        char [][] startingMatriz = puzzle.getStartingMatriz();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (glueBoard[row][col] != null) {
                    for(Glue g:glues){
                        char[][] otherGlueMatriz = g.getGlueMatriz();
                        Tile[][] otherGlueBoard = g.getGlueBoard();
                        if(otherGlueBoard[row][col] != null){
                            otherGlueBoard[row][col - 1] = glueBoard[row][col];
                            otherGlueMatriz[row][col - 1] = puzzle.colorToChar(board[row][col].getColor());
                            otherGlueMatriz[row][col] = '\u0000';
                            otherGlueBoard[row][col] = null;
                        }
                    }
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
    /**
     * tilt the glueOfGlue to down.
     */
    @Override
    public void tiltDown(){
        Tile [][] board = puzzle.getBoard(); 
        char [][] startingMatriz = puzzle.getStartingMatriz();
        for (int row = height-1; row >= 0; row--) {
            for (int col = 0; col < width; col++) {
                if (glueBoard[row][col] != null) {
                    for(Glue g:glues){
                        char[][] otherGlueMatriz = g.getGlueMatriz();
                        Tile[][] otherGlueBoard = g.getGlueBoard();
                        if (otherGlueBoard[row][col] != null){
                            otherGlueBoard[row+1][col] = glueBoard[row][col];
                            otherGlueMatriz[row+1][col] = puzzle.colorToChar(board[row][col].getColor());
                            otherGlueMatriz[row][col] = '\u0000';
                            otherGlueBoard[row][col] = null;
                        }
                    }
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
    /**
     * tilt the glueOfGlue to up.
     */
    @Override
    public void tiltUp(){
        Tile [][] board = puzzle.getBoard(); 
        char [][] startingMatriz = puzzle.getStartingMatriz();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (glueBoard[row][col] != null) {
                    for(Glue g:glues){
                        char[][] otherGlueMatriz = g.getGlueMatriz();
                        Tile[][] otherGlueBoard = g.getGlueBoard();
                        if (otherGlueBoard[row][col] != null){
                            otherGlueBoard[row-1][col] = glueBoard[row][col];
                            otherGlueMatriz[row-1][col] = puzzle.colorToChar(board[row][col].getColor());
                            otherGlueMatriz[row][col] = '\u0000';
                            otherGlueBoard[row][col] = null;
                        }
                    }
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
    /**
     * tilt the glueOfGlue to right.
     */
    @Override
    public void tiltRight(){
        Tile [][] board = puzzle.getBoard(); 
        char [][] startingMatriz = puzzle.getStartingMatriz();
        for (int row = 0; row < height; row++) {
            for (int col = width-1; col >= 0; col--) {
                if (glueBoard[row][col] != null) {
                    for(Glue g:glues){
                        char[][] otherGlueMatriz = g.getGlueMatriz();
                        Tile[][] otherGlueBoard = g.getGlueBoard();
                        if(otherGlueBoard[row][col] != null){
                            otherGlueBoard[row][col+1] = glueBoard[row][col];
                            otherGlueMatriz[row][col + 1] = puzzle.colorToChar(board[row][col].getColor());
                            otherGlueMatriz[row][col] = '\u0000';
                            otherGlueBoard[row][col] = null;
                        }
                    }
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
