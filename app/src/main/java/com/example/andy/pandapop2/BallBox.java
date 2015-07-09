package com.example.andy.pandapop2;

import android.graphics.Rect;

import com.example.andy.pandapop2.Game.GoodBallType;

/**
 * Created by Andy on 6/26/2015.
 */
public class BallBox {
    public int x;
    public int y;
    public int width;
    public int height;
    public GoodBallType goodBallType;
    public int RegenerationTime;
    public BallBox(int x, int y, int width, int height, GoodBallType goodBallType){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.goodBallType = goodBallType;
    }
    public Rect getRect(){
        return new Rect(x, y, x + width, y + height);
    }
}
