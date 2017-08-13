/**
 * Created by ppyatetskyi on 8/10/17.
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    //private final SET<Point2D> kdTree;

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private Node root;
    private int size;

    private static class Node {
        // the point
        private Point2D point;

        // the axis-aligned rectangle corresponding to this node
        private RectHV rectangle;

        // the left/bottom subtree
        private Node leftBottom;

        // the right/top subtree
        private Node rightTop;

        public Node(Point2D p, RectHV rect) {

            this.point = p;
            this.rectangle = rect;
        }

        public String toString() {

            StringBuilder s = new StringBuilder();
            s.append(point.toString());
            s.append(" ");
            s.append(rectangle.toString());

            if (leftBottom != null) {
                s.append("\nleftBottom: ");
                s.append(leftBottom.toString());
            }

            if (rightTop != null) {
                s.append("\nrightTop: ");
                s.append(rightTop.toString());
            }

            return s.toString();
        }
    }

    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public  int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insert(root, p, VERTICAL, 0, 0, 1, 1);
    }

    private Node insert(Node node, Point2D p, boolean orientation, double xMin, double yMin, double xMax, double yMax) {

        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (node == null) {
            size++;
            return new Node(p, new RectHV(xMin, yMin, xMax, yMax));
        }

        if (node.point.equals(p)) {
            return node;
        }

        if (orientation == VERTICAL) {
            // compare x coordinates
            if (node.point.x() - p.x() > 0) {
                node.leftBottom = insert(node.leftBottom, p, HORIZONTAL,
                        node.rectangle.xmin(), node.rectangle.ymin(), node.point.x(), node.rectangle.ymax());
            } else {
                node.rightTop = insert(node.rightTop, p, HORIZONTAL,
                        node.point.x(), node.rectangle.ymin(), node.rectangle.xmax(), node.rectangle.ymax());
            }

        } else {
            // compare y coordinates
            if (node.point.y() - p.y() > 0) {
                node.leftBottom = insert(node.leftBottom, p, VERTICAL,
                        node.rectangle.xmin(), node.rectangle.ymin(), node.rectangle.xmax(), node.point.y());
            } else {
                node.rightTop = insert(node.rightTop, p, VERTICAL,
                        node.rectangle.xmin(), node.point.y(), node.rectangle.xmax(), node.rectangle.ymax());
            }
        }

        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node node, Point2D p, boolean orientation) {
        if (node == null) {
            return false;
        }

        if (node.point.equals(p)) {
            return true;
        }

        double compareCoordinates;

        if (orientation == VERTICAL) {
            compareCoordinates = p.x() - node.point.x();
        } else {
            compareCoordinates = p.y() - node.point.y();
        }

        if (compareCoordinates < 0) {
            return contains(node.leftBottom, p, !orientation);
        } else {
            return contains(node.rightTop, p, !orientation);
        }

    }

    // draw all points to standard draw
    public void draw() {
        draw(root, VERTICAL);
    }

    private void draw(Node node, boolean orientation) {
        if (orientation == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), node.rectangle.ymin(), node.point.x(), node.rectangle.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rectangle.xmin(), node.point.y(), node.rectangle.xmax(), node.point.y());
        }

        if (node.leftBottom != null) {
            draw(node.leftBottom, !orientation);
        }

        if (node.rightTop != null) {
            draw(node.rightTop, !orientation);
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        node.point.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<>();
        range(root, rect, q);
        return q;
    }

    private void range(Node node, RectHV rect, Queue<Point2D> q) {
        if (node == null) {
            return;
        }

        if (!node.rectangle.intersects(rect)) {
            return;
        }

        if (rect.contains(node.point)) {
            q.enqueue(node.point);
        }

        range(node.leftBottom, rect, q);

        range(node.rightTop, rect, q);
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        return nearest(root, p, Double.POSITIVE_INFINITY);
    }

    // Find the nearest point that is closer than distance
    private Point2D nearest(Node node, Point2D p, double distance) {
        if (node == null) {
            return null;
        }

        if (node.rectangle.distanceTo(p) >= distance) {
            return null;
        }

        Point2D nearestPoint = null;
        double nearestDistance = distance;
        double d;

        d = p.distanceTo(node.point);
        if (d < nearestDistance) {
            nearestPoint = node.point;
            nearestDistance = d;
        }

        // choose subtree that is closer to point
        Node firstNode = node.leftBottom;
        Node secondNode = node.rightTop;

        if (firstNode != null && secondNode != null) {
            if (firstNode.rectangle.distanceTo(p) > secondNode.rectangle.distanceTo(p)) {
                firstNode = node.rightTop;
                secondNode = node.leftBottom;
            }
        }

        Point2D firstNearestPoint = nearest(firstNode, p, nearestDistance);
        if (firstNearestPoint != null) {
            d = p.distanceTo(firstNearestPoint);
            if (d < nearestDistance) {
                nearestPoint = firstNearestPoint;
                nearestDistance = d;
            }
        }

        Point2D secondNearestPoint = nearest(secondNode, p, nearestDistance);
        if (secondNearestPoint != null) {
            d = p.distanceTo(secondNearestPoint);
            if (d < nearestDistance) {
                nearestPoint = secondNearestPoint;
                // value below may be used in recursive calls
                nearestDistance = d;
            }
        }

        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        StdOut.println("KdTree unit testing:");

        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.206107, 0.095492));
        kdtree.insert(new Point2D(0.975528, 0.654508));
        kdtree.insert(new Point2D(0.024472, 0.345492));
        kdtree.insert(new Point2D(0.793893, 0.095492));
        kdtree.insert(new Point2D(0.793893, 0.904508));
        kdtree.insert(new Point2D(0.975528, 0.345492));
        assert kdtree.size() == 6;
        kdtree.insert(new Point2D(0.206107, 0.904508));
        // StdOut.println(kdtree);
        assert kdtree.size() == 7;

        StdOut.println("size: " + kdtree.size());


    }
}
