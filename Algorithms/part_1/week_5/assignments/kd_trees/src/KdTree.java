/**
 * Created by ppyatetskyi on 8/10/17.
 */

import java.util.Stack;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    //private final SET<Point2D> rbBST;

    // construct an empty set of points
    public KdTree() {
        //rbBST = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return false;
    }

    // number of points in the set
    public  int size() {
        return 0;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {

        return false;
    }

    // draw all points to standard draw
    public  void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
