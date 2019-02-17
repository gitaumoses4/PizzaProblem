package pizzaproblem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author
 */
public class Solution {

    private HashMap<Integer, HashMap<Integer, Slice>> slices = new HashMap();
    private ArrayList<Slice> result = new ArrayList();
    private final int cols;
    private final int rows;

    private char[][] cells;
    private int area = 0;

    public Solution(Pizza pizza) {
        this.rows = pizza.rows;
        this.cols = pizza.cols;
        this.cells = new char[rows][cols];
    }

    public void add(Slice slice) {
        int startIndex = index(slice.getPoint());
        int endIndex = index(slice.getBottomRight());
        HashMap<Integer, Slice> map = slices.containsKey(startIndex)
                ? slices.get(startIndex) : new HashMap();
        map.put(endIndex, slice);

        for (int i = slice.i1; i < slice.i2; i++) {
            for (int j = slice.j1; j < slice.j2; j++) {
                cells[i][j] = slice.cells[i - slice.i1][j - slice.j1];
                area++;
            }
        }

        result.add(slice);
        slices.put(startIndex, map);
    }

    public void remove(Slice slice) {
        int startIndex = index(slice.getPoint());
        int endIndex = index(slice.getBottomRight());

        if (slices.containsKey(startIndex)) {
            slices.get(startIndex).remove(endIndex);
            for (int i = slice.i1; i < slice.i2; i++) {
                for (int j = slice.j1; j < slice.j2; j++) {
                    cells[i][j] = '\0';
                    area--;
                }
            }
        }
        result.remove(slice);
    }

    public boolean canAddSlice(Slice slice) {
        return canAddSlice(slice.getPoint(), slice.getBottomRight());
    }

    public boolean overlaps(int i1, int j1, int i2, int j2) {
        for (int i = i1; i < i2; i++) {
            for (int j = j1; j < j2; j++) {
                if (cells[i][j] == Pizza.MUSHROOM
                        || cells[i][j] == Pizza.TOMATO) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canAddSlice(Point point, Point end) {
        return point.i < rows && point.j < cols && !overlaps(point.i, point.j, end.i, end.j);
    }

    private int index(Point point) {
        return (point.i * (Math.max(rows, cols))) + point.j;
    }

    public void clear() {
        this.slices.clear();
        this.cells = new char[rows][cols];
        area = 0;
    }

    public int getArea() {
        return area;
    }

    public ArrayList<Slice> getSlices() {
        return new ArrayList(this.result);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = cells[i][j];
                b.append(c);
            }
            b.append("\n");
        }
        return b.toString() + "\n";
    }

}
