package com.juergenkleck.android.gameengine.system;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public enum GameSubState {

    // basic sub states
    NONE // normal gameplay - rely on GameState selection
    , PAUSE // gameplay paused

    // multi player substates
    , SYNCING // synchronizing with other player
    , TIMEOUT, SYNC_COMPLETE // synchronizing with other player
    , WAITING   // waiting for other player to finish his move
    , CHOOSE_LEVEL // select the level for the current game
    , SCORE // multi player - post-end round
    ;
}
