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
     * réprésente la rotation du volant
     */
    private final MqttValue rot;

    /**
     * représente la vitesse
     */
    private final MqttValue spd;

    /**
     * représente le nombre de tour par minute du moteur
     */
    private final MqttValue rpm;

    // ================================================================================
    // Constructors
    // ================================================================================

    /**
     * créé une instance de publicateur simulé
     * 
     * @param rot_t
     * @param spd_t
     * @param rpm_t
     */
    public SimpleMqttPublisher(MqttTopic rot_t, MqttTopic spd_t, MqttTopic rpm_t) {
	super();
	this.rot = new MqttValue(-180, 180, rot_t);
	this.spd = new MqttValue(0, 200, spd_t);
	this.rpm = new MqttValue(0, 6000, rpm_t);
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
	    publishValue(this.rot);
	    publishValue(this.rpm);
	    publishValue(this.spd);
	    this.synchWait();
	}
    }

    /**
     * publie une valeur donnée
     * 
     * @param value
     */
    private static void publishValue(MqttValue value) {
	MqttMessage message = new MqttMessage(value.getValue().getBytes());
	message.setQos(0);
	message.setRetained(false);

	try {
	    MqttDeliveryToken token = value.getTopic().publish(message);
	    token.waitForCompletion();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * permet d'attendre avant de générer une nouvelle valeur
     */
    synchronized private void synchWait() {
	try {
	    this.wait(8000);
	} catch (InterruptedException e) {
	    this.interrupt();
	}
    }
}