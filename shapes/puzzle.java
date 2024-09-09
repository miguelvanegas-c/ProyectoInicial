import javax.swing.*;
/**
 * A puzzle that can be manipulated, and created with a lot of configurations.
 * 
 * @author Miguel Angel Vanegas Cardenas 
 * @version (a version number or a date)
 */
public class puzzle
{
    private Rectangle base;
    private Rectangle baseEnding;
    private Rectangle [][] board;
    private Rectangle [][] ending;
    private char [][] endingMatriz;
    private char [][] startingMatriz;
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private boolean isVisible;
    private boolean isFinal; 
    /**
     * Constructor for objects of class puzzle with the height and the width.
     * @param h the height of the board.
     * @param w the width of the board.
     */
    public puzzle(int h, int w){
        //creacion base board
        base = new Rectangle();
        base.changeSize(h*50,w*50);
        height = h;
        width = w;
        xPosition = 70;
        yPosition = 15;
        board = new Rectangle[h][w];
        baseEnding = new Rectangle();
        baseEnding.changeSize(h*50,w*50);
        baseEnding.moveVertical(w*50+50);
        this.ending = new Rectangle[h][w];
        isVisible = false;
    }
    /**
     * Constructor for objects of class puzzle with the height and the width.
     * @param ending puzzle final.
     */
    public puzzle(char[][] ending){
        endingMatriz = ending;
        int h = ending.length;
        int w = ending[0].length;
        startingMatriz = new char[h][w];
        //creacion base board
        base = new Rectangle();
        base.changeSize(h*50,w*50);
        //creacion y desplazamiento base ending
        baseEnding = new Rectangle();
        baseEnding.changeSize(h*50,w*50);
        baseEnding.moveVertical(w*50+50);
        height = h;
        width = w;
        xPosition = 70;
        yPosition = 15;
        board = new Rectangle[h][w];
        this.ending = new Rectangle[h][w];
        //creacion de las baldozas
        for(int row = 0; row < h; row+=1){
            for(int colum = 0; colum < w; colum +=1){
                startingMatriz[row][colum]='.';
                if (ending[row][colum] == '.'){
                    this.ending[row][colum] = null;
                }else{ 
                    this.ending[row][colum] = new Rectangle();
                    this.ending[row][colum].changeColor(ending[row][colum]);
                    this.ending[row][colum].tile();
                    this.ending[row][colum].puzzleMoveVertical(row);
                    this.ending[row][colum].puzzleMoveHorizontal(colum);
                    this.ending[row][colum].moveVertical(w*50+50);
                }
            }
        }
    }
    /**
     * Constructor for objects of class puzzle 
     * @param starting config de starting
     * @param ending config de ending
     */
    public puzzle(char[][] starting, char[][] ending){
        int h = ending.length;
        int w = ending[0].length;
        //creacion base board
        base = new Rectangle();
        base.changeSize(h*50,w*50);
        //creacion y desplazamiento base ending
        baseEnding = new Rectangle();
        baseEnding.changeSize(h*50,w*50);
        baseEnding.moveVertical(w*50+50);
        endingMatriz = ending;
        startingMatriz = starting;
        height = h;
        width = w;
        xPosition = 70;
        yPosition = 15;
        board = new Rectangle[h][w];
        this.ending = new Rectangle[h][w];
        //creacion de las baldozas
        for(int row = 0; row < h; row+=1){
            for(int colum = 0; colum < w; colum +=1){
                //config board
                if (starting[row][colum] == '.'){
                    board[row][colum] = null;
                }else{ 
                    board[row][colum] = new Rectangle();
                    board[row][colum].changeColor(starting[row][colum]);
                    board[row][colum].tile();
                    board[row][colum].puzzleMoveVertical(row);
                    board[row][colum].puzzleMoveHorizontal(colum);
                }
                //config ending
                if (ending[row][colum] == '.'){
                    this.ending[row][colum] = null;
                }else{ 
                    this.ending[row][colum] = new Rectangle();
                    this.ending[row][colum].changeColor(ending[row][colum]);
                    this.ending[row][colum].tile();
                    this.ending[row][colum].puzzleMoveVertical(row);
                    this.ending[row][colum].puzzleMoveHorizontal(colum);
                    this.ending[row][colum].moveVertical(w*50+50);
                }
            }
        }
        
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
                    if (board[row][colum] == null){
                        board[row][colum] = null;
                    }else{ 
                        board[row][colum].makeVisible();
                    }
                    if (ending[row][colum] == null){
                        ending[row][colum] = null;
                    }else{ 
                        ending[row][colum].makeVisible();
                    }
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
                    if (board[row][colum] == null){
                        board[row][colum] = null;
                    }else{ 
                        board[row][colum].makeInvisible();
                    }
                    if (ending[row][colum] == null){
                        ending[row][colum] = null;
                    }else{ 
                        ending[row][colum].makeInvisible();
                    }
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
     */
    public void addTile(int row, int colum, String color){ 
        row-=1;
        colum-=1;
        if (row >= 0 & colum >= 0 & row < height & colum < width){
            if (board[row][colum] == null){
                board[row][colum] = new Rectangle();
                board[row][colum].tile();
                board[row][colum].puzzleMoveVertical(row);
                board[row][colum].puzzleMoveHorizontal(colum);
                board[row][colum].makeVisible();
                board[row][colum].changeColor(color);
                startingMatriz[row][colum] = board[row][colum].colorToChar(color); 
            }else{
                JOptionPane.showMessageDialog(null,
                                         "ese movimiento no se puede realizar",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
            }
            
        }else{
            JOptionPane.showMessageDialog(null,
                                         "fuera de rango",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
        }
    }
    /**
     * delete a tile of the board
     * @param row, row of the tile
     * @param colum, colum of the tile
     */
    public void deleteTile(int row, int colum){
        row-=1;
        colum-=1;
        if (row >= 0 & colum >= 0 & row < height & colum < width){
            if (board[row][colum] == null){
                JOptionPane.showMessageDialog(null,
                                         "no hay nada que borrar",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
            }else{
                board[row][colum].makeInvisible();
                board[row][colum] = null;
                startingMatriz[row][colum] = '.';
            }
        }else{
            JOptionPane.showMessageDialog(null,
                                         "fuera de rango",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
        }
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
        if (rowTo >= 0 & columTo >= 0 & rowTo < height & columTo < width &
            rowFrom >= 0 & columFrom >= 0 & rowFrom < height
            & columFrom < width){
            if (board[rowFrom][columFrom] == null || board[rowTo][columTo] != null ){
                JOptionPane.showMessageDialog(null,
                                         "ese movimiento no se puede realizar",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
            }else{
                board[rowTo][columTo] = board[rowFrom][columFrom];
                board[rowTo][columTo].puzzleMoveVertical(rowTo-rowFrom);
                board[rowTo][columTo].puzzleMoveHorizontal(columTo-columFrom);
                startingMatriz[rowTo][columTo] = startingMatriz[rowFrom][columFrom];
                board[rowFrom][columFrom] = null;
                startingMatriz[rowFrom][columFrom] = '.';
                
            }
        }else{
            JOptionPane.showMessageDialog(null,
                                         "fuera de rango",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
        }
    }
    /**
     * add glue to a tile
     * @param row, row of tile
     * @param colum, colum of tile
     */
    
    
    public void addGlue(int row, int colum){
        row-=1;
        colum-=1;
         if (row >= 0 & colum >= 0 & row < height & colum < width){
            
            if (board[row][colum].isGlue() || board[row][colum].getGlued()!= null){
                JOptionPane.showMessageDialog(null,
                                         "ese movimiento no se puede realizar",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
            }else{
                SquaredGlued newGlued = new SquaredGlued(startingMatriz,board,row,colum);
                board[row][colum].setGlued(newGlued);
            }
        }else{
            JOptionPane.showMessageDialog(null,
                                         "fuera de rango",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * delete glue to a tile
     * @param row, row of tile
     * @param colum, colum of tile
     */
    
    public void deleteGlue(int row, int colum){
        row-=1;
        colum-=1;
        if (row >= 0 & colum >= 0 & row < height & colum < width){
            if (board[row][colum] != null && board[row][colum].getGlued() == null){
                JOptionPane.showMessageDialog(null,
                                         "ese movimiento no se puede realizar",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
            }else if (board[row][colum] != null){
                Rectangle[][] gluedBoard = board[row][colum].getGlued().getGluedBoard();
                int newRow = gluedBoard.length;
                int newColum = gluedBoard[0].length;
                for (int i = 0; i < newRow; i ++){
                    for (int j = 0; j < newColum; j ++){
                        if (gluedBoard[i][j] != null){
                        gluedBoard[i][j].makeNoGlue();   
                        }
                    }               
                }
                board[row][colum].setGlued(null);
            }
        }else{
            JOptionPane.showMessageDialog(null,
                                         "fuera de rango",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
        }
    }
    
    /**
     * move all the tile
     * @param 'u' go up, 'd' gp dpwn, 'l' go left and 'r' go right
     */
    public void tilt(char direction) {
        switch (direction) {
            case 'u':
                for (int row = 1; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        if (board[row][col] != null && board[row][col].isGlue() == false){
                            if (board[row - 1][col] == null) {
                                startingMatriz[row - 1][col] = startingMatriz[row][col];
                                startingMatriz[row][col] = '.';
                                board[row - 1][col] = board[row][col];
                                board[row][col] = null;
                                board[row - 1][col].puzzleMoveVertical(-1);
                                row = 1;
                                col = 0;
                                System.out.println("1");
                            }
                        }else if(board[row][col] != null && 
                        board[row][col].getGlued() != null){
                            Rectangle[][] glued = board[row][col].getGlued().getGluedBoard();
                            if (row -1 > 0){
                                boolean bandera = true;
                                for (int gluedRow = 0; gluedRow < glued.length; gluedRow++){
                                    for (int gluedColum = 0; gluedColum < glued.length; gluedColum++){
                                        if (gluedRow != 0){
                                            if (!(glued[gluedRow][gluedColum]!= null && 
                                            glued[gluedRow-1][gluedColum] == null && 
                                            board[row-2+gluedRow][col-1+gluedColum] == null)){
                                                bandera = false;
                                            }
                                            
                                        }else{
                                            if (glued[gluedRow][gluedColum] != null && 
                                            board[row-2+gluedRow][col-1+gluedColum] != null){
                                                bandera = false;
                                            }
                                            
                                        }
                                    }
                                }
                                if (bandera){
                                    for (int newRow = row-1; newRow < row+1; row++) {
                                        for (int newCol = col-1; newCol < col+1; col++) {
                                            if (board[newRow][newCol] != null && 
                                            board[newRow][newCol].isGlue() == false){
                                                if (board[newRow - 1][newCol] == null) {
                                                    startingMatriz[newRow - 1][newCol] = 
                                                    startingMatriz[newRow][newCol];
                                                    startingMatriz[newRow][newCol] = '.';
                                                    board[newRow - 1][newCol] = board[newRow][newCol];
                                                    board[newRow][newCol] = null;
                                                    board[newRow - 1][newCol].puzzleMoveVertical(-1);
                                                    row = 1;
                                                    col = 0;
                                                }
                                            }
                                        }
                                    }
                                }
                            }  
                        }
                    }
                }
                
                
                break;
            case 'd':
                for (int row = 0; row < height-1; row++) {
                    for (int col = 0; col < width; col++) {
                        if (board[row][col] != null && board[row + 1][col] == null) {
                            startingMatriz[row + 1][col] = startingMatriz[row][col];
                            startingMatriz[row][col] = '.';
                            board[row + 1][col] = board[row][col];
                            board[row][col] = null;
                            board[row + 1][col].puzzleMoveVertical(1);
                            row = 0;
                            col = 0;
                        }else if(board[row][col] != null && 
                        board[row][col].getGlued() != null){
                            Rectangle[][] glued = board[row][col].getGlued().getGluedBoard();
                            if (row + 1 < width-1){
                                boolean bandera = true;
                                for (int gluedRow = 0; gluedRow < glued.length; gluedRow++){
                                    for (int gluedColum = 0; gluedColum < glued.length; gluedColum++){
                                        if (gluedRow != 2){
                                            if (!(glued[gluedRow][gluedColum]!= null && 
                                            glued[gluedRow+1][gluedColum] == null && 
                                            board[row+gluedRow][col-1+gluedColum] == null)){
                                                bandera = false;
                                            }
                                            
                                        }else{
                                            if (glued[gluedRow][gluedColum] != null && 
                                            board[row+gluedRow][col-1+gluedColum] != null){
                                                bandera = false;
                                            }
                                            
                                        }
                                    }
                                }
                                if (bandera){
                                    for (int newRow = row-1; newRow < row+1; row++) {
                                        for (int newCol = col-1; newCol < col+1; col++) {
                                            if (board[newRow][newCol] != null && 
                                            board[newRow][newCol].isGlue() == false){
                                                if (board[newRow + 1][newCol] == null) {
                                                    startingMatriz[newRow + 1][newCol] = 
                                                    startingMatriz[newRow][newCol];
                                                    startingMatriz[newRow][newCol] = '.';
                                                    board[newRow + 1][newCol] = board[newRow][newCol];
                                                    board[newRow][newCol] = null;
                                                    board[newRow + 1][newCol].puzzleMoveVertical(-1);
                                                    row = row-1;
                                                    col = col-1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }  
                        }
                    }
                }
                
                break;
            case 'l':
                for (int col = 1; col < width; col++) {
                    for (int row = 0; row < height; row++) {
                        if (board[row][col] != null && board[row][col - 1] == null) {
                            startingMatriz[row - 1][col] = startingMatriz[row][col];
                            startingMatriz[row][col] = '.';
                            board[row][col - 1] = board[row][col];
                            board[row][col] = null;
                            board[row][col - 1].puzzleMoveHorizontal(- 1);
                            col = 1;
                            row = 0;
                        }
                    }
                }
                break;
            case 'r':
                for (int col = 0; col < width-1; col++) {
                    for (int row = 0; row < height; row++) {
                        if (board[row][col] != null && board[row][col + 1] == null) {
                            startingMatriz[row + 1][col] = startingMatriz[row][col];
                            startingMatriz[row][col] = '.';
                            board[row][col + 1] = board[row][col];
                            board[row][col] = null;
                            board[row][col + 1].puzzleMoveHorizontal(1);
                            col = 0;
                            row = 0;
                        }
                    }
                }
                break;
            default:
                JOptionPane.showMessageDialog(null,
                                         "la direccion es incorrecta",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
        }
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
     * show if the actual matriz is the same that the ending
     */
    public boolean isGoal() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if ((board[row][col] == null && ending[row][col] != null) ||
                    (board[row][col] != null && ending[row][col] == null)) {
                    return false;
                }
                if (board[row][col] != null && !(board[row][col].getColor().equals(ending[row][col].getColor()))) {
                    return false;
                }
            }
        }
        return true;
    }
    public void finish(){
        if (isGoal()){
            System.exit(0);
        }
    }
}
