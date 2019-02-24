package mad.nil.midterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Inclass Assignment 7
 * File name: NewsAdapter.java
 */

public class NewsAdapter extends ArrayAdapter<Application> {
    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<Application> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Application application = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.application_layout, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.applicationImage = convertView.findViewById(R.id.application_image);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.artistName = convertView.findViewById(R.id.artist_name);
            viewHolder.releaseDate = convertView.findViewById(R.id.release_date);
            viewHolder.genre = convertView.findViewById(R.id.genre_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
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
        viewHolder.artistName.setText(application.getArtistName());
        viewHolder.releaseDate.setText(application.getReleaseDate());
        viewHolder.genre.setText(application.toStringGenre());
        return convertView;
    }

    private static class ViewHolder {
        ImageView applicationImage;
        TextView name;
        TextView artistName;
        TextView releaseDate;
        TextView genre;
    }
}
