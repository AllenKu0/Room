package com.example.room.RoomDataBase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "MyTable")
public class MyData {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String phone;
    private String hobby;
    private String elseInfo;

    public MyData(String name,String phone,String hobby,String elseInfo){
        this.name = name;
        this.phone = phone;
        this.hobby = hobby;
        this.elseInfo = elseInfo;
    }
    @Ignore
    public MyData(int id ,String name,String phone,String hobby,String elseInfo){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.hobby = hobby;
        this.elseInfo = elseInfo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getElseInfo() {
        return elseInfo;
    }

    public void setElseInfo(String elseInfo) {
        this.elseInfo = elseInfo;
    }
}
