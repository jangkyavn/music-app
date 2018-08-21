package com.it.vietnv.ngovanviet_1412101031.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.vietnv.ngovanviet_1412101031.R;
import com.it.vietnv.ngovanviet_1412101031.models.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<Song> objects;

    public CustomListAdapter(Activity activity, ArrayList<Song> objects) {
        this.activity = activity;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return this.objects.size();
    }

    @Override
    public Object getItem(int position) {
        return this.objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = this.activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.custom_list_item, null);

            holder = new ViewHolder();
            holder.tvSongName = convertView.findViewById(R.id.tvSongName);
            holder.tvSingerName = convertView.findViewById(R.id.tvSingerName);
            holder.imgAvatar = convertView.findViewById(R.id.imgAvatar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Song song = this.objects.get(position);

        holder.tvSongName.setText(song.getSongName().trim());
        holder.tvSingerName.setText(song.getSingerName().trim());
        Picasso.get().load(song.getAvatar()).into(holder.imgAvatar);

        return convertView;
    }

    private class ViewHolder {
        TextView tvSongName, tvSingerName;
        ImageView imgAvatar;
    }

}
