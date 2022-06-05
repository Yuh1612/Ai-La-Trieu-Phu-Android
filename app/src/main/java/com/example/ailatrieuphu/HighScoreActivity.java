package com.example.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.ailatrieuphu.Adapter.HighScoreAdapter;
import com.example.ailatrieuphu.Model.AppDatabase;
import com.example.ailatrieuphu.Model.HighScore;
import com.example.ailatrieuphu.Model.HighScoreDAO;

import java.util.ArrayList;
import java.util.List;

public class HighScoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnBack;
    private AppDatabase appDatabase;
    private HighScoreDAO highScoreDAO;
    private List<HighScore> highScoreList;
    private HighScoreAdapter highScoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_high_score);

        recyclerView = findViewById(R.id.rv_highscore);
        btnBack = findViewById(R.id.btn_back);
        appDatabase = AppDatabase.getInstance(this);
        highScoreDAO = appDatabase.highScoreDAO();

        highScoreList = new ArrayList<>();
        highScoreAdapter = new HighScoreAdapter(highScoreList);
        recyclerView.setAdapter(highScoreAdapter);

        AsyncTask.execute(() -> {
            highScoreList.addAll(highScoreDAO.getAllHighScore());
            highScoreAdapter.notifyDataSetChanged();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnBack.setOnClickListener(view -> onBackPressed());
    }
}