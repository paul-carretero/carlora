package mqtt;

import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/**
 * service de simulation de données mqtt
 * 
 */
public class SimpleMqttPublisher extends Thread {

    /**
     * sous topic mqtt où publier les informations
     */
    private MqttTopic topic;

    /**
     * valeur actuelle de la donnée de consuite
     */
    private String value;

    // ================================================================================
    // Constructors
    // ================================================================================

    /**
     * créé une instance de publicateur simulé
     * 
     * @param topic
     */
    public SimpleMqttPublisher(MqttTopic topic) {
	super();
	this.topic = topic;
	this.value = "0";
    }

    // ================================================================================
    // Class Methods
    // ================================================================================

    /**
     * Publie un message et attends qu'il soit délivré au broker. Attends une
     * seconde et republie le message
     */
    @Override
    public void run() {
	while (!this.isInterrupted()) {
	    MqttMessage message = new MqttMessage(this.value.getBytes());
	    message.setQos(0);
	    message.setRetained(false);
	    MqttDeliveryToken token = null;
	    try {
		token = this.topic.publish(message);
		token.waitForCompletion();
		Thread.sleep(1000);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    // ================================================================================
    // Setters
    // ================================================================================

    /**
     * défini une nouvelle valeur pour la simulation
     * 
     * @param v
     */
    public void setValue(String v) {
	this.value = v;
    }
}