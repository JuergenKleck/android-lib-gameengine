package com.juergenkleck.android.gameengine.engine;


/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public interface OnlineGameEngine extends GameEngine {

    // multi player mode
    void updateMaster();

    void updatePlayerState(int rank);

    void updateLevel(int level);

    void updateSync();

    void updateStart();

    // old values
    void updateFinish(int score);

    void playerForfeit();

    void updatePing();

    void updateScore(int score);
}
