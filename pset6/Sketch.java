import java.awt.*;
import java.util.*;

/**
 * This class represents a sketch, which maintains a list of shapes
 * and their associated IDs for a collaborative drawing environment.
 */
public class Sketch {
    private final Map<Integer, Shape> shapes; // Mapping of shape IDs to shapes
    private int nextId; // ID to assign to the next shape

    public Sketch() {
        shapes = new HashMap<>();
        nextId = 1;
    }

    /**
     * Adds a shape to the sketch and returns its assigned ID.
     *
     */
    public synchronized int addShape(Shape shape) {
        int id = nextId++;
        shapes.put(id, shape);
        return id;
    }

    /**
     * Removes a shape from the sketch by its ID.
     *
     */
    public synchronized boolean removeShape(int id) {
        return shapes.remove(id) != null;
    }

    /**
     * Retrieves a shape by its ID.
     *
     */
    public synchronized Shape getShape(int id) {
        return shapes.get(id);
    }

    /**
     * Retrieves all shapes in the sketch.
     *
     */
    public synchronized Collection<Shape> getAllShapes() {
        return shapes.values();
    }

    /**
     * Updates the color of a shape by its ID.
     *
     */
    public synchronized void updateShapeColor(int id, Color newColor) {
        Shape shape = shapes.get(id);
        if (shape != null) {
            shape.setColor(newColor);
        }
    }

    /**
     * Moves a shape to a new position by its ID.
     * HELP
     */
    public synchronized void moveShape(int id, int x, int y) {

        Shape shape = shapes.get(id);
        if(shape != null){
            shape.moveBy(x, y);
        }
    }

    public Shape clickedInShape(Point p) {
        for(Integer id :shapes.keySet()) {
            if(shapes.get(id).contains(p.x, p.y)) {
                return shapes.get(id);
            }
        }
        return null;
    }

    public int getId(Shape s) {
        for(int id : shapes.keySet()) {
            if(shapes.get(id).equals(s)) {
                return id;
            }
        }
        return -1;
    }
}

/**
 * What I need to do is this --> It sends the string from editor to editor communicator (Cool) then sendsthat message to echo server
 * That echosever sends BACK To all editors connected, and THEN that is parsed, then you call the add shape and add it to each editor's sketch
 * associated with each given EditorCommunicator.
 */