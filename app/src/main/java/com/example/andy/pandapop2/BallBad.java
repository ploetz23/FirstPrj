package com.example.andy.pandapop2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.andy.pandapop2.Game.BadBallType;

/**
 * Created by Andy on 6/28/2015.
 */
public class BallBad extends GameObject {
    private Animation animation = new Animation();
    private BadBallType badBallType;
    public BallBad(Bitmap[] res, int w, int h, int numFrames, BadBallType type){
        Bitmap[] image = new Bitmap[numFrames];
        for (int i = 0; i < image.length; i++){
            image[i] = Bitmap.createBitmap(res[i]);
        }
        this.width=w;
        this.height=h;
        animation.setFrames(image);
        this.badBallType = type;
        switch (type){
            case POACHER:
                hitPoints = 60;
                firePower = 60;
        }
    }
    public void update(){

        x = x + dx;
        y = y + dy;
        animation.update();
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
}
