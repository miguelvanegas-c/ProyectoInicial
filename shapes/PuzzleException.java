
/**
 * Write a description of class PuxxleExceptions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PuzzleException extends Exception{
    public static final String ERROR_TAMAÑO = "El tablero inicial y el tablero final deben tener la misma longitud";
    
    
    public PuzzleException(String message){
        super(message);
    }
    
}
