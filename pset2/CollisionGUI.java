import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Using a quadtree for collision detection
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2025, based on prior term code
 */
public class CollisionGUI extends InteractiveGUI {
	private static final int width=800, height=600;       // size of the window
	private List<MovingPoint> points;              // all the points
	private List<MovingPoint> colliders;            // the points that collided at this time
	private char collisionHandler = 'c';            // when there's a collision, 'c'olor them, or 'd'estroy them
	private int delay = 100;                     // timer control

	public CollisionGUI() {
		super("super-collider", width, height);

		points = new ArrayList<MovingPoint>();

		// Timer drives the animation.
		startTimer();
	}

	/**
	 * Adds a point at the (x,y) location
	 */
	private void add(int x, int y) {
		points.add(new MovingPoint(x,y,width,height));
	}

	/**
	 * InteractiveGUI method, here creating a new point
	 */
	@Override
	public void handleMousePress(int x, int y) {
		add(x,y);
		repaint();
	}

	/**
	 * InteractiveGUI method
	 */
	@Override
	public void handleKeyPress(char k) {
		if (k == 'f') { // faster
			if (delay>1) delay /= 2;
			setTimerDelay(delay);
			System.out.println("delay:"+delay);
		}
		else if (k == 's') { // slower
			delay *= 2;
			setTimerDelay(delay);
			System.out.println("delay:"+delay);
		}
		else if (k == 'r') { // add some new points at random positions
			for (int i=0; i<10; i++) {
				add((int)(width*Math.random()), (int)(height*Math.random()));
			}
			repaint();
		}
		else if (k == 'c' || k == 'd') { // control how collisions are handled
			collisionHandler = k;
			System.out.println("collision:"+k);
		}
	}

	/**
	 * InteractiveGUI method, here drawing all the points and then re-drawing the colliders in red
	 */
	@Override
	public void draw(Graphics g) {
		// TODO: YOUR CODE HERE
		g.setColor(Color.BLACK);
		if (points != null){
			for (MovingPoint point : points) {
				point.draw(g);
			}
		}

		// Draw colliders in red
		if (colliders != null && !colliders.isEmpty()) { // Check for null and ensure the list is not empty
			g.setColor(Color.RED);
			for (MovingPoint point : colliders) {
				// Ensure point is not null before drawing
				point.draw(g);
			}
		}
	}

	/**
	 * Sets colliders to include all points in contact with another point
	 */
	private void findColliders() {
		PointQuadtree<MovingPoint> allPoints = null;
		colliders = new ArrayList<>();

		for (MovingPoint point : points) {
			if(allPoints == null){
				allPoints = new PointQuadtree<>(point, 0, 0, width, height);
			}else{
				allPoints.insert(point);
			}
		}

		if(allPoints != null){
			if(colliders == null){
				colliders = new ArrayList<>();
			}
		}

		for (MovingPoint point : points) {
			if(allPoints.findInCircle(point.getX(), point.getY(), point.r*2).size() > 1){
				colliders.add(point);
			}
		}
	}

	/**
	 * InteractiveGUI method, here moving all the points and checking for collisions
	 */
	@Override
	public void handleTimer() {
		// Ask all the points to move themselves.
		for (MovingPoint point : points) {
			point.move();
		}
		// Check for collisions
		if (!points.isEmpty()) {
			findColliders();
			if (collisionHandler=='d') {
				points.removeAll(colliders);
				colliders = null;
			}
		}
		// Now update the drawing
		repaint();
	}

	public static void main(String[] args) {
		new CollisionGUI();
	}
}