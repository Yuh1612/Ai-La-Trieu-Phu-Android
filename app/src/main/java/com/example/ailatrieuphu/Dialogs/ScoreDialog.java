package com.example.ailatrieuphu.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ailatrieuphu.Model.AppDatabase;
import com.example.ailatrieuphu.Model.HighScore;
import com.example.ailatrieuphu.Model.HighScoreDAO;
import com.example.ailatrieuphu.Model.Question;
import com.example.ailatrieuphu.R;

public class ScoreDialog extends Dialog implements View.OnClickListener {

    private int score;
    private EditText edtName;
    private TextView tvScore;

    private AppDatabase appDatabase;
    private HighScoreDAO highScoreDAO;

    public ScoreDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.score_dialog);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        edtName = findViewById(R.id.edt_name);
        tvScore = findViewById(R.id.tv_score);
        findViewById(R.id.btn_ok).setOnClickListener(this);

        appDatabase = AppDatabase.getInstance(context);
        highScoreDAO = appDatabase.highScoreDAO();
    }

    public void setScore(String score) {
        tvScore.setText(score + " VNĐ");
        this.score = Integer.parseInt(score.replaceAll(",", ""));
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_ok){
            if (edtName.getText().toString().isEmpty()) {
                return;
            }
            AsyncTask.execute(() -> {
                HighScore highScore = new HighScore(0,edtName.getText().toString().trim(), score);
                highScoreDAO.insertAll(highScore);
            });
            dismiss();
        }
    }
}
