package com.example.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.ailatrieuphu.Dialogs.NoticeDialog;
import com.example.ailatrieuphu.Fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bg_circle_rotate);
        animation.setDuration(3000);
        findViewById(R.id.load).startAnimation(animation);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.frame_main, new HomeFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        final NoticeDialog noticeDialog = new NoticeDialog(this);
        noticeDialog.setCancelable(true);
        noticeDialog.setNotification("Bạn muốn thoát trò chơi ?", "Đồng ý", "Hủy", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_ok) {
                    finish();
                }
                noticeDialog.dismiss();
            }
        });
        noticeDialog.show();
    }
}