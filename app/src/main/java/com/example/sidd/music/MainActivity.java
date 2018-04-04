package com.example.sidd.music;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AudioManager.OnAudioFocusChangeListener
{


    RecyclerView rv ;
SongsList sl;
static ArrayList<SongsList>al;
    private Intent playIntent;
    private boolean musicBound=false;
  static  MediaPlayer mediaPlayer=new MediaPlayer();
    CustomAdapter customAdapter;
    ImageView iv1,iv2,songimage;
    static ImageButton ib;
   static TextView tv1,tv2;
    LinearLayout ll1,ll2;
    boolean clicked=false;
   static int pause=0,imgvalue=1,mpcnt=0,position1=0,repeat=0,shuffle=0,fav=0;
    //MainActivity2 ma2=new MainActivity2();
   static String s1=null,s2=stop.Song(),s3=stop.Artist();
AudioManager audioManager;
    @Override
    public void onAudioFocusChange(int fc)
    {

        if(fc<=0)
        {

            try {
                if(mediaPlayer.isPlaying())
                    ib.setBackgroundResource(R.drawable.playpulp);
                pause=mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();

            }catch (Exception e){}

            imgvalue=0;
        }
       else
        {
            try {
                if(mpcnt>0)
                    ib.setBackgroundResource(R.drawable.pausepulp);
                mediaPlayer.seekTo(pause);
                mediaPlayer.start();

            }catch (Exception e){}

            imgvalue=1;
        }
     //   playORpause(new View(null));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioManager.abandonAudioFocus(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                return;
            }}



            audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(this,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
        rv=findViewById(R.id.recyclerview);
        iv1 = findViewById(R.id.imageside);
        iv2=findViewById(R.id.imagebottom);
       ib =  findViewById(R.id.button);
        tv1 = findViewById(R.id.text21);
        tv2 = findViewById(R.id.text22);
        ll1 = findViewById(R.id.lldown);
        ll2 = findViewById(R.id.lldown1);
        songimage = findViewById(R.id.imgsong);
        al=new ArrayList<SongsList>();
        getSongList();
        Collections.sort(al, new Comparator<SongsList>(){
            public int compare(SongsList a, SongsList b){
                return a.getSname().compareTo(b.getSname());
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),linearLayoutManager.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        rv.setLayoutManager(linearLayoutManager);
        customAdapter = new CustomAdapter(al,this);
        rv.setAdapter(customAdapter);
        customAdapter.setOnitemClickListener(new CustomAdapter.onitemClickListener() {
            @Override
            public void onItemClick(final View v,final SongsList sl,final int position) {

                        try {
                          //  if(mpcnt==0)
                            //    mediaPlayer=new MediaPlayer();


                            mediaPlayer.reset();

                           // MainActivity2.seekBar.setProgress(0);
                            mediaPlayer.setDataSource(sl.getUrl());
                            mediaPlayer.prepareAsync();
                           //  MainActivity2.seekBar.setProgress(0);
                            //MainActivity2.seekBar.setMax(mediaPlayer.getDuration());
                            s1 = sl.getUrl();
                            s2=sl.sname;
                            position1=position;
                            fav = sl.getFav();

                            // System.out.println(s2);
                            s3=sl.getAname();
                            tv1.setText(s2);
                            tv2.setText(s3);
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {

                                   // mediaPlayer.start();
                                    mediaPlayer.start();
                                    mpcnt=1;
                                    System.out.print("mp");
                                    playList.foreign=0;
                                    //ma2.thread.start();
                                    pause = 0;
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



                        } catch (Exception e) {}
                    }

        });

    //  Intent intent = new Intent(getApplicationContext(),LockScreenService.class);
     // intent.setAction(LockScreenService.action_play);
      // startService(intent);


mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
    @Override
    public void onCompletion(MediaPlayer mediaPlayer1) {
        playList.foreign=0;

        if(shuffle==1)
        {

            Random random = new Random();
            int k = random.nextInt(al.size()-1);
            while (k==position1)
                k = random.nextInt(al.size()-1);

            position1=k;
            MainActivity2.utill();

        }

      else  if(repeat==1)
        {
            MainActivity2.utill();

        }


        else {
            System.out.print("fgdg");
            position1++;
            //mediaPlayer.pause();
            //return;
            if(MainActivity.mediaPlayer.isPlaying())
                MainActivity.mediaPlayer.pause();
            MainActivity2.utill();
        }
    }
});



if(mediaPlayer.isPlaying())
{
    String x = tv1.getText().toString();
    System.out.print("hhfhh");
    for(int i=0;i<al.size();i++)
    {
        if(al.get(i).getSname().equals(x))
         {
             position1=i;
             break;
         }
    }

    tv1.setText(al.get(position1).getSname());
    tv2.setText(al.get(position1).getAname());
   // position1=playList.position1;
}





    }


    /*public void thread()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {


                while(mediaPlayer.isPlaying())
                {
                    try {
                        Thread.sleep(100);

                        Message msg = new Message();

                        msg.what = mediaPlayer.getCurrentPosition();
                        MainActivity2.seekBar.setProgress(msg.what);
                        handler.sendMessage(msg);


                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int cp = msg.what;
            if(mediaPlayer == null)
                return;
            MainActivity2.seekBar.setProgress(cp);

            String time = MainActivity2.createTimeLabel(cp);
            MainActivity2.starttxt.setText(time);

            String remainingTime = MainActivity2.createTimeLabel(mediaPlayer.getDuration() - cp);
            MainActivity2.endtxt.setText ("- "+remainingTime);
        }
    };*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        int id=menuItem.getItemId();
        if(id==R.id.playList)
        {

            Intent intent=new Intent(MainActivity.this,playList.class);
            startActivity(intent);
            return true;
        }

        return true;
    }

    public void playORpause(View view)
    {

        if(imgvalue==0)
        {

           try {
               if(mpcnt>0)
                   ib.setBackgroundResource(R.drawable.pausepulp);
               mediaPlayer.seekTo(pause);
               mediaPlayer.start();

           }catch (Exception e){}

            imgvalue=1;
        }
        else
        {
            try {
                if(mediaPlayer.isPlaying())
                    ib.setBackgroundResource(R.drawable.playpulp);
                pause=mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();

            }catch (Exception e){}

            imgvalue=0;
        }
    }

    public void startSongActivity(View view)
    {


            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            intent.putExtra("SongName", tv1.getText().toString());
            intent.putExtra("ArtistName", tv2.getText().toString());
          //  intent.putExtra("imgValue",al.get(position1).getFav());
            startActivity(intent);

    }


    public void getSongList()
    {
        ContentResolver musicResolver = getContentResolver();

        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor musicCursor = musicResolver.query(musicUri, null, null,null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int albumId = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM_ID);
            //add songs to list
            do {
                int thisId = musicCursor.getInt(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                int albumid = musicCursor.getInt(albumId);
                //String url = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String url = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                al.add(new SongsList(thisId,thisTitle,thisArtist,url,albumid,0));
            }
            while (musicCursor.moveToNext());
        }
    }
    /*public static Bitmap getAlbumart(Context context,Long album_id){
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try{
            final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
            if (pfd != null){
                FileDescriptor fd = pfd.getFileDescriptor();
                bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
                pfd = null;
                fd = null;
            }
        } catch(Error ee){}
        catch (Exception e) {}
        return bm;
    }*/

}
