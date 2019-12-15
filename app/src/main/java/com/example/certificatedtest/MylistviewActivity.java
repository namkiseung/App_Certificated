package com.example.certificatedtest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

class RecyclerviewProduct{
    private String url;
    public RecyclerviewProduct(String name){this.url=name;}
    public String getUrl(){return this.url;}
}
class RecyclerviewViewHolder extends RecyclerView.ViewHolder{
    public TextView textview;
    public RecyclerviewViewHolder(@NonNull View itemView) {
        super(itemView);
        textview = (TextView)itemView.findViewById(R.id.itemtextview);
    }
}
//기계를 연결할 때 필요한 변환작업을 위함 함수
class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewViewHolder> implements View.OnCreateContextMenuListener{
    private ArrayList<RecyclerviewProduct> arrayList; //item의 갯수
    private Context context;
    RecyclerviewViewHolder holder;
    public int itemposition;

    public void setArrayList(ArrayList<RecyclerviewProduct> list, Context context){
        this.arrayList = list;
        this.context=context;
    }
    /*다행히 onBindViewHolder에서 리사이클러뷰의 위치 값을 가지고 있네...
    * 그리고 onMenuItem쪽에서는 위치 값을 알려주지 않아서 방법이 없음...그래서 onBindViewHolder에서 위치값을 전역변수로 뺌
    * onMunuItemClick이 onClick때 이벤트가 일어나서 setOnLongClickListener이벤트를 deploy함*/

    @NonNull
    @Override
    public RecyclerviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //URL추가시 아이템이 통채로 추가할 때 setContentView 를 하기 위해 인플레이터가 필요
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        holder = new RecyclerviewViewHolder(view);
        view.setOnCreateContextMenuListener(this);//메뉴 아이템 클릭 리스너 사용
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewViewHolder recyclervierHolder, int i) {
        RecyclerviewProduct data = arrayList.get(holder.getAdapterPosition());
        System.out.println("현재 위치 값(매개변수) :"+i);
        System.out.println("현재 위치 값(호출변수) :"+holder.getAdapterPosition());
        holder.textview.setText(data.getUrl());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                itemposition = holder.getAdapterPosition();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    /*플로팅의 메뉴에 수정과 삭제 버튼에 대한 이벤트 정의하기 ㅋ*/
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem update = contextMenu.add(Menu.NONE, 100, 1, "수정");
        MenuItem remove = contextMenu.add(Menu.NONE, 101, 2, "삭제");
        update.setOnMenuItemClickListener(onEditMenu);
        remove.setOnMenuItemClickListener(onEditMenu);

    }
    private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            /*수정할까 삭제할까*/
            switch (menuItem.getItemId()){
                /*수정할 때 사용하는 Edit값과 버튼 객체 만들어놓자*/
                case 100:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View view = LayoutInflater.from(context).inflate(R.layout.dialogupdate, null, false);
                    builder.setView(view);
                    final Button buttonSubmit = (Button)view.findViewById(R.id.dialog_urlupdate_btn);
                    final EditText editupdateText = (EditText)view.findViewById(R.id.editupdateurl);
                    editupdateText.setText(arrayList.get(itemposition).getUrl());
                    final AlertDialog dialog = builder.create();
                    buttonSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String updateurl = editupdateText.getText().toString();
                            arrayList.set(itemposition, new RecyclerviewProduct(updateurl));
                            notifyItemChanged(itemposition);
                            Toast.makeText(holder.itemView.getContext(), "수정 완료", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 101:
                    arrayList.remove(itemposition);
                    notifyItemRemoved(itemposition);
                    notifyItemRangeChanged(itemposition, arrayList.size());
                    Toast.makeText(holder.itemView.getContext(), "삭제", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    };

}

public class MylistviewActivity extends AppCompatActivity {
    com.github.clans.fab.FloatingActionButton itemaddbutton;
    RecyclerView recyclerView;
    RecyclerviewAdapter adapter;
    ArrayList<RecyclerviewProduct> products;
    LinearLayoutManager mLayoutManager;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylistview);
        itemaddbutton = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.floatingbutton);

        recyclerView = (RecyclerView)findViewById(R.id.recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(MylistviewActivity.this));

        products = new ArrayList<>();
        adapter = new RecyclerviewAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemaddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*다이얼 로그떴다?*/
                dialog = new Dialog(MylistviewActivity.this);
                dialog.setContentView(R.layout.dialogadd);

                final EditText editText = (EditText)dialog.findViewById(R.id.edittext);
                Button urladdbtn = (Button)dialog.findViewById(R.id.dialog_urladd_btn);
                /*추가 버튼 클릭했으므로 Add 코드 작성*/
                urladdbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = editText.getText().toString();
                        products.add(new RecyclerviewProduct(url));
                        adapter.notifyDataSetChanged();
                        /*어뎁터 init*/
                        adapter.setArrayList(products, MylistviewActivity.this);
                        recyclerView.setAdapter(adapter);
                        /**/
                        Toast.makeText(MylistviewActivity.this, "추가되었습니다", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                createdialog(dialog);
            }
        });
    }
    /*다이얼로그 해상도 밸런스 맞추기*/
    public void createdialog(Dialog dialog){
        dialog.show();
        Display display = getWindowManager().getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);

        Window window = dialog.getWindow();
        int x = (int)(size.x * 0.8f);
        int y = (int)(size.y * 0.25f);
        window.setLayout(x,y);

    }
}
