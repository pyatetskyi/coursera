/**
 * Created by Pavlo Pyatetskyi on 8/10/17.
 */

import java.util.Stack;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

    private final SET<Point2D> rbBST;

    // construct an empty set of points
    public PointSET() {
        rbBST = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return rbBST.isEmpty();
    }

    // number of points in the set
    public  int size() {
        return rbBST.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {

        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (!rbBST.contains(p)) {
            rbBST.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {

        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        return rbBST.contains(p);
    }

    // draw all points to standard draw
    public  void draw() {

        for (Point2D p : rbBST) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        Stack<Point2D> stack = new Stack<>();

        for (Point2D p : rbBST) {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax()
                    && p.y() >= rect.ymin() && p.y() <= rect.ymax()) {
                stack.push(p);
            }
        }

        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        double maxDist = Double.MAX_VALUE;
        Point2D nearestPoint = new Point2D(0,0);

        for (Point2D point : rbBST) {

            double newDist = Math.sqrt(Math.pow(point.x() - p.x(), 2) + Math.pow(point.y() - p.y(), 2));

            if (maxDist > newDist) {
                maxDist = newDist;
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        PointSET bruteForce = new PointSET();

        // manual entry of points from file circle10.txt
        Point2D p1 = new Point2D(0.206107, 0.095492);
        Point2D p2 = new Point2D(0.975528, 0.654508);
        Point2D p3 = new Point2D(0.024472, 0.345492);
        Point2D p4 = new Point2D(0.793893, 0.095492);
        Point2D p5 = new Point2D(0.793893, 0.904508);
        Point2D p6 = new Point2D(0.975528, 0.345492);
        Point2D p7 = new Point2D(0.206107, 0.904508);
        Point2D p8 = new Point2D(0.500000, 0.000000);
        Point2D p9 = new Point2D(0.024472, 0.654508);
        Point2D pt = new Point2D(0.500000, 1.000000);

        bruteForce.insert(p1);
        bruteForce.insert(p2);
        bruteForce.insert(p3);
        bruteForce.insert(p4);
        bruteForce.insert(p5);
        bruteForce.insert(p6);
        bruteForce.insert(p7);
        bruteForce.insert(p8);
        bruteForce.insert(p9);
        bruteForce.insert(pt);

        // create rectangle
        RectHV rect = new RectHV(0.005, 0.5, 0.995, 0.95);
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        rect.draw();
        bruteForce.draw();

        // redraw points in rectangle
        StdDraw.setPenRadius(.02);
        StdDraw.setPenColor(StdDraw.RED);
        for (Point2D p : bruteForce.range(rect)) {
            p.draw();
        }

        // goal point
        Point2D goal = new Point2D(0.45, 0.65);

        // color for goal
        StdDraw.setPenColor(StdDraw.CYAN);
        bruteForce.nearest(goal).draw();

        // color for nearest to goal
        StdDraw.setPenColor(StdDraw.ORANGE);
        goal.draw();
    }
}
