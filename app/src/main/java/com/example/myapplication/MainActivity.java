package com.example.myapplication;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    Button newBooking,booking,checkTrain,checkStation,checkSeats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newBooking = findViewById(R.id.newBooking);
        booking = findViewById(R.id.bookings);
        checkTrain = findViewById(R.id.checkTrain);
        checkStation = findViewById(R.id.checkStation);
        checkSeats = findViewById(R.id.checkSeats);

        checkStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),CheckStation.class);
                startActivity(i1);
            }
        });

        newBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),NewBooking.class);
                startActivity(i1);
            }
        });

        checkTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),CheckTrain.class);
                startActivity(i1);

            }
        });

        checkSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),CheckSeats.class);
                startActivity(i1);
            }
        });

    }

    public void changeUser(View view) {
        Intent i1 = new Intent(getApplicationContext(),UpdateUser.class);
        startActivity(i1);
    }
}


