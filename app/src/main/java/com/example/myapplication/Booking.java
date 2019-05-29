package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.regex.PatternSyntaxException;

public class Booking extends AppCompatActivity {
    String val,id = "";
    EditText fname,lname,gender,mobNo,age,day,month,year;
    Button bookTicket;
    private Calendar calendar;
    int flag = 0;
    String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        val = getIntent().getExtras().getString("id");
        for(int i = 0 ; i < val.length() - 1 ; i++){

            if(flag == 1){
                id+=val.charAt(i);
            }
            if(val.charAt(i) == '('){
                flag = 1;
            }
        }


        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        Log.e("val", "onCreate: "+ val );
        Log.e("id", "onCreate: "+ id );
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        gender = findViewById(R.id.gender);
        mobNo = findViewById(R.id.mobNo);
        age = findViewById(R.id.age);
        bookTicket = findViewById(R.id.bookTicket);


        final Spinner dropdown = findViewById(R.id.spinner1);
//create a list of items for the spinner.
        String[] items = new String[]{"SS", "A1", "A2", "A3"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        Thread myThread = new Thread(new MyServerThread());
        myThread.start();

        SharedPreferences prefs = getSharedPreferences("MYPREF", MODE_PRIVATE);
        final String restoredText = prefs.getString("userid", null);


        bookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = day.getText().toString() + "-" + month.getText().toString() + "-" + year.getText().toString();
                MessageSender messageSender = new MessageSender();
                Log.e("date", "onClick: " + date );
                messageSender.execute("5"+ id + "/" + date + "/" + restoredText + "/" + dropdown.getSelectedItem().toString() + "/" + fname.getText().toString() + "/" + lname.getText().toString() +"/"+ gender.getText().toString() +"/"+ mobNo.getText().toString()+"/"+ age.getText().toString());
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
