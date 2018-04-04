package com.example.sidd.music;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class playList extends AppCompatActivity {

    RecyclerView rv;
    CustomAdapter customAdapter;
    ArrayList<SongsList>al;
    String s1,s2,s3;
   static TextView tv1,tv2;
    static ImageView iv1,iv2,songimage;
    LinearLayout ll1,ll2;
     static  int pause=0,imgvalue=1,mpcnt=0,position1=0;
    static ImageButton ib;
    int cnt=0;
    static int foreign=0;
    static String extUrl="";
    songsDB sdb;// = new songsDB(this);
    SQLiteDatabase db ;//= sdb.getReadableDatabase();
    //ContentValues contentValues=new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        rv = findViewById(R.id.plRecyclerView);

        iv1 = findViewById(R.id.imageside);
        iv2 = findViewById(R.id.imagebottom);
        tv1 = findViewById(R.id.text21);
        tv2 = findViewById(R.id.text22);
        songimage = findViewById(R.id.imgsong);
        ll1 = findViewById(R.id.lldown);
        ll2 = findViewById(R.id.lldown1);
        ib = findViewById(R.id.button);
        al = new ArrayList<SongsList>();
        addRsub();
        if (al.size() > 0)
        {
            customAdapter = new CustomAdapter(al, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(), linearLayoutManager.getOrientation());
            rv.addItemDecoration(dividerItemDecoration);
            rv.setLayoutManager(linearLayoutManager);
            rv.setAdapter(customAdapter);
            Collections.sort(al, new Comparator<SongsList>() {
                public int compare(SongsList a, SongsList b) {
                    return a.getSname().compareTo(b.getSname());
                }
            });


            customAdapter.setOnitemClickListener(new CustomAdapter.onitemClickListener() {
                @Override
                public void onItemClick(final View v, final SongsList sl, final int position) {

                    try {
                        //  if(mpcnt==0)
                        //    mediaPlayer=new MediaPlayer();


                        MainActivity.mediaPlayer.reset();
                        MainActivity.mpcnt=1;

                        // MainActivity2.seekBar.setProgress(0);
                        MainActivity.mediaPlayer.setDataSource(sl.getUrl());
                        MainActivity.mediaPlayer.prepareAsync();
                        //  MainActivity2.seekBar.setProgress(0);
                        //MainActivity2.seekBar.setMax(mediaPlayer.getDuration());
                        s1 = sl.getUrl();
                        s2 = sl.sname;
                        extUrl=sl.getUrl();
                        position1 = position;
                        //MainActivity.position1=position;
                        //  fav = sl.getFav();
                        // System.out.println(s2);
                        s3 = sl.getAname();
                        tv1.setText(s2);
                        tv2.setText(s3);
                        MainActivity.tv1.setText(s2);
                        MainActivity.tv2.setText(s3);

                        MainActivity.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {

                                // mediaPlayer.start();
                                mediaPlayer.start();
                                mpcnt = 1;
                                foreign=1;
                                MainActivity.mpcnt = 1;
                                System.out.print("mp");
                                //ma2.thread.start();
                                MainActivity.pause = 0;
                                pause = 0;
                                MainActivity.ib.setBackgroundResource(R.drawable.pausepulp);
                                ib.setBackgroundResource(R.drawable.pausepulp);


                                    /*if(mpcnt==0)
                                    {
                                        System.out.print("c");
                                       MainActivity2.seekBar = findViewById(R.id.seekbar);
                                        MainActivity2.seekbarr();
                                        thread();
                                        mpcnt=1;
                                    }*/

                            }
                        });


                    } catch (Exception e) {
                    }
                }

            });

            //  Intent intent = new Intent(getApplicationContext(),LockScreenService.class);
            // intent.setAction(LockScreenService.action_play);
            // startService(intent);


            MainActivity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer1) {

                    if (MainActivity.shuffle == 1) {

                        Random random = new Random();
                        int k = random.nextInt(MainActivity.al.size() - 1);
                        while (k == MainActivity.position1)
                            k = random.nextInt(MainActivity.al.size() - 1);

                        MainActivity.position1 = k;
                        MainActivity2.utill();

                    } else if (MainActivity.repeat == 1) {
                        MainActivity2.utill();

                    } else {
                        System.out.print("fgdg");
                        MainActivity.position1++;
                        //mediaPlayer.pause();
                        //return;
                        if (MainActivity.mediaPlayer.isPlaying())
                            MainActivity.mediaPlayer.pause();
                        MainActivity2.utill();
                    }
                }
            });


        }

        if(MainActivity.mediaPlayer.isPlaying() || MainActivity.mpcnt>0)
        {
            if(!MainActivity.mediaPlayer.isPlaying())
            ib.setBackgroundResource(R.drawable.playpulp);
            else
            ib.setBackgroundResource(R.drawable.pausepulp);
            mpcnt = 1;
            pause = MainActivity.pause;
            imgvalue=MainActivity.imgvalue;
            //s1 = MainActivity.al.get(position1).getUrl();
            position1 = MainActivity.position1;
            CharSequence cs1,cs2;
            cs1=MainActivity.tv1.getText(); //MainActivity.al.get(MainActivity.position1).sname;

            //  fav = sl.getFav();
            // System.out.println(s2);
            cs2 = MainActivity.tv2.getText();
            tv1.setText(cs1);
            tv2.setText(cs2);

            MainActivity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer1) {

                    if (MainActivity.shuffle == 1) {

                        Random random = new Random();
                        int k = random.nextInt(MainActivity.al.size() - 1);
                        while (k ==MainActivity. position1)
                            k = random.nextInt(MainActivity.al.size() - 1);

                        MainActivity.position1 = k;
                        MainActivity2.utill();

                    } else if (MainActivity.repeat == 1) {
                        MainActivity2.utill();

                    } else {
                        System.out.print("fgdg");
                        MainActivity.position1++;
                        //mediaPlayer.pause();
                        //return;
                        if (MainActivity.mediaPlayer.isPlaying())
                            MainActivity.mediaPlayer.pause();
                        MainActivity2.utill();
                    }
                }
            });
        }
    }

   /* public static void utill()
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

    }*/

    public  void addRsub() {

      /*  if(cnt==0)
        {
            sdb = new songsDB(this);
            db = sdb.getReadableDatabase();
        }*/
        cnt=1;

      /*  if (MainActivity2.done > 0)
        {
            for (int i = 0; i < MainActivity.al.size(); i++) {
                if (MainActivity.al.get(i).getFav() == 1)
                    al.add(new SongsList(MainActivity.al.get(i).getId(), MainActivity.al.get(i).getSname(), MainActivity.al.get(i).getAname(), MainActivity.al.get(i).getUrl(),
                            MainActivity.al.get(i).getAlbumId(), MainActivity.al.get(i).getFav()));
                System.out.print("HEre");
            }
        }*/

     //   else {

           /* SharedPreferences sharedPreferences = getSharedPreferences("songValue", Context.MODE_PRIVATE);
            String st = sharedPreferences.getString("url", "");
            String[] iw = st.split(",");
            for (int i = 0; i < iw.length; i++)
            {
                for (int j = 0; j < MainActivity.al.size(); j++)
                {
                    if (MainActivity.al.get(j).getUrl().equals(iw[i]))
                    {
                        al.add(new SongsList(MainActivity.al.get(j).getId(), MainActivity.al.get(j).getSname(), MainActivity.al.get(j).getAname(), MainActivity.al.get(j).getUrl(),
                                MainActivity.al.get(j).getAlbumId(), MainActivity.al.get(j).getFav()));
                        //break;
                        System.out.print(iw[i]);
                    }

                }
            }*/
    songsDB sdb1 = new songsDB(getBaseContext());
    sdb1.open();
            //String p[] = {"url"};
            Cursor c =sdb1.returnData(); //db.query("songs", p, null, null, null, null, null);
            if (c.moveToFirst())
            {

                do
                {
                     /*String st = c.getString(0);
                    for (int j = 0; j < MainActivity.al.size(); j++)
                    {
                        if (MainActivity.al.get(j).getUrl().equals(st))
                        {
                            al.add(new SongsList(MainActivity.al.get(j).getId(), MainActivity.al.get(j).getSname(), MainActivity.al.get(j).getAname(), MainActivity.al.get(j).getUrl(),
                                    MainActivity.al.get(j).getAlbumId(), MainActivity.al.get(j).getFav()));
                            break;
                           // System.out.print(iw[i]);
                        }

                    }*/
                    int id1= Integer.parseInt(c.getString(0));
                    String sname=c.getString(1);
                    String aname = c.getString(2);
                    String url = c.getString(3);
                    int id2 = Integer.parseInt(c.getString(4));
                     al.add(new SongsList(id1,sname,aname,url,id2,1));

                } while (c.moveToNext());
            }
            sdb1.close();
        //}
    }

    public void startSongActivity(View view)
    {


        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        intent.putExtra("SongName", tv1.getText().toString());
        intent.putExtra("ArtistName", tv2.getText().toString());
        //intent.putExtra("imgValue",1);
        startActivity(intent);

    }


    public void playORpause(View view)
    {

        if(MainActivity.imgvalue==0 || imgvalue==0)
        {

            try {
                if(MainActivity.mpcnt>0 || mpcnt>0)
                {
                    ib.setBackgroundResource(R.drawable.pausepulp);
                    MainActivity.ib.setBackgroundResource(R.drawable.pausepulp);
                }
                MainActivity.mediaPlayer.seekTo(pause);
                MainActivity.mediaPlayer.start();

            }catch (Exception e){}

            imgvalue=1;
            MainActivity.imgvalue=1;
        }
        else
        {
            try {
                if(MainActivity.mediaPlayer.isPlaying())
                {
                    ib.setBackgroundResource(R.drawable.playpulp);
                    MainActivity.ib.setBackgroundResource(R.drawable.playpulp);
                }
                MainActivity.pause=MainActivity.mediaPlayer.getCurrentPosition();
                pause=MainActivity.pause;
                MainActivity.mediaPlayer.pause();

            }catch (Exception e){}

            imgvalue=0;
            MainActivity.imgvalue=0;
        }
    }
}
