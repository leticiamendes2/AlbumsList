package com.sushmobile.albumslist.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sushmobile.albumslist.R;
import com.sushmobile.albumslist.adapters.AlbumListArrayAdapter;
import com.sushmobile.albumslist.dialogs.ConnectionErrorDialogFragment;
import com.sushmobile.albumslist.enums.Service;
import com.sushmobile.albumslist.interfaces.AsyncResponse;
import com.sushmobile.albumslist.models.Album;
import com.sushmobile.albumslist.models.User;
import com.sushmobile.albumslist.network.GetService;
import com.sushmobile.albumslist.network.RequestSettings;
import com.sushmobile.albumslist.network.ResponseInfo;
import com.sushmobile.albumslist.util.Util;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private ArrayList<Album> albums;
    private ArrayList<User> users;
    private ListView albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (new Util().hasNetworkConnection(this)) {
            GetService getService = new GetService();
            getService.delegate = this;
            getService.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new RequestSettings(Service.ALBUMS, null));
        } else {
            new ConnectionErrorDialogFragment().show(this.getFragmentManager(), "connection_error_message");
        }

        albumList = (ListView) this.findViewById(R.id.album_list);
    }

    @Override
    public void processFinish(ResponseInfo result) {
        if(result.getService().equals(Service.ALBUMS))
        {
            treatResponseServiceAlbums(result.getResponse());
        }
        else if(result.getService().equals(Service.USERS))
        {
            treatResponseServiceUsers(result.getResponse());
        }
    }

    private void treatResponseServiceAlbums(String result)
    {
        JSONArray jsonArray = new Util().parseStringToJSONArray(result);
        albums = new Util().processResultsAlbums(jsonArray);
        albumList.setAdapter(new AlbumListArrayAdapter(this, albums));
        albumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(MainActivity.this, AlbumDetailsActivity.class);
                mIntent.putExtra("album", (Album)adapterView.getItemAtPosition(i));
                startActivity(mIntent);
            }
        });

        GetService getService = new GetService();
        getService.delegate = this;
        getService.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new RequestSettings(Service.USERS, null));
    }

    private void treatResponseServiceUsers(String result)
    {
        final JSONArray jsonArray = new Util().parseStringToJSONArray(result);
        users = new Util().processResultsUsers(jsonArray);
        joinLists();
        albumList.setAdapter(new AlbumListArrayAdapter(this, albums));
    }

    private void joinLists()
    {
        for (Album album : albums) {
            for (User user : users) {
                if(album.getUser().getId() == user.getId()) {
                    album.getUser().setName(user.getName());
                }
            }
        }
    }
}