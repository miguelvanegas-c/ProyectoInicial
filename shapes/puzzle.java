import javax.swing.*;
import java.util.ArrayList;
/**
 * A puzzle that can be manipulated, and created with a lot of configurations.
 * 
 * @author Miguel Angel Vanegas Cardenas 
 * @version (a version number or a date)
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
    private int xPosition;
    private int yPosition;
    private boolean isVisible;
    private boolean isFinal; 
    private boolean [][] matrizPegados;
    private Tile [][] matrizHole;
    /**
     * Constructor for objects of class puzzle with the height and the width.
     * @param h the height of the board.
     * @param w the width of the board.
     */
    public Puzzle(int h, int w){
        //creacion base board
        base = new Tile();
        base.changeSize(h*50,w*50);
        base.changeXPosition(-1);
        base.changeYPosition(-1);
        height = h;
        width = w;
        xPosition = 70;
        yPosition = 15;
        board = new Tile[h][w];
        baseEnding = new Tile();
        matrizHole = new Tile[h][w];
        baseEnding.changeSize(h*50,w*50);
        baseEnding.moveVertical(w*50+50);
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
        baseEnding.moveVertical(w*50+50);
        baseEnding.changeXPosition(-1);
        baseEnding.changeYPosition(-1);
        height = h;
        width = w;
        xPosition = 70;
        yPosition = 15;
        board = new Tile[h][w];
        this.ending = new Tile[h][w];
        matrizHole = new Tile [h][w];
        isVisible = false;
        //creacion de las baldozas
        for(int row = 0; row < h; row+=1){
            for(int colum = 0; colum < w; colum +=1){
                startingMatriz[row][colum]='.';
                if (ending[row][colum] == '.'){
                    this.ending[row][colum] = null;
                }else{ 
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
        if (h != starting.length || w != starting[0].length){
            JOptionPane.showMessageDialog(null, 
                                          "el tablero final y el tablero inicial deben ser del mismo tamaño, tanto de alto como de ancho", 
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
        baseEnding.moveVertical(w*50+50);
        baseEnding.changeXPosition(-1);
        baseEnding.changeYPosition(-1);
        endingMatriz = ending;
        startingMatriz = starting;
        height = h;
        width = w;
        xPosition = 70;
        yPosition = 15;
        board = new Tile[h][w];
        this.ending = new Tile[h][w];
        matrizHole = new Tile[h][w];
        isVisible = false;
        //creacion de las baldozas
        for(int row = 0; row < h; row+=1){
            for(int colum = 0; colum < w; colum +=1){
                //config board
                if (starting[row][colum] == '.'){
                    board[row][colum] = null;
                }else{ 
                    String color = charToColor(starting[row][colum]);
                    addTile(row+1,colum+1,color,1);
                }
                //config ending
                if (ending[row][colum] == '.'){
                    this.ending[row][colum] = null;
                }else{ 
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
                if(matrizPegados[row][column]){
                        addGlue(row,column);
                }
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
                    if(board[row][column].isGluedMidle()){
                        newMatrizPegados[row][column] = true;
                    }
                }else{
                    newMatrizPegados[row][column] = false;
                }
            }
        }
        return newMatrizPegados;
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
        if (row >= 0 & colum >= 0 & row < height & colum < width){
            if (ending[row][colum] == null){
                ending[row][colum] = new Tile();
                ending[row][colum].puzzleMoveVertical(row);
                ending[row][colum].puzzleMoveHorizontal(colum);
                if (isVisible){
                    ending[row][colum].makeVisible();
                }
                ending[row][colum].changeColor(color);
                ending[row][colum].moveVertical(width*50+50);
                endingMatriz[row][colum] = colorToChar(color); 
            }else{
                JOptionPane.showMessageDialog(null,
                                         "ese movimiento no se puede realizar",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
            }
            
        }else{
            JOptionPane.showMessageDialog(null,
                                         "fuera de rango",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * change char for a color
     * @param color a char. Must be 'r', 'y', 'b', 'g' and 'm'.
     * @return the nes color String. Must be "red","yellow","blue", "green" and "magenta" 
     * and "magenta".
     */
    public String charToColor(char color){
        
        if (color == 'r'){
            return "red";
        }
        if (color =='y'){
            return "yellow";    
        }
        if (color == 'b'){
            return "blue";    
        }
        if (color == 'g'){
            return "green";
        }
        if (color == 'm'){
            return "magenta";
        }
        return null;
    }
    
    /**
     * change color for a char
     * @param the nes color String. Must be "red","yellow","blue", "green" and "magenta".
     * @return color a char. Must be 'r', 'y', 'b', 'g' and 'm'.
     */
    public char colorToChar(String color){
        if (color == "red"){
            return 'r';
        }
        if (color =="yellow"){
            return 'y';    
        }
        if (color == "blue"){
            return 'b';    
        }
        if (color == "green"){
            return 'g';
        }
        if (color == "magenta"){
            return 'm';
        }
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
     * @param typeOfTile, if 1 normal Tile, if 2 fixedTile,if 3 roughtTile
     */
    public void addTile(int row, int colum, String color,int){ 
        row-=1;
        colum-=1;
        if (row >= 0 & colum >= 0 & row < height & colum < width){
            if (board[row][colum] == null && matrizHole[row][colum] == null){
                if(typeOfTile == 1) board[row][colum] = new Tile();
                if(typeOfTile == 2) board[row][colum] = new FixedTile();
                board[row][colum].puzzleMoveVertical(row);
                board[row][colum].puzzleMoveHorizontal(colum);
                if (isVisible){
                    board[row][colum].makeVisible();
                }
                board[row][colum].changeColor(color);
                startingMatriz[row][colum] = colorToChar(color); 
            }else{
                JOptionPane.showMessageDialog(null,
                                         "En esa posicion ya se encuentra una baldoza o un agujero",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
            }
                
        }else{
            JOptionPane.showMessageDialog(null,
                                         "los valores ingresados para la fila y la columna se encuentran fuera del rango del puzzle",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
        }
        
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
        if (row >= 0 & colum >= 0 & row < height & colum < width){
            if (board[row][colum] == null || matrizHole[row][colum] != null ){
                JOptionPane.showMessageDialog(null,
                                         "En esa posicion no se encuentra ninguna baldoza para eliminar",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
            }else if (board[row][colum] != null && board[row][colum].isGlued()){
                JOptionPane.showMessageDialog(null,
                                         "En esa posicion se encuentra una baldoza, pero esta pegada, por lo tanto no se puede eliminar ",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
            }else{
                if ( !(board[row][colum] instanceof FixedTile)){
                    board[row][colum].makeInvisible();
                    board[row][colum] = null;
                    startingMatriz[row][colum] = '.';
                }else{
                    JOptionPane.showMessageDialog(null,
                                         "los valores ingresados para la fila y la columna se encuentran fuera del rango del ",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;    
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,
                                         "los valores ingresados para la fila y la columna se encuentran fuera del rango del puzzle",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
        }
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
        if (rowTo >= 0 & columTo >= 0 & rowTo < height & columTo < width &
            rowFrom >= 0 & columFrom >= 0 & rowFrom < height
            & columFrom < width){
            if (board[rowFrom][columFrom] == null || board[rowTo][columTo] != null || matrizHole[rowFrom][columFrom] != null || matrizHole[rowTo][columTo] != null){
                JOptionPane.showMessageDialog(null,
                                         "ese movimiento no se puede realizar, es posible que no haya una baldoza que relocalizar o donde se va a relocalizar la baldoza haya un elemento, ya sea un agujero u otra baldoza, ",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
            }else if(board[rowFrom][columFrom] != null && board[rowFrom][columFrom].isGlued()){
                JOptionPane.showMessageDialog(null,
                                         "ese movimiento no se puede realizar, la baldoza que se va a relocalizar se encuentra pegada, por lo tanto no se puede relocalizar",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
            }else{
                String color = charToColor(startingMatriz[rowFrom][columFrom]);
                deleteTile(rowFrom+1,columFrom+1);
                addTile(rowTo+1,columTo+1,color,1);
            }
        }else{
            JOptionPane.showMessageDialog(null,
                                         "los valores ingresados para la fila y la columna se encuentran fuera del rango del puzzle",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
        }
        finish();
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
            if (board[row][colum] == null || matrizHole[row][colum] != null){
                JOptionPane.showMessageDialog(null,
                                         "en esa posicion no se encuentra ninguna baldoza o se encuentra un agujero",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
            }else if(board[row][colum].isGluedMidle()){
                JOptionPane.showMessageDialog(null,
                                         "esa baldoza ya tiene un pegante agregado",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
            }else{
                board[row][colum].makeGluedMidle();
                Glue newGlue = new Glue(startingMatriz,board,row,colum,height,width,this);
                board[row][colum].setGlue(newGlue);
                matrizPegados[row][colum] = true;
            }
        }else{
            JOptionPane.showMessageDialog(null,
                                         "los valores ingresados para la fila y la columna se encuentran fuera del rango del puzzle",
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
            if (board[row][colum] == null || matrizHole[row][colum] != null){
                JOptionPane.showMessageDialog(null,
                                         "en esa posicion no se encuentra ninguna baldoza o se encuentra un agujero",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
            }
            if (board[row][colum] != null && board[row][colum].isGluedMidle() == false){
                JOptionPane.showMessageDialog(null,
                                         "No se le ha aplicado pegante a esta baldoza",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
            }
            if (board[row][colum] != null && board[row][colum].isGluedMidle()){
                board[row][colum].getGlue().deleteGlue(); 
                matrizPegados[row][colum] = false;
            }
        }else{
            JOptionPane.showMessageDialog(null,
                                         "los valores ingresados para la fila y la columna se encuentran fuera del rango del puzzle",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * tilt the tile
     * @param 'u' go up, 'd' gp dpwn, 'l' go left and 'r' go right
     */
    public void tilt(char direction) {
        switch (direction) {
            case 'u':
                tiltUp();
                break;
                
            case 'd':
                tiltDown();
                break;
                
            case 'l':
                tiltLeft();
                break;
                
            case 'r':
                tiltRight();
                break;
                
            default:
                JOptionPane.showMessageDialog(null,
                                         "la direccion es incorrecta",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
        }
    }
    /*
     * tilt the puzzle to up.
     */
    private void tiltUp(){
        for (int row = height - 1; row >= 1; row--) {
            for (int col = 0; col < width; col++) {
                if(board[row][col] != null){
                    if (board[row - 1][col] == null && !board[row][col].isGlued()) {
                        startingMatriz[row - 1][col] = colorToChar(board[row][col].getColor());
                        startingMatriz[row][col] = '.';
                        board[row - 1][col] = board[row][col];
                        board[row][col] = null;
                        board[row - 1][col].puzzleMoveVertical(-1);
                        row = height;
                        col =width;
                    }else if( board[row][col].isGluedMidle() && board[row][col].getGlue().isPosibleUpTilt()){
                        board[row][col].getGlue().tiltUp();
                        row = height;
                        col = width;
                    }else if((board[row - 1][col] != null && matrizHole[row - 1][col] != null  && !board[row - 1][col].isGlued()
                            && !board[row][col].isGlued())|| (board[row - 1][col] != null && matrizHole[row - 1][col] != null && 
                            board[row][col].isGlued() && !board[row - 1][col].isGlued() )){
                        startingMatriz[row - 1][col] = '.';
                        board[row - 1][col].makeInvisible();
                        board[row - 1][col] = null;
                        row = height;
                        col = width;
                    }
                }else if ( board[row - 1][col] != null && matrizHole[row - 1][col] != null && !board[row - 1][col].isGlued()){
                    startingMatriz[row - 1][col] = '.';
                    board[row - 1][col].makeInvisible();
                    board[row - 1][col] = null;
                    row = height;
                    col = width;    
                }
            }
        }
        matrizPegados = createMatrizPegados();
        finish();
    }
    
    /*
     * tilt the puzzle to down.
     */
    private void tiltDown(){
        for (int row = 0; row < height-1; row++) {
            for (int col = 0; col < width; col++) {
                if(board[row][col] != null){
                    if (board[row + 1][col] == null && !board[row][col].isGlued()) {
                        startingMatriz[row + 1][col] = colorToChar(board[row][col].getColor());
                        startingMatriz[row][col] = '.';
                        board[row + 1][col] = board[row][col];
                        board[row][col] = null;
                        board[row + 1][col].puzzleMoveVertical(1);
                        row = -1;
                        col =width;
                    }else if(board[row][col].isGluedMidle() && board[row][col].getGlue().isPosibleDownTilt()){
                        board[row][col].getGlue().tiltDown();
                        row = -1;
                        col = width;
                    }else if((board[row + 1][col] != null && matrizHole[row + 1][col] != null  && !board[row][col].isGlued() 
                            && !board[row][col].isGlued())|| (board[row + 1][col] != null && matrizHole[row + 1][col] != null && 
                            board[row][col].isGlued() && !board[row + 1][col].isGlued() )){
                        startingMatriz[row + 1][col] = '.';
                        board[row + 1][col].makeInvisible();
                        board[row + 1][col] = null;
                        row = -1;
                        col = width;
                    }
                }else if(board[row + 1][col] != null && matrizHole[row + 1][col] != null && !board[row + 1][col].isGlued()){
                    startingMatriz[row + 1][col] = '.';
                    board[row + 1][col].makeInvisible();
                    board[row + 1][col] = null;
                    row = -1;
                    col = width; 
                }
            }
        }
        matrizPegados = createMatrizPegados();
        finish();
    }
    
    /*
     * tilt the puzzle to left.
     */
    private void tiltLeft(){
        for (int row = 0; row < height; row++) {
            for (int col = width-1; col >= 1; col--) {
                if(board[row][col] != null ){
                    if (board[row][col - 1] == null && !board[row][col].isGlued()) {
                        startingMatriz[row][col- 1] = colorToChar(board[row][col].getColor());
                        startingMatriz[row][col] = '.';
                        board[row][col - 1] = board[row][col];
                        board[row][col] = null;
                        board[row][col - 1].puzzleMoveHorizontal(- 1);
                        row = -1;
                        col =0;
                    }else if(board[row][col].isGluedMidle() && board[row][col].getGlue().isPosibleLeftTilt()){
                        board[row][col].getGlue().tiltLeft();
                        row = -1;
                        col = 0;
                    }else if((board[row][col - 1] != null && matrizHole[row][col - 1] != null  && !board[row][col].isGlued()
                            && !board[row][col].isGlued())|| (board[row][col - 1] != null && matrizHole[row][col - 1] != null && 
                            board[row][col].isGlued() && !board[row][col - 1].isGlued() )){
                        startingMatriz[row][col - 1] = '.';
                        board[row][col - 1].makeInvisible();
                        board[row][col - 1] = null;
                        row = -1;
                        col = 0;
                    }
                }else if (board[row][col - 1] != null && matrizHole[row][col - 1] != null && !board[row][col - 1].isGlued() ){
                    startingMatriz[row][col - 1] = '.';
                        board[row][col - 1].makeInvisible();
                        board[row][col - 1] = null;
                        row = -1;
                        col = 0;   
                }
            }
        }
        matrizPegados = createMatrizPegados();
        finish();
    }
    
    /*
     * tilt the puzzle to right.
     */
    private void tiltRight(){
        for (int row = 0; row< height; row++) {
            for (int col = 0; col < width-1; col++) {
                if (board[row][col] != null){
                    if (board[row][col + 1] == null && !board[row][col].isGlued()) {
                        startingMatriz[row][col + 1] = colorToChar(board[row][col].getColor());
                        startingMatriz[row][col] = '.';
                        board[row][col + 1] = board[row][col];
                        board[row][col] = null;
                        board[row][col + 1].puzzleMoveHorizontal(1);
                        row = -1;
                        col =width;
                    }else if(board[row][col].isGluedMidle() && board[row][col].getGlue().isPosibleRightTilt()){
                        board[row][col].getGlue().tiltRight();
                        row = -1;
                        col = width;
                    }else if((board[row][col + 1] != null && matrizHole[row][col + 1] != null  && !board[row][col].isGlued()
                            && !board[row][col].isGlued())|| (board[row][col + 1] != null && matrizHole[row][col + 1] != null && 
                            board[row][col].isGlued() && !board[row][col + 1].isGlued() )){
                        startingMatriz[row][col + 1] = '.';
                        board[row][col + 1].makeInvisible();
                        board[row][col + 1] = null;
                        row = -1;
                        col = width;
                    }
                }else if (board[row][col + 1] != null && matrizHole[row][col + 1] != null && !board[row][col + 1].isGlued() ){
                    startingMatriz[row][col + 1] = '.';
                    board[row][col + 1].makeInvisible();
                    board[row][col + 1] = null;
                    row = -1;
                    col = width; 
                }
            }
        }
        matrizPegados = createMatrizPegados();
        finish();
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
        char [] movement = {'r','l','u','d'};
        int[][] fixedInt;
        for (int row = 0; row < height; row++){
            for(int column = 0; column < width; column++){
                for(char m: movement){
                    if (board[row][column] != null){
                        if (!isPosibleRightTilt(row, column) && !isPosibleLeftTilt(row, column) && !isPosibleDownTilt(row, column) && !isPosibleUpTilt(row, column)){
                            Integer [] arreglo = {row, column};
                            fixed.add(arreglo);
                        }
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
            if (board[row][columna] == null){
                return true;
            }
            if (board[row][columna].isGlued() && !board[row][columna].getGluedMidleTile().getGlue().isPosibleLeftTilt()){
                return false;
            }
        }
        return false;
    }
    
    /*
     * if is posible move the tile to the down with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    private boolean isPosibleDownTilt(int row, int column){
        for (int fila = row; fila < height; fila++){
            if (board[fila][column] == null){
                return true;
            }
            if (board[fila][column].isGlued() && !board[fila][column].getGluedMidleTile().getGlue().isPosibleDownTilt()){
                return false;
            }
        }
        return false;
    }
    
    /*
     * if is posible move the tile to the up with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    private boolean isPosibleUpTilt(int row, int column){
        for (int fila = row; fila >= 0; fila--){
            if (board[fila][column] == null){
                return true;
            }
            if (board[fila][column].isGlued() && !board[fila][column].getGluedMidleTile().getGlue().isPosibleUpTilt()){
                return false;
            }
        }
        return false;
    }
    
    /*
     * if is posible move the tile to the right with a tilt.
     * @return boolean, true if you can move, false if not.
     */
    private boolean isPosibleRightTilt(int row, int column){
        for (int columna = column; columna < width; columna++){
            if (board[row][columna] == null){
                return true;
            }
            if (board[row][columna].isGlued() && !board[row][columna].getGluedMidleTile().getGlue().isPosibleRightTilt()){
                return false;
            }
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
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("El hilo fue interrumpido.");
            }
            for(int[] f:blindTiles){
                board[f[0]][f[1]].makeVisible();
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
                    if (colorToChar(board[row][column].getColor()) != endingMatriz[row][column]){
                        countMisplacedTiles ++;     
                    }
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
        if (finalMovement != 'i'){
            tilt(finalMovement);
        }
        if (finalMovement == 'i'){
            JOptionPane.showMessageDialog(null,
                                         "No fue posible determinar un ladeo que permita avanzar para finalizar el puzzle",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
        }
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
        xPosition = 70;
        yPosition = 15;
        board = new Tile[h][w];
        this.ending = new Tile[h][w];
        matrizHole = new Tile[h][w];
        isVisible = false;
        //creacion de las baldozas
        for(int row = 0; row < h; row+=1){
            for(int colum = 0; colum < w; colum +=1){
                //config board
                if (newStarting[row][colum] == '.'){
                    board[row][colum] = null;
                }else{ 
                    String color = charToColor(newStarting[row][colum]);
                    addTile(row+1,colum+1,color,1);
                }
                //config ending
                if (newEnding[row][colum] == '.'){
                    this.ending[row][colum] = null;
                }else{ 
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
        if (row >= 0 & colum >= 0 & row < height & colum < width){
            if (board[row][colum] == null && matrizHole[row][colum] == null){
                matrizHole[row][colum] = new Tile();
                matrizHole[row][colum].puzzleMoveVertical(row);
                matrizHole[row][colum].puzzleMoveHorizontal(colum);
                if (isVisible){
                    matrizHole[row][colum].makeVisible();
                }
                matrizHole[row][colum].changeColor("white");
                matrizHole[row][colum].moveVertical(12);
                matrizHole[row][colum].moveHorizontal(12);
                matrizHole[row][colum].changeSize(25, 25);
            }else{
                JOptionPane.showMessageDialog(null,
                                         "En esa posicion ya se encuentra una baldoza o un agujero",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
            }
            
        }else{
            JOptionPane.showMessageDialog(null,
                                         "los valores ingresados para la fila y la columna se encuentran fuera del rango del puzzle",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);;
        }   
    }
}
