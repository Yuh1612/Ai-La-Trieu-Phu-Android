package com.example.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ailatrieuphu.Dialogs.AudienceDialog;
import com.example.ailatrieuphu.Dialogs.CallDialog;
import com.example.ailatrieuphu.Dialogs.NoticeDialog;
import com.example.ailatrieuphu.Dialogs.ScoreDialog;
import com.example.ailatrieuphu.Layout.MoneyLayout;
import com.example.ailatrieuphu.Model.Question;
import com.example.ailatrieuphu.ViewModel.QuestionApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private MoneyLayout layoutMoney;
    private LinearLayout layoutPlay;
    private NoticeDialog noticeDialog;
    private ScoreDialog scoreDialog;
    private AudienceDialog audienceDialog;
    private CallDialog callDialog;
    private Runnable runnable;
    private Runnable runnableTimer;
    private Handler handler;
    private Random random;
    private DrawerLayout.DrawerListener drawerListener;
    private List<Question> questions;
    private TextView tvTimer, tvMoney, tvCase[], tvQuestion, tvLevel;
    private ImageButton btnCall, btnAudience, btnStop, btn5050, btnChange;
    private ProgressBar pgTimer;
    private boolean isPlaying;
    private boolean isReady;
    private int timer;
    private int level;
    
    private QuestionApiService questionApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_player);

        noticeDialog = new NoticeDialog(this);
        scoreDialog = new ScoreDialog(this);
        audienceDialog = new AudienceDialog(this);
        callDialog = new CallDialog(this);


        findViewByIds();
        setEvents();
        loadRules();
    }

    public PlayerActivity(){
        handler = new Handler();
        random = new Random();
        isPlaying = false;
        isReady = false;

//        questionApiService = new QuestionApiService();
        questions = new ArrayList<>();
//        questionApiService.getQuestions()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<Question>>() {
//                    @Override
//                    public void onSuccess(@NonNull List<Question> ques) {
//                        for (Question question: ques) {
//                            Log.d("DEBUG", question.getQuestion());
//                            questions.add(question);
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d("DEBUG",e.getMessage());
//                    }
//                });

        questions.add(new Question(1,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(2,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(3,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(4,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(5,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(6,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(7,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(8,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(9,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(10,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(11,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(12,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(13,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(14,"1+1","2",new String[]{"1","2","3","4"},1,"math"));
        questions.add(new Question(15,"1+1","2",new String[]{"1","2","3","4"},1,"math"));

        level = 1;
        tvCase = new TextView[4];
        runnableTimer = new Runnable() {
            @Override
            public void run() {
                if (isPlaying) timer--;
                tvTimer.setText(timer + "");
                if (timer == 0) {
                    isPlaying = false;
                    noticeDialog.setNotification("Hết giờ !", "Đóng", null, null);
                    noticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            saveScore(false);
                        }
                    });
                    noticeDialog.show();
                    return;
                }
                handler.postDelayed(runnableTimer, 1000);
            }
        };
    }

    public void findViewByIds(){
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_player);
        layoutPlay = (LinearLayout) findViewById(R.id.ln_play);
        tvCase[0] = (TextView) findViewById(R.id.tv_case_a);
        tvCase[1] = (TextView) findViewById(R.id.tv_case_b);
        tvCase[2] = (TextView) findViewById(R.id.tv_case_c);
        tvCase[3] = (TextView) findViewById(R.id.tv_case_d);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvTimer = (TextView) findViewById(R.id.tv_timer);
        tvLevel = (TextView) findViewById(R.id.tv_level);
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        pgTimer = (ProgressBar) findViewById(R.id.pg_timer);
        layoutMoney = (MoneyLayout) findViewById(R.id.layout_money);
        layoutMoney.findViewByIds();
        btn5050 = (ImageButton) findViewById(R.id.btn_5050);
        btnAudience = (ImageButton) findViewById(R.id.btn_audience);
        btnChange = (ImageButton) findViewById(R.id.btn_change);
        btnCall = (ImageButton) findViewById(R.id.btn_call);
        btnStop = (ImageButton) findViewById(R.id.btn_stop);
        drawerListener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.removeDrawerListener(drawerListener);
                if (!isReady) {
                    handler.removeCallbacks(runnable);
                    startGame();
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };
    }

    private void setEvents() {
        tvCase[0].setOnClickListener(this);
        tvCase[1].setOnClickListener(this);
        tvCase[2].setOnClickListener(this);
        tvCase[3].setOnClickListener(this);
        layoutPlay.setVisibility(View.GONE);
        tvMoney.setText("0");
        btnStop.setOnClickListener(this);
        btnAudience.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        btn5050.setOnClickListener(this);
    }

    private void loadRules() {
        drawerLayout.openDrawer(GravityCompat.START);
        noticeDialog.setCancelable(false);
        noticeDialog.setNotification("Bạn đã sẵn sàng chơi với chúng tôi ?", "Sẵn sàng", "Bỏ qua", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_cancle:
                        noticeDialog.dismiss();
                        stopThread();
                        finish();
                        break;
                    case R.id.btn_ok:
                        noticeDialog.dismiss();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startGame();
                        break;
                    default:
                        break;
                }
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                noticeDialog.show();
            }
        }, 3000);
    }

    private void startGame() {
        isReady = true;
        drawerLayout.openDrawer(GravityCompat.START);
        playGame();
    }

    public void playGame() {
        drawerLayout.closeDrawer(GravityCompat.START);
        layoutPlay.setVisibility(View.VISIBLE);
        setQuestion();
        handler.post(runnableTimer);
    }

    private void setQuestion() {
        Question ques = questions.get(level - 1);
        tvCase[0].setEnabled(true);
        tvCase[1].setEnabled(true);
        tvCase[2].setEnabled(true);
        tvCase[3].setEnabled(true);
        tvCase[0].setBackgroundResource(R.drawable.player_answer_background_normal);
        tvCase[1].setBackgroundResource(R.drawable.player_answer_background_normal);
        tvCase[2].setBackgroundResource(R.drawable.player_answer_background_normal);
        tvCase[3].setBackgroundResource(R.drawable.player_answer_background_normal);
        tvLevel.setText("Câu: " + level);
        tvQuestion.setText(ques.getQuestion());
        tvCase[0].setText("A: " + ques.getChoices()[0]);
        tvCase[1].setText("B: " + ques.getChoices()[1]);
        tvCase[2].setText("C: " + ques.getChoices()[2]);
        tvCase[3].setText("D: " + ques.getChoices()[3]);
        timer = 30;
        pgTimer.setVisibility(View.VISIBLE);

        runnable = new Runnable() {
            @Override
            public void run() {
                isPlaying = true;
                setClickAble(true);
            }
        };
        handler.postDelayed(runnable, 1200);
    }

    private void setClickAble(boolean b) {
        tvCase[0].setClickable(b);
        tvCase[1].setClickable(b);
        tvCase[2].setClickable(b);
        tvCase[3].setClickable(b);
        btnStop.setClickable(b);
        btnAudience.setClickable(b);
        btn5050.setClickable(b);
        btnCall.setClickable(b);
        btnChange.setClickable(b);
    }

    public int getTrueAnswer() {
        Question ques = questions.get(level - 1);

        for(int i = 0; i < 4; i++){
            if(ques.getAnswer() == ques.getChoices()[i]){
                return i;
            }
        }

        return 1;
    }

    private void answerFalse(final View v) {
        v.setBackgroundResource(R.drawable.player_answer_background_wrong);
        tvCase[getTrueAnswer()].setBackgroundResource(R.drawable.player_answer_background_true);
        tvCase[getTrueAnswer()].startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_loop));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                saveScore(false);
            }
        }, 2000);
    }

    private void answerTrue(final View v) {
        tvMoney.setText(layoutMoney.getMoney(level));
        v.setBackgroundResource(R.drawable.player_answer_background_true);
        v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_loop));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getNewQuestion();
            }
        }, 2000);
    }

    private void getNewQuestion() {
        if (level == 15) {
            noticeDialog.setNotification("Chúc mừng bạn đã vượt qua 15 câu hỏi", "Đóng", null, null);
            noticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    saveScore(false);
                }
            });
            noticeDialog.show();
            return;
        }
        layoutMoney.setBackGroundLevel(level);
        drawerLayout.openDrawer(GravityCompat.START);
        level++;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawerLayout.closeDrawer(GravityCompat.START);
                setQuestion();
            }
        }, 2000);
    }

    private void checkAnswer(final View v, final int id) {
        setClickAble(false);
        isPlaying = false;
        pgTimer.setVisibility(View.GONE);
        v.setBackgroundResource(R.drawable.player_answer_background_selected);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getTrueAnswer() == id) {
                    answerTrue(v);
                } else {
                    answerFalse(v);
                }
            }
        }, 2000);
    }

    public void saveScore(boolean stopGame) {
        stopThread();
        setClickAble(false);
        if (level > 1) {
            if (stopGame) {
                scoreDialog.setScore(tvMoney.getText().toString());
            } else {
                if (level < 5) {
                    scoreDialog.setScore("200,000");
                } else if (level < 10) {
                    scoreDialog.setScore("2,000,000");
                } else if (level < 15) {
                    scoreDialog.setScore("22,000,000");
                } else {
                    scoreDialog.setScore("150,000,000");
                }
            }
            scoreDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                }
            });
            scoreDialog.show();
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_case_a:
                checkAnswer(v, 0);
                break;
            case R.id.tv_case_b:
                checkAnswer(v, 1);
                break;
            case R.id.tv_case_c:
                checkAnswer(v, 2);
                break;
            case R.id.tv_case_d:
                checkAnswer(v, 3);
                break;
            case R.id.btn_stop:
                stopGame();
                break;
            case R.id.btn_5050:
                noticeDialog.setNotification("Bạn thực sự muốn dùng sự trợ giúp 50:50 ?", "Đồng ý", "Hủy bỏ", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.btn_ok) {
                            isPlaying = false;
                            btn5050.setEnabled(false);
                            int count = 0;
                            int b = 0;
                            while (count < 2) {
                                int temp = random.nextInt(4);
                                if (temp != getTrueAnswer() && temp != b) {
                                    b = temp;
                                    tvCase[b].setEnabled(false);
                                    tvCase[b].setBackgroundResource(R.drawable.player_answer_background_hide);
                                    tvCase[b].setText("");
                                    count++;
                                    if (count == 2) isPlaying = true;
                                }
                            }
                        }
                        noticeDialog.dismiss();
                    }
                });
                noticeDialog.show();
                break;
            case R.id.btn_call:
                noticeDialog.setNotification("Bạn thực sự gọi điện thoại cho người thân ?", "Đồng ý", "Hủy bỏ", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.btn_ok) {
                            btnCall.setEnabled(false);
                            isPlaying = false;
                            setClickAble(false);
                            callDialog.setTrueAnswer(getTrueAnswer());
                            callDialog.show();
                            callDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    isPlaying = true;
                                    setClickAble(true);
                                }
                            });
                        }
                        noticeDialog.dismiss();
                    }
                });
                noticeDialog.show();
                break;
            case R.id.btn_audience:
                noticeDialog.setNotification("Bạn thực sự hỏi ý kiến khán giả ?", "Đồng ý", "Hủy bỏ", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.btn_ok) {
                            btnAudience.setEnabled(false);
                            isPlaying = false;
                            setClickAble(false);
                            String cs = "";
                            for (int i = 0; i < tvCase.length; i++) {
                                if (!tvCase[i].isEnabled()) {
                                    cs += i;
                                }
                            }
                            audienceDialog.prepareVote(getTrueAnswer(), cs);
                            audienceDialog.show();
                            audienceDialog.voteAnswer();
                            audienceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    isPlaying = true;
                                    setClickAble(true);
                                }
                            });
                        }
                        noticeDialog.dismiss();
                    }
                });
                noticeDialog.show();
                break;
            default:
                break;
        }
    }

    public void stopGame() {
        noticeDialog.setCancelable(true);
        noticeDialog.setNotification("Bạn thực sự muốn dừng cuộc chơi ?", "Đồng ý", "Hủy bỏ", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_ok) {
                    handler.removeCallbacks(runnable);
                    saveScore(true);
                }
                noticeDialog.dismiss();
            }
        });
        noticeDialog.show();
    }


    public void stopThread() {
        isPlaying = false;
        Thread.currentThread().interrupt();
    }

    @Override
    public void onBackPressed() {
        if (Thread.currentThread().isAlive()) {
            if (!isPlaying) return;
            stopGame();
        } else {
            super.onBackPressed();
        }
    }
}