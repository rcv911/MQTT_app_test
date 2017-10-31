package com.example.user.testmqtt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import service.MQTTService;


public class MainActivity extends AppCompatActivity {

    String topicStr = "/test topic";
    String ames = "test";

    MQTTService mqttService;

    public String mqqthost, username, userps, topic, tmessage;
    //btn_setting
    ImageButton Btn_Setting, Btn_Connection;
    Button publishButton;
    boolean Mconnect = true;
    EditText TextTopic;
    EditText TextMessage;

    //subscribe text
    TextView SubText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init components
        TextTopic = (EditText) findViewById(R.id.texttopic);
        TextMessage = (EditText) findViewById(R.id.textmessage);
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
        publishButton = (Button) findViewById(R.id.publishButton);

//        new Setting2Activity().load();

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                topic = TextTopic.getText().toString();
                tmessage = TextMessage.getText().toString();
                mqttService.PubMes(topic, tmessage);
            }
        };
        publishButton.setOnClickListener(onClickListener);
        //Btn_Setting.setImageResource(R.drawable.gearwheel); //set Image
        Btn_Setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //going to SettingActivityk
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
                mqqthost = getIntent().getExtras().getString("hostmqtt");
                username = getIntent().getExtras().getString("nameuser");
                userps = getIntent().getExtras().getString("psuser");
                System.out.println(mqqthost + " " + username + " " + userps);
                mqttService = new MQTTService(mqqthost, username, userps, MainActivity.this);

                if (Mconnect) {
                    //pic on connect
                    Btn_Connection.setImageResource(R.mipmap.ic_cloud_off_black_24dp);
                    //Passing data from Setting2Activity
                    //Intent intent = new Intent(MainActivity.this, Setting2Activity.class);
                    //Toast.makeText(MainActivity.this, getIntent().getExtras().getString("hostmqtt"), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(MainActivity.this, getIntent().getExtras().getString("nameuser"), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(MainActivity.this, getIntent().getExtras().getString("psuser"), Toast.LENGTH_SHORT).show();
                    mqttService.connectMQTT();

                    SubText.setText(mqttService.MCallback());
                    //mqttService.setSubscribe("topic test");
                    TextConnection.setText("Connected to " + mqttService.getMqttHost());
                }
                else {
                    //pic off connect
                    Btn_Connection.setImageResource(R.mipmap.ic_cloud_black_24dp);
                    //Dissconet from MQTT host
                    mqttService.hostDisconnect();
                    TextConnection.setText("Disconnected");
                }
                Mconnect = !Mconnect;
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
//        new Setting2Activity().save();
    }
} // end code
