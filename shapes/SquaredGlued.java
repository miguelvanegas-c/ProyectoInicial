
/**
 * Write a description of class CuadrosPegados here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SquaredGlued{
    
    private char[][] gluedMatriz;
    private Rectangle[][] gluedBoard;
    private int xPosition;
    private int yPosition;
    /**
     * Constructor for objects of class CuadrosPegados
     */
    public SquaredGlued(char[][] matriz, Rectangle[][] board,int row,int colum){
        int countRow = 0;
        for(int newRow = row-1; newRow <= row+1;newRow++){
            int countColum = 0;
            for(int newColum = colum-1; newColum <= colum+1;newColum++){
                if (newRow < matriz.length & newColum < matriz[0].length 
                    & newRow >= 0 & newColum >= 0){
                    if(board[newRow][newColum] != null ||
                       board[newRow][newColum].isGlue() == false){
                        gluedMatriz[countRow][countColum] = matriz[newRow][newColum];
                        gluedBoard[countRow][countColum] = board[newRow][newColum];
                        board[newRow][newColum].makeGlue();
                        
                    }
                    countColum++;
                }
            }
            countRow++; 
        }
    }
    public Rectangle[][] getGluedBoard(){
        return gluedBoard;
    }
    
}
