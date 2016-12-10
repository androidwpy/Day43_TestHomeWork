package com.example.administrator.day43_testhomework.fragment;


import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.administrator.day43_testhomework.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private String path="http://flv.bn.netease.com/videolib3/1610/11/WMZHK8637/HD/WMZHK8637-mobile.mp4";
    private VideoView videoView;
    private CheckBox checkBox;
    private int oldHeight;
    private boolean isPrepared=false;
    private ImageView playerControl;

    public FragmentThree() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("1608", "---------------->onCreateView: ");
        
        View view = inflater.inflate(R.layout.fragment_fragment_three, container, false);

        videoView = ((VideoView) view.findViewById(R.id.fragment_three_videoView));
        checkBox = ((CheckBox) view.findViewById(R.id.fragment_three_fullorexit));
        playerControl = ((ImageView) view.findViewById(R.id.playerId));

        //设置资源路径
        videoView.setVideoPath(path);
        //设置底部控制条
        videoView.setMediaController(new MediaController(getContext()));

//        playerControl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                videoView.start();
//                playerControl.setVisibility(View.GONE);
//            }
//        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (!isPrepared){

                }
            }
        });

        checkBox.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b){
            oldHeight = videoView.getHeight();

            //切换成横屏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
            layoutParams.height=ViewGroup.LayoutParams.MATCH_PARENT;
            videoView.setLayoutParams(layoutParams);
        }else{
            //切换成竖屏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
            layoutParams.height=oldHeight;
            videoView.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("1608", "---------------->onCreate: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("1608", "---------------->onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
       if (videoView.isPlaying()){
           videoView.pause();
       }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("1608", "---------------->onPause: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("1608", "---------------->onResume: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("1608", "---------------->onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("1608", "---------------->onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("1608", "---------------->onDetach: ");
    }
}
