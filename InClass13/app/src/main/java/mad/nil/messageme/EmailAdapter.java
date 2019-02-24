package mad.nil.messageme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Nilanjan Mhatre
 * EmailObj: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public class EmailAdapter extends ArrayAdapter<EmailObj> {
    public EmailAdapter(@NonNull Context context, int resource, @NonNull List<EmailObj> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final EmailObj emailObj = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.email_layout, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.readImage = convertView.findViewById(R.id.read_image);
            viewHolder.senderTextView = convertView.findViewById(R.id.from_sender);
            viewHolder.emailTimeTextView = convertView.findViewById(R.id.email_time);
            viewHolder.messageTextView = convertView.findViewById(R.id.email_message);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(emailObj.getRead()) {
            viewHolder.readImage.setImageResource(R.drawable.circle_grey);
        } else {
            viewHolder.readImage.setImageResource(R.drawable.circle_blue);
        }
        viewHolder.senderTextView.setText(emailObj.getFromUsername());
        viewHolder.emailTimeTextView.setText(emailObj.getSentDate());
        viewHolder.messageTextView.setText(emailObj.getMessage());

        return convertView;
    }

    private static class ViewHolder {
        ImageView readImage;
        TextView senderTextView;
        TextView emailTimeTextView;
        TextView messageTextView;
    }
}
