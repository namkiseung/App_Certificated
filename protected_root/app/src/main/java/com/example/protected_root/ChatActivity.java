package com.example.protected_root;


import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.os.Build;
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
    String[] myDataset = {"JAMES: 계산금액 누가 올려바바", "CHRIS: 얼마야??","LANE: 나한테 일단 5천원씩 입금!","LANE: 농협 351-0514-4844-79"};

    EditText etText;
    Button btnSend;
    String email;
    private List<Chat> mChat;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        copy("");
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            email = user.getEmail();
        }else{
            email = "anonymous";
        }
        etText = (EditText) findViewById(R.id.etMessages);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chat chatModel = new Chat();
                String stText = etText.getText().toString();

                if (stText.equals("") || stText.isEmpty()){
                    Toast.makeText(ChatActivity.this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(ChatActivity.this, stText, Toast.LENGTH_SHORT).show();
                   // myDataset[myDataset.length+1]=email+": "+stText;

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("chats").child(formattedDate);
                    //FirebaseDatabase.getInstance().getReference("chats");//.child(formattedDate);
                    Hashtable<String, String> chat = new Hashtable<String, String>();
                    chat.put("email", email);
                    chat.put("text", stText);

                    //FirebaseDatabase.getInstance().getReference().child("chats").push().setValue(stText);//.child(formattedDate);
                    myRef.setValue(chat);
                    etText.setText("");
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

        // specify an adapter (see also next example)
        mChat = new ArrayList<>();
        mAdapter = new MyAdapter(mChat);//mAdapter = new MyAdapter(mChat, email);
        mRecyclerView.setAdapter(mAdapter);//mRecyclerView.setAdapter(mAdapter);
        etText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatActivity.this, "복사 되었습니다.", Toast.LENGTH_SHORT).show();
                copy(myDataset[3]);
            }
        });
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
    public void copy(String copytext) {
        // 클립보드 객체 얻기
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 클립데이터 생성
        ClipData clipData = ClipData.newPlainText("Test Clipboard", copytext);
        // 클립보드에 추가
        clipboardManager.setPrimaryClip(clipData);
    }
    public String paste() {
        String res_data="";
        // 클립보드 객체 얻기
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (!(clipboardManager.hasPrimaryClip())) {
            // 클립보드 데이터가 있을 때 처리
            ClipData data = clipboardManager.getPrimaryClip();
            res_data = data.toString();
        }
        return res_data;
    }
}
/*
Copy 구현
1.URI 형태로 임의의 데이터를 클립보드에 저장하려면, 해당 URI에 접근했을 때 실제 데이터를 얻을 수 있도록 ContentProvider를 제공한다.
2.ClipboardManager 객체를 얻는다.
3.ClipData 객체를 생성한다.
4.클립보드에 추가한다.
...
// 임의의 메서드
public void copy() {
    // 클립보드 객체 얻기
    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    // 클립데이터 생성
    ClipData clipData = ClipData.newPlainText("Test Clipboard", "Test");
    // 클립보드에 추가
    clipboardManager.setPrimaryClip(clipData);
    ...
}
...
Paste 구현
1.ClipboardManager 객체를 얻는다.
2.ClipDescription 객체를 얻어서 원하는 MIME 타입인지 조사한다.
3.MIME 타입에 맞게 처리한다.
...
// 임의의 메서드
public void paste() {
    // 클립보드 객체 얻기
    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    if(!(clipboard.hasPrimaryClip())) {
        // 클립보드 데이터가 있을 때 처리
        ClipData data = clipboard.getPrimaryClip();
        ...
        if(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN)) {
            // MIME 타입이 텍스트일 때 처리
            ...
        }
    }
    ...
}
...
Clipboard 변경시점 알기
ClipboardManager객체에 ClipboardManager.OnPrimaryClipChangedListener 리스너를 등록하면, onPrimaryClipChanged() 메서드를 구현하여 클립보드가 변경되었을 때 콜백을 받을 수 있다.

*/
class ClipboardService extends Service implements ClipboardManager.OnPrimaryClipChangedListener {
    ClipboardManager mManager;

   /* @Override
    public void onCreate() {
        super.onCreate();
        mManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 리스너 등록
        mManager.addPrimaryClipChangedListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 리스너 해제
        mManager.removePrimaryClipChangedListener(this);
    }
*/
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onPrimaryClipChanged() {
        if (mManager != null && mManager.getPrimaryClip() != null) {
            ClipData data = mManager.getPrimaryClip();

            // 한번의 복사로 복수 데이터를 넣었을 수 있으므로, 모든 데이터를 가져온다.
            int dataCount = data.getItemCount();
            for (int i = 0 ; i < dataCount ; i++) {
                Log.e("Test", "clip data - item : "+data.getItemAt(i).coerceToText(this));
            }
        } else {
            Log.e("Test", "No Manager or No Clip data");
        }
    }

}