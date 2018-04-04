package com.example.sidd.music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity
{

    MainActivity ma=new MainActivity();
  static   TextView txt1, txt2,starttxt, endtxt;
     static ImageButton ib1, ib2, ib3, dropdown,ibrepeat,ibshuffle,ibfav;
    int imgvalue = 1, mpcnt = 0,thcnt=0;
    int pause = 0;
   static SeekBar seekBar,soundBar;
     //SeekBar seekBar = findViewById(R.id.seekbar);
 static   Handler handler = new Handler();
 StringBuilder stringBuilder;
 List<String>url;
  static   songsDB sdb;// = new songsDB(this);

    //Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ib2 = findViewById(R.id.btnplay);
        ib1 = findViewById(R.id.btnprevious);
        ib3 = findViewById(R.id.btnnext);
        txt1 = findViewById(R.id.txt1c);
        txt2 = findViewById(R.id.txt2c);
        starttxt=findViewById(R.id.starttxt);
        endtxt=findViewById(R.id.endtxt);
        dropdown = findViewById(R.id.dropdown);
        ibrepeat = findViewById(R.id.btnrepeat);
        ibshuffle= findViewById(R.id.btnshuffle);
        if(ibfav==null)
        ibfav = findViewById(R.id.favimg);
        url=new ArrayList<>();
        stringBuilder = new StringBuilder();
        //if(seekBar==null)
        seekBar= findViewById(R.id.seekbar);
        seekBar.setProgress(0);
       txt1.setText(getIntent().getStringExtra("SongName"));
        txt2.setText(getIntent().getStringExtra("ArtistName"));

        if (MainActivity.imgvalue == 1 && MainActivity.mpcnt>0)
            ib2.setBackgroundResource(R.drawable.pausepulp);
        else
            ib2.setBackgroundResource(R.drawable.playpulp);

        if(MainActivity.repeat==1)
            ibrepeat.setBackgroundResource(R.drawable.repeatset22);
        if(MainActivity.shuffle==1)
            ibshuffle.setBackgroundResource(R.drawable.shuffle3);

//thread.start();
       // mediaPlayer=new MediaPlayer();
        //mediaPlayer = ma.returnMediaPlayer();

        if(MainActivity.mediaPlayer!=null && MainActivity.mpcnt>0) {
            thcnt++;
            seekbarr();
            thread();
            if (MainActivity.al.get(MainActivity.position1).getFav() > 0)
            {ibfav.setBackgroundResource(R.drawable.subicon);System.out.print("abcd");}
         else
            {
            if (sdb == null)
                sdb = new songsDB(getBaseContext());

            sdb.open();
            int gg = sdb.getImg(MainActivity.al.get(MainActivity.position1).getUrl());
            if (gg != 0)
            {
                ibfav.setBackgroundResource(R.drawable.subicon);
                MainActivity.al.get(MainActivity.position1).setFav(1);
             }
            else if (playList.foreign == 1) {
                ibfav.setBackgroundResource(R.drawable.subicon);
                for (int i = 0; i < MainActivity.al.size(); i++) {
                    if (MainActivity.al.get(i).getUrl().equals(playList.extUrl)) {
                        MainActivity.al.get(i).setFav(1);
                        break;
                    }
                }
            }
            sdb.close();
            //MainActivity.mediaPlayer.g
        }

        }




    }

