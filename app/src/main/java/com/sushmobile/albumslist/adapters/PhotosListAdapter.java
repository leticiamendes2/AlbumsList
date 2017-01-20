package com.sushmobile.albumslist.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sushmobile.albumslist.R;
import com.sushmobile.albumslist.network.PicassoCache;

import java.util.LinkedHashMap;

public class PhotosListAdapter extends BaseAdapter {

    private final Activity activity;
    private final LinkedHashMap<String, String> photosLinkedHashMap;

    public PhotosListAdapter(Activity activity, LinkedHashMap<String, String> photos) {
        this.activity = activity;
        this.photosLinkedHashMap = photos;
    }

    @Override
    public int getCount() {
        return this.photosLinkedHashMap.size();
    }

    @Override
    public String getItem(int i) {
        return (String)this.photosLinkedHashMap.values().toArray()[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.adapter_item_photo, viewGroup, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.photo_album_small);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar_adapter);

        PicassoCache.getPicassoInstance(activity)
                .load((String)photosLinkedHashMap.keySet().toArray()[i])
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar.getVisibility() == View.VISIBLE)
                            progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        if (progressBar.getVisibility() == View.VISIBLE)
                            progressBar.setVisibility(View.GONE);
                    }
                });

        return view;
    }
}
