package com.solidgeargroup.dialogflow.dialogflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Documento> documentos;
    DatabaseReference databaseReference;
    AdaptadorFirebase miAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        documentos = new ArrayList<Documento>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Documento");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String titulo = ds.child("titulo").getValue().toString();
                    String url = ds.child("imagen").getValue().toString();
                    String descripcion = ds.child("descripcion").getValue().toString();
                    Documento d = new Documento(titulo,descripcion,url);
                    documentos.add(d);
                }
                miAdaptador = new AdaptadorFirebase(MainActivity.this, documentos);
                recyclerView.setAdapter(miAdaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Opss ... un error ocurrio", Toast.LENGTH_SHORT).show();;
            }
        });



    }


}
