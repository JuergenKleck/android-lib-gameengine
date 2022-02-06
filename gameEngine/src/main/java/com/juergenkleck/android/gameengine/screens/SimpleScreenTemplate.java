package com.juergenkleck.android.gameengine.screens;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.juergenkleck.android.gameengine.EngineConstants;
import com.juergenkleck.android.gameengine.rendering.GenericViewTemplate;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public abstract class SimpleScreenTemplate extends GenericScreenTemplate {

    public abstract void actionAdditionalAction(int action);

    public abstract void prepareStorage(Context context);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepareStorage(getApplicationContext());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        doUpdateChecks();

        setContentView(getScreenLayout());

        mScreenView = (GenericViewTemplate) findViewById(getViewLayoutId());

        if (savedInstanceState == null) {
        } else {
            Bundle map = savedInstanceState.getBundle(getViewKey());
            if (map != null && mScreenView != null) {
                mScreenView.restoreState(map);
            }
        }
    }

    public void actionHandler(int action) {
        switch (action) {
            case EngineConstants.ACTION_NONE:
                break;
            default:
                actionAdditionalAction(action);
                break;
        }
    }

}