package puzzle;
/**
 * The exception to the class Puzzle.
 * 
 * @author Miguel Angel Vanegas Cardenas y Allan Steef Contreras.
 * @version 1.0
 */
public class PuzzleException extends Exception{
    
    public static final String ERROR_BOARD_SIZE = "El tablero inicial y el tablero final deben tener la misma longitud";
    public static final String ERROR_OUT_RANGE = "los valores ingresados para la fila y la columna se encuentran fuera del rango del puzzle";
    public static final String ERROR_SPACE_NO_EMPTY = "En esa posicion ya se encuentra una baldoza o un agujero";
    public static final String ERROR_NO_TILE_DELETE = "En esa posicion no se encuentra ninguna baldoza para eliminar";
    public static final String ERROR_TILE_GLUED = "En esa posicion se encuentra una baldoza, pero esta pegada, por lo tanto no se puede eliminar ";
    public static final String ERROR_TILE_FIXED = "la baldoza es de tipo fixed, lo que quiere decir que no se puede eliminar ";
    public static final String ERROR_SPACE_EMPTY = "en esa posicion no se encuentra ninguna baldoza";
    public static final String ERROR_TILE_GLUED_MIDLE = "esa baldoza ya tiene un pegante agregado";
    public static final String ERROR_TILE_FREELANCE_FLYING  = "esa baldoza no se puede pegar debido a que es del tipo freelance o flying"; 
    public static final String ERROR_TILE_NOT_GLUED_MIDLE = "No se le ha aplicado pegante a esta baldoza";
    public static final String ERROR_WRONG_DIRECTION = "La direccion es incorrecta";
    public static final String ERROR_NOT_INTELLIGENT_TILT = "No fue posible determinar un ladeo que permita avanzar para finalizar el puzzle";
    
    public PuzzleException(String message){
        super(message);
    }
    
}
