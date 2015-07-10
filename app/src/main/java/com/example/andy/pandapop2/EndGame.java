package com.example.andy.pandapop2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class EndGame extends ActionBarActivity {

    private Intent resultIntent;
    private Bundle b;
    private int Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultIntent = new Intent();
        b = getIntent().getExtras();
        int wonGame = b.getInt("WonGame");
        Level = b.getInt("Level");
        setContentView(R.layout.activity_end_game);
        if (wonGame==1) {
            TextView text = (TextView) findViewById(R.id.textView);
            text.setText(R.string.str_congratulations);
        } else {
            TextView text = (TextView) findViewById(R.id.textView);
            text.setText(R.string.str_sorry_you_lost);
            text.setTextColor(Color.RED);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_end_game, menu);
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

    public void onMainMenuClick(View view){
        finish();
    }
    public void onRestartClick(View view){
        finish();
        Intent intent= new Intent(EndGame.this,Game.class);
        Bundle b = new Bundle();
        b.putInt("level", Level);
        intent.putExtras(b);
        startActivity(intent);
    }

}
