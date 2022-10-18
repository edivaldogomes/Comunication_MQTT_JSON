package Publish_Subscriber;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;


public class Subscriber_E4_1 implements MqttCallback {
        @Override
        public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
        	String msg = new String(message.getPayload());
        	JSONObject jsonMessage = new JSONObject(msg);
        	long value = jsonMessage.getLong("Valor");
        	String unit = (String) jsonMessage.get("Unidad");            
            if (value <= 20) return;
        	System.out.println("-------------------------------------------------");
            System.out.println("| Topic:" + topic);
            System.out.println("| Message: " + msg);
            System.out.println("-------------------------------------------------");
            String result= "Lectura " + value + " " + unit;
            System.out.println(result);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
        }
        public void suscriptor() {};
        MqttClient sampleClient;
    


	public static void main(String[] args) {
		new Subscriber_E4_1().DoDemo();
	}
	public void DoDemo(){
        int qos = 0;
        String broker = "tcp://localhost:1883";
        String clientId = "JavaSample_suc";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            sampleClient.subscribe("casa/habitacion/+/temperatura", qos);
            System.out.println("Suscrito");
            sampleClient.setCallback(this);
            try {
                Thread.sleep(50000);
                sampleClient.disconnect();
            } catch(Exception e) {
                e.printStackTrace();
            }
            System.out.println("Disconnected 1");
            System.exit(0);
        } catch(MqttException me){
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("except " + me);
            me.printStackTrace();
        }
	}
}
