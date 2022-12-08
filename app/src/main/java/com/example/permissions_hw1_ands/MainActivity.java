package com.example.permissions_hw1_ands;


import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity {

    //view
    private EditText main_edt_password;
    private MaterialButton main_btn_submit;

    //service
    private AudioManager audioManager;
    private ConnectivityManager connManager;
    private BluetoothAdapter bluetoothadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initSystemServices(MainActivity.this);
        initButtons();
    }

    public void findViews() {
        main_edt_password = findViewById(R.id.main_edt_password);
        main_btn_submit = findViewById(R.id.main_btn_submit);

    }

    private void initSystemServices(Context context) {
        //Vibrate
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //Connectivity Service
        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Bluetooth
        bluetoothadapter = BluetoothAdapter.getDefaultAdapter();



    }
    private void initButtons() {
        main_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(main_edt_password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
                }else if(checkConditions(MainActivity.this))
                    Toast.makeText(MainActivity.this,"submit you in",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean checkConditions(Context context){
        //Condition 1 - Vibrate
        if(!isVibrateOn()){
            Toast.makeText(MainActivity.this, "Vibrate is not on", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Condition 2 - Wifi connect
        if(!isWifiConnect()){
            Toast.makeText(MainActivity.this, "Wifi is not connected", Toast.LENGTH_SHORT).show();
            return false;
        }
        //condition 3 -Dark mode
        if (!checkDarkMode(MainActivity.this)){
            Toast.makeText(MainActivity.this, "dark mode is not on", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Condition 4 - Bluetooth
        if(!isBluetoothEnable()){
            Toast.makeText(MainActivity.this, "Bluetooth is disabled", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private boolean checkDarkMode(MainActivity mainActivity) {
        boolean darkIsOn = false;

        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                darkIsOn = true;
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                darkIsOn = false;
                break;
        }
        return darkIsOn;
    }

    private boolean isVibrateOn(){
        if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isWifiConnect(){
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    public boolean isBluetoothEnable(){
        if(bluetoothadapter.isEnabled())
            return true;
        else
            return false;

    }


}