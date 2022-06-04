package com.example.ailatrieuphu.Model;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HighScore {
    @PrimaryKey
    @NonNull
    private String name;

    @ColumnInfo
    private String score;

    public HighScore(@NonNull String name, String score) {
        this.name = name;
        this.score = score;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
