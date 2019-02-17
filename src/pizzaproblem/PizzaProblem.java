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

    private static Pizza pizza;
    private static char[][] solution;

    public static void main(String[] args) {
        readInputFile(A);
        solution = new char[pizza.rows][pizza.cols];

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
        printResult();
    }

    private static void clear() {
        solution = new char[pizza.rows][pizza.cols];
    }

    public static void solve() {
        solve(new Point(0, 0), 0);
    }

    public static void placeSlice(Slice slice) {
        for (int i = slice.i1; i < slice.i2; i++) {
            for (int j = slice.j1; j < slice.j2; j++) {
                solution[i][j] = slice.cells[i - slice.i1][j - slice.j1];
            }
        }
    }

    public static void removeSlice(Slice slice) {
        for (int i = slice.i1; i < slice.i2; i++) {
            for (int j = slice.j1; j < slice.j2; j++) {
                solution[i][j] = '\0';
            }
        }
    }

    public static boolean overlaps(Slice slice) {
        for (int i = slice.i1; i < slice.i2; i++) {
            for (int j = slice.j1; j < slice.j2; j++) {
                if (solution[i][j] == Pizza.MUSHROOM
                        || solution[i][j] == Pizza.TOMATO) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean solve(Point point, int current) {
        int numSlices = pizza.numSlices(point.i, point.j);
        boolean bottom = false, right = false;
        Slice slice = pizza.getSlice(point, current);
        System.out.println(slice);
        if (overlaps(slice)) {
            if (current < numSlices - 1) {
                return solve(point, current + 1);
            } else {
                return false;
            }
        }
        placeSlice(slice);
        if (slice.getBottomLeft().i < pizza.rows
                && slice.getTopRight().j < pizza.cols) {
            Slice bottomLeft = slice.getBottomLeftSlice();
            Slice topRight = slice.getTopRightSlice();

            if (bottomLeft != null) {
                bottom = solve(bottomLeft.getPoint(), 0);
            }
            if (topRight != null) {
                right = solve(topRight.getPoint(), 0);
            }
            if (bottom && right) {
                return true;
            } else {
                if (current < numSlices - 1) {
                    removeSlice(slice);
                    return solve(point, current + 1);
                } else {
                    return false;
                }
            }
        } else {
            return true;
        }
    }

    public static void printResult() {
        for (int i = 0; i < pizza.rows; i++) {
            for (int j = 0; j < pizza.cols; j++) {
                char c = solution[i][j];
                System.out.print(c);
            }
            System.out.println("");
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
