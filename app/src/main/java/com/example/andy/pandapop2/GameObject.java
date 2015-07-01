package com.example.andy.pandapop2;

import android.graphics.Rect;

/**
 * Created by Andy on 6/21/2015.
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;
    protected int mass;
    protected boolean dead = false;
    protected int hitPoints;
    protected int firePower;

    public void hitSides(int screenWidth){
        if ((x <= 0)||((x+width) >= screenWidth)){
            dx=-dx;
        }
    }

    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }
}
