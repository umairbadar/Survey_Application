package com.example.surveyapplication;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.surveyapplication.dao.UsersDAO;
import com.example.surveyapplication.models.Users;

@Database(entities = {Users.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsersDAO getUsersDAO();

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
                    "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, phone_number TEXT, pin INTEGER NOT NULL default 0, province_id INTEGER NOT NULL default 0, district_id INTEGER NOT NULL default 0, tehsils_id INTEGER NOT NULL default 0)");
        }
    };
}
