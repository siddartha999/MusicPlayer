package com.example.sidd.music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sidd on 01/04/18.
 */

public class songsDB
{
    SQLiteDatabase db1;
    dbh dbhelper;
    public songsDB(Context context)
    {
        dbhelper = new dbh(context);
    }
    private static class dbh extends SQLiteOpenHelper {
        public dbh(Context context) {
            super(context, "songs.db", null, 1);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL("create table songs(id int,sname varchar2(40),aname varchar2(40),url varchar2(40),albumId int)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists songs");
            onCreate(sqLiteDatabase);
        }
    }
    public songsDB open()
    {
        db1 = dbhelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        dbhelper.close();
    }
    public Cursor returnData()
    {
        return db1.query("songs",new String[]{"id","sname","aname","url","albumId"},null,null,null,null,null);
    }

    public void insertData(int id, String sname, String aname, String url1,int albumId)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("sname",sname);
        contentValues.put("aname",aname);
        contentValues.put("url",url1);
        contentValues.put("albumId",albumId);
        db1.insertOrThrow("songs",null,contentValues);
      //db1.delete("songs","url",contentValues);
    }

 public void deleteData(String url1)
 {
    SQLiteDatabase db= dbhelper.getWritableDatabase();
    String query = "DELETE FROM " + "songs" +" WHERE "+"url = '"+url1+"'";
    db.execSQL(query);
   // db1.delete("songs","url= "+url1,null);
 }
public  int getImg(String url1)
{
    SQLiteDatabase db= dbhelper.getWritableDatabase();
    String query = "SELECT url FROM " + "songs" +" WHERE "+"url = '"+url1+"'";
   Cursor cursor = db.rawQuery(query,null);

   if (cursor.moveToFirst())
   {
       do {
           if(cursor.getString(0).equals(url1))
           {cursor.close(); return 1;}
       }while (cursor.moveToNext());

   }
   cursor.close();
    return 0;
}
}
