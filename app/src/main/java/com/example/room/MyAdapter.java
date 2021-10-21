package com.example.room;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.RoomDataBase.DataBase;
import com.example.room.RoomDataBase.MyData;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<MyData> myData;
    private Activity activity;
    private OnItemClickListener onItemClickListener;
    public MyAdapter(Activity activity, List<MyData> myData){
        this.activity = activity;
        this.myData = myData;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    //------------更薪資料-----------------//
    public void refreshView(){
        new Thread(() -> {
            List<MyData> data = DataBase.getInstance(activity).getDataUao().displayAll();
            this.myData = data;
            activity.runOnUiThread(()->{
                notifyDataSetChanged();
            });
        }).start();
    }
    //------------刪除資料-----------------//
    public void  deleteData(int position){
        new Thread(() -> {
            DataBase.getInstance(activity).getDataUao().deleteData(myData.get(position).getId());
            activity.runOnUiThread(()->{
                notifyItemRemoved(position);
                refreshView();
            });
        }).start();
    }
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.binding(position);
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }
    public interface OnItemClickListener {
        void onItemClick(MyData myData);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tx1;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tx1 = itemView.findViewById(R.id.data);
            view = itemView;
        }
        void binding(int position){
            tx1.setText(myData.get(position).getName());
            view.setOnClickListener(v -> {
                onItemClickListener.onItemClick(myData.get(position));
            });
        }
    }
}
