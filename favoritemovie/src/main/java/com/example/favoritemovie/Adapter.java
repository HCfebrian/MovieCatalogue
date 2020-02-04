package com.example.favoritemovie;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class Adapter extends  RecyclerView.Adapter<Adapter.MovieViewHolder> {
    private Cursor mCursor;
    private Context context;

    Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_film, parent, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(mCursor.moveToPosition(position));
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvDuration,tvRating;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_film_photo);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRating = itemView.findViewById(R.id.tv_rv_rating);
            tvDuration = itemView.findViewById(R.id.tv_rv_durasi);

        }
        void bind(boolean moveToPosition) {
            if (moveToPosition) {
                tvTitle.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Utils.COLUMN_TITLE)));
                tvRating.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Utils.COLUMN_POPULARITY)));
                tvDuration.setText(mCursor.getString(mCursor.getColumnIndexOrThrow(Utils.COLUMN_RATING)));
                Glide.with(context)
                        .load(Utils.POSTER_BASE_URL + mCursor.getString(mCursor.getColumnIndexOrThrow(Utils.COLUMN_POSTER)))
                        .into(ivPoster);
            }
        }
    }

    void setData(Cursor cursor){
        mCursor =cursor;
        notifyDataSetChanged();
    }
}
