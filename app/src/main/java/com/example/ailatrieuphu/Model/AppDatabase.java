package com.example.ailatrieuphu.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {HighScore.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HighScoreDAO highScoreDAO();

    private static AppDatabase _instance;

    public static AppDatabase getInstance(Context context){
        if (_instance == null){
            _instance = Room.databaseBuilder(context,AppDatabase.class,"db_AiLaTrieuPhu").build();
        }
        return _instance;
    }
}
