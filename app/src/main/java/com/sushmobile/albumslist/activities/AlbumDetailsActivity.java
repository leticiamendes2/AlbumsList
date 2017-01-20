package com.sushmobile.albumslist.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.sushmobile.albumslist.R;
import com.sushmobile.albumslist.adapters.PhotosListAdapter;
import com.sushmobile.albumslist.dialogs.ConnectionErrorDialogFragment;
import com.sushmobile.albumslist.enums.Service;
import com.sushmobile.albumslist.interfaces.AsyncResponse;
import com.sushmobile.albumslist.models.Album;
import com.sushmobile.albumslist.network.GetService;
import com.sushmobile.albumslist.network.RequestSettings;
import com.sushmobile.albumslist.network.ResponseInfo;
import com.sushmobile.albumslist.util.Util;

import org.json.JSONArray;

import java.util.LinkedHashMap;

public class AlbumDetailsActivity extends AppCompatActivity implements AsyncResponse {

    private Album album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        album = getIntent().getParcelableExtra("album");

        if (new Util().hasNetworkConnection(this)) {
            GetService getService = new GetService();
            getService.delegate = this;

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("albumId", String.valueOf(album.getAlbumId()));

            getService.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new RequestSettings(Service.PHOTOS, parameters));
        } else {
            new ConnectionErrorDialogFragment().show(this.getFragmentManager(), "connection_error_message");
        }

        TextView txt_albumName = (TextView) this.findViewById(R.id.text_album_name_details);
        txt_albumName.setText(album.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(ResponseInfo result) {
        JSONArray jsonArray = new Util().parseStringToJSONArray(result.getResponse());
        LinkedHashMap<String, String> linkedHashMap = new Util().processResultsPhotos(jsonArray);
        album.setUrlImageLarge(linkedHashMap);

        final GridView gridViewPhotosAlbum = (GridView) this.findViewById(R.id.gridview_photos_album);
        gridViewPhotosAlbum.setAdapter(new PhotosListAdapter(this, album.getUrlImages()));
        gridViewPhotosAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(AlbumDetailsActivity.this, ImageAlbumLargeActivity.class);
                mIntent.putExtra("photo", (String)adapterView.getItemAtPosition(i));
                startActivity(mIntent);
            }
        });
    }
}
