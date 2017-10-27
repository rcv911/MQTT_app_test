package service;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.example.user.testmqtt.MainActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Lab1 on 27.10.2017.
 */

public class MQTTService {

    public MQTTService(String mqqthost, String username, String userps, Activity activity ){
        setMqttHost(mqqthost);
        setUsername(username);
        setUserPassword(userps);
        clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(activity.getApplicationContext(), getMqttHost(), clientId);
        options = new MqttConnectOptions();
        options.setUserName(getUsername());
        options.setPassword(getUserPassword().toCharArray());
        setActivity(activity);
    }

    public void connectMQTT() {
        System.out.println("Inside connectMQTT");
        //Connect to MQTT host
        //MQTT init
        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(getActivity(), "connected", Toast.LENGTH_SHORT).show();
                    //We are Subscribed
//                    System.out.println("Subscribe to = " + getPublishTopic());
//                    setSubscribe(getPublishTopic());


                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(getActivity(), "connection failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("don't subscribe to publishTopic");
        }


        //Set Callback Reaction
//        MCallback();

    } //end method

    public void PubMes(String topic, String topicMessage) {
        System.out.println("Enter");
//        EditText TextTopic = (EditText) findViewById(R.id.texttopic);
//        EditText TextMessage = (EditText) findViewById(R.id.textmessage);
        setPublishTopic(topic);
        setPublishPayload(topicMessage);
        //String publishTopic = topicStr;
        //String mess = publishPayload; //the same Payload = Message
        //String as = "test";
        //byte[] encodedPayload = new byte[0];
        try {
            //encodedPayload = payload.getBytes("UTF-8");
            // MqttMessage message = new MqttMessage(encodedPayload);
            // client.publish(publishTopic, mess.getBytes(), 0, false);
            client.publish(topic, getPublishPayload().getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        /*
                } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
         */ //if you want to add encode
    }

    public void hostDisconnect() {
        try {
            IMqttToken token = client.disconnect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are disconnected
                    Toast.makeText(getActivity(), "disconnected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. disconnection timeout or firewall problems
                    Toast.makeText(getActivity(), "disconnection failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    //to Subscribe
    public void setSubscribe(String topicStr) {
        System.out.println("Subscribe to = " + topicStr);
        try {
            getClient().subscribe(topicStr, 0, null, null); // (name of publishTopic, QoS = 0)
            Toast.makeText(getActivity(), "subscribe", Toast.LENGTH_SHORT).show();
        }catch (MqttException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "exception", Toast.LENGTH_SHORT).show();
        }


    }

    public String MCallback(    ) {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                setSubscribePayload("" + new String(message.getPayload()));

                //сюда можно добавить вибрацию телефона и рингтон
                //описывать "сущности" вибрации и рингтона в onCreate!
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        System.out.println("Subscribe payload = " + getSubscribePayload());
        return getSubscribePayload();
    }

    //Setters

    public void setUsername(String un){
        username = un;
    }

    public void setUserPassword(String up){
        userPassword = up;
    }

    public void setMqttHost(String mh){
        mqttHost = mh;
    }

    public void setClientId(String ci){
        clientId = ci;
    }

    public void setSubscribePayload(String sp){
        subscribePayload = sp;
    }

    public void setPublishTopic(String t){
        publishTopic = t;
    }

    public void setPublishPayload(String tm){
        publishPayload = tm;
    }

    public void setSubscribeTopic(String subscribeTopic) {
        this.subscribeTopic = subscribeTopic;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setClient(MqttAndroidClient client) {
        this.client = client;
    }

    //Getters

    public String getUsername(){
        return username;
    }

    public String getUserPassword(){
        return userPassword;
    }

    public String getMqttHost(){
        return mqttHost;
    }

    public String getClientId(){
        return clientId;
    }

    public String getSubscribePayload(){
        return subscribePayload;
    }

    public String getPublishTopic(){
        return publishTopic;
    }

    public String getPublishPayload(){
        return publishPayload;
    }

    public String getSubscribeTopic() {
        return subscribeTopic;
    }

    public Activity getActivity() {
        return activity;
    }

    public MqttAndroidClient getClient() {
        return client;
    }


    //private fields
    private Activity activity;





    private MqttAndroidClient client;
    private MqttConnectOptions options;
    private String publishTopic;
    private String publishPayload;
    private String username;
    private String userPassword;
    private String mqttHost;
    private String clientId;
    private String subscribeTopic;
    private String subscribePayload;
}
