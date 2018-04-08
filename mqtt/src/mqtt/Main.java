package mqtt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * classe principale de l'application, initialise la connexion au broker et
 * lance le client-serveur mqtt
 */
public class Main {

    /**
     * Thread simulant la publication de données de conduite
     */
    private static Map<String, SimpleMqttPublisher> publishers;

    // ================================================================================
    // Constructors
    // ================================================================================

    /**
     * Lance l'application
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
	publishers = new HashMap<>();

	SimpleMqttClient c = new SimpleMqttClient();
	c.run();

	initVariables(c);

	for (SimpleMqttPublisher pub : publishers.values()) {
	    pub.start();
	}

	readAndPublish();

	for (SimpleMqttPublisher pub : publishers.values()) {
	    pub.interrupt();
	}

	c.disconnect();
	System.exit(0);
    }

    // ================================================================================
    // Class Methods
    // ================================================================================

    /**
     * défini des paramètres pour le nombre de tour par minute, l'angle de braquage
     * des roues et la vitesse
     * 
     * @param c
     */
    private static void initVariables(SimpleMqttClient c) {
	publishers.put("rpm", new SimpleMqttPublisher(c.getSubTopic("rpm")));

	publishers.put("rot", new SimpleMqttPublisher(c.getSubTopic("rot")));

	publishers.put("spd", new SimpleMqttPublisher(c.getSubTopic("spd")));
    }

    /**
     * permet à l'utilisateur d'entrée des valeur pour les données de conduite
     * 
     * @throws IOException
     */
    private static void readAndPublish() throws IOException {
	boolean stop = false;
	while (!stop) {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    System.out.print("Update Value \"rpm:value\", \"rot:value\", \"spd:value\", \"q\":\n\n $>");
	    String s = br.readLine();
	    stop = s.equals("q");
	    String[] parts = s.split(":");
	    if (parts.length == 2 && publishers.containsKey(parts[0])) {
		publishers.get(parts[0]).setValue(parts[1]);
	    }
	}
    }

}
