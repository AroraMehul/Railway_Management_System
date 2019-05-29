package com.example.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.PatternSyntaxException;

public class CheckSeats extends AppCompatActivity {

    private EditText train,dates;
    private TextView sss,a1,a2,a3;
    private Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_seats);

        train = findViewById(R.id.trainId);
        dates = findViewById(R.id.dates);
        check = findViewById(R.id.check);
        sss = findViewById(R.id.ss);
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);

        Thread myThread = new Thread(new MyServerThread());
        myThread.start();


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageSender messageSender = new MessageSender();
                messageSender.execute("d"+train.getText().toString() + "/" + dates.getText().toString());
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
                            sss.setText("SS : " +splitArray[0]);
                            a1.setText("A1 : " +splitArray[1]);
                            a2.setText("A2 : " +splitArray[2]);
                            a3.setText("A3 : " + splitArray[3]);

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
