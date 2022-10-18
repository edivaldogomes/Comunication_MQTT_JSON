package Subscriber_E2_2;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;


public class Subscriber_E2_2 implements MqttCallback {
        @Override
        public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
        	String msg;
        	msg= new String(message.getPayload());
            System.out.println("-------------------------------------------------");
            System.out.println("| Topic:" + topic);
            System.out.println("| Message: " + msg);
            System.out.println("-------------------------------------------------");
            /* Obtain the value */
            String valor = msg.substring(msg.indexOf(":") + 1,msg.indexOf(";"));
            String unidad = msg.substring(msg.lastIndexOf(":") + 1,msg.length());
            String result= "Lectura " + valor + " " + unidad;
            System.out.println(result);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
        }
        public void suscriptor() {};
        MqttClient sampleClient;
    


	public static void main(String[] args) {
		new Subscriber_E2_2().DoDemo();
	}
	public void DoDemo()
	{
		String topic = "test2";
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
            sampleClient.subscribe("test2", qos);
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
