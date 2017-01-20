package com.sushmobile.albumslist.network;

import android.os.AsyncTask;

import com.sushmobile.albumslist.enums.Service;
import com.sushmobile.albumslist.interfaces.AsyncResponse;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class GetService extends AsyncTask<RequestSettings, Void, String> {

    public AsyncResponse delegate = null;
    private Service service;

	@Override
	protected String doInBackground(RequestSettings... requestSettings) {
		URL url;
		HttpURLConnection urlConnection = null;
        String responseContent = null;
        service = requestSettings[0].getService();

        try {
            String uri = buildURL(requestSettings[0].getUri(), requestSettings[0].getController(), requestSettings[0].getUriParameters());
            url = new URL(uri);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15 * 1000);
            urlConnection.setReadTimeout(15 * 1000);
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            responseContent = readStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }

		return responseContent;
	}

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private static String readStream(InputStream in) throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);

        return responseStrBuilder.toString();
    }

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
        delegate.processFinish(new ResponseInfo(service, result));
	}

    private String buildURL(String uri, String controller, HashMap<String, String> uriParameters) {
        String url;
        url = uri + controller;

        if(uriParameters != null){
            url += "?";

            Object[] keys = uriParameters.keySet().toArray();
            Object[] values = uriParameters.values().toArray();

            for(int i = 0; i < uriParameters.size(); i++){
                try {
                    if(keys[i] != null && values[i] != null) {
                        url += keys[i].toString() + "=" + URLEncoder.encode(values[i].toString(), "UTF-8") + "&";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    url += keys[i].toString() + "=" + values[i].toString() + "&";
                }
            }

            url = url.substring(0, url.length()-1);
        }

        return url;
    }
}