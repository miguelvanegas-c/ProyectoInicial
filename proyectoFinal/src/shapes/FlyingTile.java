package shapes;
import puzzle.*;
/**
 * Is a tile that is flying over the puzzle .
 * 
 * @author Miguel Angel Vanegas y Allan Steef Contreras
 * @version 1.0
 */
public class FlyingTile extends Tile{
    /**
     * Constructor for objects of class FlyingTile
     */
    public FlyingTile(){
        super();
        moveVertical(- 3);
        moveHorizontal(- 3);
    }
}
