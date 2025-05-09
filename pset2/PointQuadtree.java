import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A point quadtree: stores an element at a 2D position, with children at the subdivided quadrants
 * E extends Point2D to ensure whatever the PointQuadTree holds implements getX and getY
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2025, based on prior term code
 *
 */
public class PointQuadtree<E extends Point2D> {
	private E point;                     // the point anchoring this node
	private int x1, y1;                      // upper-left corner of the region
	private int x2, y2;                      // bottom-right corner of the region
	private PointQuadtree<E> c1, c2, c3, c4;   // children

	/**
	 * Initializes a leaf quadtree, holding the point in the rectangle
	 */
	public PointQuadtree(E point, int x1, int y1, int x2, int y2) {
		this.point = point;
		this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
	}

	// Getters
	public E getPoint() { return point; }
	public int getX1() { return x1; }
	public int getY1() { return y1; }
	public int getX2() { return x2; }
	public int getY2() { return y2; }

	/**
	 * Returns the child (if any) at the given quadrant, 1-4
	 * @param quadrant 1 through 4
	 * @return child for quadrant
	 */
	public PointQuadtree<E> getChild(int quadrant) {
		if (quadrant==1) return c1;
		if (quadrant==2) return c2;
		if (quadrant==3) return c3;
		if (quadrant==4) return c4;
		return null;
	}

	/**
	 * Returns whether there is a child at the given quadrant, 1-4
	 * @param quadrant 1 through 4
	 */
	public boolean hasChild(int quadrant) {
		return (quadrant==1 && c1!=null) || (quadrant==2 && c2!=null) || (quadrant==3 && c3!=null) || (quadrant==4 && c4!=null);
	}

	/**
	 * Inserts the point into the tree
	 */
	public void insert(E p2) {
		int quadrant = findQuadrant(p2);
		if(hasChild(quadrant)){
			getChild(quadrant).insert(p2);
		}else{
			PointQuadtree<E> curChild = constructChild(quadrant, p2);
			if(quadrant == 1){
				c1 = curChild;
			}else if(quadrant == 2){
				c2 = curChild;
			}else if(quadrant == 3){
				c3 = curChild;
			}else{
				c4 = curChild;
			}
		}
	}

	public int findQuadrant(E p2){
		if(p2.getX() >= point.getX() && p2.getY() <= point.getY()){
			return 1;
		}else if(p2.getX() <= point.getX() && p2.getY() < point.getY()){
			return 2;
		}else if(p2.getX() <= point.getX() && p2.getY() > point.getY()){
			return 3;
		}else{
			return 4;
		}
	}

	public PointQuadtree<E> constructChild(int quadrant, E p2){

		if(quadrant == 1){
			return new PointQuadtree<E>(p2,(int) point.getX(), y1, x2,(int) point.getY());
		}else if(quadrant == 2){
			return new PointQuadtree<E>(p2, x1, y1, (int) point.getX(), (int) point.getY());
		}else if(quadrant == 3){
			return new PointQuadtree<E>(p2, x1, (int) point.getY(), (int) point.getX(), y2);
		}else{
			return new PointQuadtree<E>(p2, (int) point.getX(), (int) point.getY(), x2, y2);
		}
	}

	public String toString() {
		return toStringHelper(this, "", "");
	}

	/**
	 * Recursively constructs a String representation of the tree from this node,
	 * starting with the given indentation and indenting further going down the tree
	 */
	public String toStringHelper(PointQuadtree node, String indent, String childIndent) {
		String res = indent + node.point + "\n";
		if (node.c1 != null) res += toStringHelper(node.c1, childIndent + "├── ", childIndent + "│   ");
		if (node.c2 != null) res += toStringHelper(node.c2, childIndent + "├── ", childIndent + "│   ");
		if (node.c3 != null) res += toStringHelper(node.c3, childIndent + "├── ", childIndent + "│   ");
		if (node.c4 != null) res += toStringHelper(node.c4, childIndent + "└── ", childIndent + "    ");
		return res;
	}

	/**
	 * Finds the number of points in the quadtree (including its descendants)
	 */
	public int size() {
		int num = 1;
		if(hasChild(1)){
			num += c1.size();
		}
		if(hasChild(2)){
			num += c2.size();
		}
		if(hasChild(3)){
			num += c3.size();
		}
		if(hasChild(4)){
			num += c4.size();
		}
		return num;
	}

	/**
	 * Builds a list of all the points in the quadtree (including its descendants)
	 * @return List with all points in the quadtree
	 */
	public List<E> allPoints() {
		List<E> curList = new ArrayList<>();
		if (point != null) {
			curList.add(point);
		}
		if (hasChild(1)) {
			curList.addAll(c1.allPoints());
		}
		if (hasChild(2)) {
			curList.addAll(c2.allPoints());
		}
		if (hasChild(3)) {
			curList.addAll(c3.allPoints());
		}
		if (hasChild(4)) {
			curList.addAll(c4.allPoints());
		}
		return curList;
	}

	/**
	 * Uses the quadtree to find all points within the circle
	 * @param cx   circle center x
	 * @param cy   circle center y
	 * @param cr   circle radius
	 * @return     the points in the circle (and the qt's rectangle)
	 */
	public List<E> findInCircle(double cx, double cy, double cr) {
		/**To find all points within the circle (cx,cy,cr), stored in a tree covering rectangle (x1,y1)-(x2,y2)
		 If the circle intersects a quadrant's rectangle
		 If the tree's point is in the circle, then the point is a "hit"
		 For each non-null child quadrant
		 Recurse with that child**/
		List<E> curList = new ArrayList<>();

		if (Geometry.circleIntersectsRectangle(cx, cy, cr, x1, y1, x2, y2)) {
			if (Geometry.pointInCircle(point.getX(), point.getY(), cx, cy, cr)) {
				curList.add(point);
			}
			if (c1 != null) {
				curList.addAll(c1.findInCircle(cx, cy, cr));
			}
			if (c2 != null) {
				curList.addAll(c2.findInCircle(cx, cy, cr));
			}
			if (c3 != null) {
				curList.addAll(c3.findInCircle(cx, cy, cr));
			}
			if (c4 != null) {
				curList.addAll(c4.findInCircle(cx, cy, cr));
			}
		}
		return curList;
	}
}