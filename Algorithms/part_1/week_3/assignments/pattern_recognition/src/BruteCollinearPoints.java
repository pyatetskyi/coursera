/**
 * Coursera: Algorithms
 * Part 1 - Week 3: Pattern Recognition
 *
 * @author Pavlo Pyatetskyi
 */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

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
       segments = new ArrayList<>();

       /*
       System.out.println("\nSorted output:");
       for (Point p : pSorted) {
           System.out.println(p.toString());
       }
       */

       for (int i = 0; i < size - 3; i++) {
           for (int j = i + 1; j < size - 2; j++) {
               for (int k = j + 1; k < size - 1; k++) {
                   for (int m = k + 1; m < size; m++) {

                       double slope1 = pSorted[i].slopeTo(pSorted[j]);
                       double slope2 = pSorted[i].slopeTo(pSorted[k]);

                       if (Double.compare(slope1, slope2) == 0) {

                           double slope3 = pSorted[i].slopeTo(pSorted[m]);

                           if (Double.compare(slope1, slope3) == 0) {
                               // System.out.println("\nTest:");
                               // System.out.println("i, j = " + i + ", " + j + " slope = " + p_sorted[i].slopeTo(p_sorted[j]));
                               // System.out.println("k, m = " + k + ", " + m + " slope = " + p_sorted[k].slopeTo(p_sorted[m]));
                               segments.add(new LineSegment(pSorted[i], pSorted[m]));
                           }
                       }
                   }
               }
           }
       }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

}
