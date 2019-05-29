package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity {

    Button delStation,delTrain,upStation,upTrain,inStation,inTrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        delStation = findViewById(R.id.deleteStation);
        delTrain = findViewById(R.id.deleteTrain);
        upStation = findViewById(R.id.updateStation);
        upTrain = findViewById(R.id.updateTrain);
        inStation = findViewById(R.id.insertStation);
        inTrain = findViewById(R.id.insertTrain);

        inTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),InsertTrain.class);
                startActivity(i1);
            }
        });

        inStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),InsertStation.class);
                startActivity(i1);
            }
        });

        delStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),DeleteStation.class);
                startActivity(i1);
            }
        });

        delTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),DeleteTrain.class);
                startActivity(i1);
            }
        });

        upStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),UpdateStation.class);
                startActivity(i1);
            }
        });

        upTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),UpdateTrain.class);
                startActivity(i1);
            }
        });
    }
}
