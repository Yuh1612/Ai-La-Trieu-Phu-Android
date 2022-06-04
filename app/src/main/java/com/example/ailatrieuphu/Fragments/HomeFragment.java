package com.example.ailatrieuphu.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.ailatrieuphu.Dialogs.AboutDialog;
import com.example.ailatrieuphu.PlayerActivity;
import com.example.ailatrieuphu.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bg_circle_rotate);
        view.findViewById(R.id.bg_circle_anim).setAnimation(animation);
        view.findViewById(R.id.btn_about).setOnClickListener(this);
        view.findViewById(R.id.btn_play).setOnClickListener(this);
        view.findViewById(R.id.btn_high_score).setOnClickListener(this);
        view.findViewById(R.id.btn_about).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_play) {
            Log.d("DEBUG","Ok");
            Intent intent = new Intent(getContext(), PlayerActivity.class);
            startActivity(intent);

        }
        else if(view.getId() == R.id.btn_about){
            Log.d("DEBUG","Ok1");
            AboutDialog aboutDialog = new AboutDialog(getContext());
            aboutDialog.show();
        }
    }
}