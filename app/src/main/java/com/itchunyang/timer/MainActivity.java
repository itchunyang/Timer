package com.itchunyang.timer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Timer timer;
    private AlarmManager alarm;
    private PendingIntent broadcastIntent;
    private PendingIntent activityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent("android.action.alarm");
        broadcastIntent = PendingIntent.getBroadcast(this,1,intent,0);

        intent = new Intent(this,SingleTopActivity.class);
        activityIntent = PendingIntent.getActivity(this,100,intent,0);

        //SystemClock.uptimeMillis();//表示自系统启动时开始计数，以毫秒为单位.不包含休眠时间
        //elapsedRealtime() and elapsedRealtimeNanos() 返回系统启动到现在的时间，包含设备深度休眠的时间
    }

    public void timerTask(View view) {
//        timer.schedule(new Task(),1000,8000);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,19);
        calendar.set(Calendar.SECOND,0);
        timer.schedule(new Task(),calendar.getTime(),10000);
    }

    public void stopTimerTask(View view) {
        timer.cancel();
    }

    public void alarmBroadcast(View view) {

//        alarm.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 5000,pendingIntent);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+5000,10000,broadcastIntent);
    }

    public void alarmActivity(View view) {

        alarm.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+4000,5000,activityIntent);

        //设置一个非精确的周期性任务,与上面的不同是两个闹钟执行的间隔时间不是固定的而已。
        //它相对而言更省电（power-efficient）一些，因为系统可能会将几个差不多的闹钟合并为一个来执行，减少设备的唤醒次数
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 4000,5000,activityIntent);
    }

    public void stopAlarm(View view) {
        alarm.cancel(broadcastIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setWindow(View view) {
        System.out.println(System.currentTimeMillis());
        alarm.setWindow(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+ 5000,7000,activityIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setExact(View view) {
        //设置一个精确的任务
        alarm.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 5000,activityIntent);
    }

    /**
     * 从API 19开始，AlarmManager的机制都是非准确传递
     * 从API 19以后，则采用了如下方法：
     *      setWindow(int, long, long, PendingIntent)设置Alarm在特定时间范围内进行提醒
     *      setExact(int, long, PendingIntent)设置Alarm的精确时间
     *
     * 从上面的两个方法我们可以看出，没有了repeat，就是设置了闹钟只能响一次了,而且这两种方法都可以设置精确的
     */

    private class Task extends TimerTask{

        @Override
        public void run() {
            Log.i(TAG, "run: " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "run: end" + Thread.currentThread().getName());

        }
    }
}
