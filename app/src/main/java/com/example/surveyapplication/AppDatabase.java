package com.example.surveyapplication;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.surveyapplication.dao.ResidentialInfoDAO;
import com.example.surveyapplication.dao.UsersDAO;
import com.example.surveyapplication.models.ResidentialInfo;
import com.example.surveyapplication.models.Users;

@Database(entities = {Users.class, ResidentialInfo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsersDAO getUsersDAO();

    public abstract ResidentialInfoDAO getResidentialInfoDAO();

    public static AppDatabase getAppDatabase(Context context) {


        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                "survey_app")
                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries()
                .build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, phone_number TEXT, pin INTEGER NOT NULL default 0, logged_in_status INTEGER NOT NULL default 0)");

            database.execSQL(
                    "CREATE TABLE residential_info (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, serial_no TEXT, name_of_person TEXT,father_husband_name TEXT, mother_name TEXT, residential_address TEXT, mobile_number TEXT, date_of_birth TEXT, age INTEGER NOT NULL default 0, gender_id INTEGER NOT NULL default 0, marital_status_id INTEGER NOT NULL default 0, education_id INTEGER NOT NULL default 0, occupation_id INTEGER NOT NULL default 0, children INTEGER NOT NULL default 0, pregnant_status_id INTEGER NOT NULL default 0, province_id INTEGER NOT NULL default 0, district_id INTEGER NOT NULL default 0, tehsil_id INTEGER NOT NULL default 0)");
        }
    };
}
