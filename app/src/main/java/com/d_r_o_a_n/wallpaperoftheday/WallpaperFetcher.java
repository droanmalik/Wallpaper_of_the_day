package com.d_r_o_a_n.wallpaperoftheday;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Aaron on 13-Feb-15.
 */
public class WallpaperFetcher extends AsyncTask<Void, Void, Bitmap> {
    private final String LOG_TAG=WallpaperFetcher.class.getSimpleName();
    private Context mContext;
    private View rootView;
    WallpaperFetcher(Context context, View rootView){
        this.mContext=context;
        this.rootView=rootView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        String link="http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        HttpURLConnection urlConnection = null;
        String json;
        BufferedReader reader = null;
        try {
            URL url=new URL(link);
            //open connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //read input stream
            InputStream inputStream=urlConnection.getInputStream();
            StringBuffer buffer=new StringBuffer();
            if(inputStream==null){
                json=null;
            }
            reader=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                json = null;
            }
            json = buffer.toString();
            Log.v(LOG_TAG, json);



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
