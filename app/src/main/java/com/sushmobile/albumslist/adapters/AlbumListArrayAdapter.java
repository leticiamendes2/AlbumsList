package com.sushmobile.albumslist.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sushmobile.albumslist.R;
import com.sushmobile.albumslist.models.Album;

import java.util.ArrayList;

public class AlbumListArrayAdapter extends BaseAdapter {

    private final Activity activity;
    private final ArrayList<Album> albumArrayList;

    public AlbumListArrayAdapter(Activity activity, ArrayList<Album> albums) {
        this.activity = activity;
        this.albumArrayList = albums;
    }

    @Override
    public int getCount() {
        return this.albumArrayList.size();
    }

    @Override
    public Album getItem(int i) {
        return this.albumArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.adapter_item_album, viewGroup, false);
        Album album = albumArrayList.get(i);

        TextView name = (TextView) view.findViewById(R.id.text_album_name_item_list);
        name.setText(album.getTitle());

        TextView user = (TextView) view.findViewById(R.id.text_user_album_name_item_list);
        user.setText(album.getUser().getName());

        return view;
    }
}
