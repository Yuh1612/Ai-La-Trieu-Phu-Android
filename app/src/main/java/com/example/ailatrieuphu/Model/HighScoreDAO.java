package com.example.ailatrieuphu.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HighScoreDAO {
    @Query("Select * from HighScore")
    public List<HighScore> getAllHighScore();

    @Insert
    public void insertAll(HighScore... highScores);
}
