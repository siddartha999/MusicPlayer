package com.example.sidd.music;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sidd on 18/03/18.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>

{

    ArrayList<SongsList> al;
 private Context context;

onitemClickListener onitemClickListener;
public interface onitemClickListener
{
    void onItemClick(View v,SongsList sl,int position);
}

public void setOnitemClickListener(onitemClickListener onitemClickListener)
{
    this.onitemClickListener=onitemClickListener;
}

   public CustomAdapter(ArrayList<SongsList> al,Context context)
   {
 this.al=al;
this.context=context;
   }

    @Override
    public CustomViewHolder onCreateViewHolder( ViewGroup parent, int viewType)
    {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.list_item_layout,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position)
    {

      final SongsList sl =al.get(position);
     holder.txt1.setText(sl.sname);
     holder.txt2.setText(sl.aname);
     holder.songimg.setBackgroundResource(R.drawable.appicon);
   //  holder.songimg.setImageBitmap(sl.getBitmap());
      //  Bitmap art = MainActivity.getAlbumart(context,sl.getAlbumId());
     //holder.img.setImageDrawable(new BitmapDrawable(art));

    holder.linearLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(onitemClickListener != null)
            {
                onitemClickListener.onItemClick(view,sl,position);
            }
        }
    });

    }

    @Override
    public int getItemCount() {
      // System.out.println(al.size());
        return al.size();
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder
    {
        ImageView songimg;
        TextView txt1,txt2;
        LinearLayout linearLayout;
        public CustomViewHolder(View v)
        {
            super(v);
            //img =  itemView.findViewById(R.id.img);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            songimg = itemView.findViewById(R.id.imgsong);
            linearLayout= itemView.findViewById(R.id.linearlayout);

        }

    }



    }

