
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
                System.out.println("cambiar por error de ya esta ocupada ");
            }
            
        }else{
            System.out.println("cambiar por error de fuera de rango");
        }
    }
    
    public void deleteTile(int row, int colum){
        row-=1;
        colum-=1;
        if (row >= 0 & colum >= 0 & row < height & colum < width){
            if (board[row][colum] == null){
                System.out.println("cambiar por error de no hay nada que borrar");
            }else{
                board[row][colum].makeInvisible();
                board[row][colum] = null;
                startingMatriz[row][colum] = '.';
            }
        }else{
            System.out.println("cambiar por error de fuera de rango");
        }
    }
    
    public void relocateTile(int [] from, int [] to ){
        int rowFrom = from[0]-1;
        int columFrom = from[1]-1;
        int rowTo = to[0]-1;
        int columTo = to[1]-1;
        if (rowTo >= 0 & columTo >= 0 & rowTo < height & columTo < width &
            rowFrom >= 0 & columFrom >= 0 & rowFrom < height
            & columFrom < width){
            if (board[rowFrom][columFrom] == null || board[rowTo][columTo] != null ){
                System.out.println("cambiar error no hay en from o si hay en to ");
            }else{
                board[rowTo][columTo] = board[rowFrom][columFrom];
                board[rowTo][columTo].puzzleMoveVertical(rowTo-rowFrom);
                board[rowTo][columTo].puzzleMoveHorizontal(columTo-columFrom);
                startingMatriz[rowTo][columTo] = startingMatriz[rowFrom][columFrom];
                board[rowFrom][columFrom] = null;
                startingMatriz[rowFrom][columFrom] = '.';
                
            }
        }else{
            System.out.println("cambiar por error de fuera de rango");
        }
    }
    
    
    
    public void addGlue(int row, int colum){
        row-=1;
        colum-=1;
         if (row >= 0 & colum >= 0 & row < height & colum < width){
            
            if (board[row][colum].isGlue() || board[row][colum].getGlued()!= null){
                System.out.println("cambiar por error de ya esta pegada");
            }else{
                SquaredGlued newGlued = new SquaredGlued(startingMatriz,board,row,colum);
                board[row][colum].setGlued(newGlued);
            }
        }else{
            System.out.println("cambiar por error de fuera de rango");
        }
    }
    
    public void deleteGlue(int row, int colum){
        row-=1;
        colum-=1;
        if (row >= 0 & colum >= 0 & row < height & colum < width){
            if (board[row][colum] != null && board[row][colum].getGlued() == null){
                System.out.println("cambiar por error de no tiene pegante");
            }else{
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
            System.out.println("cambiar por error de fuera de rango");
        }
    }
    
    public void tilt(char direction) {
        switch (direction) {
            case 'u':
                for (int row = 1; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        if (board[row][col] != null && board[row - 1][col] == null) {
                            board[row - 1][col] = board[row][col];
                            board[row][col] = null;
                            board[row - 1][col].puzzleMoveVertical(-1);
                            row = 1;
                            col = 0;
                        }
                    }
                }
                break;
            case 'd':
                for (int row = 0; row < height-1; row++) {
                    for (int col = 0; col < width; col++) {
                        if (board[row][col] != null && board[row + 1][col] == null) {
                            board[row + 1][col] = board[row][col];
                            board[row][col] = null;
                            board[row + 1][col].puzzleMoveVertical(1);
                            row = 0;
                            col = 0;
                        }
                    }
                }
                break;
            case 'l':
                for (int col = 1; col < width; col++) {
                    for (int row = 0; row < height; row++) {
                        if (board[row][col] != null && board[row][col - 1] == null) {
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
                System.out.println("cambiar por error");
        }
    }
}
