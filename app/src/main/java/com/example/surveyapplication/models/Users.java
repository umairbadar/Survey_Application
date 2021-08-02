package com.example.surveyapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class Users {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name, phone_number;
    private int pin;
    private int province_id;
    private int district_id;
    private int tehsils_id;

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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public int getTehsils_id() {
        return tehsils_id;
    }

    public void setTehsils_id(int tehsils_id) {
        this.tehsils_id = tehsils_id;
    }
}
