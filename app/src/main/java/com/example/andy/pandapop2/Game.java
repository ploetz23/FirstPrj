package com.example.andy.pandapop2;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.andy.pandapop2.R;

import java.net.ContentHandler;
import java.util.ArrayList;

import static com.example.andy.pandapop2.Game.GoodBallType.PAWN;

public class Game extends Activity implements View.OnTouchListener {

    public GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoodBallType[] ballTypeAry = new GoodBallType[1];
        BadBallType[] badBallTypes = new BadBallType[1];
        Bundle b = getIntent().getExtras();
        int level = b.getInt("level");
        //Turn off title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        switch (level){
            case 1: {
                ballTypeAry= new GoodBallType[1];
                ballTypeAry[0] = PAWN;
            }
        }
        gamePanel = new GamePanel(this, ballTypeAry, badBallTypes, level);
        setContentView(gamePanel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }
    protected void onPause() {
        super.onPause();
        gamePanel.surfaceDestroyed(gamePanel.getHolder());
        gamePanel = null;
        finish();
    }

    public enum GoodBallType{
        PAWN
    }
    public enum BadBallType{
        POACHER
    }
}
