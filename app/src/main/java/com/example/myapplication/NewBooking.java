package com.example.myapplication;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.PatternSyntaxException;

public class NewBooking extends AppCompatActivity {

    Button search;
    Context content;
    RecyclerView recyclerView;
    String[] myListData;
    EditText source,destination,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking);
        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
        date = findViewById(R.id.date);
        search = findViewById(R.id.search);
        final RelativeLayout relativeLayout = findViewById(R.id.relLay);

        Thread myThread = new Thread(new MyServerThread());
        myThread.start();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.VISIBLE);
                MessageSender messageSender = new MessageSender();
                messageSender.execute("4"+source.getText().toString() + "/" + destination.getText().toString() +"/"+ date.getText().toString());
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
                            Log.e("hojaa", "run: "+ splitArray[0] );
                            myListData = splitArray;

                            MyListAdapter adapter = new MyListAdapter(myListData,getApplicationContext());
                            Log.e("ek baar", "onClick: "+ myListData.length );
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(adapter);

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
