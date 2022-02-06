package com.juergenkleck.android.gameengine.screens;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.juergenkleck.android.gameengine.engine.GameEngine;
import com.juergenkleck.android.gameengine.rendering.GenericViewTemplate;
import com.juergenkleck.android.gameengine.system.GameState;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public abstract class GameScreenTemplate extends GenericScreenTemplate {

    public abstract boolean isContinueGame();

    public abstract void actionAdditionalAction(int action);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // safety call
        prepareStorage(getApplicationContext());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(getScreenLayout());

        mScreenView = (GenericViewTemplate) findViewById(getViewLayoutId());

        if (savedInstanceState == null && !isContinueGame()) {
            // we were just launched: set up a new game
            getGameEngine().setMode(GameState.NONE);
        } else {
            // we are being restored: resume a previous game
            getGameEngine().restoreState(savedInstanceState);
        }

        getGameEngine().doStart();
    }

    /**
     * Invoked when the Activity loses user focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        getGameEngine().pause(); // pause game when Activity pauses
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGameEngine().unpause();//resume();
    }

    /**
     * Notification that something is about to happen, to give the Activity a
     * chance to save state. (Done via HOME button or another activity popping up
     *
     * @param outState a Bundle into which this Activity should save its state
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
        getGameEngine().saveState(outState);
    }

    /**
     * Get the game engine instance
     *
     * @return
     */
    public GameEngine getGameEngine() {
        return GameEngine.class.cast(mScreenView.getBasicEngine());
    }


    public void actionHandler(int action) {
        actionAdditionalAction(action);
    }

}
