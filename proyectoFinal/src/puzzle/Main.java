package puzzle;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        acceptanceTestSolveAndSimulateThePuzzleByMiguel();
        acceptanceTestAllTheGlueByAllan();
    }
    private static void acceptanceTestSolveAndSimulateThePuzzleByMiguel() {
        char[][] starting = {{'.', 'b', '.', '.', '.', 'm'},
                {'.', '.', 'b', '.', '.', '.'},
                {'.', '.', 'y', '.', '.', 'b'},
                {'g', '.', '.', '.', '.', '.'},
                {'.', '.', 'g', '.', '.', 'b'},
                {'.', '.', '.', '.', '.', '.'},
                {'b', '.', '.', 'm', '.', 'y'}};
        char[][] startingForPuzzleContest = {{'.', 'b', '.', '.', '.', 'm'},
                {'.', '.', 'b', '.', '.', '.'},
                {'.', '.', 'y', '.', '.', 'b'},
                {'g', '.', '.', '.', '.', '.'},
                {'.', '.', 'g', '.', '.', 'b'},
                {'.', '.', '.', '.', '.', '.'},
                {'b', '.', '.', 'm', '.', 'y'}};
        char[][] ending = {{'r', 'b', 'b', '.', '.', '.'},
                {'r', '.', '.', '.', '.', '.'},
                {'r', '.', '.', '.', '.', '.'},
                {'g', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.'}};
        Puzzle puzzle = new Puzzle(starting, ending);
        puzzle.makeVisible();
        JOptionPane.showMessageDialog(null, "La aplicacion seguira cuando oprimas ok");
        puzzle.tilt('r');
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }
        puzzle.tilt('d');
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }
        puzzle.tilt('l');
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }
        puzzle.tilt('u');
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }

        char[][] endingToPuzzleContest = puzzle.getStartingMatriz();
        JOptionPane.showMessageDialog(null, "La aplicacion seguira cuando oprimas ok");
        puzzle.makeInvisible();
        PuzzleContest puzzleContest = new PuzzleContest();
        puzzleContest.simulate(startingForPuzzleContest, endingToPuzzleContest);
        char[][] contestCaseOneStarting = {{'.', 'r', '.', '.'}, {'r', 'g', 'y', 'b'}, {'.', 'b', '.', '.'}, {'.', 'y', 'r', '.'}};
        char[][] contestCaseOneEnding = {{'y', 'r', 'b', 'r'}, {'.', '.', 'y', 'r'}, {'.', '.', '.', 'g'}, {'.', '.', '.', 'b'}};
        puzzleContest.simulate(contestCaseOneStarting, contestCaseOneEnding);
        char[][] contestCaseTwoStarting = {{'y', 'r', '.'}, {'.', '.', 'b'}, {'r', 'y', '.'}, {'b', '.', '.'}};
        char[][] contestCaseTwoEnding = {{'.', '.', '.'}, {'.', '.', 'b'}, {'.', 'r', 'y'}, {'b', 'y', 'b'}};
        puzzleContest.simulate(contestCaseTwoStarting, contestCaseTwoEnding);
        JOptionPane.showMessageDialog(null, "Finaliza la primera prueba de aceptacion");

    }
    private static void acceptanceTestAllTheGlueByAllan(){
        char[][] starting = {{'.', 'b', '.', '.', '.', 'm'},
                {'.', '.', 'b', '.', '.', '.'},
                {'.', '.', 'y', '.', '.', 'b'},
                {'g', '.', '.', '.', '.', '.'},
                {'.', '.', 'g', '.', '.', 'b'},
                {'.', '.', '.', '.', '.', '.'},
                {'b', '.', '.', 'm', '.', 'y'}};
        char[][] ending = {{'r', 'b', 'b', '.', '.', '.'},
                {'r', '.', '.', '.', '.', '.'},
                {'r', '.', '.', '.', '.', '.'},
                {'g', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.'}};
        Puzzle puzzle = new Puzzle(starting,ending);
        puzzle.makeVisible();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }
        puzzle.tilt('r');
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }
        JOptionPane.showMessageDialog(null, "se añade el primer pegante en la fila uno columna 6");
        puzzle.addGlue(1,6,1);
        puzzle.tilt('d');
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }
        JOptionPane.showMessageDialog(null, "se añade el segundo pegante en la fila 4 columna 6");
        puzzle.addGlue(4,6,1);
        puzzle.tilt('l');
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }
        puzzle.tilt('u');
        JOptionPane.showMessageDialog(null, "se eliminan los pegantes");
        puzzle.deleteGlue(1,2);
        puzzle.deleteGlue(3,2);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }
        puzzle.tilt('d');
        JOptionPane.showMessageDialog(null, "se añade otro pegante pero ahora fragil");
        puzzle.addGlue(4,2,2);
        puzzle.tilt('u');
        JOptionPane.showMessageDialog(null, "se comprueba que se halla roto el pegante despues del movimiento");
        puzzle.tilt('u');
        JOptionPane.showMessageDialog(null, "finaliza la prueba dos");
        puzzle.tilt('r');
        puzzle.makeInvisible();
    }
}
