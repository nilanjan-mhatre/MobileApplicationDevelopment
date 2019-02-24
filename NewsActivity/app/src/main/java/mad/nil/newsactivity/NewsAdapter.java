package mad.nil.newsactivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Inclass Assignment 7
 * File name: NewsAdapter.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.headline_layout_1, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.imageView = convertView.findViewById(R.id.news_image_view);
            viewHolder.titleTextView = convertView.findViewById(R.id.title_text_view);
            viewHolder.publishedTextView = convertView.findViewById(R.id.published_text_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(news.getImageUrl() != null && !news.getImageUrl().equals("")) {
            Picasso.with(getContext()).load(news.getImageUrl()).placeholder(R.drawable.loading).into(viewHolder.imageView);
        }
        viewHolder.titleTextView.setText(news.getTitle());
        viewHolder.publishedTextView.setText(news.getPublishedAt());
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView publishedTextView;
    }
}
