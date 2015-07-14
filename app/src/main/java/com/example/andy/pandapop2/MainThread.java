package com.example.andy.pandapop2;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Andy on 6/20/2015.
 */
public class MainThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running=true;
    private static Canvas canvas;
    private double badGuyCountDown=0;
    private long badGuyCountDownRate;
    private int EndGameCounter=15;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
        switch (this.gamePanel.Level){
            case 1:
                badGuyCountDownRate = 3;
            case 2:
                badGuyCountDownRate = 3;
            case 3:
                badGuyCountDownRate = 3;
            case 4:
                badGuyCountDownRate = 3;
            case 5:
                badGuyCountDownRate = 3;
            case 6:
                badGuyCountDownRate = 3;
            case 7:
                badGuyCountDownRate = 3;
            case 8:
                badGuyCountDownRate = 3;
            case 9:
                badGuyCountDownRate = 3;
            case 10:
                badGuyCountDownRate = 3;
            case 11:
                badGuyCountDownRate = 3;
            case 12:
                badGuyCountDownRate = 3;
            case 13:
                badGuyCountDownRate = 3;
            case 14:
                badGuyCountDownRate = 3;
            case 15:
                badGuyCountDownRate = 3;
            case 16:
                badGuyCountDownRate = 3;
            case 17:
                badGuyCountDownRate = 3;
            case 18:
                badGuyCountDownRate = 3;
            case 19:
                badGuyCountDownRate = 3;
            case 20:
                badGuyCountDownRate = 3;
            case 21:
                badGuyCountDownRate = 3;
            case 22:
                badGuyCountDownRate = 3;
            case 23:
                badGuyCountDownRate = 3;
            case 24:
                badGuyCountDownRate = 3;
            case 25:
                badGuyCountDownRate = 3;
        }
    }


    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        int FPS = 30;
        long targetTime = 1000 / FPS;
        while (running) {
            startTime = System.nanoTime();
            if (badGuyCountDown<=0){
                setNeedsToCreateBadGuy();
            }

            //Try locking canvas for paint editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                        this.gamePanel.update();
                        this.gamePanel.draw(canvas);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime()-startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try {
                this.sleep(waitTime);
            } catch (Exception e){

            }
            totalTime += System.nanoTime() - startTime;
            badGuyCountDown = badGuyCountDown-badGuyCountDownRate;
            frameCount++;
            if (frameCount == FPS){
                frameCount = 0;
                totalTime = 0;
            }
            CheckGame();
        }
    }

    public void setRunning(boolean b){
        running = b;
    }

    private void setNeedsToCreateBadGuy(){
        gamePanel.needToGenerateBadGuy=true;
        double rand = Math.random();
        badGuyCountDown = (int) rand*300;
        if (badGuyCountDown<200) {
            badGuyCountDown = 200;
        }
    }
    public void CheckGame(){
        if (gamePanel.castleHitPoints<=0){
            EndGameCounter--;
            if (EndGameCounter <=0) {
                gamePanel.LoseGame();
                gamePanel.game.LoseGame();
            }
        }else if (gamePanel.BadGuysLeft == 0 && gamePanel.ballBadsToUpdate.size()==0){
            EndGameCounter--;
            if (EndGameCounter <=0) {
                gamePanel.WinGame();
                gamePanel.game.WinGame();
            }
        }
    }

}
