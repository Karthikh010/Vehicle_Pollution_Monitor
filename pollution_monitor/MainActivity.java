package com.example.pollution_monitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference2;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText regno =(EditText) findViewById(R.id.regno);
        Button login = (Button) findViewById(R.id.loginbtn);
        Button t = (Button) findViewById(R.id.addv);


        reference2=FirebaseDatabase.getInstance().getReference().child("User");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scuid=regno.getText().toString();
                if (scuid.isEmpty()) {
                    Toast.makeText(MainActivity.this, "enter reg no.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Query query = reference2.orderByChild("regno").equalTo(scuid);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    user = snapshot.getValue(User.class);

                                        Toast.makeText(getApplicationContext(), "logged successfully"+user.getRegno(), Toast.LENGTH_SHORT).show();
                                        Intent i=new Intent(MainActivity.this,home.class);
                                        i.putExtra("regno",user.getRegno());
                                        i.putExtra("date",user.getDate());
                                        i.putExtra("enorm",user.getEnorm());
                                        i.putExtra("fuel",user.getFuel());
                                        i.putExtra("co",user.getCo());
                                        i.putExtra("hc",user.getHc());
                                        startActivity(i);

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
                //Intent i = new Intent(MainActivity.this,home.class);
                //startActivity(i);
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this,new_reg.class);
                startActivity(i2);
            }
        });
    }
}