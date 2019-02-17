package pizzaproblem;

/**
 *
 * @author
 */
public class Slice implements Comparable<Slice> {

    public int i1;
    public int i2;
    public int j1;
    public int j2;
    private final Pizza pizza;
    public char[][] cells;

    public int rows;
    public int cols;
    public int numCells;

    public int numMushrooms;
    public int numTomatoes;

    public Slice(Pizza pizza) {
        this.pizza = pizza;
    }

    public boolean cut(int i1, int j1, int i2, int j2) {
        this.i1 = i1;
        this.i2 = i2;
        this.j1 = j1;
        this.j2 = j2;
        this.rows = Math.abs(i1 - i2);
        this.cols = Math.abs(j1 - j2);
        this.numCells = rows * cols;

        if (numCells <= pizza.maxCellsPerSlice) {
            this.cells = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    cells[i][j] = pizza.getCell(i1 + i, j1 + j);
                    numMushrooms += cells[i][j] == Pizza.MUSHROOM ? 1 : 0;
                    numTomatoes += cells[i][j] == Pizza.TOMATO ? 1 : 0;
                }
            }
            return true;
        }
        return false;
    }

    public boolean valid() {
        return numMushrooms >= pizza.numIngredients
                && numTomatoes >= pizza.numIngredients
                && numCells <= pizza.maxCellsPerSlice;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("─────────────────────────────────────────\n");
        for (int a = 0; a < 8; a++) {
            b.append(' ');
        }
        for (int i = 0; i < cols; i++) {
            if (i == 0) {
                b.append('┌');
            }
            b.append('─');
            if (i == cols - 1) {
                b.append('┐');
            }
        }
        b.append("\n");
        for (int i = 0; i < rows; i++) {
            if (i == 0) {
                b.append(String.format("(%-2d, %-2d)", i1, j1));
            } else if (i == 1) {
                b.append(String.format("(%-2d, %-2d)", i2, j2));
            } else {
                for (int a = 0; a < 8; a++) {
                    b.append(' ');
                }
            }
            b.append("│");
            for (int j = 0; j < cols; j++) {
                b.append(cells[i][j]);
            }
            b.append("│");
            b.append('\n');
        }
        for (int a = 0; a < 8; a++) {
            b.append(' ');
        }
        for (int i = 0; i < cols; i++) {
            if (i == 0) {
                b.append('└');
            }
            b.append('─');
            if (i == cols - 1) {
                b.append('┘');
            }
        }
        b.append("\nRows: ").append(rows).append(" Cols: ").append(cols);
        b.append("\n─────────────────────────────────────────");
        return "\n" + b.toString() + "\n";
    }

    @Override
    public int compareTo(Slice o) {
        return Integer.compare(this.numCells, o.numCells);
    }

    public Point getPoint() {
        return new Point(i1, j1);
    }

    public Point getTopRight() {
        return new Point(i1, j2);
    }

    public Point getBottomLeft() {
        return new Point(i2, j1);
    }

    public Point getBottomRight() {
        return new Point(i2, j2);
    }

    public Slice getBottomLeftSlice() {
        return pizza.getBestSlice(this.getBottomLeft());
    }

    public Slice getBottomRightSlice() {
        return pizza.getBestSlice(this.getBottomRight());
    }

    public Slice getTopRightSlice() {
        return pizza.getBestSlice(this.getTopRight());
    }

    public Slice getTopLeftSlice() {
        return pizza.getBestSlice(this.getPoint());
    }
}
