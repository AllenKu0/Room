package com.example.room.RoomDataBase;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.room.MyAdapter;

import java.util.List;

public interface FunctionContract {

    interface MainView {
        void getMyData(List<MyData> myData);
        void setRecyclerFunction(RecyclerView recyclerView);
        void setRecyclerOnClick(MyAdapter adapter);
    }

    interface Presenter<M extends MainView> {
        void refreshData();
        void initData();
        void onAttachMainView(M mainView);
        void modifyData(MyData data);
        void insertData(MyData data);
        void deleteData(MyData myData);
        void setRecycler(RecyclerView recycler);

    }
}
