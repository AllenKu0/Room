package com.example.room.RoomDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface DataUao {
    String tableName = "MyTable";

    //--------------新增資料--------------------//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(MyData myData);
    /*
    @Query("INSERT INTO "+tableName+"(name,phone,hobby,elseInfo) VALUES (:name,:phone,:hobby,:elseData)")
    void insertData(String name,String phone,String hobby ,String elseData);
    */
    //--------------撈取資料--------------------//
    @Query("SELECT * FROM " + tableName)
    List<MyData> displayAll();
    /*
    @Query("SELECT * FROM "+ tableName +" WHERE name = :name")
    List<MyData> findDataByName(String name);
     */
    //--------------更新資料--------------------//
    @Update
    void updataData(MyData myData);
    /*
    @Query("UPDATE "+tableName+" SET name=:name,phone=:phone,hobby=:hobby,elseInfo=elseInfo WHERE id =:id" )
    void updateData(int id,String name,String phone,String hobby ,String elseData);
     */
    //--------------刪除資料--------------------//
    /*
    @Delete
    void deleteData(MyData myData);
     */
    @Query("DELETE FROM "+tableName+" WHERE id = :id")
    void deleteData(int id);
}
