package com.example.user.testmqtt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;


public class MainActivity extends AppCompatActivity {



    String MQTTHOST = "tcp://m20.cloudmqtt.com:15305";
    String USERNAME = "pavgnvtr";
    String USERPASSWORD = "fpvVr7L5Ke3y";
    String topicStr = "/test topic";
    String ames = "test";

    public String mqqthost, username, userps, topic, tmessage;

    MqttAndroidClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init components
        EditText TextHost = (EditText) findViewById(R.id.texthost);
        EditText TextUserName = (EditText) findViewById(R.id.textusername);
        EditText TextPassword = (EditText) findViewById(R.id.textpassword);
        EditText TextTopic = (EditText) findViewById(R.id.texttopic);
        EditText TextMessage = (EditText) findViewById(R.id.textmessage);

        //add text
        TextHost.setText(MQTTHOST);
        mqqthost = TextHost.getText().toString();

        TextUserName.setText(USERNAME);
        username = TextUserName.getText().toString();

        TextPassword.setText(USERPASSWORD);
        userps = TextPassword.getText().toString();

        TextTopic.setText(topicStr);
        topic = TextTopic.getText().toString();

        TextMessage.setText(ames);
        tmessage = TextMessage.getText().toString();

    }



    public void ToConnect(View v){
        //MQTT init
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), mqqthost, clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(userps.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this, "connection failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }



    public void PubMes(View v) {
        EditText TextTopic = (EditText) findViewById(R.id.texttopic);
        EditText TextMessage = (EditText) findViewById(R.id.textmessage);
        topic = TextTopic.getText().toString();
        tmessage = TextMessage.getText().toString();
        //String topic = topicStr;
        //String mess = tmessage; //the same Payload = Message
        //String as = "test";
        //byte[] encodedPayload = new byte[0];
        try {
            //encodedPayload = payload.getBytes("UTF-8");
           // MqttMessage message = new MqttMessage(encodedPayload);
           // client.publish(topic, mess.getBytes(), 0, false);
            client.publish(topic, tmessage.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        /*
                } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
         */ //if you want to add encode
    }

}
