package com.notionassortment;

import android.graphics.Canvas;

public abstract class Task {

    public boolean onUpdate(){
        return true;
    }

    public void onDraw(Canvas canvas) {

    }

    public void remove(){

    }
}