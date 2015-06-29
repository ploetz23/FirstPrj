package com.example.andy.pandapop2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.andy.pandapop2.Game.GoodBallType;

/**
 * Created by Andy on 6/21/2015.
 */
public class BallBasic extends GameObject {
    private Animation animation = new Animation();
    public GoodBallType goodBallType;
    public BallBasic(Bitmap[] res, int w, int h, int numFrames, GoodBallType type){
        Bitmap[] image = new Bitmap[numFrames];
        for (int i = 0; i < image.length; i++){
            image[i] = Bitmap.createBitmap(res[i]);
        }
        animation.setFrames(image);
        this.goodBallType = type;
        switch (type){
            case PAWN:
                mass = 100;
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
