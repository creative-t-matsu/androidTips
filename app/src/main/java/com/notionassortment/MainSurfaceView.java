package com.notionassortment;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    private MainManager _mainManager = new MainManager();
    private Thread _thread;

    private float baseWidth = 634;
    private float baseHeight = 356;
    public static float VIEW_WIDTH = 0;
    public static float VIEW_HEIGHT = 0;
    public static float scale;

    public static float base_w = 0;
    public static float base_h = 0;
    public static float density = 0;

    public MainSurfaceView(Context context){
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int heidht){}

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        density = getResources().getDisplayMetrics().density;

        float scale_x = getWidth() / (baseWidth * density);
        float scale_y = getHeight() / (baseHeight * density);
        scale = scale_x > scale_y ? scale_y : scale_x;

        VIEW_WIDTH = baseWidth * density;
        VIEW_HEIGHT = baseHeight * density;

        _thread = new Thread(this);
        _thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        _thread = null;
    }

    @Override
    public void run(){
        while (_thread!=null){
            _mainManager.onUpdate();
            onDrawMain(getHolder());
        }
    }

    private void onDrawMain(SurfaceHolder holder){
        Canvas canvas = holder.lockCanvas();

        if(canvas == null)
            return;

        canvas.scale(scale, scale);
        _mainManager.onDraw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }
}
