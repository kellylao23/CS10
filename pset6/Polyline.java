import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 * @author Kelly Lao, Shane Wattenmaker
 */
public class Polyline implements Shape {
    // TODO: YOUR CODE HERE
    private List<Point>points;
    private Color color;
    private int x1,y1,x2,y2;



    public Polyline(Point p, Color color) {
        this.points = new ArrayList<Point>();
        points.add(p);
        this.color = color;
    }

    public void addPoint(Point p){
        if(p != null){
            points.add(p);
        }
    }

    @Override
    public synchronized void moveBy(int dx, int dy) {
        for(int i = 0; i < points.size(); i++) {
            Point temp = points.get(i); // Get the current point
            temp.translate(dx, dy);      // Move the point by dx (horizontal) and dy (vertical)
        }
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color=color;
    }

    @Override
    public boolean contains(int x, int y) {

        for(int i1 = 0; i1<points.size()-1;i1++) {
            if (Segment.pointToSegmentDistance(x, y, points.get(i1).x, points.get(i1).y, points.get(i1+1).x, points.get(i1+1).y) <= 10) {
                return true;
            }
        }
        return false;

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        for(int i =0;i<points.size()-1;i++) {
            g.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x,points.get(i+1).y);
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("freehand" + " " + color.getRGB());
        for (Point p : points) {
            s.append(" ").append(p.x).append(" ").append(p.y);
        }
        return s.toString();
    }


}