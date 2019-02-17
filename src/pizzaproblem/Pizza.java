package pizzaproblem;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class Pizza {

    public static final char MUSHROOM = 'M';
    public static final char TOMATO = 'T';
    public int rows;
    public int cols;
    public int numCells;
    public int numIngredients;
    public int maxCellsPerSlice;
    public char[][] cells;
    public int numSlices = 0;
    public HashMap<Integer, ArrayList<Slice>> slices = new HashMap();

    public Pizza(int rows, int cols, int numIngredients, int maxCellsPerSlice) {
        this.rows = rows;
        this.cols = cols;
        this.numCells = rows * cols;
        this.numIngredients = numIngredients;
        this.maxCellsPerSlice = maxCellsPerSlice;
        this.cells = new char[rows][cols];
    }

    public void setCell(int i, int j, char c) {
        cells[i][j] = c;
    }

    public char getCell(int i, int j) {
        return cells[i][j];
    }

    public void addSlice(int i, int j, Slice slice) {
        int index = this.calculateIndex(i, j);
        ArrayList<Slice> queue = slices.containsKey(index) ? slices.get(index) : new ArrayList();
        queue.add(slice);

        numSlices++;
        slices.put(index, queue);
    }

    public void sortSlices(int i, int j) {
        ArrayList<Slice> atPosition = slices.get(calculateIndex(i, j));
        if (atPosition != null) {
            Collections.sort(atPosition);
        }
    }

    public Slice getBestSlice(Point point) {
        return this.getBestSlice(point.i, point.j);
    }

    public Slice getBestSlice(int i, int j) {
        ArrayList<Slice> atPosition = slices.get(calculateIndex(i, j));
        if (atPosition == null || atPosition.isEmpty()) {
            return null;
        } else {
            Slice slice = atPosition.get(0);
            return slice;
        }
    }

    public Slice getSlice(Point point, int index) {
        return getSlice(point.i, point.j, index);
    }

    public Slice getSlice(int i, int j, int index) {
        ArrayList<Slice> atPosition = slices.get(calculateIndex(i, j));
        if (atPosition == null || atPosition.isEmpty() || index >= atPosition.size()) {
            return null;
        } else {
            Slice slice = atPosition.get(index);
            return slice;
        }
    }

    public boolean hasSlices(Point point) {
        return hasSlices(point.i, point.j);
    }

    public boolean hasSlices(int i, int j) {
        ArrayList<Slice> atPosition = slices.get(calculateIndex(i, j));
        return atPosition == null || !atPosition.isEmpty();
    }

    public int numSlices(int i, int j) {
        ArrayList<Slice> atPosition = slices.get(calculateIndex(i, j));
        return atPosition == null ? 0 : atPosition.size();
    }

    private int calculateIndex(int i, int j) {
        return (i * (Math.max(rows, cols))) + j;
    }

    public ArrayList<Slice> getSlices(Point point) {
        return getSlices(point.i, point.j);
    }

    public ArrayList<Slice> getSlices(int i, int j) {
        int index = this.calculateIndex(i, j);
        ArrayList<Slice> a = slices.get(index);
        return a == null ? new ArrayList() : new ArrayList(a);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                b.append(cells[i][j]);
            }
            b.append('\n');
        }
        return b.toString();
    }
}
