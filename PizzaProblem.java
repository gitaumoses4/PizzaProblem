package pizzaproblem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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

    private static SliceSolution best = new SliceSolution();
    private static int greatestArea = 0;
    private static HashMap<Slice, SliceSolution> data = new HashMap();

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
        out.println(best.slices.size());
        for (Slice slice : best.slices) {
            out.printf("%d %d %d %d\n", slice.i1, slice.j1, slice.i2 - 1, slice.j2 - 1);
        }

        out.flush();
        out.close();
    }

    public static void solve() {
        ArrayList<Slice> slices = pizza.getSlices(0, 0);
        for (int i = 0; i < slices.size(); i++) {
            SliceSolution s = solve(slices.get(i));
            if (s.area > best.area) {
                best = s;
            }
            System.out.println(s.area);
        }
    }

    public static SliceSolution solve(Slice slice) {
        SliceSolution s = new SliceSolution();
        if (data.containsKey(slice)) {
            return data.get(slice);
        }
        if (solution.canAddSlice(slice)) {
            solution.add(slice);
            s.slices.add(slice);
            s.area = slice.numCells;
            int bottomArea = 0;
            ArrayList<Slice> ba = new ArrayList();
            for (Slice bottom : pizza.getSlices(slice.getBottomLeft())) {
                SliceSolution sb = solve(bottom);
                if (sb.area > bottomArea) {
                    bottomArea = sb.area;
                    ba = new ArrayList(sb.slices);
                }
            }
            int topArea = 0;
            ArrayList<Slice> ta = new ArrayList();
            for (Slice top : pizza.getSlices(slice.getTopRight())) {
                SliceSolution st = solve(top);
                if (st.area > topArea) {
                    topArea = st.area;
                    ta = new ArrayList(st.slices);
                }
            }
            s.area += topArea + bottomArea;
            System.out.println(s.area);
            s.slices.addAll(ta);
            s.slices.addAll(ba);
            solution.remove(slice);

            data.put(slice, s);
        }
        return s;
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
