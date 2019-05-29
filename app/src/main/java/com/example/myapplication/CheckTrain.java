package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.PatternSyntaxException;

public class CheckTrain extends AppCompatActivity {

    EditText trainID;
    TextView trName,arrTime,depTime,source,destination,date,noOfSeats;
    Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_train);

        trainID = findViewById(R.id.trainId);
        check = findViewById(R.id.checkTrain);
        trName = findViewById(R.id.trName);
        arrTime = findViewById(R.id.arrTime);
        depTime = findViewById(R.id.depTime);
        date = findViewById(R.id.date);
        noOfSeats = findViewById(R.id.noOfSeats);

        Thread myThread = new Thread(new MyServerThread());
        myThread.start();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageSender messageSender = new MessageSender();
                messageSender.execute("3"+trainID.getText().toString());
            }
        });
    }

    class MyServerThread  implements Runnable {
        String[] splitArray = null;
        Socket s;
        ServerSocket ss;
        InputStreamReader isr;
        BufferedReader br;
        String mess;
        Handler h = new Handler();
        @Override
        public void run() {
            Log.e("In run","In run");
            try{

                ss = new ServerSocket(7802);
                Log.e("abc", "run: ks"  );
                while(true){

                    s = ss.accept();
                    Log.e("hjj", "run: kunn");
                    isr = new InputStreamReader(s.getInputStream());
                    br = new BufferedReader(isr);
                    mess = br.readLine();
                    Log.e("Mewss : ",mess);


                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                splitArray = mess.split("@");
                                Log.d("inside run", "run: split hua");
                            } catch (PatternSyntaxException ex) {
                                System.out.println(ex);
                            }
                            Log.e("hojaa", "run: "+ splitArray.length );
                            trName.setText("Name : " +splitArray[1]);
                            arrTime.setText("SS : " +splitArray[2]);
                            depTime.setText("A1 : " +splitArray[3]);
                            noOfSeats.setText("A2 : " +splitArray[4]);
                            date.setText( "A3 :" +splitArray[5]);

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
