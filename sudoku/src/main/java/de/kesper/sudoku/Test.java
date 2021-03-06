package de.kesper.sudoku;

/**
 * User: kesper
 * Date: 08.10.2014
 * Time: 16:48
 */
public class Test {
    public static void main(String[] args) throws Exception {

        System.out.println("Wikipedia Sudoku instance.");
        Grid g = new Grid();

        g.set(0,1,3);
        g.set(1,3,1);
        g.set(1,4,9);
        g.set(1,5,5);
        g.set(2,2,8);
        g.set(2,7,6);
        g.set(3,0,8);
        g.set(3,4,6);
        g.set(4,0,4);
        g.set(4,3,8);
        g.set(4,8,1);
        g.set(5,4,2);
        g.set(6,1,6);
        g.set(6,6,2);
        g.set(6,7,8);
        g.set(7,3,4);
        g.set(7,4,1);
        g.set(7,5,9);
        g.set(7,8,5);
        g.set(8,7,7);
        Solver s = new Solver(g);
        s.run();


        System.out.println("\n\nhard for brute force.");
        g = new Grid();
        g.set(1,5,3);
        g.set(1,7,8);
        g.set(1,8,5);
        g.set(2,2,1);
        g.set(2,4,2);
        g.set(3,3,5);
        g.set(3,5,7);
        g.set(4,2,4);
        g.set(4,6,1);
        g.set(5,1,9);
        g.set(6,0,5);
        g.set(6,7,7);
        g.set(6,8,3);
        g.set(7,2,2);
        g.set(7,4,1);
        g.set(8,4,4);
        g.set(8,8,9);

        s = new Solver(g);
        s.run();


        System.out.println("\n\nSupposed to be extreme");
        g = new Grid();
        g.set(0,1,5);
        g.set(0,4,6);
        g.set(0,7,8);
        g.set(1,0,9);
        g.set(1,3,1);
        g.set(1,5,8);
        g.set(1,8,2);
        g.set(2,2,1);
        g.set(2,6,9);
        g.set(3,1,6);
        g.set(3,4,3);
        g.set(3,7,2);
        g.set(4,0,4);
        g.set(4,3,5);
        g.set(4,5,2);
        g.set(4,8,8);
        g.set(5,1,7);
        g.set(5,4,1);
        g.set(5,7,4);
        g.set(6,2,7);
        g.set(6,6,8);
        g.set(7,0,5);
        g.set(7,3,7);
        g.set(7,5,1);
        g.set(7,8,3);
        g.set(8,1,9);
        g.set(8,4,5);
        g.set(8,7,7);

        s = new Solver(g);
        s.run();

        System.out.println("\n\nAnother hard one.");
        g = new Grid();
        g.set(0,"010009402");
        g.set(1,"400030900");
        g.set(2,"097100050");
        g.set(3,"061800000");
        g.set(4,"000070000");
        g.set(5,"000004120");
        g.set(6,"050003640");
        g.set(7,"004010005");
        g.set(8,"603400010");

        s = new Solver(g);
        s.run();

        System.out.println("\n\nThe Telegraph \"worlds hardest sudoku\"");
        g = new Grid();
        g.set(0,"800000000");
        g.set(1,"003600000");
        g.set(2,"070090200");
        g.set(3,"050007000");
        g.set(4,"000045700");
        g.set(5,"000100030");
        g.set(6,"001000068");
        g.set(7,"008500010");
        g.set(8,"090000400");

        s = new Solver(g);
        s.run();

        System.out.println("\n\nThe same but just 1, non unique solutions.");
        g = new Grid();
        g.set(0,"010000000");
        g.set(1,"000000100");
        g.set(2,"000001000");
        g.set(3,"100000000");
        g.set(4,"000000001");
        g.set(5,"000100000");
        g.set(6,"001000000");
        g.set(7,"000000010");
        g.set(8,"000010000");

        s = new Solver(g);
        s.setFirstSolutionOnly(false);
        s.setQuiet(true);
        s.run();
        g.print();
        System.out.println("Found "+s.getSolutions()+" solutions.");

        System.out.println("\n\nempty sudoku.");
        g = new Grid();
        g.set(0,"000000000");
        g.set(1,"000000000");
        g.set(2,"000000000");
        g.set(3,"000000000");
        g.set(4,"000000000");
        g.set(5,"000000000");
        g.set(6,"000000000");
        g.set(7,"000000000");
        g.set(8,"000000000");

        s = new Solver(g);
        s.run();

        System.out.println("\n\nSudoku with maximum empty field.");
        g = new Grid();
        g.set(0,"006703500");
        g.set(1,"000040000");
        g.set(2,"500000002");
        g.set(3,"900000007");
        g.set(4,"030000040");
        g.set(5,"800000001");
        g.set(6,"100000004");
        g.set(7,"000000000");
        g.set(8,"059267310");

        s = new Solver(g);
        s.run();

//        g = new Grid();
//        g.set("000000043004000000200006000000000000800000030000710020002060400001300060683000002");
//        s = new Solver(g);
//        s.run();

        g = new Grid();
        g.set("917000000000000000000000000000000000000000000000000000000000000000000000791000000");
        s = new Solver(g);
        s.run();

        System.out.println("A 'diabolical' sudoku by A.C.Stuart");
        g = new Grid();
        g.set(0,"074302000");
        g.set(1,"000005040");
        g.set(2,"000607900");
        g.set(3,"056000790");
        g.set(4,"300000005");
        g.set(5,"027000680");
        g.set(6,"005701000");
        g.set(7,"010200000");
        g.set(8,"000408160");

        s = new Solver(g);
        s.run();

    }
}
