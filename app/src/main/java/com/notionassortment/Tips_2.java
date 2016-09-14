package com.notionassortment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

//--- 衝突判定(円×円) ---//
public class Tips_2 extends Task{

    private float b1x,b1y,b2x,b2y,r1,r2;
    private Paint paint1 = new Paint();
    private Paint paint2 = new Paint();
    private boolean ballhit = false;

    public Tips_2(){
        b1x = b2x = 200;
        b1y = 200;
        b2y = 400;

        r1 = 50;
        r2 = 30;

        paint1.setColor(Color.CYAN);
        paint2.setColor(Color.MAGENTA);
    }

    public boolean onUpdate(){

        if(MainActivity.move){
            b1x = MainActivity.x;
            b1y = MainActivity.y;
        }

        //--- 本題 ---//
        if(Math.pow((b1x-b2x),2) + Math.pow((b1y-b2y),2) <= Math.pow((r1+r2),2)) {
            ballhit = true;
            Log.d("debug", "テスト");
        } else{
            ballhit = false;
        }


        return true;
    }

    public void onDraw(Canvas canvas) {

        //--- 接触したら色が変わる ---//
        if(ballhit)
            canvas.drawCircle(b1x,b1y,r1,paint2);
        else
            canvas.drawCircle(b1x,b1y,r1,paint1);

        canvas.drawCircle(b2x,b2y,r2,paint1);
    }
}
