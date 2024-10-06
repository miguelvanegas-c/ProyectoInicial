import javax.swing.*;
/**
 * Write a description of class PuzzleContest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
        char[] cicloFinal;
        if(solve(starting, ending)){
            cicloFinal = cicloFinal(starting, ending);
            for(char c : cicloFinal){
                puzzle.tilt(c);
                try {
                Thread.sleep(2000);
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
    private static char[] cicloFinal(char [][] starting, char [][] ending){
        char [] cicloFinal = new char[5];
        int height = starting.length;
        int width = starting.length;
        char [][] newStarting = cloneStarting(starting, height, width);
        Puzzle puzzle = new Puzzle(newStarting, ending);
        int count = 0;
        for(char[] ciclo : ciclos){
            for(char m: ciclo){
                puzzle.tilt(m);
                cicloFinal[count] = m;
                count++;
                if(puzzle.isGoal()){
                    return cicloFinal;
                }
            }
            count = 0;
            cicloFinal =new char[5];
            newStarting = cloneStarting(starting, height, width);
            puzzle = new Puzzle(newStarting, ending);
        }
        return null;    
    }
    
}
