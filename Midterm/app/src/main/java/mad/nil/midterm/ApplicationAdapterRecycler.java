package mad.nil.midterm;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by Nilanjan Mhatre
 * email: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public class ApplicationAdapterRecycler extends RecyclerView.Adapter {
    ArrayList<Application> applicationList;

    public ApplicationAdapterRecycler(ArrayList<Application> applicationList) {
        this.applicationList = applicationList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_layout, parent, false);
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

        Application application = applicationList.get(position);
        MyViewHolder viewHolder = (MyViewHolder) holder;

        if(application.getImageUrl() != null && !application.getImageUrl().equals("")) {
//            Picasso.with(viewHolder.imageView.getContext())
//                    .load(application.getImageUrl())
//                    .error(R.drawable.not_found_404)
//                    .placeholder(R.drawable.loading)
//                    .into(viewHolder.imageView);
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .error(R.drawable.not_found_404)
                    .priority(Priority.LOW);
            Glide.with(viewHolder.applicationImage.getContext()).load(application.getImageUrl())
                    .thumbnail(Glide.with(viewHolder.applicationImage.getContext()).load(R.drawable.loading_cat))
                    .apply(options)
                    .into(viewHolder.applicationImage);
        }
        viewHolder.name.setText(application.getName());
        viewHolder.artistName.setText(application.getName());
        viewHolder.releaseDate.setText(application.getName());
        viewHolder.genre.setText(application.toStringGenre());
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }



    private static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView applicationImage;
        TextView name;
        TextView artistName;
        TextView releaseDate;
        TextView genre;

        public MyViewHolder(View itemView) {
            super(itemView);
            applicationImage = itemView.findViewById(R.id.application_image);
            name = itemView.findViewById(R.id.name);
            artistName = itemView.findViewById(R.id.artist_name);
            releaseDate = itemView.findViewById(R.id.release_date);
            genre = itemView.findViewById(R.id.genre_text);
        }
    }
}
