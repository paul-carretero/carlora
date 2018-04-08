package mqtt;

import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttTopic;

/**
 * représente une valeur à publier sur le broker mqtt
 */
public class MqttValue {
    /**
     * valeur actuelle de la donnée de consuite
     */
    private String value;

    /**
     * valeur minimum
     */
    private final int min;

    /**
     * valeur maximum
     */
    private final int max;

    /**
     * topic mqtt sur lequel publier cette valeur
     */
    private final MqttTopic topic;

    /**
     * @param min
     * @param max
     * @param topic
     */
    public MqttValue(int min, int max, MqttTopic topic) {
	super();
	this.min = min;
	this.max = max;
	this.topic = topic;
	this.value = "0";
    }

    /**
     * @return the value
     */
    public String getValue() {
	generateNewValue();
	return this.value;
    }

    /**
     * Génère une nouvelle valeur de la valeure simulée pour la prochaine exécution
     */
    private void generateNewValue() {
	int current = Integer.valueOf(this.value);
	Random r = new Random();
	int res = this.min + r.nextInt(this.max - this.min);

	if (res == 0) {
	    current = 0;
	} else {
	    if (res > current) {
		current += (this.max - res) / 2;
	    } else {
		current -= (res - this.min) / 2;
	    }
	}

	this.value = String.valueOf(current);
    }

    /**
     * @return le topic mqtt sur lequel publier cette valeur
     */
    public MqttTopic getTopic() {
	return this.topic;
    }
}
