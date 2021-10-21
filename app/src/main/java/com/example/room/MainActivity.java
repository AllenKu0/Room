package com.example.room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.room.RoomDataBase.DataBase;
import com.example.room.RoomDataBase.MyData;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyAdapter myAdapter;
    MyData nowSelectedData;
    Button create_btn,modify_btn,delete_btn;
    EditText name_edt,phone_edt,hobby_edt,else_edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        setRecyclerFunction(recyclerView);
        //--------------初始化recyclerview---------------------//
        new Thread(()->{
            List<MyData> data = DataBase.getInstance(this).getDataUao().displayAll();
            myAdapter = new MyAdapter(this,data);
            runOnUiThread(()->{
                recyclerView.setAdapter(myAdapter);
                myAdapter.setOnItemClickListener((myData)->{
                    nowSelectedData = myData;
                    name_edt.setText(myData.getName());
                    phone_edt.setText(myData.getPhone());
                    hobby_edt.setText(myData.getHobby());
                    else_edt.setText(myData.getElseInfo());
                });
            });
            Log.d("recyclerView","初始化recyclerview");
        }).start();
        //--------------修改資料---------------------//
        modify_btn.setOnClickListener((v)-> {
            new Thread(()->{
                if(nowSelectedData == null) return;
                String name = name_edt.getText().toString();
                String phone = phone_edt.getText().toString();
                String hobby = hobby_edt.getText().toString();
                String elseInfo = else_edt.getText().toString();
                MyData data = new MyData(nowSelectedData.getId(),name,phone,hobby,elseInfo);
                DataBase.getInstance(this).getDataUao().updataData(data);
                runOnUiThread(()->{
                    name_edt.setText("");
                    phone_edt.setText("");
                    hobby_edt.setText("");
                    else_edt.setText("");
                    nowSelectedData = null;
                    myAdapter.refreshView();
                    Toast.makeText(this,"已更新資訊",Toast.LENGTH_SHORT).show();
                });
                Log.d("Modify","修改資料");
            }).start();
        });

        //-------------新增資料--------------//
        create_btn.setOnClickListener((v->{
            new Thread(()->{
                String name = name_edt.getText().toString();
                String phone = phone_edt.getText().toString();
                String hobby = hobby_edt.getText().toString();
                String elseInfo = else_edt.getText().toString();
                if (name.length() == 0) return;
                MyData data = new MyData(name,phone,hobby,elseInfo);
                DataBase.getInstance(this).getDataUao().insertData(data);
                runOnUiThread(()->{
                    myAdapter.refreshView();
                    name_edt.setText("");
                    phone_edt.setText("");
                    hobby_edt.setText("");
                    else_edt.setText("");
                });
                Log.d("Create","新增資料");
            }).start();
        }));
        //------------刪除資料----------------//
        delete_btn.setOnClickListener((v)->{
            new Thread(()->{
                runOnUiThread(()->{
                    name_edt.setText("");
                    phone_edt.setText("");
                    hobby_edt.setText("");
                    else_edt.setText("");
                    nowSelectedData = null;
                });
                Log.d("delete","清空資料");
            }).start();
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
    }
    private void setRecyclerFunction(RecyclerView recyclerView){
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
}