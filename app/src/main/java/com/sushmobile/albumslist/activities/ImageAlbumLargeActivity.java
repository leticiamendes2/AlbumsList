package com.sushmobile.albumslist.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sushmobile.albumslist.R;
import com.sushmobile.albumslist.network.PicassoCache;

public class ImageAlbumLargeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_album_large);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setBackgroundColor(Color.BLACK);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.BLACK);
        }

        String photoURL = getIntent().getStringExtra("photo");

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            actionBar.setTitle("");
        }

        final ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.progressBar);

        final ImageView img_albumLarge = (ImageView) this.findViewById(R.id.image_album_large);
        PicassoCache.getPicassoInstance(this)
                .load(photoURL)
                .into(img_albumLarge, new com.squareup.picasso.Callback() {
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
