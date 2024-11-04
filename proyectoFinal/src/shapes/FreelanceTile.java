package shapes;
import puzzle.*;
/**
 * A tile that can´t be glued.
 * 
 * @author Miguel Angel Vanegas Cardenas y Allan Steef Contreras.
 * @version 1.0.
 */
public class FreelanceTile extends Tile{
    
    /**
     * Constructor of FreeLanceTile.
     */
    public FreelanceTile(){
        super();
        changeSize(29,29);
        moveVertical(10);
        moveHorizontal(10); 
    }
}
