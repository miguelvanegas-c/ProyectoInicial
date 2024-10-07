import java.util.ArrayList;
/**
 * Is a design of a generalGlue.
 * 
 * @author Miguel Angel Vanegas Cardenas y Allan S teef Contreras
 * @version 1.0
 */

public abstract class GeneralGlue{
    
    protected Puzzle puzzle;
    protected char[][] glueMatriz;
    protected Tile[][] glueBoard;
    protected int height;
    protected int width;
    protected Tile gluedMidle;
    
    /**
     * find the left border of a glue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    protected ArrayList<Integer[]> leftPositionTiles(){
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
    /**
     * find the right border of a glue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    protected ArrayList<Integer[]> rightPositionTiles(){
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
    /**
     * find the down border of a glue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    protected ArrayList<Integer[]> downPositionTiles(){
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
    /**
     * find the up border of a glue.
     * @return ArrayList<Integer[]>, list of the border.
     */
    protected ArrayList<Integer[]> upPositionTiles(){
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
    
    public abstract boolean isPosibleLeftTilt();
    
    public abstract boolean isPosibleDownTilt();
    
    public abstract boolean isPosibleUpTilt();
    
    public abstract boolean isPosibleRightTilt();
    
    /**
     * look if a glue if in a hole
     * @return boolean true if all the glue if in a hole
     */
    protected boolean glueInHole(){
        for (int row = 0; row < height; row ++){
            for (int col = 0; col < width; col ++){
                if (glueBoard[row][col] != null && puzzle.getMatrizHole()[row][col] == null){
                    return false;    
                }
            }
        }
        return true;
    }
    
    /**
     *Delete glue if is in a hole
     */
    protected void glueDeleteInHole(){
        Tile [][] board = puzzle.getBoard(); 
        char [][] startingMatriz = puzzle.getStartingMatriz();
        if (glueInHole()){
            for (int row = 0; row < height; row++) {
                for (int col = width-1; col >= 0; col--) {
                    if (glueBoard[row][col] != null){
                        board[row][col].makeInvisible();
                        board[row][col] = null;
                        startingMatriz[row][col] = '.';
                    }
                }    
            }
        }    
    }
    
    public abstract void tiltLeft();
    
    public abstract void tiltDown();
    
    public abstract void tiltUp();
    
    public abstract void tiltRight();
}
