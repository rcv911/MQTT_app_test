package com.example.user.testmqtt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
    final String USER_NAME = "user_name";
    final String PASSWORD = "password";

    EditText THost;
    EditText TUserName;
    EditText TPassword;

    Button loadButton;

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
        loadButton = (Button) findViewById(R.id.loadButton);

        View.OnClickListener loadButtonOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUserName();
                loadPassword();
            }
        };

        loadButton.setOnClickListener(loadButtonOnClick);

        //add text
        THost.setText(MQTTHOST);
        mqqthost2 = THost.getText().toString();
//
//        TUserName.setText(USERNAME);
//        username2 = TUserName.getText().toString();
//
//        TPassword.setText(USERPASSWORD);
//        userps2 = TPassword.getText().toString();



    }


    public void onSave(View v){
        //mqqthost2 = THost.getText().toString();
        username2 = TUserName.getText().toString();
        userps2 = TPassword.getText().toString();
        saveUserName();
        savePassword();
        //passing data to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("hostmqtt", mqqthost2);
        intent.putExtra("nameuser", username2);
        intent.putExtra("psuser", userps2);
        startActivity(intent);
    }

    public void saveUserName(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, TUserName.getText().toString());
        editor.commit();
        Toast.makeText(this, "username saved", Toast.LENGTH_SHORT).show();
    }

    public void savePassword(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, TPassword.getText().toString());
        editor.commit();
        Toast.makeText(this, "password saved", Toast.LENGTH_SHORT).show();
    }

    public void loadUserName(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String savedText = sharedPreferences.getString(USER_NAME, "");
        TUserName.setText(savedText);
        Toast.makeText(this, "username loaded", Toast.LENGTH_SHORT).show();
    }

    public void loadPassword(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String savedText = sharedPreferences.getString(PASSWORD, "");
        TPassword.setText(savedText);
        Toast.makeText(this, "password loaded", Toast.LENGTH_SHORT).show();
    }




    public void onClickStart(View v) {
        startService(new Intent(this, ConnectionService.class));
    }

    /*public void onClickStop(View v) {
        stopService(new Intent(this, ConnectionService.class));
    }*/

}
