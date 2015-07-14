package com.example.andy.pandapop2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private ArrayList<Stats> statsArrayList;
    private int MaxLevel=1;
    private NumberPicker LevelPicker;
    public static final int LAST_LEVEL = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (setStatsArrayList()==false){
            this.finish();
        }
        Stats lastLevelStats;
        lastLevelStats = statsArrayList.get(statsArrayList.size()-1);
        if (lastLevelStats.HasWon && lastLevelStats.Level != LAST_LEVEL){
            MaxLevel = statsArrayList.size()+1;
        }else{
            MaxLevel = statsArrayList.size();
        }
        if (MaxLevel <= 1){
            MaxLevel=1;
        }
        LevelPicker = (NumberPicker) findViewById(R.id.levelPicker);
        LevelPicker.setMinValue(1);
        LevelPicker.setMaxValue(MaxLevel);
        System.out.println(getApplicationContext().getFilesDir().getPath().toString() + "StatsArrayList");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void buttonOnClick(View view){
        int level=((NumberPicker) findViewById(R.id.levelPicker)).getValue();

        //Add new Stats to list if it isn't there
        Stats stats = new Stats(level);
        if (statsArrayList.size()<level){
            statsArrayList.add(level-1,stats);
            storeStatsArrayList();
        }

        Intent intent= new Intent(MainActivity.this,Game.class);
        Bundle b = new Bundle();
        b.putInt("level", ((NumberPicker) findViewById(R.id.levelPicker)).getValue());
        intent.putExtras(b);
        startActivity(intent);
    }

    private boolean setStatsArrayList(){
        boolean success=false;
        try {
            File file = new File(getApplicationContext().getFilesDir().getPath().toString() + "/StatsArrayList");
            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stats stats = new Stats(1);
                statsArrayList = new ArrayList<>();
                statsArrayList.add(0,stats);
                storeStatsArrayList();
                return true;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                ObjectInputStream input = new ObjectInputStream(fileInputStream);
                try {
                    statsArrayList = (ArrayList<Stats>) input.readObject();
                    input.close();
                    success=true;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return success;
    }

    private void storeStatsArrayList(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getApplicationContext().getFilesDir().getPath().toString() + "/StatsArrayList");
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
