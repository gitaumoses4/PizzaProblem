package pizzaproblem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PizzaProblem {

    private static final String A = "a_example";
    private static final String B = "b_small";
    private static final String C = "c_medium";
    private static final String D = "d_big";

    private static Pizza pizza;
    private static Solution solution;

    private static ArrayList<Slice> best;
    private static int greatestArea = 0;

    public static void main(String[] args) throws FileNotFoundException {
        String f = B;
        readInputFile(new File(f + ".in"));
        solution = new Solution(pizza);

        System.out.println(pizza.maxCellsPerSlice);
        for (int i = 0; i < pizza.rows; i++) {
            for (int j = 0; j < pizza.cols; j++) {
                for (int k = 1; k <= pizza.maxCellsPerSlice && (i + k <= pizza.rows); k++) {
                    if (pizza.maxCellsPerSlice % k == 0) {
                        for (int l = 1; l <= pizza.maxCellsPerSlice / k && (j + l <= pizza.cols); l++) {
                            Slice slice = new Slice(pizza);
                            if (slice.cut(i, j, i + k, j + l) && slice.valid()) {
                                pizza.addSlice(i, j, slice);
                            }
                        }
                    }
                }
            }
        }
//        System.out.println(pizza.slices);

        solve();
        System.out.println(greatestArea);
        printOutput(System.out);

        printOutput(new FileOutputStream(new File(f + ".txt")));
    }

    public static void printOutput(OutputStream stream) {
        PrintWriter out = new PrintWriter(stream);
        out.println(best.size());
        for (Slice slice : best) {
            out.printf("%d %d %d %d\n", slice.i1, slice.j1, slice.i2 - 1, slice.j2 - 1);
        }

        out.flush();
        out.close();
    }

    public static void solve() {
        ArrayList<Slice> slices = pizza.getSlices(0, 0);
        for (int i = 0; i < slices.size(); i++) {
            System.out.printf("%d out of %d\n", i, slices.size());
            solve(slices.get(i));
        }
    }

    public static void solve(Slice slice) {
        if (solution.canAddSlice(slice)) {
            solution.add(slice);
            for (Slice bottom : pizza.getSlices(slice.getBottomLeft())) {
                solve(bottom);
            }
            for (Slice top : pizza.getSlices(slice.getTopRight())) {
                solve(top);
            }
            if (solution.getArea() > greatestArea) {
                best = solution.getSlices();
                greatestArea = solution.getArea();
            }
            solution.remove(slice);
        }
    }

    private static void readInputFile(File file) {
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(file)));
            int rows = in.nextInt();
            int cols = in.nextInt();
            int numIngredients = in.nextInt();
            int maxPerSlice = in.nextInt();

            pizza = new Pizza(rows, cols, numIngredients, maxPerSlice);

            for (int i = 0; i < rows; i++) {
                String line = in.next().trim();
                if (!line.isEmpty()) {
                    for (int j = 0; j < cols; j++) {
                        pizza.setCell(i, j, line.charAt(j));
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PizzaProblem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
