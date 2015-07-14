package com.example.andy.pandapop2;

import java.io.Serializable;

/**
 * Created by Andy on 7/13/2015.
 */
public class Stats implements Serializable {
    private static final long serialVersionUID = 8309080721495266420L;
    public int Level;
    public long Wins=0;
    public long Losses=0;
    public long HighScore=0;
    public boolean HasWon=false;
    public Stats(int level){
        this.Level=level;
        this.Wins=0;
        this.Losses=0;
        this.HighScore=0;
    }
}
