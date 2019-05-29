package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class UpdateUser extends AppCompatActivity {

    EditText newVal,confVal;
    Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        final Spinner dropdown = findViewById(R.id.spinner1);
//create a list of items for the spinner.
        String[] items = new String[]{"Name", "Aadhar Number", "Address", "Mobile Number","Email_Id","Password"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        newVal = findViewById(R.id.newVal);
        confVal = findViewById(R.id.confVal);
        update = findViewById(R.id.update);

        SharedPreferences prefs = getSharedPreferences("MYPREF", MODE_PRIVATE);
        final String restoredText = prefs.getString("userid", null);

        Thread myThread = new Thread(new MyServerThread());
        myThread.start();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newVal.getText().toString().compareTo(confVal.getText().toString()) == 0){
                    // code to update user
                    //need to find how to take out user id from login screen
                    MessageSender messageSender = new MessageSender();
                    messageSender.execute("2"+restoredText +"/"+dropdown.getSelectedItem() + "/" + newVal.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(),"New value and Confirm value doesnot match",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    class MyServerThread  implements Runnable {

        Socket s;
        ServerSocket ss;
        InputStreamReader isr;
        BufferedReader br;
        String mess;
        Handler h = new Handler();
        @Override
        public void run() {
            try{
                ss = new ServerSocket(7802);
                while(true){
                    s = ss.accept();
                    isr = new InputStreamReader(s.getInputStream());
                    br = new BufferedReader(isr);
                    mess = br.readLine();
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),mess ,Toast.LENGTH_LONG).show();
                        }
                    });
                    ss.close();
                }
            }catch (IOException e){
                Log.e("run", "run: " + e );
            }
        }
    }
}
