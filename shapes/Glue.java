/**
 * Write a description of class CuadrosPegados here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Glue{
    private char[][] glueMatriz;
    private Tile[][] glueBoard;
    private int xPosition;
    private int yPosition;
    /**
     * Constructor for objects of class CuadrosPegados
     * @param, matriz of the board.
     * @param, board.
     * @param, row of the midle
     * @param, colum of the midle
     */
    public Glue(char[][] matriz, Tile[][] board,int row,int colum, int height, int width){
        glueMatriz = new char[height][width];
        glueBoard = new Tile[height][width];
        for (int fila = row - 1;  fila < row + 1 ; fila ++){
            for (int columna = colum - 1; columna < colum ; columna ++){
                if(fila >= 0 & fila < height & columna >= 0 & columna < width & matriz[fila][columna]!= '.'){
                    glueMatriz[fila][columna] = matriz[fila][columna];   
                    glueBoard[fila][columna] = board[fila][columna];
                }
            }
        }
    }
    

    
}
