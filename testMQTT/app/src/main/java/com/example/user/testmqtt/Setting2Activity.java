package com.example.user.testmqtt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Setting2Activity extends AppCompatActivity {

    String MQTTHOST = "tcp://m20.cloudmqtt.com:15305";
    String USERNAME = "pavgnvtr";
    String USERPASSWORD = "fpvVr7L5Ke3y";
    SharedPreferences sharedPreferences;
    final String USER_DATA = "user_data";

    EditText THost;
    EditText TUserName;
    EditText TPassword;

    public String mqqthost2, username2, userps2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "What do you want?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //init components
        THost = (EditText) findViewById(R.id.Thostname);
        TUserName = (EditText) findViewById(R.id.Tusername);
        TPassword = (EditText) findViewById(R.id.Tpassword);

        //add text
        THost.setText(MQTTHOST);
        mqqthost2 = THost.getText().toString();

        TUserName.setText(USERNAME);
        username2 = TUserName.getText().toString();

        TPassword.setText(USERPASSWORD);
        userps2 = TPassword.getText().toString();



    }


    public void onSave(View v){
        //passing data to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("hostmqtt", mqqthost2);
        intent.putExtra("nameuser", username2);
        intent.putExtra("psuser", userps2);
        startActivity(intent);
    }

    public void save(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_DATA, TUserName.getText().toString());
        editor.commit();
        Toast.makeText(this, "text saved", Toast.LENGTH_SHORT).show();
    }

    public void load(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String savedText = sharedPreferences.getString(USER_DATA, "");
        TUserName.setText(savedText);
        Toast.makeText(this, "text loaded", Toast.LENGTH_SHORT).show();
    }




    public void onClickStart(View v) {
        startService(new Intent(this, ConnectionService.class));
    }

    /*public void onClickStop(View v) {
        stopService(new Intent(this, ConnectionService.class));
    }*/

}
