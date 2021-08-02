package com.example.surveyapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.surveyapplication.models.Users;

@Dao
public interface UsersDAO {

    @Insert
    void insert(Users... Users);

    @Update
    void update(Users... Users);

    @Delete
    void delete(Users... Users);

    @Query("SELECT COUNT(id) from users")
    int getAllCount();

    @Query("SELECT COUNT(id) from users WHERE phone_number =:phone_number")
    int checkAlreadyExists(String phone_number);

    @Query("SELECT * from users WHERE phone_number =:phone_number")
    Users getUserDetails(String phone_number);
}
