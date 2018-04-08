package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import ethereum.Entry;
import ethereum.RecorderQueue;

// https://uga.jyse.io/player.html?page=Dashboard&user=etu-1718-m2pgi-iot-03

/**
 * Client simple Mqtt recevant des données de conduite et les envoyant au
 * service de blockchain
 * 
 */
public class SimpleMqttClient implements MqttCallback {

    /**
     * adresse du broker (Mosquitto par exemple)
     */
    private static final String BROKER_URL = "tcp://127.0.0.1:1883";

    /**
     * sujet principal
     */
    private static final String M2MIO_DOMAIN = "carlora";

    /**
     * sous sujet
     */
    private static final String M2MIO_THING = "lopy";

    /**
     * true si l'on doit afficher plus de log
     */
    private static final boolean debug = false;

    /**
     * client Mqtt pour reception des données
     */
    private MqttClient myClient;

    /**
     * topic d'écoute
     */
    private final String myTopic;

    /**
     * Recorder permettant d'ajouter en BDD les données de conduites une fois
     * complètes
     */
    private final RecorderQueue recorder;

    /**
     * l'entrée des données de conduite courrante
     */
    private Entry currentEntry;

    // ================================================================================
    // Constructors
    // ================================================================================

    /**
     * créé une instance de client Mqtt
     */
    public SimpleMqttClient() {
	super();
	this.recorder = new RecorderQueue();
	this.recorder.start();
	String clientID = M2MIO_THING;
	MqttConnectOptions connOpt = new MqttConnectOptions();
	connOpt.setCleanSession(true);
	connOpt.setKeepAliveInterval(30);

	try {
	    this.myClient = new MqttClient(BROKER_URL, clientID);
	    this.myClient.setCallback(this);
	    this.myClient.connect(connOpt);
	} catch (MqttException e) {
	    e.printStackTrace();
	    System.exit(-1);
	}

	this.myTopic = M2MIO_DOMAIN + "/" + M2MIO_THING;
    }

    // ================================================================================
    // Class Methods
    // ================================================================================

    @Override
    public void connectionLost(Throwable t) {
	System.out.println("Connection lost!");
    }

    @Override
    public void messageArrived(String t, MqttMessage message) throws Exception {
	String payload = new String(message.getPayload());
	if (debug) {
	    System.out.println("\n");
	    System.out.println("-------------------------------------------------");
	    System.out.println("| Topic:" + t);
	    System.out.println("| Message: " + payload);
	    System.out.println("-------------------------------------------------");
	    System.out.println("\n");
	}

	String[] topics = t.split("/");
	String topic = topics[topics.length - 1];
	this.MqttHandler(topic, payload);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
	// ok
    }

    /**
     * @param name
     * @return un sous sujet ayant le nom spécifié
     */
    public MqttTopic getSubTopic(String name) {
	return this.myClient.getTopic(this.myTopic + "/" + name);
    }

    /**
     * lance le client
     */
    protected void run() {
	try {
	    this.myClient.subscribe(this.myTopic + "/+", 0);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * permet de se déconnecter du broker
     */
    protected void disconnect() {
	try {
	    this.myClient.disconnect();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * maintient à jour l'état courrant et si l'on dispose de toutes les
     * informations alors met à jour la blockchain
     * 
     * @param topic
     * @param message
     */
    protected void MqttHandler(String topic, String message) {
	if (this.currentEntry == null) {
	    this.currentEntry = new Entry();
	}

	if (topic.equals("rpm")) {
	    this.currentEntry.setRpm(Integer.valueOf(message));
	}
	if (topic.equals("rot")) {
	    this.currentEntry.setRot(Math.abs(Integer.valueOf(message)));
	}
	if (topic.equals("spd")) {
	    this.currentEntry.setSpd(Integer.valueOf(message));
	}

	if (this.currentEntry.isValid()) {
	    this.recorder.addToQueue(this.currentEntry);
	    this.currentEntry = null;
	}
    }
}