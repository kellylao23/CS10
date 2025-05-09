import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Handles communication between the server and one client, for SketchServer
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate SketchServerCommunicator
 * @author Tim Pierson Dartmouth CS 10, provided for Winter 2025
 */
public class SketchServerCommunicator extends Thread {
	private Socket sock;					// to talk with client
	private BufferedReader in;				// from client
	private PrintWriter out;				// to client
	private SketchServer server;			// handling communication for

	public SketchServerCommunicator(Socket sock, SketchServer server) {
		this.sock = sock;
		this.server = server;
	}

	/**
	 * Sends a message to the client
	 * @param msg
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the client
	 */
	public void run() {
		try {
			System.out.println("someone connected");

			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);

			// Tell the client the current state of the world
			// TODO: YOUR CODE HERE
			// Tell the client the current state of the world
			for (Shape s : server.getSketch().getAllShapes()) {
				send("draw " + s.toString()); // Send the shape to the client
			}

			// Keep getting and handling messages from the client
			// TODO: YOUR CODE HERE
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println("Received from client: " + line);
				handleClientMessage(line); // Method to process the message
			}

			// Clean up -- note that also remove self from server's list so it doesn't broadcast here
			server.removeCommunicator(this);
			out.close();
			in.close();
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}


	}

	/**
	 *
	 * @param line takes in message
	 */
	public void handleClientMessage(String line){
		String[] tokens = line.split(" ");
		System.out.print("Handling client message in Server: ");
		for(String word : tokens){
			System.out.print(word + " ");
		}
		// draw message
		if (tokens[0].equals("draw")) {
			Shape shape = createShapeFromTokens(tokens);
			int id = server.getSketch().addShape(shape);
			server.broadcast("draw" + " " + shape.toString());
		// move message
		} else if (tokens[0].equals("move")) {
			int moveId = Integer.parseInt(tokens[1]);
			int newX = Integer.parseInt(tokens[2]);
			int newY = Integer.parseInt(tokens[3]);
			server.getSketch().moveShape(moveId, newX, newY);
			server.broadcast("move " + moveId + " " + newX + " " + newY);
		// delete message
		} else if (tokens[0].equals("delete")) {
			int deleteId = Integer.parseInt(tokens[1]);
			server.getSketch().removeShape(deleteId);
			server.broadcast("delete " + deleteId);
		// recolor message
		} else if (tokens[0].equals("recolor")) {
			int ID = Integer.parseInt(tokens[2]);
			Color newColor = new Color(Integer.parseInt(tokens[1]));
			server.getSketch().updateShapeColor(ID, newColor);
			server.broadcast("recolor" + " " + newColor.getRGB() + " " + ID);
		}
	}

	/**
	 *
	 * @param tokens parts of message string
	 * @return
	 */
	private Shape createShapeFromTokens(String[] tokens) {
		String type = tokens[1];

		if (type.equals("ellipse")) {
			return new Ellipse(
					Integer.parseInt(tokens[2]),
					Integer.parseInt(tokens[3]),
					Integer.parseInt(tokens[4]),
					Integer.parseInt(tokens[5]),
					new Color(Integer.parseInt(tokens[6])));

		} else if (type.equals("rectangle")) {
			return new Rectangle(
					Integer.parseInt(tokens[2]),
					Integer.parseInt(tokens[3]),
					Integer.parseInt(tokens[4]),
					Integer.parseInt(tokens[5]),
					new Color(Integer.parseInt(tokens[6])));

		} else if (type.equals("segment")) {
			return new Segment(
					Integer.parseInt(tokens[2]),
					Integer.parseInt(tokens[3]),
					Integer.parseInt(tokens[4]),
					Integer.parseInt(tokens[5]),
					new Color(Integer.parseInt(tokens[6])));

		} else if (type.equals("freehand")) {
			Color color = new Color(Integer.parseInt(tokens[2]));
			Polyline polyline = new Polyline(new Point(Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4])), color);
			for (int i = 5; i < tokens.length; i += 2) {
				int x = Integer.parseInt(tokens[i]);
				int y = Integer.parseInt(tokens[i + 1]);
				polyline.addPoint(new Point(x, y)); // Add all points from the message
			}
			return polyline;
		}
		return  null;
	}
}
