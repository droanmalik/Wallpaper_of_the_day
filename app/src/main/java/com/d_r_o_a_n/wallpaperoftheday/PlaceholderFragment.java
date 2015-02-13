package com.d_r_o_a_n.wallpaperoftheday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aaron on 13-Feb-15.
 */
class PlaceholderFragment extends Fragment {
    ImageView image;TextView text;
    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        WallpaperFetcher task=new WallpaperFetcher(getActivity(),rootView);
        task.execute();
        image = (ImageView)rootView.findViewById(R.id.logo);
        Animation animationFadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        image.startAnimation(animationFadeIn);
        text= (TextView)rootView.findViewById(R.id.wait);
        text.setVisibility(View.VISIBLE);
        return rootView;
    }
public void onPause() {
    // TODO Auto-generated method stub
    super.onPause();
    image.clearAnimation();

}
}

