package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Login extends AppCompatActivity {

    RelativeLayout reLay1,reLay2;
    Button signUp,login;
    String usernameStr,passwordStr;
    EditText username,password;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            reLay1.setVisibility(View.VISIBLE);
            reLay2.setVisibility(View.VISIBLE);
        }
    };
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RadioGroup radioGroup;
    RadioButton admin,user;
    String typeUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        radioGroup = findViewById(R.id.type);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.user){
                    typeUs = "user";
                }
                if(checkedId == R.id.admin){
                    typeUs = "admin";
                }
            }
        });
        admin = findViewById(R.id.admin);
        user = findViewById(R.id.user);
        signUp = findViewById(R.id.signUp);
        reLay1 = findViewById(R.id.relLay1);
        reLay2 = findViewById(R.id.relLay2);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        sharedPreferences = getSharedPreferences("MYPREF", this.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        handler.postDelayed(runnable,2000);

        Thread myThread = new Thread(new MyServerThread());
        myThread.start();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Login.this,SignUp.class);
                startActivity(i1);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameStr = username.getText().toString();
                passwordStr = password.getText().toString();
                MessageSender messageSender = new MessageSender();
                messageSender.execute("1"+usernameStr + "/" + passwordStr);
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
                    System.out.println(mess);
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),mess,Toast.LENGTH_LONG).show();
                            if(mess.compareTo("yes") == 0){
                                editor.putString("userid", usernameStr);
                                editor.commit();

                                Intent i1 = null;
                                if(typeUs.compareTo("admin") == 0){
                                    i1 = new Intent(Login.this,Admin.class);
                                }
                                if(typeUs.compareTo("user") == 0){
                                    i1 = new Intent(Login.this,MainActivity.class);
                                }

                                startActivity(i1);
                                finish();
                            }
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