public static void thread()
{

    new Thread(new Runnable() {
        @Override
        public void run() {


                while(MainActivity.mediaPlayer.isPlaying())
                {
                    try {
                Thread.sleep(100);

                 Message msg = new Message();

                    msg.what = MainActivity.mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(msg.what);
                   // setTxt(msg);


                }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
    }).start();


}
/*public  void  setTxt(Message msg)
{
    String time = createTimeLabel(msg.what);
   starttxt.setText(time);

    String remainingTime = createTimeLabel(MainActivity.mediaPlayer.getDuration() - msg.what);
    endtxt.setText ("- "+remainingTime);

}*/

  /*  @SuppressLint("HandlerLeak")
      Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int cp = msg.what;
            seekBar.setProgress(cp);

            String time = createTimeLabel(cp);
            starttxt.setText(time);

            String remainingTime = createTimeLabel(MainActivity.mediaPlayer.getDuration() - cp);
            endtxt.setText ("- "+remainingTime);
        }
    };*/
    //handler.postDelayed(r,100);



public static String createTimeLabel(int position)
{
    String time="";

    int min = position/1000/60;
    int sec = position/1000%60;

    time = min+":";
    if(sec<10) time+="0";
    time+=sec;

    return time;
}

    public void goBack(View view) {
        finish();
    }

    public static void seekbarr()
    {
     //   if(!MainActivity.mediaPlayer.isPlaying())
       //     return;

       seekBar.setMax(MainActivity.mediaPlayer.getDuration());
        seekBar.setProgress(MainActivity.mediaPlayer.getCurrentPosition());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b)
                {
                    MainActivity.mediaPlayer.seekTo(i);
                    seekBar.setProgress(i);
                    System.out.print(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void previousSong(View view)
    {
        playList.foreign=0;

        if(MainActivity.mpcnt==0)
            return;

            if(MainActivity.position1 > 0)
            {
                MainActivity.position1--;
                utill();
            }

    }

    public  void nextSong(View view)
    {
        playList.foreign=0;
        if(MainActivity.mpcnt==0)
            return;

              if(MainActivity.position1 < MainActivity.al.size()-1)
              {
                  MainActivity.position1++;
                  utill();
              }

    }

    public void songRepeat(View view)
    {


        if(MainActivity.repeat ==0)
        {
            MainActivity.repeat=1;
         //  MainActivity.repeatCnt=1;
           ibrepeat.setBackgroundResource(R.drawable.repeatset22);
        }
        else
        {
            MainActivity.repeat=0;
            //MainActivity.repeatCnt=0;
            ibrepeat.setBackgroundResource(R.drawable.repeatset21);
        }

    }
    public void songShuffle(View view)
    {
        playList.foreign=0;
        if(MainActivity.shuffle ==0)
        {
            MainActivity.shuffle=1;
          //  MainActivity.shuffleCnt=1;
            ibshuffle.setBackgroundResource(R.drawable.shuffle3);
        }
        else
        {
            MainActivity.shuffle=0;
          //  MainActivity.shuffleCnt=0;
            ibshuffle.setBackgroundResource(R.drawable.shuffle1);
        }
    }

public static void utill()
{

            try {

              //  seekBar.setProgress(0);
               // seekBar.setMax(MainActivity.mediaPlayer.getDuration());

               // thread();
                System.out.print("utill");
                if(MainActivity.mediaPlayer.isPlaying())
                   MainActivity.mediaPlayer.pause();

                MainActivity.mediaPlayer.reset();
                String url = MainActivity.al.get(MainActivity.position1).getUrl();
                int favv = MainActivity.al.get(MainActivity.position1).getFav();
                if(favv>0)
                    ibfav.setBackgroundResource(R.drawable.subicon);

               else if(playList.foreign==1)
                    ibfav.setBackgroundResource(R.drawable.subicon);
                MainActivity.mediaPlayer.setDataSource(url);
                MainActivity.mediaPlayer.prepareAsync();
                MainActivity.pause=0;
                playList.pause=0;
                MainActivity.ib.setBackgroundResource(R.drawable.pausepulp);
                playList.ib.setBackgroundResource(R.drawable.pausepulp);
                ib2.setBackgroundResource(R.drawable.pausepulp);
                String sn,an;
                sn = MainActivity.al.get(MainActivity.position1).getSname();
                an = MainActivity.al.get(MainActivity.position1).getAname();
                MainActivity.tv1.setText(sn);
                MainActivity.tv2.setText(an);
                playList.tv1.setText(sn);
                playList.tv2.setText(an);
                txt1.setText(sn);
                txt2.setText(an);
                MainActivity.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        mediaPlayer.start();
                        MainActivity.mpcnt=1;
                      //  if(MainActivity.mediaPlayer!=null)
                        seekbarr();
                        thread();

                        //ma2.thread.start();
                        MainActivity.pause = 0;
                        playList.pause=0;
                        //ib.setBackgroundResource(R.drawable.pausepulp);
                    }
                });

            }
            catch (Exception e){}

        }


        public void addRsub(View view)
        {



            int favv = MainActivity.al.get(MainActivity.position1).getFav();
            if(favv>0)
            {
                ibfav.setBackgroundResource(R.drawable.addicon);
                MainActivity.al.get(MainActivity.position1).setFav(0);
              // stringBuilder.deleteCharAt(stringBuilder.indexOf(MainActivity.al.get(MainActivity.position1).getUrl()));
       if(sdb==null)
           sdb=new songsDB(getBaseContext());
              sdb.open();
              sdb.deleteData(MainActivity.al.get(MainActivity.position1).getUrl());
              sdb.close();
            }
            else
            {
                ibfav.setBackgroundResource(R.drawable.subicon);
                MainActivity.al.get(MainActivity.position1).setFav(1);
               // stringBuilder.append(MainActivity.al.get(MainActivity.position1).getUrl());
              //  contentValues.put("url",MainActivity.al.get(MainActivity.position1).getUrl());
                if(sdb==null)
                    sdb=new songsDB(getBaseContext());
                sdb.open();
             sdb.insertData(MainActivity.al.get(MainActivity.position1).getId(),
                            MainActivity.al.get(MainActivity.position1).getSname(),
                            MainActivity.al.get(MainActivity.position1).getAname(),
                             MainActivity.al.get(MainActivity.position1).getUrl(),
                             MainActivity.al.get(MainActivity.position1).getAlbumId());
                sdb.close();

            }

        }



    public void playRpause(View view)  {

    if(MainActivity.mpcnt==0)
        return;

        if(MainActivity.imgvalue==0)
        {
            try {
                if(MainActivity.mpcnt>0)
                {
                    ib2.setBackgroundResource(R.drawable.pausepulp);
                    MainActivity.ib.setBackgroundResource(R.drawable.pausepulp);
                    playList.ib.setBackgroundResource(R.drawable.pausepulp);
                }
                MainActivity.mediaPlayer.seekTo(MainActivity.pause);
                MainActivity.mediaPlayer.start();

            }catch (Exception e){}

            MainActivity.imgvalue=1;
            playList.imgvalue=1;
        }
        else
        {

            try {
                if(MainActivity.mediaPlayer.isPlaying())
                {
                    ib2.setBackgroundResource(R.drawable.playpulp);
                    MainActivity.ib.setBackgroundResource(R.drawable.playpulp);
                    playList.ib.setBackgroundResource(R.drawable.playpulp);
                }
                MainActivity.pause=MainActivity.mediaPlayer.getCurrentPosition();
                playList.pause=MainActivity.pause;
                MainActivity.mediaPlayer.pause();

            }catch (Exception e){}

            MainActivity.imgvalue=0;
            playList.imgvalue=0;
        }
    }


    }







