package com.example.andy.pandapop2;

import android.graphics.Bitmap;

/**
 * Created by Andy on 6/21/2015.
 */
public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay=30;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frms){
        this.frames = frms;
        currentFrame = 0;
        startTime = System.nanoTime();
    }
    public void setDelay(long d){
        delay = d;
    }
    public void setCurrentFrame(int i){
        currentFrame = i;
    }
    public  void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if (elapsed > delay){
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }

    }
    public Bitmap getImage(){
        return frames[currentFrame];
    }
    public int getCurrentFrame(){
        return currentFrame;
    }
    public boolean isPlayedOnce(){
        return playedOnce;
    }
}
