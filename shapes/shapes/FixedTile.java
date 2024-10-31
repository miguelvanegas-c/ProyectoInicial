package shapes; 
import puzzle.*;
/**
 * Is a tile that can´t be deleted or ralocated.
 * 
 * @author Miguel Angel Vanegas Cardenas y Allan Steef Contreras
 * @version 1.0
 */
public class FixedTile extends Tile{
    private  Tile[] representacionGrafica = new Tile[2];
    
    /**
     * Constructor of a fixedTile.
     */
    public FixedTile(){
        super();
        Tile rightTile = new Tile();
        rightTile.changeColor('r');
        rightTile.changeSize(49,24);
        rightTile.moveHorizontal(25);
        Tile leftTile = new Tile();
        leftTile.changeColor('b');
        leftTile.changeSize(49,24);
        representacionGrafica[1] = rightTile;
        representacionGrafica[0] = leftTile;
    }
    
    /**
     * Make visible the fixedTile
     */
    @Override
    public void makeVisible(){
        for (Tile t: representacionGrafica){
            t.makeVisible();    
        }
    }
    
    /**
     * Make invisible the fixedTile
     */
    @Override
    public void makeInvisible(){
        for (Tile t: representacionGrafica){
            t.makeInvisible();    
        }
    }
    
    /**
     * Horizontal move the fixedTile in the puzzle.
     * @param posicion, the horizontal position in the puzzle 
     */
    @Override
    public void puzzleMoveHorizontal(int posicion){
        super.puzzleMoveHorizontal(posicion);
        for (Tile t: representacionGrafica){
            t.puzzleMoveHorizontal(posicion); 
        }
    }
    
    /**
     * Vertical move the fixedTile in the puzzle.
     * @param posicion, the vertical position in the puzzle 
     */
    @Override
    public void puzzleMoveVertical(int posicion){
        super.puzzleMoveVertical(posicion);
        for (Tile t: representacionGrafica){
            t.puzzleMoveVertical(posicion); 
        }
    }
}
