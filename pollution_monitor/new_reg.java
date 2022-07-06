package com.example.pollution_monitor;

import static java.sql.DriverManager.println;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;


public class new_reg extends AppCompatActivity {
    String sregno, sowner, senorm, sfuel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reg);
        Button b = (Button) findViewById(R.id.add);
        EditText regno = (EditText) findViewById(R.id.regno);
        EditText owner = (EditText) findViewById(R.id.owner);
        EditText enorm = (EditText) findViewById(R.id.emnrm);
        EditText fuel = (EditText) findViewById(R.id.fuel);
        user=new User();
        firebaseDatabase = FirebaseDatabase.getInstance();
//        CollectionReference dbCourses = firebaseDatabase.collection("User");
         reference= FirebaseDatabase.getInstance().getReference().child("User");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(regno.getText().toString().isEmpty()){
                    Toast.makeText(new_reg.this,"Enter a Regno.",Toast.LENGTH_SHORT).show();
                }
                else if(owner.getText().toString().isEmpty()){
                    Toast.makeText(new_reg.this,"Enter owner name.",Toast.LENGTH_SHORT).show();
                }
                else if(enorm.getText().toString().isEmpty()){
                    Toast.makeText(new_reg.this,"Enter the emmission norm",Toast.LENGTH_SHORT).show();
                }
                else if(fuel.getText().toString().isEmpty()){
                    Toast.makeText(new_reg.this,"Enter vehicle fuel.",Toast.LENGTH_SHORT).show();
                }
                else{
                    sregno  = regno.getText().toString();
                    sowner  = owner.getText().toString();
                    senorm  = enorm.getText().toString();
                    sfuel  = fuel.getText().toString();
//                    Toast.makeText(getApplicationContext(),""+sregno,Toast.LENGTH_SHORT).show();
                    user.setCo("0");
                    user.setDate("0");
                    user.setDensity("0");
                    user.setEnorm(senorm);
                    user.setFuel(sfuel);
                    user.setHc("0");
                    user.setRegno(sregno);
                    user.setOwner(sowner);
                    Query query = reference.orderByChild("regno").equalTo(sregno);

//                    reference.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            // after the data addition is successful
//                            // we are displaying a success toast message.
//                            // Toast.makeText(MainActivity.this, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // this method is called when the data addition process is failed.
//                            // displaying a toast message when data addition is failed.
//                            //   Toast.makeText(MainActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
//                        }
//                    });


                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                Toast.makeText(getApplicationContext(), "already exist", Toast.LENGTH_SHORT).show();
                            } else {


                                reference.child(sregno).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(getApplicationContext(), "succefully registered", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(new_reg.this,MainActivity.class);
                                        startActivity(i);

                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(getApplicationContext(), "failed to register", Toast.LENGTH_SHORT).show();


                        }
                    });


                }

            }
        });
    }
}