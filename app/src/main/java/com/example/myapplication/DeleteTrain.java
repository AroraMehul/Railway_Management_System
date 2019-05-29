package com.example.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.PatternSyntaxException;

public class DeleteTrain extends AppCompatActivity {

    EditText id;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_train);

        id = findViewById(R.id.train);
        delete = findViewById(R.id.delete);

        Thread myThread = new Thread(new MyServerThread());
        myThread.start();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageSender messageSender = new MessageSender();
                messageSender.execute("9"+id.getText().toString());
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
                            //Log.e("hojaa", "run: "+ splitArray[1] );
                            if(mess.compareTo("successful") == 0){
                                Toast.makeText(getApplicationContext(),mess,Toast.LENGTH_LONG).show();
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
