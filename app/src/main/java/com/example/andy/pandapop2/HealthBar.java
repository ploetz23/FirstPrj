package com.example.andy.pandapop2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Andy on 7/8/2015.
 */
public class HealthBar {
    private static int x;
    private static int y;
    private static long MaxHealth;
    private static long healthLeft;
    private static long healthGone;
    private static int length;
    private static int width;
    public HealthBar(int x, int y, int length, int width, long maxHealth){
        HealthBar.x = x;
        HealthBar.y = y;
        MaxHealth = maxHealth;
        healthLeft = maxHealth;
        HealthBar.length = length;
        HealthBar.width =width;
    }

    public void update(long health){
        healthLeft = health;
        healthGone = MaxHealth-healthLeft;
        if(healthLeft <= 0){
            healthLeft=0;
            healthGone=MaxHealth;
        }
    }
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        if (healthLeft == MaxHealth){
            //Draw green rectangle
            paint.setColor(Color.GREEN);
            canvas.drawRect(x,y,x+length,y+width,paint);
        } else if (healthGone==MaxHealth){
            //Draw red rectangle
            paint.setColor(Color.RED);
            canvas.drawRect(x, y, x + length, y + width, paint);
        } else {
            //Draw both red and green rectangles
            paint.setColor(Color.GREEN);
            long lengthGreen = length*healthLeft/MaxHealth;
            canvas.drawRect(x, y, x+lengthGreen, y + width, paint);

            paint.setColor(Color.RED);
            canvas.drawRect(x+lengthGreen,y, x+length, y+width,paint);
        }
    }
}
