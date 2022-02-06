package com.juergenkleck.android.gameengine.system;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class GameRound {

    // the game round number
    public int round;
    // the playing level
    public int level;
    // the round time (e.g. 5 minutes) purchases are added here
    public long time;
    public long fpsdelay;
    public int background;
    public boolean night;

    // multi player add-ins
    public boolean levelChosen;
    public boolean opponentReady;
    public boolean startSent;
    public boolean scoreSent;
    public boolean opponentFinished;

    public GameRound(int round, long time, int background) {
        this.round = round;
        this.time = time;
        this.background = background;
        level = -1;
    }


}
