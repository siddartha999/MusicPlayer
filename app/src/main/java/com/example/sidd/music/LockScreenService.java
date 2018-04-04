package com.example.sidd.music;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.IBinder;

/**
 * Created by sidd on 20/03/18.
 */

public class LockScreenService extends Service
{
    private MediaSession mediaSession;
    public static final String action_play = "action_play";
    public static final String action_pause = "action_pause";
    public static final String action_previous = "action_previous";
    public static final String action_next = "action_next";
    private MediaSessionManager mediaSessionManager;
    private MediaController mediaController;
    private MediaPlayer mediaPlayer;
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    @Override
    public boolean onUnbind(Intent intent)
    {
        mediaSession.release();
        return super.onUnbind(intent);
    }

    private void handleIntent(Intent intent)
    {
        if(intent==null || intent.getAction()==null)
            return;;

           String action = intent.getAction();
           if(action.equalsIgnoreCase(action_play))
               mediaController.getTransportControls().play();
           else if(action.equalsIgnoreCase(action_pause))
               mediaController.getTransportControls().pause();
           else if(action.equalsIgnoreCase(action_previous))
               mediaController.getTransportControls().skipToPrevious();
           else if(action.equalsIgnoreCase(action_next))
               mediaController.getTransportControls().skipToNext();
    }

    private Notification.Action generateAction(int icon,String title,String intentAction)
    {

        Intent intent = new Intent(getApplicationContext(),LockScreenService.class);
        intent.setAction(intentAction);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),1,intent,0);
        return  new Notification.Action.Builder(icon,title,pendingIntent).build();
    }
    private void buildNotification(Notification.Action action)
    {
        Notification.MediaStyle style=new Notification.MediaStyle();
        Intent intent = new Intent(getApplicationContext(),LockScreenService.class);
        intent.setAction(action_pause);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),1,intent,0);
        Notification.Builder builder= new Notification.Builder(this)
                                       .setSmallIcon(R.drawable.songicon1)
                                       .setContentTitle(MainActivity.s2)
                                       .setContentText(MainActivity.s3)
                                       .setDeleteIntent(pendingIntent)
                                       .setStyle(style);
        builder.addAction(action);
        builder.addAction(generateAction(R.drawable.previous,"previous",action_previous));
        builder.addAction(generateAction(R.drawable.next,"next",action_next));
        builder.addAction(generateAction(R.drawable.pausepulp,"pause",action_pause));
        builder.addAction(action);
        style.setShowActionsInCompactView(0,1,2,3,4);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if(mediaPlayer==null)
            initMediaSession();
        handleIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }
    private void initMediaSession()
    {
        mediaPlayer = new MediaPlayer();
       mediaSession=new MediaSession(getApplicationContext(),"session");
       mediaController=new MediaController(getApplicationContext(),mediaSession.getSessionToken());
       mediaSession.setCallback(new MediaSession.Callback() {
           @Override
           public void onPlay() {
               super.onPlay();
               buildNotification(generateAction(R.drawable.pausepulp,"pause",action_pause));
           }

           @Override
           public void onPause() {
               super.onPause();
               buildNotification(generateAction(R.drawable.playpulp,"play",action_play));
           }

           @Override
           public void onSkipToPrevious() {
               super.onSkipToPrevious();
               buildNotification(generateAction(R.drawable.previous,"previous",action_previous));
           }

           @Override
           public void onSkipToNext() {
               super.onSkipToNext();
               buildNotification(generateAction(R.drawable.next,"next",action_next));
           }

           @Override
           public void onStop() {
               super.onStop();
               NotificationManager notificationManager = (NotificationManager)getApplicationContext()
                                                            .getSystemService(Context.NOTIFICATION_SERVICE);
               notificationManager.cancel(1);
               Intent intent = new Intent(getApplicationContext(),LockScreenService.class);
               stopService(intent);
           }
       });
    }
}
