package mqtt;

import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

@SuppressWarnings("javadoc")
public class SimpleMqttPublisher extends Thread {

    private MqttTopic topic;
    private String value;

    public SimpleMqttPublisher(MqttTopic topic) {
	super();
	this.topic = topic;
	this.value = "0";
    }

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

    public void setValue(String v) {
	this.value = v;
    }
}