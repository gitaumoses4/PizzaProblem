package pizzaproblem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PizzaProblem {

    private static final File A = new File("a_example.in");
    private static final File B = new File("b_small.in");
    private static final File C = new File("c_medium.in");
    private static final File D = new File("d_big.in");

    public static void main(String[] args) {
        Pizza pizza = readInputFile(B);

        System.out.println(pizza);
    }

    private static Pizza readInputFile(File file) {
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(file)));
            int rows = in.nextInt();
            int cols = in.nextInt();
            int numIngredients = in.nextInt();
            int maxPerSlice = in.nextInt();

            Pizza pizza = new Pizza(rows, cols, numIngredients, maxPerSlice);

            for (int i = 0; i < rows; i++) {
                String line = in.nextLine().trim();
                if (!line.isEmpty()) {
                    for (int j = 0; j < cols; j++) {
                        pizza.setCell(i, j, line.charAt(i));
                    }
                }
            }

            return pizza;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PizzaProblem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
