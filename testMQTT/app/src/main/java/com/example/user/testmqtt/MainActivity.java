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
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MainActivity extends AppCompatActivity {

    String topicStr = "/test topic";
    String ames = "test";

    public String mqqthost, username, userps, topic, tmessage;


    //MQTT
    MqttAndroidClient client;

    MqttConnectOptions options;

    //btn_setting
    ImageButton Btn_Setting, Btn_Connection;
    boolean Mconnect = true;

    //subscribe text
    TextView SubText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //init components
        EditText TextTopic = (EditText) findViewById(R.id.texttopic);
        EditText TextMessage = (EditText) findViewById(R.id.textmessage);

        //init subscribe text view
        SubText = (TextView) findViewById(R.id.subText);


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

        // нужно условие, при кот после заполнения данных сервера отобразится кнопка соединения !!!
        // либо как-то по-другому организовать код программы
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
                    connectMQTT();

                    MCallback();


                    TextConnection.setText("Connected to " + mqqthost);

                }
                else {
                    //pic off connect
                    Btn_Connection.setImageResource(R.mipmap.ic_cloud_black_24dp);
                    //Dissconet from MQTT host
                    hostDisconnect();

                    TextConnection.setText("Disconnected");
                }

                Mconnect = !Mconnect;
            }


        });




    }


    public void connectMQTT() {
        System.out.println("Inside connectMQTT");
        //Connect to MQTT host
        //MQTT init
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), mqqthost, clientId);

        options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(userps.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_SHORT).show();
                    //We are Subscribed
                    setSubscribe();


                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this, "connection failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("don't subscribe to topic");
        }


        //Set Callback Reaction
//        MCallback();

    } //end method


    public void PubMes(View v) {
        System.out.println("Enter");
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





    public void hostDisconnect() {
        try {
            IMqttToken token = client.disconnect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are disconnected
                    Toast.makeText(MainActivity.this, "disconnected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. disconnection timeout or firewall problems
                    Toast.makeText(MainActivity.this, "disconnection failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }


    //to Subscribe
    private void setSubscribe() {
        try {
            client.subscribe(topicStr, 0); // (name of topic, QoS = 0)
            Toast.makeText(MainActivity.this, "subscribe", Toast.LENGTH_SHORT).show();
        }catch (MqttException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "exception", Toast.LENGTH_SHORT).show();
        }


    }

    public void MCallback() {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                SubText.setText("" + new String(message.getPayload()));

                //сюда можно добавить вибрацию телефона и рингтон
                //описывать "сущности" вибрации и рингтона в onCreate!
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }






} // end code
