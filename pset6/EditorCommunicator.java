import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Handles communication to/from the server for the editor
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 * @author Tim Pierson Dartmouth CS 10, provided for Winter 2025
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor;		// handling communication for

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			//Socket sock = new Socket(serverIP, 4242);
			Socket sock = new Socket();
			sock.connect(new InetSocketAddress(serverIP, 4242), 2000);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}

	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			// Handle messages
			// TODO: YOUR CODE HERE
			System.out.println("someone connected");
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println("EditorCommunicator received: " + line);
				handleMessage(line);

			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("server hung up");
		}
		//maybe consider closing
	}

	public void handleMessage(String line){
		System.out.println("Entered handleMessage");
		String[] tokens = line.split(" ");

		//comm.sendToServer("draw" + " " + curr.toString());
		if (tokens[0].equals("draw")){
			System.out.println("IT's drawing");
			String type = tokens[1];
			//"draw ellipse x1 y1 x2 y2 color"
			if (type.equals("ellipse")) {
				System.out.println("It's an ellipse");
				int x1 = Integer.parseInt(tokens[2]);
				int y1 = Integer.parseInt(tokens[3]);
				int x2 = Integer.parseInt(tokens[4]);
				int y2 = Integer.parseInt(tokens[5]);
				Color color = new Color(Integer.parseInt(tokens[6]));

				// Create the Ellipse shape
				Ellipse ellipse = new Ellipse(x1, y1, x2, y2, color);
				editor.getSketch().addShape(ellipse);
				editor.repaint();
			// "draw rectangle " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + color.getRGB();
			}else if(type.equals("rectangle")){
				int x1 = Integer.parseInt(tokens[2]);
				int y1 = Integer.parseInt(tokens[3]);
				int x2 = Integer.parseInt(tokens[4]);
				int y2 = Integer.parseInt(tokens[5]);
				Color color = new Color(Integer.parseInt(tokens[6]));

				// Create the Rectangle shape
				Rectangle rectangle = new Rectangle(x1, y1, x2, y2, color);
				editor.getSketch().addShape(rectangle);
				editor.repaint();
			}else if(type.equals("segment")){
				int x1 = Integer.parseInt(tokens[2]);
				int y1 = Integer.parseInt(tokens[3]);
				int x2 = Integer.parseInt(tokens[4]);
				int y2 = Integer.parseInt(tokens[5]);
				Color color = new Color(Integer.parseInt(tokens[6]));

				// Create the Segment's shape
				Segment segment = new Segment(x1, y1, x2, y2, color);
				editor.getSketch().addShape(segment);
				editor.repaint();
				// polyline " + color.getRGB());
				//        for (Point p : points) {
				//            s.append(" ").append(p.x).append(" ").append(p.y);
			}else if(type.equals("freehand")){
				System.out.println("drawing polyLine");
				Color color = new Color(Integer.parseInt(tokens[2]));
				Polyline polyline = new Polyline(new Point(Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4])), color);
				for (int i = 5; i < tokens.length; i += 2) {
					int x = Integer.parseInt(tokens[i]);
					int y = Integer.parseInt(tokens[i + 1]);
					polyline.addPoint(new Point(x, y)); // Add all points from the message
				}

				editor.getSketch().addShape(polyline);
				editor.repaint();
			}
		// 	comm.sendToServer("move " + movingId + " " + p.x + " " +  p.y);
		}else if (tokens[0].equals("move")){
			System.out.println("IT'S MOVING");
			int ID = Integer.parseInt(tokens[1]);
			editor.getSketch().moveShape(ID, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
			editor.repaint();
		// ("recolor" + " " + color.getRGB()  + " " + id );
		} else if (tokens[0].equals("recolor")) {
			System.out.println("IT'S Recoloring");
			for(String word : tokens) {
				System.out.print(word + ".");
			}
			Color color = new Color(Integer.parseInt(tokens[1]));

			editor.getSketch().updateShapeColor(Integer.parseInt(tokens[2]), color);
			editor.repaint();

			//comm.sendToServer("delete" + " " + sketch.getId(shape));
		}else if (tokens[0].equals("delete")) {
			System.out.println(" IT'S deleting");
			editor.getSketch().removeShape(Integer.parseInt(tokens[1]));
			editor.repaint();
		}
	}

	// Send editor requests to the server
	// TODO: YOUR CODE HERE
	public void sendToServer(String s){
		System.out.println("Received from Editor: " +  s);
		send(s);
	}
}
