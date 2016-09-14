package com.notionassortment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

//--- 衝突判定(楕円×楕円) ---//
public class Tips_3 extends Task{

    private float b1x,b1y,b2x,b2y;
    private RectF rectf1 = new RectF();
    private RectF rectf2 = new RectF();
    private Paint paint1 = new Paint();
    private Paint paint2 = new Paint();
    private boolean ballhit = false;

    //--- 楕円(擬似)構造体 ---//
    class ELLIPSE{
        float fRad_X; // X軸径
        float fRad_Y; // Y軸径
        float fAngle; // 回転角度
        float fCx; // 制御点X座標
        float fCy; // 制御点Y座標
    }

    ELLIPSE e1 = new ELLIPSE();
    ELLIPSE e2 = new ELLIPSE();

    public Tips_3(){

        b1x = b2x = 200;
        b1y = 200;
        b2y = 600;

        e1.fRad_X = 60;
        e1.fRad_Y = 120;
        e1.fAngle = 0;
        e1.fCx = b1x;
        e1.fCy = b1y;

        e2.fRad_X = 60;
        e2.fRad_Y = 60;
        e2.fAngle = 0;
        e2.fCx = b2x;
        e2.fCy = b2y;

        rectf1.top = b1y + e1.fRad_Y;
        rectf1.bottom = b1y - e1.fRad_Y;
        rectf1.left = b1x - e1.fRad_X;
        rectf1.right = b1x + e1.fRad_X;

        rectf2.top = b2y + e2.fRad_Y;
        rectf2.bottom = b2y - e2.fRad_Y;
        rectf2.left = b2x - e2.fRad_X;
        rectf2.right = b2x + e2.fRad_X;

        paint1.setColor(Color.CYAN);
        paint2.setColor(Color.MAGENTA);
    }

    public boolean onUpdate(){

        if(MainActivity.move){
            b1x = MainActivity.x;
            b1y = MainActivity.y;

            e1.fCx = b1x;
            e1.fCy = b1y;

            rectf1.top = b1y + 120;
            rectf1.bottom = b1y -120;
            rectf1.left = b1x - 60;
            rectf1.right = b1x + 60;
        }

        //--- 本題 ---//
        if(collisionEllipse(e1,e2)) {
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
            canvas.drawOval(rectf1,paint1);
        else
            canvas.drawOval(rectf1,paint2);

        canvas.drawOval(rectf2,paint1);
    }

    private boolean collisionEllipse(ELLIPSE E1, ELLIPSE E2){
        // STEP1 : E2を単位円にする変換をE1に施す
        float DefAng = E1.fAngle-E2.fAngle;
        double Cos = Math.cos(DefAng);
        double Sin = Math.sin( DefAng );
        double nx = E2.fRad_X * Cos;
        double ny = -E2.fRad_X * Sin;
        double px = E2.fRad_Y * Sin;
        double py = E2.fRad_Y * Cos;
        double ox = Math.cos( E1.fAngle )*(E2.fCx-E1.fCx) + Math.sin(E1.fAngle)*(E2.fCy-E1.fCy);
        double oy = -Math.sin( E1.fAngle )*(E2.fCx-E1.fCx) + Math.cos(E1.fAngle)*(E2.fCy-E1.fCy);

        // STEP2 : 一般式A～Gの算出
        float rx_pow2 = 1/(E1.fRad_X*E1.fRad_X);
        float ry_pow2 = 1/(E1.fRad_Y*E1.fRad_Y);
        double A = rx_pow2*nx*nx + ry_pow2*ny*ny;
        double B = rx_pow2*px*px + ry_pow2*py*py;
        double D = 2*rx_pow2*nx*px + 2*ry_pow2*ny*py;
        double E = 2*rx_pow2*nx*ox + 2*ry_pow2*ny*oy;
        double F = 2*rx_pow2*px*ox + 2*ry_pow2*py*oy;
        double G = (ox/E1.fRad_X)*(ox/E1.fRad_X) + (oy/E1.fRad_Y)*(oy/E1.fRad_Y) - 1;

        // STEP3 : 平行移動量(h,k)及び回転角度θの算出
        double tmp1 = 1/(D*D-4*A*B);
        double h = (F*D-2*E*B)*tmp1;
        double k = (E*D-2*A*F)*tmp1;
        double Th = (B-A)==0?0:Math.atan( D/(B-A) ) * 0.5f;

        // STEP4 : +1楕円を元に戻した式で当たり判定
        double CosTh = Math.cos(Th);
        double SinTh = Math.sin(Th);
        double A_tt = A*CosTh*CosTh + B*SinTh*SinTh - D*CosTh*SinTh;
        double B_tt = A*SinTh*SinTh + B*CosTh*CosTh + D*CosTh*SinTh;
        double KK = A*h*h + B*k*k + D*h*k - E*h - F*k + G;
        if(KK>0) KK = 0; // 念のため
        double Rx_tt = 1+Math.sqrt(-KK/A_tt);
        double Ry_tt = 1+Math.sqrt(-KK/B_tt);
        double x_tt = CosTh*h-SinTh*k;
        double y_tt = SinTh*h+CosTh*k;
        double JudgeValue = x_tt*x_tt/(Rx_tt*Rx_tt) + y_tt*y_tt/(Ry_tt*Ry_tt);

        if( JudgeValue <= 1 )
            return true; // 衝突

        return false;
    }
}
