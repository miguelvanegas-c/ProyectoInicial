import java.awt.*;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 1.0  (15 July 2000)()
 */


 
public class Rectangle{

    public static int EDGES = 4;
    
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
    private boolean pegante;
    private CuadrosPegados pegados;

    /**
     * Create a new rectangle at default position with default color.
     */
    public Rectangle(){
        height = 30;
        width = 40;
        xPosition = 70;
        yPosition = 15;
        color = "magenta";
        isVisible = false;
        pegante = false;
    }
    
    /**
     * Make a square with the rectangle.
     */
    public void tile(){
        changeSize(50, 50);
    }
    /**
     * Make this rectangle visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this rectangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the rectangle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the rectangle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the rectangle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the rectangle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the rectangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the rectangle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the rectangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the rectangle vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
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
     * Draw the rectangle with current specifications on screen.
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
     * Erase the rectangle on screen.
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
     * @return the nes color String. Must be "red","yellow","blue","green" 
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
}

    