package com.example.developertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;
    public String name, image, title, message;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;
    private static final String TAG = "Something";
    private FirebaseFirestore db = FirebaseFirestore.getInstance( );
    private CollectionReference emailsRef = db.collection("emails");
    private Object EmailsHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.result_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference query = db.collection("emails");

        FirestoreRecyclerOptions <MainActivity> response = new FirestoreRecyclerOptions.Builder <MainActivity>( )
                .setQuery(query, MainActivity.class)
                .build( );

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter <MainActivity, EmailsHolder>(response) {
            @Override
            public void onBindViewHolder(EmailsHolder holder, int position, MainActivity model) {
                holder.name_text.setText(model.getName( ));
                holder.title_text.setText(model.getTitle( ));
                holder.message_text.setText(model.getMessage( ));
                holder.onCreateViewHolder().setOnClickListener(v -> {
                    Snackbar.make((View) EmailsHolder, model.getName( ) + ", " + model.getTitle( ) + " at " + model.getMessage( ), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show( );
                });
            }
        };


        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String status) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MainActivity(String name, String image, String title, String message) {
        this.name = name;
        this.image = image;
        this.title = title;
        this.message = message;
    }
}