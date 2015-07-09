package com.example.andy.pandapop2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.andy.pandapop2.Game.GoodBallType;

/**
 * Created by Andy on 6/21/2015.
 */
public class BallBasic extends GameObject {
    private Animation animation = new Animation();
    private static final double DamageConstant = 25;

    public GoodBallType goodBallType;
    public long RegenerationTime;
    public long RegenerationCounter=0;

    public boolean inPlay = true;
    public BallBasic(Bitmap[] res, int w, int h, int numFrames, GoodBallType type){
        Bitmap[] image = new Bitmap[numFrames];
        for (int i = 0; i < image.length; i++){
            image[i] = Bitmap.createBitmap(res[i]);
        }
        width=w;
        height=h;
        animation.setFrames(image);
        this.goodBallType = type;
        switch (type){
            case PAWN:
                firePower = 50;
                hitPoints = 50;
                RegenerationTime = 15; //half second
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
    public void CheckGoodBadCollision(BallBad ballBad){
        double xGood = this.x;
        double xBad = ballBad.x;
        double yGood = this.y;
        double yBad = ballBad.y;
        double radiusGood = this.width/2;
        double radiusBad = ballBad.width/2;
        double distance = Math.pow((xGood - xBad) * (xGood - xBad) + (yGood - yBad) * (yGood - yBad), 0.5);
        if ((radiusBad >= radiusGood) && (distance <= (radiusBad - radiusGood))){
            System.out.println("Circle 1 is inside Circle 2.");
        }
        else if ((radiusGood >= radiusBad) && (distance <= (radiusGood - radiusBad))){
            System.out.println("Circle 2 is inside Circle 1.");
        }
        else if ((radiusBad+radiusGood)>distance){
            //Contact! Do something about it!
            KillAndUpdateHitPoints(ballBad, radiusGood, radiusBad);
        }
    }
    private void KillAndUpdateHitPoints(BallBad ballBad, double GoodRadius, double BadRadius){
        double damageToGood;
        double damageToBad;
        double deltaXGoodBad = (this.x-GoodRadius)-(ballBad.x-BadRadius);
        double deltaYGoodBad = (this.y-GoodRadius)-(ballBad.y-BadRadius);
        double deltaXBadGood = -deltaXGoodBad;
        double deltaYBadGood = -deltaYGoodBad;

        damageToBad = (Math.cos(Math.atan(deltaXGoodBad/deltaYGoodBad)))*firePower*Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2))/DamageConstant;
        damageToGood = (Math.cos(Math.atan(deltaXBadGood/deltaYBadGood)))*ballBad.firePower*Math.sqrt(Math.pow(ballBad.dx,2)+Math.pow(ballBad.dy,2))/DamageConstant;

        System.out.println(damageToBad);
        System.out.println(damageToGood);

        if (damageToBad<0){
            damageToBad = 0;
        }
        if (damageToGood<0){
            damageToGood=0;
        }
        if (damageToBad>damageToGood){
            ballBad.dead=true;
            hitPoints = hitPoints-(int)damageToGood;
            if(hitPoints<=0){
                this.dead=true;
            }
        }
        else if(damageToGood>damageToBad){
            dead = true;
            ballBad.hitPoints = ballBad.hitPoints-(int)damageToBad;
            if(ballBad.hitPoints<=0){
                ballBad.dead=true;
            }
        }
        else{
            this.dead=true;
            ballBad.dead=true;
        }
    }
}
