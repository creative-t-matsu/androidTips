package com.notionassortment;

import android.graphics.Canvas;
import android.graphics.Color;
import java.util.LinkedList;

public class MainManager{
    private LinkedList<Task> _taskList = new LinkedList<Task>();

    MainManager(){
        _taskList.add(new Tips_3());
    }

    public boolean onUpdate(){
        for (int i = 0; i < _taskList.size(); i++) {
            if (!_taskList.get(i).onUpdate()) {
                _taskList.remove(i);
                i--;
            }
        }
        return true;
    }

    public void onDraw(Canvas canvas){
        canvas.drawColor(Color.BLACK);

        for(int i=0; i<_taskList.size(); i++){
            _taskList.get(i).onDraw(canvas);
        }
    }

    public void remove(){

    }
}
