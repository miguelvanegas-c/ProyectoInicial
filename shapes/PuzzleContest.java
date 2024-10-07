import javax.swing.*;
import java.util.ArrayList;
/**
 * A simulator to solve puzzles.
 * 
 * @author Miguel Angel Vanegas Cardenas y Allan Steef Contreras
 * @version 1.0
 */
public class PuzzleContest{
    private static char [][] ciclos = {{'r','u','l','d','r'},
                                       {'r','d','l','u','r'},
                                       {'l','u','r','d','l'},
                                       {'l','d','r','u','l'},
                                       {'u','r','d','l','u'},
                                       {'u','l','d','r','u'},
                                       {'d','r','u','l','d'},
                                       {'d','l','u','r','d'}};
    
    /**
     * 
     */
    public static boolean solve(char [][] starting, char [][] ending){
        if (cicloFinal(starting,ending) != null){
            return true;
        }else{
            return false;
        }
    }
    /*
     * 
     */
    private static char[][] cloneStarting(char[][] starting, int height, int width){
        char [][] newStarting = new char[height][width];
        for (int i = 0; i < starting.length; i++) {
            newStarting[i] = starting[i].clone();
        }
        return newStarting;
    }
    /**
     * 
     */
    public static void simulate (char [][] starting, char [][] ending){
        Puzzle puzzle = new Puzzle(starting, ending);
        puzzle.makeVisible();
        ArrayList<Character> cicloFinal;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }
        if(solve(starting, ending)){
            cicloFinal = cicloFinal(starting, ending);
            for(char c : cicloFinal){
                puzzle.tilt(c);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("El hilo fue interrumpido.");
                }
            }
            JOptionPane.showMessageDialog(null, 
                                          "Se ha llegado a la solucion.", 
                                          "¡Final!", 
                                           JOptionPane.INFORMATION_MESSAGE);
            
        }else{
            JOptionPane.showMessageDialog(null, 
                                          "No es posible llegar a la solucion.", 
                                          "¡Final!", 
                                           JOptionPane.ERROR_MESSAGE);
        }
    }
    /*
     * 
     */
    private static ArrayList<Character> cicloFinal(char [][] starting, char [][] ending){
        ArrayList<Character> cicloFinal = new ArrayList<>();
        int height = starting.length;
        int width = starting.length;
        char [][] newStarting = cloneStarting(starting, height, width);
        Puzzle puzzle = new Puzzle(newStarting, ending);
        for(char[] ciclo : ciclos){
            for(char m: ciclo){
                puzzle.tilt(m);
                cicloFinal.add(m);
                if(puzzle.isGoal()){
                    return cicloFinal;
                }
            }
            cicloFinal =  new ArrayList<>();
            newStarting = cloneStarting(starting, height, width);
            puzzle = new Puzzle(newStarting, ending);
        }
        return null;    
    }
    
}
