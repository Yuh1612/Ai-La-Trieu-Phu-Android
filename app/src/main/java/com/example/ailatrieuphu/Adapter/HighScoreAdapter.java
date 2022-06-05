package com.example.ailatrieuphu.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ailatrieuphu.Model.HighScore;
import com.example.ailatrieuphu.R;

import java.text.DecimalFormat;
import java.util.List;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.ViewHolder> {

    public List<HighScore> highScoreList;

    public HighScoreAdapter(List<HighScore> highScoreList) {
        this.highScoreList = highScoreList;
    }

    @NonNull
    @Override
    public HighScoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.high_score_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreAdapter.ViewHolder holder, int position) {
        String score = new DecimalFormat("###,###").format(highScoreList.get(position).getScore());
        holder.tvScore.setText(score + " VNĐ");
        holder.tvName.setText(highScoreList.get(position).getName());

        if (position == 0) {
            holder.tvRank.setBackgroundResource(R.drawable.rank_1);
            holder.itemView.setBackgroundColor(Color.parseColor("#9C27B0"));
            holder.tvName.setTextColor(Color.parseColor("#FF9800"));
        } else if (position == 1) {
            holder.tvRank.setBackgroundResource(R.drawable.rank_2);
            holder.itemView.setBackgroundColor(Color.parseColor("#009688"));
            holder.tvName.setTextColor(Color.parseColor("#00E676"));
        } else if (position == 2) {
            holder.tvRank.setBackgroundResource(R.drawable.rank_3);
            holder.itemView.setBackgroundColor(Color.parseColor("#03A9F4"));
            holder.tvName.setTextColor(Color.parseColor("#9C27B0"));
        } else {
            holder.tvRank.setText(position + 1 + "");
        }
    }

    @Override
    public int getItemCount() {
        return highScoreList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvRank;
        public TextView tvScore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvRank = itemView.findViewById(R.id.tv_rank);
            tvScore = itemView.findViewById(R.id.tv_score);
        }
    }
}
