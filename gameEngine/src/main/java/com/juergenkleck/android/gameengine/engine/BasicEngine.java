package com.juergenkleck.android.gameengine.engine;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public interface BasicEngine {

    void exit();

    void create();

    void reset();

    void setRunning(boolean b);

    void doStart();

    boolean run(SurfaceHolder newHolder);

    void pause();

    void unpause();

    void saveGameState();

    void restoreGameState();

    Bundle saveState(Bundle map);

    void restoreState(Bundle savedState);

    boolean onTouchEvent(MotionEvent event);

    void actionHandler(int action);

}
