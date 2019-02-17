package pizzaproblem;

import java.util.ArrayList;

/**
 *
 * @author
 */
public class SliceSolution {

    public int area;
    public ArrayList<Slice> slices = new ArrayList();

    @Override
    public String toString() {
        return "Area = " + area + " [" + slices + "]";
    }

}
