package com.example.pollution_monitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class home extends AppCompatActivity {
String sreg;
    DatabaseReference reference2;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button prev = (Button) findViewById(R.id.prev);
        Button c = (Button) findViewById(R.id.next);
        Button d = (Button) findViewById(R.id.due);
        reference2= FirebaseDatabase.getInstance().getReference().child("User");
        OkHttpClient client = new OkHttpClient();
        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            sreg=b.getString("regno");

        }
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(home.this,prev_read.class);
                i.putExtra("regno",sreg);
                startActivity(i);
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.43.4";


                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            home.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i2 = new Intent(home.this,check.class);
                                    i2.putExtra("Message",myResponse);
                                    i2.putExtra("regno",sreg);
                                    startActivity(i2);
                                    Toast.makeText(home.this, "Successfully Connected", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = reference2.orderByChild("regno").equalTo(sreg);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                user = snapshot.getValue(User.class);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar c = Calendar.getInstance();
                                try {
                                    c.setTime(sdf.parse(user.getDate())); // Using today's date
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                c.add(Calendar.DATE, 30); // Adding 5 days
                                String dt = sdf.format(c.getTime());
                                Toast.makeText(home.this,"Your Pollution test is due on  "+dt,Toast.LENGTH_LONG).show();
                                // Toast.makeText(getApplicationContext(), "logged successfully", Toast.LENGTH_SHORT).show();
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
        });
    }
}