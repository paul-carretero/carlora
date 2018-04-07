package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

@SuppressWarnings("javadoc")
public class SimpleMqttClient implements MqttCallback {

    private static final String BROKER_URL = "tcp://127.0.0.1:1883";
    private static final String M2MIO_DOMAIN = "CarLoRa";
    private static final String M2MIO_THING = "LoPy";
    private static final boolean debug = false;

    private MqttClient myClient;
    private String myTopic;

    public SimpleMqttClient() {
	super();
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

    public MqttTopic getSubTopic(String name) {
	return this.myClient.getTopic(this.myTopic + "/" + name);
    }

    @Override
    public void connectionLost(Throwable t) {
	System.out.println("Connection lost!");
    }

    @Override
    public void messageArrived(String t, MqttMessage message) throws Exception {
	if (debug) {
	    System.out.println("\n");
	    System.out.println("-------------------------------------------------");
	    System.out.println("| Topic:" + t);
	    System.out.println("| Message: " + new String(message.getPayload()));
	    System.out.println("-------------------------------------------------");
	    System.out.println("\n");
	}
    }

    protected void run() {
	try {
	    this.myClient.subscribe(this.myTopic + "/+", 0);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    protected void disconnect() {
	try {
	    this.myClient.disconnect();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
	// ok
    }
}