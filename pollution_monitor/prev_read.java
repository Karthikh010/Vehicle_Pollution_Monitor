package com.example.pollution_monitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class prev_read extends AppCompatActivity {
String sreg;
DatabaseReference reference2;
User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_read);
        TextView co = (TextView) findViewById(R.id.co);
        TextView hc = (TextView) findViewById(R.id.hc);
        TextView date = (TextView) findViewById(R.id.date);
        TextView regno = (TextView) findViewById(R.id.reg);
        TextView enorm = (TextView) findViewById(R.id.norm);
        TextView fuel = (TextView) findViewById(R.id.fu);
        TextView vut = (TextView) findViewById(R.id.vut);
        reference2= FirebaseDatabase.getInstance().getReference().child("User");

        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            sreg=b.getString("regno");


        }
        Query query = reference2.orderByChild("regno").equalTo(sreg);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(User.class);

                       // Toast.makeText(getApplicationContext(), "logged successfully", Toast.LENGTH_SHORT).show();
                        co.setText(user.getCo());
                        hc.setText(user.getHc());
                        date.setText(user.getDate());
                        regno.setText(user.getRegno());
                        enorm.setText(user.getEnorm());
                        fuel.setText(user.getFuel());

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(sdf.parse(user.getDate())); // Using today's date
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c.add(Calendar.DATE, 30); // Adding 5 days
                        String dt = sdf.format(c.getTime());

                        vut.setText(dt);

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "no account in this Id", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}