package mqtt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * classe principale de l'application, initialise la connexion au broker et
 * lance le client-serveur mqtt
 */
public class Main {

    // ================================================================================
    // Constructors
    // ================================================================================

    /**
     * Lance l'application
     * 
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
	SimpleMqttClient c = new SimpleMqttClient();
	c.run();

	SimpleMqttPublisher pub = new SimpleMqttPublisher(c.getSubTopic("rot"), c.getSubTopic("spd"),
		c.getSubTopic("rpm"));
	pub.start();

	checkStop();

	pub.interrupt();

	c.disconnect();
	System.exit(0);
    }

    // ================================================================================
    // Class Methods
    // ================================================================================

    /**
     * permet à l'utilisateur de terminer le programme
     * 
     * @throws IOException
     */
    private static void checkStop() throws IOException {
	boolean stop = false;
	while (!stop) {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    System.out.print("\"q\"\n");
	    String s = br.readLine();
	    stop = s.equals("q");
	}
    }

}
