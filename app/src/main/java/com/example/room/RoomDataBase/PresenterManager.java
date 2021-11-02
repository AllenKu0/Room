package com.example.room.RoomDataBase;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.room.MyAdapter;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PresenterManager<M extends FunctionContract.MainView> implements FunctionContract.Presenter<M>{
    private M mainView;
    private MyAdapter adapter;
    private Context mContext;
    public PresenterManager(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onAttachMainView(M mainView) {
        this.mainView = mainView;
    }
    @Override
    public void initData() {
        DataBase.getInstance(mContext).getDataUao().displayAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MyData>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<MyData> myData) {
                        adapter = new MyAdapter(mContext,myData);
                        mainView.setRecyclerOnClick(adapter);
                        mainView.getMyData(myData);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Override
    public void refreshData() {
        DataBase.getInstance(mContext).getDataUao().displayAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MyData>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<MyData> myData) {
                        adapter.refreshView(myData);
                        Log.d("Refresh","已刷新");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Override
    public void modifyData(MyData data) {
        DataBase.getInstance(mContext).getDataUao().updataData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        refreshData();
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void insertData(MyData data) {
        DataBase.getInstance(mContext).getDataUao().insertData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                       refreshData();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void deleteData(MyData myData) {
        DataBase.getInstance(mContext).getDataUao().deleteData(myData.getId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    refreshData();
                }
                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
    }

    @Override
    public void setRecycler(RecyclerView recycler) {
        mainView.setRecyclerFunction(recycler);
    }
}
