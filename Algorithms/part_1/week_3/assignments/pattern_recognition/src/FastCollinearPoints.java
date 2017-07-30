import java.util.ArrayList;
import java.util.Arrays;

/**
 * Coursera: Algorithms
 * Part 1 - Week 3: Pattern Recognition
 *
 * @author Pavlo Pyatetskyi
 */

public class FastCollinearPoints {

    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        // corner case: parameter not set
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        int size = points.length;

        // corner case: parameter set incorrectly
        for (int i = 0; i < size; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }

            for (int j = 0; j < size; j++) {
                if (i != j && points[i].compareTo(points[j]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }

        Point[] pSorted = points.clone();
        Arrays.sort(pSorted);
        ArrayList<LineSegment> ls = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Point[] pSortedSlope = pSorted.clone();

            // Sorting the points according to the slopes they make with p_sorted.
            Arrays.sort(pSortedSlope, pSorted[i].slopeOrder());

            /*
            System.out.println("\ni = " + i + ":");
            for (Point p : p_sorted_slope) {
                String s = p.toString()
                         + " && slope with "
                         + p_sorted_slope[i].toString()
                         + " = ";

                if (p.compareTo(p_sorted_slope[i]) != 0) {
                    s += p.slopeTo(p_sorted_slope[i]);
                }
                else {
                    s += p.slopeTo(p_sorted_slope[i]); //"self";
                }

                System.out.println(s);

            }
            */

            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p. If so, these points,
            // together with p, are collinear.

            int j = 1;
            while (j < size - 2) {
                int k = j;
                double slope1 = pSortedSlope[0].slopeTo(pSortedSlope[k++]);
                double slope2;
                do {
                    if (k == size) {
                        k++;
                        break;
                    }
                    slope2 = pSortedSlope[0].slopeTo(pSortedSlope[k++]);
                } while (Double.compare(slope1, slope2) == 0);
                if (k - j < 4) {
                    j++;
                    continue;
                }
                int len = k-- - j;
                Point[] line = new Point[len];
                line[0] = pSortedSlope[0];

                for (int m = 1; m < len; m++) {
                    line[m] = pSortedSlope[j + m - 1];
                }
                Arrays.sort(line);

                // removing duplicates
                if (line[0] == pSortedSlope[0]) {
                    ls.add(new LineSegment(line[0], line[len - 1]));
                }
                j = k;
            }
        }

        // saving as array
        segments = ls.toArray(new LineSegment[ls.size()]);

    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }
}
