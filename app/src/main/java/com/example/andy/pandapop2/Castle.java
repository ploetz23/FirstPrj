package com.example.andy.pandapop2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.HashSet;

/**
 * Created by Andy on 7/12/2015.
 */
public class Castle extends GameObject {
    public static Bitmap image;
    public Castle(Bitmap res, int x, int y){
        image = res;
        this.x=x;
        this.y=y;
        this.width=res.getWidth();
        this.height=res.getHeight();
    }
    public void update(){

    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }
}
