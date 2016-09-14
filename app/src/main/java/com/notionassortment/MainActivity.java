package com.notionassortment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    private GestureDetector gestureDetector;
    private MainSurfaceView _view;
    public static Point point = new Point();
    public static float x;
    public static float y;
    public static boolean move = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = this.getWindowManager().getDefaultDisplay();
        display.getSize(point);

        _view = new MainSurfaceView(this);
        setContentView(_view);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //---SE/BGMぶっこみ場所
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                move = false;
                break;
            case MotionEvent.ACTION_MOVE:
                move = true;
                x = event.getX();
                y = event.getY();
                break;
        }
        return true;
    }
}
