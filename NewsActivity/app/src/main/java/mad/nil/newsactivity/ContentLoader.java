package mad.nil.newsactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by nilan on 2/18/2018.
 */

public class ContentLoader implements Runnable {

    Handler handler;
    String category;

    public ContentLoader(Handler handler, String category) {
        this.handler = handler;
        this.category = category;
    }

    @Override
    public void run() {

        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
