package com.example.ailatrieuphu.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HighScoreDAO {
    @Query("select * from HighScore ORDER BY Score DESC")
    public List<HighScore> getAllHighScore();

    @Insert
    public void insertAll(HighScore... highScores);
}
