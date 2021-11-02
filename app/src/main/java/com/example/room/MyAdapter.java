package com.example.room;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.RoomDataBase.DataBase;
import com.example.room.RoomDataBase.MyData;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<MyData> myData;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    public MyAdapter(Context mContext,List<MyData> myData){
        this.myData = myData;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    //------------更薪資料-----------------//
    public void refreshView(List<MyData> myData){
        this.myData = myData;
        notifyDataSetChanged();
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
        TextView name;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.data);
            view = itemView;
        }
        void binding(int position){
            name.setText(myData.get(position).getName());
            view.setOnClickListener(v -> {
                onItemClickListener.onItemClick(myData.get(position));   //按下view，將按下的項目傳給interface OnItemClickListener中的onItemClick
            });
        }
    }
}
