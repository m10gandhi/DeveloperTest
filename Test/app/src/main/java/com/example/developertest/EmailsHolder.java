package com.example.developertest;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.BreakIterator;

import androidx.appcompat.app.AppCompatActivity;

class EmailsHolder extends AppCompatActivity {

    public TextView name_text;
    public  TextView title_text;
    public  TextView message_text;

    public EmailsHolder(View view) {
    }

    public EmailsHolder onCreateViewHolder() {
        View view = LayoutInflater.from(group.getContext())
                .inflate(R.layout.list_layout, group, false);
        return new EmailsHolder(view);
    }


}


