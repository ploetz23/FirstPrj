package com.example.andy.pandapop2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static com.example.andy.pandapop2.Game.*;
import static com.example.andy.pandapop2.Game.BadBallType.POACHER;
import static com.example.andy.pandapop2.Game.GoodBallType.PAWN;

/**
 * Created by Andy on 6/20/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public boolean needToGenerateBadGuy=false;
    public int Level;
    public long castleHitPoints = 2100;

    public MainThread thread;

    private final int LAST_LEVEL = MainActivity.LAST_LEVEL;

    private long Score = 0;
    private ArrayList<Stats> statsArrayList= new ArrayList<>();
    private Background bg;
    private Castle castle;
    private Bitmap[] pawnRes;
    private Bitmap[] poacherRes;
    private Bitmap[] poacherExpRes;
    private BallBasic ballBasicSelected;
    private ArrayList<BallBasic> ballBasicsToUpdate = new ArrayList<>();
    public ArrayList<BallBad> ballBadsToUpdate = new ArrayList<>();
    private ArrayList<BallBadExplosion> ballBadExplosionsToUpdate = new ArrayList<>();
    private BallBox ballBox;
    private HealthBar healthBar;
    private ArrayList<BallBasic> ballBasicsInBox = new ArrayList<>();
    private double badGuyCountdownRate;

    public int BadGuysLeft;

    private VelocityTracker mVelocityTracker = null;

    //Screen Dimensions
    private WindowManager windowManager;
    private Display display;
    private Point size = new Point();
    private int screenWidth;
    private int screenHeight;

    //Castle position
    private final int CASTLE_LEFT = 100;
    private final int CASTLE_RIGHT = 1340;

    //Ball boxes hold balls to bring into play
    private ArrayList<BallBox> ballBoxArrayList = new ArrayList<>();

    //Game Activity
    public Game game;


    public GamePanel(Context context, GoodBallType[] goodBallTypeAry, BadBallType[] badBallTypeAry,int level,Game game){
        super (context);
        this.Level = level;
        this.game=game;

        setStatsArrayList();
        double badGuyCountDownRate=0;
        switch (level){
                case 1:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 2:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 3:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 4:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 5:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 6:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 7:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 8:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 9:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 10:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 11:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 12:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 13:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 14:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 15:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 16:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 17:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 18:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 19:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 20:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 21:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 22:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 23:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 24:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
                case 25:
                    badGuyCountdownRate = 3;
                    BadGuysLeft = 30;
        }


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        //Get screen dimensions
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        display.getSize(size);
        screenHeight = size.y;
        screenWidth = size.x;

        //Add callback to surface holder to intercept events
        getHolder().addCallback(this);
        thread = new MainThread(this.getHolder(),this);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                int x = (int) event.getX();
                int y = (int) event.getY();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Rect rect;
                        // Retrieve a new VelocityTracker object to watch the velocity of a motion.

                        mVelocityTracker = VelocityTracker.obtain();

                        Boolean found = false;
                        for (BallBox ballBox : ballBoxArrayList) {
                            rect = ballBox.getRect();
                            if (rect.contains(x, y)) {
                                for (BallBasic bbInBox : ballBasicsInBox){
                                    if (bbInBox.goodBallType==ballBox.goodBallType){
                                        if (bbInBox.RegenerationCounter <=0){
                                            bbInBox.RegenerationCounter=bbInBox.RegenerationTime;
                                            found = true;
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                        if (!found) {
                            mVelocityTracker.clear();
                            break;
                        }
                        ballBasicSelected = CreateBallBasic(ballBox.goodBallType);
                        ballBasicSelected.x = x;
                        ballBasicSelected.y = y;
                        synchronized (ballBasicsToUpdate) {
                            ballBasicsToUpdate.add(ballBasicSelected);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mVelocityTracker == null) {
                            mVelocityTracker = VelocityTracker.obtain();
                        } else {
                            // Reset the velocity tracker back to its initial state.
                        }
                        // Add a user's movement to the tracker.
                        mVelocityTracker.addMovement(event);
                        // When you want to determine the velocity, call
                        // computeCurrentVelocity(). Then call getXVelocity()
                        // and getYVelocity() to retrieve the velocity for each pointer ID.
                        mVelocityTracker.computeCurrentVelocity(1000);
                        // Log velocity of pixels per second
                        // Best practice to use VelocityTrackerCompat where possible.
                        if (ballBasicSelected != null) {
                            if (y > screenHeight - 400) {
                                ballBasicSelected.y = y;
                                ballBasicSelected.x = x;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        int dx = (int) mVelocityTracker.getXVelocity();
                        int dy = (int) mVelocityTracker.getYVelocity();
                        if (ballBasicSelected != null) {
                            if (dy > -100) {
                                ballBasicsToUpdate.remove(ballBasicSelected);
                                ballBasicSelected = null;
                                break;
                            }
                            BallBasic bb = CreateBallBasic(ballBasicSelected.goodBallType);
                            if (dx > 2500) dx = 2500;
                            if (dx < -2500) dx = -2500;
                            if (dy < -2500) dy = -2500;
                            bb.x = ballBasicSelected.x;
                            bb.y = ballBasicSelected.y;
                            bb.dx = dx / 60;
                            bb.dy = dy / 60;
                            ballBasicsToUpdate.add(bb);
                            ballBasicsToUpdate.remove(ballBasicSelected);
                            ballBasicSelected = null;
                        }
                        mVelocityTracker.clear();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return true;
            }
        });

        //Set focusable so we it can handle events.
        setFocusable(true);

        //Instantiate background
        bg = new Background(drawableToBitmap(getResources().getDrawable(R.drawable.start_background)));

        //Instantiate Castle
        Bitmap resCastle = drawableToBitmap(getResources().getDrawable(R.drawable.castle));
        int xCastle = screenWidth/2-resCastle.getWidth()/2;
        castle = new Castle(resCastle,xCastle,screenHeight-850);

        //Instantiate healthbar
        healthBar = new HealthBar(10,screenHeight-20,screenWidth/4,15,castleHitPoints);

        //Set up box of balls
        int numBoxes = goodBallTypeAry.length;
        int i;
        for (i = 0; i< goodBallTypeAry.length; i++){
            ballBox = new BallBox((int)i*(screenWidth/ numBoxes),screenHeight-200,screenWidth/ numBoxes,200, goodBallTypeAry[i]);
            BallBasic bbb  = CreateBallBasic(goodBallTypeAry[i]);
            bbb.inPlay=false;
            bbb.x = bbb.x + ballBox.width/2 - bbb.width/2;
            bbb.y = screenHeight - ballBox.height;
            ballBasicsToUpdate.add(bbb);
            ballBasicsInBox.add(bbb);
            ballBoxArrayList.add(ballBox);
        }

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while (retry){
            try{
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder){
        if(thread.getState() == Thread.State.NEW){
            thread.setRunning(true);
            thread.start();
        }else{

            if (thread.getState() == Thread.State.TERMINATED){
                thread = new MainThread(this.getHolder(),this);
                thread.setRunning(true);
                thread.start();  // Start a new thread
            }
        }

    }


    public void update(){
        for (Iterator<BallBadExplosion> iterator = ballBadExplosionsToUpdate.iterator();iterator.hasNext();){
            BallBadExplosion ballBadExplosion = iterator.next();
            ballBadExplosion.update();
            if (ballBadExplosion.done){
                iterator.remove();
                ballBadExplosion = null;
            }
        }
       for (Iterator<BallBasic> iterator = ballBasicsToUpdate.iterator(); iterator.hasNext();){
           BallBasic ballBasic = iterator.next();
           if (ballBasicsInBox.contains(ballBasic)){
               ballBasic.RegenerationCounter--;  //Decrement regeneration time so we can eventually regenerate balls to throw
           }
           ballBasic.hitSides(screenWidth);
           ballBasic.update();
           if ((ballBasic.y+ballBasic.width)<0){
               iterator.remove();
               ballBasic = null;
           }
       }
        for (Iterator<BallBad> iterator = ballBadsToUpdate.iterator(); iterator.hasNext();){
            BallBad ballBad = iterator.next();
            ballBad.hitSides(screenWidth);
            ballBad.update();
            if (isCollisionDetected(ballBad,castle)){
                DoDamageToBase(ballBad);
                BallBadExplosion ballBadExplosion = ExplodeBallBad(ballBad);
                ballBadExplosionsToUpdate.add(ballBadExplosion);
                iterator.remove();
                ballBad = null;
            } else if(ballBad.y>screenHeight-300){
                BallBadExplosion ballBadExplosion = ExplodeBallBad(ballBad);
                ballBadExplosionsToUpdate.add(ballBadExplosion);
                iterator.remove();
                ballBad = null;
            }
        }
        if (needToGenerateBadGuy && BadGuysLeft!=0){
            BallBad ballBad = CreateBallBad(POACHER);
            ballBadsToUpdate.add(ballBad);
            BadGuysLeft--;
            needToGenerateBadGuy = false;
        }
        CheckGoodBadCollisions();
        healthBar.update(castleHitPoints);
    }

    @Override
    public SurfaceHolder getHolder() {
        return super.getHolder();
    }

    @Override
    public void draw(Canvas canvas){
        final float scaleFactorX = getScaleX();
        final float scaleFactorY = getScaleY();
        if (canvas  != null){
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            castle.draw(canvas);

            //Syncrhonize ballBasicsToUpdate, because the user's onTouch
            //could modify this and we have no control over when it's accessed.
            //Prevents "ConcurrentModificationExeption".
            synchronized (ballBasicsToUpdate) {
                for (BallBasic ballBasic : (Iterable<BallBasic>) ballBasicsToUpdate) {
                    if (ballBasicsInBox.contains(ballBasic)){
                        if (ballBasic.RegenerationCounter <=0){
                            ballBasic.draw(canvas);
                        }
                    }else{
                        ballBasic.draw(canvas);
                    }
                }
            }
            for (BallBad ballBad : (Iterable<BallBad>) ballBadsToUpdate) {
                ballBad.draw(canvas);
            }
            for (BallBadExplosion ballBadExplosion: (Iterable<BallBadExplosion>) ballBadExplosionsToUpdate){
                ballBadExplosion.draw(canvas);
            }
            healthBar.draw(canvas);
        }
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;

    }

    public BallBasic CreateBallBasic(GoodBallType goodBallType){
        switch (goodBallType){
            case PAWN:
                if (pawnRes==null){
                    pawnRes = new Bitmap[5];
                    pawnRes[0] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_basic));
                    pawnRes[1] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_basic1));
                    pawnRes[2] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_basic2));
                    pawnRes[3] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_basic3));
                    pawnRes[4] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_basic4));
                }
                return new BallBasic(pawnRes,pawnRes[0].getWidth(),pawnRes[0].getHeight(),5,PAWN);
        }

        //We'll never actually get to this line but it's included to
        //suppress compile errors
        return new BallBasic(pawnRes,50,50,5,PAWN);
    }
    public BallBad CreateBallBad(BadBallType badBallType){
        BallBad bb;
        switch (badBallType){
            case POACHER:
                if (poacherRes==null){
                    poacherRes = new Bitmap[5];
                    poacherRes[0] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad));
                    poacherRes[1] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad1));
                    poacherRes[2] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad2));
                    poacherRes[3] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad3));
                    poacherRes[4] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad4));
                    bb = new BallBad(poacherRes,poacherRes[0].getWidth(),poacherRes[0].getHeight(),5,badBallType,screenWidth);
                    return bb;
                }

        }
        //We'll never actually get to this line but it's included to
        //suppress compile errors
        return new BallBad(poacherRes,60,60,5,badBallType,screenWidth);
    }
    private void CheckGoodBadCollisions(){
        BallBad ballBad;
        BallBasic ballBasic;
        for (Iterator<BallBasic> iterator = ballBasicsToUpdate.iterator(); iterator.hasNext();){
            ballBasic = iterator.next();
            if (ballBasic!=ballBasicSelected && ballBasic.inPlay){
                for (Iterator<BallBad> iteratorBad = ballBadsToUpdate.iterator(); iteratorBad.hasNext();){
                    ballBad = iteratorBad.next();
                    ballBasic.CheckGoodBadCollision(ballBad);
                    if (ballBad.dead){
                        iteratorBad.remove();
                        BallBadExplosion ballBadExplosion = ExplodeBallBad(ballBad);
                        ballBadExplosionsToUpdate.add(ballBadExplosion);
                        ballBad = null;
                    }
                    if (ballBasic.dead) break;
                }
            }
            if (ballBasic.dead){
                iterator.remove();
                ballBasic = null;
            }
        }
    }
    private void DoDamageToBase(BallBad ballBad){
        int x = ballBad.x;
        if (x > CASTLE_LEFT && x < CASTLE_RIGHT){
            castleHitPoints = castleHitPoints-ballBad.firePower;
        }
    }
    private BallBadExplosion ExplodeBallBad(BallBad ballBad){
        BallBadExplosion explosion;
        switch (ballBad.badBallType){
            case POACHER:
                if (poacherExpRes == null){
                    poacherExpRes = new Bitmap[11];
                    poacherExpRes[0] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad_exp));
                    poacherExpRes[1] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad_exp2));
                    poacherExpRes[2] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad_exp3));
                    poacherExpRes[3] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad_exp4));
                    poacherExpRes[4] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad_exp5));
                    poacherExpRes[5] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad_exp6));
                    poacherExpRes[6] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad_exp7));
                    poacherExpRes[7] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad_exp8));
                    poacherExpRes[8] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad_exp9));
                    poacherExpRes[9] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad_exp10));
                    poacherExpRes[10] = drawableToBitmap(getResources().getDrawable(R.drawable.ball_bad_exp11));
                }
                explosion = new BallBadExplosion(poacherExpRes,poacherExpRes[0].getWidth(),poacherExpRes[0].getHeight(),11);
                explosion.x = ballBad.x - (explosion.width-ballBad.width)/2;
                explosion.y = ballBad.y - (explosion.height-ballBad.height)/2;
                return explosion;
        }
        return new BallBadExplosion(poacherExpRes,100,100,11);
    }

    public void LoseGame(){
        boolean needsToAdd=true;
        Stats statsFound = new Stats(Level);
        for (Stats stats : statsArrayList){
            if (stats.Level==Level){
                statsFound=stats;
                needsToAdd=false;
                break;
            }
        }
        statsFound.Losses++;
        if (needsToAdd){
            statsArrayList.add(statsFound);
        }

        storeStatsArrayList();
    }
    public void WinGame(){
        Stats statsFound = statsArrayList.get(Level-1);
        statsFound.HasWon=true;
        statsFound.Wins++;
        if (Score > statsFound.HighScore){
            statsFound.HighScore=Score;
        }

        storeStatsArrayList();
    }

    public boolean isCollisionDetected(BallBad sprite1, Castle sprite2) {
        Rect bounds1 = sprite1.getRectangle();
        Rect bounds2 = sprite2.getRectangle();

        if (Rect.intersects(bounds1, bounds2)) {
            Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
            for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                    int sprite1Pixel = getBitmapPixelBallBad(sprite1, i, j);
                    int sprite2Pixel = getBitmapPixelCastle(sprite2, i, j);
                    if (isFilled(sprite1Pixel) && isFilled(sprite2Pixel)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int getBitmapPixelBallBad(BallBad sprite, int i, int j) {
        return sprite.animation.getImage().getPixel(i - (int) sprite.x, j - (int) sprite.y);
    }

    private int getBitmapPixelCastle(Castle sprite, int i, int j){
        return sprite.image.getPixel(i - (int) sprite.x, j - (int) sprite.y);
    }

    private Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = Math.max(rect1.left, rect2.left);
        int top = Math.max(rect1.top, rect2.top);
        int right = Math.min(rect1.right, rect2.right);
        int bottom = Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }

    private boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
    }

    private void setStatsArrayList(){
        try {
            String filePath = getContext().getFilesDir().getPath().toString() + "/StatsArrayList";
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                ObjectInputStream input = new ObjectInputStream(fileInputStream);
                try {
                    statsArrayList = (ArrayList<Stats>) input.readObject();
                    System.out.println(statsArrayList.size());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void storeStatsArrayList(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getContext().getFilesDir().getPath().toString() + "/StatsArrayList");
            try {
                ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);
                output.writeObject(statsArrayList);
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
