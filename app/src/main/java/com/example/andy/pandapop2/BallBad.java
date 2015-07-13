package com.example.andy.pandapop2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.andy.pandapop2.Game.BadBallType;

/**
 * Created by Andy on 6/28/2015.
 */
public class BallBad extends GameObject {
    public Animation animation = new Animation();
    public BadBallType badBallType;
    public BallBad(Bitmap[] res, int w, int h, int numFrames, BadBallType type, int screenWidth){
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
                firePower = 100;
        }
        double randX = Math.random();
        this.dx = (int)((randX-.5)*0.3*100);
        double randY = Math.random();
        this.dy = (int) ((randY)*25);

        if (this.dy < 8) this.dy = 8;

        while ((this.x > (screenWidth-this.width))||this.x<1) {
            this.x = (int) (Math.random() * screenWidth);
        }
        this.y = -this.height;
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
