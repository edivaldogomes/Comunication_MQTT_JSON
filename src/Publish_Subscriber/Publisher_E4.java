package Publish_Subscriber;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;

public class Publisher_E4 {

	public static void main(String[] args) {
        String kichen_topic             = "casa/habitacion/cocina/temperatura";
        String room1_topic              = "casa/habitacion/dorm1/temperatura";
        String room2_topic              = "casa/habitacion/dorm2/temperatura";
        String bathroom_topic           = "casa/habitacion/aseo/temperatura";
        String humidity_bathroom_topic  = "casa/habitacion/aseo/humedad";
        int qos                         = 2;
        String broker                   = "tcp://localhost:1883";
        String clientId                 = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message");
            for (int i = 0; i <= 100; i=i+10) {
            	JSONObject jsonObject = new JSONObject();
            	jsonObject.put("Valor", new Integer(i));
            	jsonObject.put("Unidad", "Kwh");
                MqttMessage text_message = new MqttMessage(jsonObject.toString().getBytes());
                text_message.setQos(qos);
                sampleClient.publish(kichen_topic, text_message);
                sampleClient.publish(room1_topic, text_message);
                sampleClient.publish(room2_topic, text_message);
                sampleClient.publish(bathroom_topic, text_message);
                sampleClient.publish(humidity_bathroom_topic, text_message);
            }
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
	}
}
