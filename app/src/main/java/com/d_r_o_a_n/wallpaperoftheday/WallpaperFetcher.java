package com.d_r_o_a_n.wallpaperoftheday;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Aaron on 13-Feb-15.
 */
public class WallpaperFetcher extends AsyncTask<Void, Void, Bitmap> {
    private final String LOG_TAG=WallpaperFetcher.class.getSimpleName();
    private Context mContext;
    private View rootView;
    Bitmap bitm;
    WallpaperFetcher(Context context, View rootView){
        this.mContext=context;
        this.rootView=rootView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        String link="http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-WW";
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
            //json parsing
            JSONObject mStart=new JSONObject(json);
            JSONArray images=mStart.getJSONArray("images");
            JSONObject a=images.getJSONObject(0);
            String b=a.getString("url");
            b="http://bing.com"+b;
            bitm=loadBitmap(b);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bitm;
    }

    @Override
    protected void onPostExecute(final Bitmap result) {
        super.onPostExecute(result);
        Animation animationFadeIn = AnimationUtils.loadAnimation(mContext, R.anim.fadein);
        Animation animationFadeOut = AnimationUtils.loadAnimation(mContext, R.anim.fadeout);
        RelativeLayout relative = (RelativeLayout) rootView.findViewById(R.id.lay);
        relative.startAnimation(animationFadeIn);
        Drawable dr = new BitmapDrawable(result);
        (relative).setBackgroundDrawable(dr);
        ImageView b = (ImageView) rootView.findViewById(R.id.activate);
        b.setVisibility(View.VISIBLE);
        TextView t= (TextView)rootView.findViewById(R.id.wait);
        t.setVisibility(View.INVISIBLE);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
                try {
                    int h = wallpaperManager.getDesiredMinimumHeight();
                    int w = wallpaperManager.getDesiredMinimumWidth();
                    wallpaperManager.suggestDesiredDimensions(w, h);
                    Bitmap bitmapResized = Bitmap.createScaledBitmap(result, w, h, true);
                    wallpaperManager.setBitmap(bitmapResized);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        b.startAnimation(animationFadeIn);


    }

    public Bitmap loadBitmap(String url) {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("w", "null");
                }
            }
        }
        return bm;
    }

}
