package com.d_r_o_a_n.wallpaperoftheday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Aaron on 13-Feb-15.
 */
class PlaceholderFragment extends Fragment {

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        WallpaperFetcher task=new WallpaperFetcher(getActivity(),rootView);
        task.execute();
        return rootView;
    }
}

