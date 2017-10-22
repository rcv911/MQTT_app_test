package com.example.user.testmqtt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;


public class MainActivity extends AppCompatActivity {

    String topicStr = "/test topic";
    String ames = "test";

    public String mqqthost, username, userps, topic, tmessage;


    //MQTT
    MqttAndroidClient client;

    //btn_setting
    ImageButton Btn_Setting, Btn_Connection;
    boolean Msetting, Mconnect = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init components
        EditText TextTopic = (EditText) findViewById(R.id.texttopic);
        EditText TextMessage = (EditText) findViewById(R.id.textmessage);


        //add text
        TextTopic.setText(topicStr);
        topic = TextTopic.getText().toString();

        TextMessage.setText(ames);
        tmessage = TextMessage.getText().toString();

        //init Buttons
        Btn_Setting = (ImageButton) findViewById(R.id.btn_setting);
        Btn_Connection = (ImageButton) findViewById(R.id.btn_connection);


        //Btn_Setting.setImageResource(R.drawable.gearwheel); //set Image
        Btn_Setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //going to SettingActivity
                switch (v.getId()) {
                    case R.id.btn_setting:
                        //call SettingActivity
                        Intent SetActIntent = new Intent(MainActivity.this, Setting2Activity.class);
                        startActivity(SetActIntent);
                        break;
                    default:
                        break;
                }
            }
        });

        //Btn_Connection
        Btn_Connection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView TextConnection = (TextView) findViewById(R.id.con_status);

                if (Mconnect) {
                    //pic on connect
                    Btn_Connection.setImageResource(R.mipmap.ic_cloud_off_black_24dp);
                    //Passing data from Setting2Activity
                    //Intent intent = new Intent(MainActivity.this, Setting2Activity.class);
                    mqqthost = getIntent().getExtras().getString("hostmqtt");
                    username = getIntent().getExtras().getString("nameuser");
                    userps = getIntent().getExtras().getString("psuser");

                    //Toast.makeText(MainActivity.this, getIntent().getExtras().getString("hostmqtt"), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(MainActivity.this, getIntent().getExtras().getString("nameuser"), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(MainActivity.this, getIntent().getExtras().getString("psuser"), Toast.LENGTH_SHORT).show();
                    ConnetcMQTT(v);
                    TextConnection.setText("Connected to " + mqqthost);

                }
                else {
                    //pic off connect
                    Btn_Connection.setImageResource(R.mipmap.ic_cloud_black_24dp);
                    //Dissconet from MQTT host
                    TextConnection.setText("Disconnected");
                }

                Mconnect = !Mconnect;
            }


        });


    }


    public void ConnetcMQTT(View v) {
        //Connect to MQTT host
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
    } //end method


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













} // end code
