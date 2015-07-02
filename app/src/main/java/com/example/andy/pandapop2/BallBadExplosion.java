package com.example.andy.pandapop2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Andy on 7/1/2015.
 */
public class BallBadExplosion extends GameObject {
    private Animation animation = new Animation();
    public boolean done = false;
    public BallBadExplosion(Bitmap[] res, int w, int h, int numFrames){
        Bitmap[] image = new Bitmap[numFrames];
        for (int i = 0; i < image.length; i++){
            image[i] = Bitmap.createBitmap(res[i]);
        }
        this.width=w;
        this.height=h;
        animation.setFrames(image);
    }
    public void update(){
        animation.update();
        if (animation.playedOnce){
            done = true;
        }
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
}
