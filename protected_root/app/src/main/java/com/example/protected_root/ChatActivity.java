package com.example.protected_root;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    String TAG = this.getClass().getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset = {"JAMES: 안녕", "CHRIS: 오늘 뭐했어?","LANE: 영화볼래?"};


    EditText etText;
    Button btnSend;
    String email;
    List<Chat> mChat;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            email = user.getEmail();
        }
        etText = (EditText) findViewById(R.id.etMessages);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String stText = etText.getText().toString();
                if (stText.equals("") || stText.isEmpty()){
                    Toast.makeText(ChatActivity.this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(ChatActivity.this, stText, Toast.LENGTH_SHORT).show();
                    myDataset[myDataset.length+1]=email+": "+stText;

                    //Calendar c = Calendar.getInstance();
                    //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //String formattedDate = df.format(c.getTime());
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("chats");//.child(formattedDate);

//                    Hashtable<String, String> chat = new Hashtable<String, String>();
//                    chat.put("email", email);
//                    chat.put("text", stText);
                    myRef.child("chats").setValue(stText);

                }
            }
        });

//        Button btnFinish = (Button) findViewById(R.id.btnFinish);
//        btnFinish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                FirebaseAuth.getInstance().signOut();
//                finish();
//
//            }
//        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mChat = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);//mAdapter = new MyAdapter(mChat, email);
        mRecyclerView.setAdapter(mAdapter);//mRecyclerView.setAdapter(mAdapter);

        DatabaseReference myRef = database.getReference("chats");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Chat chat = dataSnapshot.getValue(Chat.class);

                // [START_EXCLUDE]
                // Update RecyclerView

                mChat.add(chat);
                mAdapter.notifyItemInserted(mChat.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    @Override
    public void onStop(){
        super.onStop();
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
