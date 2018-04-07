package mqtt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("javadoc")
public class Main {

    private static Map<String, SimpleMqttPublisher> publishers;

    public static void main(String[] args) throws IOException {
	SimpleMqttClient c = new SimpleMqttClient();
	c.run();
	publishers = new HashMap<>();
	publishers.put("rpm", new SimpleMqttPublisher(c.getSubTopic("rpm")));

	for (SimpleMqttPublisher pub : publishers.values()) {
	    pub.start();
	}

	readAndPublish();

	for (SimpleMqttPublisher pub : publishers.values()) {
	    pub.interrupt();
	}

	c.disconnect();
    }

    private static void readAndPublish() throws IOException {
	boolean stop = false;
	while (!stop) {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    System.out.print("Update Value \"rpm:value\" ou \"q\":\n $");
	    String s = br.readLine();
	    stop = s.equals("q");
	    String[] parts = s.split(":");
	    if (parts.length == 2 && publishers.containsKey(parts[0])) {
		publishers.get(parts[0]).setValue(parts[1]);
	    }
	}
    }

}
