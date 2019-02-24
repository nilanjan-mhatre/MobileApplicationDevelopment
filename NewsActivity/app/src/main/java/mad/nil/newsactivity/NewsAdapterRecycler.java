package mad.nil.newsactivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nilanjan Mhatre
 * email: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public class NewsAdapterRecycler extends RecyclerView.Adapter {
    ArrayList<News> headlines;

    public NewsAdapterRecycler(ArrayList<News> headlines) {
        this.headlines = headlines;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_layout_2, parent, false);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        WindowManager windowManager = (WindowManager) parent.getContext().getSystemService(Context.WINDOW_SERVICE);
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
//        int width = displayMetrics.widthPixels;
//        view.setLayoutParams(new RecyclerView.LayoutParams(width/2, height/2));

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        News news = headlines.get(position);
        MyViewHolder viewHolder = (MyViewHolder) holder;

        if(news.getImageUrl() != null && !news.getImageUrl().equals("")) {
//            Picasso.with(viewHolder.imageView.getContext())
//                    .load(news.getImageUrl())
//                    .error(R.drawable.not_found_404)
//                    .placeholder(R.drawable.loading)
//                    .into(viewHolder.imageView);
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .error(R.drawable.not_found_404)
                    .priority(Priority.LOW);
            Glide.with(viewHolder.imageView.getContext()).load(news.getImageUrl())
                    .thumbnail(Glide.with(viewHolder.imageView.getContext()).load(R.drawable.loading_cat))
                    .apply(options)
                    .into(viewHolder.imageView);
        }
        viewHolder.titleTextView.setText(news.getTitle());
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }



    private static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.news_image_view);
            titleTextView = itemView.findViewById(R.id.title_text_view);
        }
    }
}
