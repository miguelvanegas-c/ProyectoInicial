import java.awt.*;

/**
 * A tiles that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Miguel Angel Vanegas Cardenas y Allan Steef Contreras
 * @version 1.5
 */

public class Tile{

    public static int EDGES = 4;
    
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
    private boolean glued;
    private boolean gluedMidle;
    private Tile gluedMidleTile;
    private Glue glue;

    /**
     * Create a new rectangle at default position with default color.
     */
    public Tile(){
        height = 48;
        width = 48;
        xPosition = 71;
        yPosition = 16;
        color = "black";
        isVisible = false;
        glued = false;
    }
    
    /**
     * Make this tile visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this tile invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    

    /**
     * Move the tile horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }
    
    public void changeXPosition(int x){
        xPosition += x;
    }
    
    public void changeYPosition(int y){
        yPosition += y;
    }

    /**
     * Move the tile vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }


    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidth must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid String. are "red, "yellow, "blue", "green", "black" and "magenta".
     */
    
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    /**
     * Change the color. 
     * @param color the new color. Valid chars are 'r', 'y', 'b', 'g' and 'm'.
     */
    
    public void changeColor(char newColorChar){
        String newColor = charToColor(newColorChar);
        if (newColor == null){
            System.out.println("el color no es correcto");
        }else{
            color = newColor;
            draw();
        }
    }

    /*
     * Draw the tile with current specifications on screen.
     */

    private void draw() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, 
                                       width, height));
            canvas.wait(10);
        }
    }

    /*
     * Erase the tile on screen.
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    /**
     * Move the tile in the puzzle.
     * @param vertical move in puzzle,must be >0 and < widthPuzzle
     */
    
    public void puzzleMoveVertical(int position){
        moveVertical(50*position);
    }
    /** Move the tile in the puzzle.
     * @param horizontal move in puzzle,must be >0 and < widthPuzzle
     */
    
    public void puzzleMoveHorizontal(int position){
        moveHorizontal(50*position);
    }
    
    /*
     * change char for a color
     * @param color a char. Must be 'r', 'y', 'b', 'g' and 'm'.
     * @return the nes color String. Must be "red","yellow","blue","green" and "magenta"
     * and "magenta".
     */
    private String charToColor(char color){
        
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
     * @param the nes color String. Must be "red","yellow","blue","green" and "magenta".
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
     * make a tile glue, part of a Glue
     */
    public void makeGlued(){
        if (!glued){
            glued = true;
            
        }
    }
    /**
    * make a tile not glue, not part of a Glue
    */
    public void makeNoGlued(){
        if (glued){
            glued = false;
        }
    }
    
    public boolean isGlued(){
        return glued;
    }
    /**
     * make a tile the gluedMidle of a Glue
     */
    public void makeGluedMidle(){
        if(!gluedMidle){
            gluedMidle = true;
        }
    }
    /**
     * make a tile no the gluedMidle of a Glue
     */
    public void makeNoGluedMidle(){
        if(gluedMidle){
            gluedMidle = false;
        }
    }
    
    public boolean isGluedMidle(){
        return gluedMidle;
    }
    
    public void setGlue(Glue newGlue){
        glue = newGlue;
    }
    /**
     * create a SquaredGlued, with the tile in the midle
     */
    public Glue getGlue(){
        return glue;
    }
    
    public String getColor(){
        return color;
    }
    
    public void setGluedMidleTile(Tile glueMidle){
        gluedMidleTile = glueMidle;
    }
    
    public Tile getGluedMidleTile(){
        return gluedMidleTile;
    }
    /**
     * get the GlueOfGlue of a glue.
     */
    public GlueOfGlue getGlueOfGlue(){
        if (isGlued()){
            Tile midle = getGluedMidleTile();
            GlueOfGlue glueOfGlue = midle.getGlue().getGlueOfGlue();
            return glueOfGlue;
        }
        return null;
    }

}
 

    