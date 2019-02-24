package mad.nil.midterm;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Nilanjan Mhatre
 * email: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public class DelayThread implements Runnable {
    Handler handler;
    Integer code;
    String parameter;

    public DelayThread(Handler handler, Integer code, String parameter) {
        this.handler = handler;
        this.code = code;
        this.parameter = parameter;
    }

    @Override
    public void run() {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("code", code);
        bundle.putString("parameter", parameter);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
