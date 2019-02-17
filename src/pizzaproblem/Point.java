package pizzaproblem;

/**
 *
 * @author
 */
public class Point {

    public int i;
    public int j;

    public Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int xDistanceFrom(Point point) {
        return Math.abs(i - point.i);
    }

    public int yDistanceFrom(Point point) {
        return Math.abs(j - point.j);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", i, j);
    }

}
