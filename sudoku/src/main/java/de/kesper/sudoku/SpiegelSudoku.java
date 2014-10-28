package de.kesper.sudoku;

/**
 * User: kesper
 * Date: 28.10.2014
 * Time: 13:26
 */
public class SpiegelSudoku {

    public static void main(String[] args) throws Exception {

        Grid g = new Grid();
        g.set(0,"020000003");
        g.set(1,"860007900");
        g.set(2,"300500800");
        g.set(3,"008030057");
        g.set(4,"090000030");
        g.set(5,"400700008");
        g.set(6,"100020470");
        g.set(7,"702000500");
        g.set(8,"905001000");

        Solver s = new Solver(g);
        s.run();



        g = new Grid();
        g.set(0,"508000041");
        g.set(1,"060090000");
        g.set(2,"000000702");
        g.set(3,"400080605");
        g.set(4,"027009000");
        g.set(5,"001000000");
        g.set(6,"004001020");
        g.set(7,"000706030");
        g.set(8,"000008009");

        s = new Solver(g);
        s.run();


        System.out.println("Minimal with 17 clues.");
        g = new Grid();
        g.set(0,"000801000");
        g.set(1,"000000043");
        g.set(2,"500000000");
        g.set(3,"000070800");
        g.set(4,"000000100");
        g.set(5,"020030000");
        g.set(6,"600000075");
        g.set(7,"003400000");
        g.set(8,"000200600");

        s = new Solver(g);
        s.run();
    }
}
