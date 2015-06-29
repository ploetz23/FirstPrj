package com.example.andy.pandapop2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Andy on 6/20/2015.
 */
public class Background {
    private static Bitmap image;
    private static final int x = 0;
    private static final int y = 0;
    public Background(Bitmap res){
        image = res;
    }
    public void update(){

    }
    public static void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

}
