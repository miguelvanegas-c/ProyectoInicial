
/**
 * A puzzle that can be manipulated, and created with a lot of configurations.
 * @author Miguel Angel Vanegas Cardenas 
 * @author Allan Steef Contreras Rodriguez 
 * @version (a version number or a date)
 */
public class puzzle
{
    private Rectangle base;
    private Rectangle baseEnding;
    private Rectangle [][] board;
    private Rectangle [][] ending;
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
        base.makeVisible();
        height = h;
        width = w;
        xPosition = 70;
        yPosition = 15;
        board = new Rectangle[h][w];
    }
    /**
     * Constructor for objects of class puzzle with the height and the width.
     * @param ending puzzle final.
     */
    public puzzle(char[][] ending){
        int h = ending.length;
        int w = ending[0].length;
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
                    board[row][colum].changeColor(ending[row][colum]);
                    board[row][colum].tile();
                    board[row][colum].puzzleMoveVertical(row);
                    board[row][colum].puzzleMoveHorizontal(colum);
                }
                //config ending
                if (ending[row][colum] == '.'){
                    this.ending[row][colum] = null;
                }else{ 
                    this.ending[row][colum] = new Rectangle();
                    this.ending[row][colum].changeColor(starting[row][colum]);
                    this.ending[row][colum].tile();
                    this.ending[row][colum].puzzleMoveVertical(row);
                    this.ending[row][colum].puzzleMoveHorizontal(colum);
                    this.ending[row][colum].moveVertical(w*50+50);
                }
            }
        }
        
    }

    public void actualArrangement() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (board[row][col] != null) {
                    System.out.print(board[row][col].getColor().charAt(0) + " "); // Mostrar la primera letra del color
                } else {
                    System.out.print(". "); // Espacio vacío
                }
            }
            System.out.println(); // Salto de línea para la siguiente fila
        }
    }
     */
    public boolean isGoal() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if ((board[row][col] == null && ending[row][col] != null) ||
                    (board[row][col] != null && ending[row][col] == null)) {
                    return false;
                }
                if (board[row][col] != null && !board[row][col].getColor().equals(ending[row][col].getColor())) {
                    return false;
                }
            }
        }
        return true;
    }
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
        public void makeiNVisible(){
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
     * Tilt the puzzle in a given direction (up, down, left, right).
     * 
     * @param direction The direction to tilt: "up", "down", "left", "right".
     */
    public void tilt(String direction) {
        if (!isVisible) {
            System.out.println("Puzzle is not visible.");
            return;
        }

        switch (direction.toLowerCase()) {
            case "up":
                for (int row = 1; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        if (board[row][col] != null && board[row - 1][col] == null) {
                            board[row - 1][col] = board[row][col];
                            board[row][col] = null;
                            board[row - 1][col].puzzleMoveVertical(row - 1);
                        }
                    }
                }
                break;
            case "down":
                for (int row = height - 2; row >= 0; row--) {
                    for (int col = 0; col < width; col++) {
                        if (board[row][col] != null && board[row + 1][col] == null) {
                            board[row + 1][col] = board[row][col];
                            board[row][col] = null;
                            board[row + 1][col].puzzleMoveVertical(row + 1);
                        }
                    }
                }
                break;
            case "left":
                for (int col = 1; col < width; col++) {
                    for (int row = 0; row < height; row++) {
                        if (board[row][col] != null && board[row][col - 1] == null) {
                            board[row][col - 1] = board[row][col];
                            board[row][col] = null;
                            board[row][col - 1].puzzleMoveHorizontal(col - 1);
                        }
                    }
                }
                break;
            case "right":
                for (int col = width - 2; col >= 0; col--) {
                    for (int row = 0; row < height; row++) {
                        if (board[row][col] != null && board[row][col + 1] == null) {
                            board[row][col + 1] = board[row][col];
                            board[row][col] = null;
                            board[row][col + 1].puzzleMoveHorizontal(col + 1);
                        }
                    }
                }
                break;
            default:
                System.out.println("Invalid direction. Use: up, down, left, right.");
        }
    }

    
