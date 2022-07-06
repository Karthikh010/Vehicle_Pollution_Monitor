package com.example.pollution_monitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class check extends AppCompatActivity {
    String sreg;
    DatabaseReference reference2;
    User user;
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
//        LocalDate today = LocalDate.now();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Using today's date
        c.add(Calendar.DATE, 30); // Adding 5 days
        String dt = sdf.format(c.getTime());
        String df = sdf.format(d);

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
            msg=b.getString("Message");


        }

        String[] readings = msg.split(",");
        co.setText("0");
        hc.setText("0");
        co.setText(readings[0]);
        hc.setText(readings[1]);
        date.setText(df);
        vut.setText(dt);
        Query query = reference2.orderByChild("regno").equalTo(sreg);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(User.class);
                        snapshot.getRef().child("date").setValue(date.getText());
                        snapshot.getRef().child("co").setValue(co.getText());
                        snapshot.getRef().child("hc").setValue(hc.getText());
                        Toast.makeText(getApplicationContext(),"value updated",Toast.LENGTH_SHORT).show();

                        // Toast.makeText(getApplicationContext(), "logged successfully", Toast.LENGTH_SHORT).show();
//                        co.setText(user.getCo());
//                        hc.setText(user.getHc());
//                        date.setText(user.getDate());
                        regno.setText(user.getRegno());
                        enorm.setText(user.getEnorm());
                        fuel.setText(user.getFuel());

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