package com.example.sidd.music;

import android.graphics.Bitmap;

/**
 * Created by sidd on 18/03/18.
 */

public class SongsList {

    String sname,aname,url;
    int id,albumId;
    int fav;
    Bitmap bm;
   // Bitmap bitmap;
    //Uri ur;

    public void setFav(int fav) {
        this.fav = fav;
    }

    public int getFav() {
        return fav;
    }

    public Bitmap getBm()
    {
        return bm;
    }

    /*  public Bitmap getBitmap()
            {
                return bitmap;
            }


        */
    public SongsList(int id, String sname, String aname, String url, int albumId, int fav)
    {
        this.id=id;
        this.sname=sname;
        this.aname=aname;
        this.url=url;
        this.albumId=albumId;
        this.fav=fav;
      //  this.bm=bm;



      //  this.bitmap=bitmap;



       // this.ur=ur;
    }
    public String getSname() {
        return sname;
    }

    /*public Uri getUr() {
        return ur;
    }*/

    public int getId() {
        return id;
    }

    public String getAname() {
        return aname;
    }
    public String getUrl() {
        return url;
    }

    public int getAlbumId() {
        return albumId;
    }



}
