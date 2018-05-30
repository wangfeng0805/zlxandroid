package com.example.ylf019.zlxandroid.view;

import android.os.Handler;
import android.os.Message;

/**
 * Description ${TODO}
 * Created by yangjinxin on 2017/11/23.
 */
public class SwitchAnimatorController {
    private static AnimatorHandler mHandler = new AnimatorHandler();

    private static final long DELAY_TIME = 10;

    public static void sendMsg(Runnable runnable) {
        Message msg = new Message();
        msg.obj = runnable;
        mHandler.sendMessageDelayed(msg, DELAY_TIME);
    }

    private static class AnimatorHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                ((Runnable) msg.obj).run();
            }
        }
    }
}
