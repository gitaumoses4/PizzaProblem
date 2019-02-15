package pizzaproblem;

public class Pizza {

    public static final char MUSHROOM = 'M';
    public static final char TOMATO = 'T';
    public int rows;
    public int cols;
    public int numIngredients;
    public int maxCellsPerSlice;
    public char[][] cells;

    public Pizza(int rows, int cols, int numIngredients, int maxCellsPerSlice) {
        this.rows = rows;
        this.cols = cols;
        this.numIngredients = numIngredients;
        this.maxCellsPerSlice = maxCellsPerSlice;
        this.cells = new char[rows][cols];
    }

    public void setCell(int i, int j, char c) {
        cells[i][j] = c;
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
