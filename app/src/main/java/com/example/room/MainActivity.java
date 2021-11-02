package com.example.room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.room.RoomDataBase.FunctionContract;
import com.example.room.RoomDataBase.MyData;
import com.example.room.RoomDataBase.PresenterManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FunctionContract.MainView{
    PresenterManager presenterManager;
    Context mContext = this;
    MyData nowSelectedData;
    List<MyData> myData;
    FunctionContract.MainView view = this;
    Button create_btn,modify_btn,delete_btn;
    EditText name_edt,phone_edt,hobby_edt,else_edt;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        presenterManager.initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        presenterManager.onAttachMainView(view);
        presenterManager.setRecycler(recyclerView);
        //--------------修改資料---------------------//
        modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nowSelectedData == null) return;
                String name = name_edt.getText().toString();
                String phone = phone_edt.getText().toString();
                String hobby = hobby_edt.getText().toString();
                String elseInfo = else_edt.getText().toString();
                MyData data = new MyData(nowSelectedData.getId(), name, phone, hobby, elseInfo);
                presenterManager.modifyData(data);
                name_edt.setText("");
                phone_edt.setText("");
                hobby_edt.setText("");
                else_edt.setText("");
                nowSelectedData = null;
                Toast.makeText(mContext, "已更新資訊", Toast.LENGTH_SHORT).show();
                Log.d("Modify", "修改資料");
            }
        });
        //-------------新增資料--------------//
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_edt.getText().toString();
                String phone = phone_edt.getText().toString();
                String hobby = hobby_edt.getText().toString();
                String elseInfo = else_edt.getText().toString();
                if (name.length() == 0) return;
                MyData data = new MyData(name,phone,hobby,elseInfo);
                presenterManager.insertData(data);
                name_edt.setText("");
                phone_edt.setText("");
                hobby_edt.setText("");
                else_edt.setText("");
                Log.d("Create","新增資料");
                Toast.makeText(mContext, "已新增資訊", Toast.LENGTH_SHORT).show();
            }
        });
        //------------刪除資料----------------//
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_edt.setText("");
                phone_edt.setText("");
                hobby_edt.setText("");
                else_edt.setText("");
                nowSelectedData = null;
                Log.d("delete","清空資料");
            }
        });
    }
    private void init(){
        create_btn = findViewById(R.id.create_btn);
        modify_btn = findViewById(R.id.modify_btn);
        delete_btn = findViewById(R.id.clear_btn);
        name_edt = findViewById(R.id.name_edt);
        phone_edt = findViewById(R.id.phone_edt);
        hobby_edt = findViewById(R.id.hobby_edt);
        else_edt = findViewById(R.id.else_edt);
        recyclerView = findViewById(R.id.recyclerView);
        presenterManager = new PresenterManager(mContext);       //忘記創會出null的問題
    }

    @Override
    public void getMyData(List<MyData> myData) {
        this.myData = myData;
    }

    @Override
    public void setRecyclerFunction(RecyclerView recyclerView) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction){
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        presenterManager.deleteData(myData.get(position));
                        break;
                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void setRecyclerOnClick(MyAdapter adapter) {
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((myData)-> {
            nowSelectedData = myData;
            name_edt.setText(myData.getName());
            phone_edt.setText(myData.getPhone());
            hobby_edt.setText(myData.getHobby());
            else_edt.setText(myData.getElseInfo());
        });
    }
    //--------------初始化recyclerview---------------------//
    /*
    @Override
    public void viewInitData(List<MyData> data) {

    }

    @Override
    public void setRecyclerFunction(RecyclerView recyclerView){
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction){
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        myAdapter.deleteData(position);
                        break;
                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
    */
}