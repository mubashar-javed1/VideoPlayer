package com.mobi.videoplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mobi.videoplayer.R;
import com.mobi.videoplayer.data.Video;
import com.mobi.videoplayer.ui.playeractivity.PlayerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ItemViewHolder> {

    private Context context;
    private List<Video> videos;


    public VideoAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        holder.tvTitle.setText(videos.get(position).getTitle());
        holder.tvTime.setText(String.valueOf(videos.get(position).getDuration()));

        Glide.with(context)
                .setDefaultRequestOptions(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(videos.get(position).getThumbnail()).into(holder.ivThumb);

        holder.itemView.setOnClickListener(v -> context.startActivity(new Intent(context, PlayerActivity.class)
                .putExtra("video", videos.get(position))));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}