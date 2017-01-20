package com.sushmobile.albumslist.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sushmobile.albumslist.models.Album;
import com.sushmobile.albumslist.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Util {

    public boolean hasNetworkConnection(Context ctx) {
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Album> processResultsAlbums(JSONArray result) {

        ArrayList<Album> albums = new ArrayList<>();

        try {
            for(int i = 0; i < result.length(); i++){
                JSONObject album = (JSONObject) result.get(i);

                int albumId = album.getInt("id");
                String title = album.getString("title");

                int userId = album.getInt("userId");
                User user = new User(userId);

                Album a = new Album(albumId, title, user);
                albums.add(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return albums;
    }

    public ArrayList<User> processResultsUsers(JSONArray result) {

        ArrayList<User> users = new ArrayList<>();

        try {
            for(int i = 0; i < result.length(); i++){
                JSONObject user = (JSONObject) result.get(i);

                int id = user.getInt("id");
                String name = user.getString("name");
                User u = new User(id, name);
                users.add(u);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }

    public JSONArray parseStringToJSONArray(String stringToParse)
    {
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(stringToParse);
        } catch (Exception ex)
        {
            System.out.println(" - ex: " + ex.toString());
        }

        return jsonArray;
    }

    public LinkedHashMap<String, String> processResultsPhotos(JSONArray result) {
        LinkedHashMap<String, String> photos = new LinkedHashMap<>();

        try {
            for(int i = 0; i < result.length(); i++){
                JSONObject user = (JSONObject) result.get(i);

                String urlSmall = user.getString("thumbnailUrl");
                String urlLarge = user.getString("url");
                photos.put(urlSmall, urlLarge);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return photos;
    }
}
