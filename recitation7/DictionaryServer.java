import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple server that waits for someone to connect on port 4242,
 * and then repeatedly asks for their name and greets them.
 * Connect either by "telnet localhost 4242" or by running HelloClient.java
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Tim Pierson, Dartmouth CS 10, provided for Winter 2025
 */

public class DictionaryServer {
    public static void main(String[] args) throws IOException {
        // Listen on a server socket for a connection
        Map<String, String> map = new HashMap<>();
        System.out.println("waiting for someone to connect");
        try {
            ServerSocket listen = new ServerSocket(4242);

            // When someone connects, create a specific socket for them
            Socket sock = listen.accept(); // wait until someone makes a connection
            System.out.println("someone connected");

            // Now talk with them
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream())); // uses socket
            out.println("What do you want to do? ");
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("received:" + line);
                String[] tokens = line.split(" ");
                String command = tokens[0];
                String word = tokens[1];
                String definition = "";
                if (tokens.length > 2){
                    for (int i = 2; i < tokens.length; i++){
                        definition += tokens[i];
                    }
                    if ((command.equalsIgnoreCase("GET"))){
                        if (!map.containsKey(word)){
                            out.println("Word does not exist");
                        }else{
                            out.println(map.get(word));
                        }
                    } else if (command.equalsIgnoreCase("DEFINE")) {
                        if (tokens.length < 3) {
                            out.println("Missing definition");
                        } else {
                            map.put(word, definition);
                        }
                    }
                }
                out.println("Would you like to do something else? ");
            }
            System.out.println(map);
            System.out.println("client hung up");
            // Clean up shop
            out.close();
            in.close();
            sock.close();
            listen.close();
        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e);
        }
    }
}


















