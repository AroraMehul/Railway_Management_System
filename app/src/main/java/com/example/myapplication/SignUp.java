package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SignUp extends AppCompatActivity {

    LinearLayout nameLin,genLin,adhLin,mobLin,emLin,passLin;
    Button btn_sign,downLog,back;
    EditText name,address,adhar,mobNo,emailId,password;
    String nameStr,genderStr,adharStr,mobNoStr,emailIDStr,passwordStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.username);
        address = findViewById(R.id.address);
        adhar = findViewById(R.id.adhar);
        mobNo = findViewById(R.id.mobNo);
        emailId = findViewById(R.id.emailId);
        password = findViewById(R.id.password);

        nameLin = findViewById(R.id.nameLin);
        genLin = findViewById(R.id.genLin);
        adhLin = findViewById(R.id.adhLin);
        mobLin = findViewById(R.id.mobLin);
        emLin = findViewById(R.id.emLin);
        passLin = findViewById(R.id.passLin);
        btn_sign = findViewById(R.id.btn_signUp);
        downLog = findViewById(R.id.downLog);
        back = findViewById(R.id.back);

        Thread myThread = new Thread(new MyServerThread());
        myThread.start();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameLin.setVisibility(View.VISIBLE);
                genLin.setVisibility(View.VISIBLE);
                adhLin.setVisibility(View.VISIBLE);
                mobLin.setVisibility(View.VISIBLE);
                emLin.setVisibility(View.GONE);
                passLin.setVisibility(View.GONE);
                btn_sign.setText("Next");
                back.setText("");
            }
        });

        downLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(SignUp.this,Login.class);
                startActivity(i1);
                finish();
            }
        });

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_sign.getText().toString().compareTo("Next") == 0) {
                    nameStr = name.getText().toString();
                    genderStr = address.getText().toString();
                    adharStr = adhar.getText().toString();
                    mobNoStr = mobNo.getText().toString();
                    nameLin.setVisibility(View.GONE);
                    genLin.setVisibility(View.GONE);
                    adhLin.setVisibility(View.GONE);
                    mobLin.setVisibility(View.GONE);
                    emLin.setVisibility(View.VISIBLE);
                    passLin.setVisibility(View.VISIBLE);

                    emailIDStr = emailId.getText().toString();
                    passwordStr = password.getText().toString();
                    btn_sign.setText("SignUp");
                    back.setText("Back");
                }
                else{
                    // Code to register a user
                    Log.e("jope", "onClick: kuch hua" );
                    MessageSender messageSender = new MessageSender();
                    messageSender.execute("e/" + name.getText().toString() + "/" + password.getText().toString() + "/" + emailId.getText().toString() + "/" + address.getText().toString() + "/" +mobNo.getText().toString() + "/" + adhar.getText().toString());

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
                            Toast.makeText(getApplicationContext(),mess,Toast.LENGTH_LONG).show();
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
