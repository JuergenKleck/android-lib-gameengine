package com.juergenkleck.android.gameengine.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import com.juergenkleck.android.gameengine.EngineConstants;
import com.juergenkleck.android.gameengine.rendering.GenericRendererTemplate;

import java.util.Properties;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class NullRenderer extends GenericRendererTemplate implements BasicEngine {

    Rect mRect;
    long delay = 100l;
    long lastTime = 0l;
    boolean triggered = false;

    public NullRenderer(Context context, Properties p) {
        super(context, p);
    }

    @Override
    public void doStart() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void unpause() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public void doUpdateRenderState() {
        final long time = System.currentTimeMillis();

        if (delay > 0l && lastTime > 0l) {
            delay -= time - lastTime;
        }
        if (delay <= 0l && !triggered) {
            instantActionHandler(EngineConstants.ACTION_NONE, EngineConstants.ACTION_NONE);
            triggered = true;
        }

        lastTime = time;
    }

    @Override
    public void doDrawRenderer(Canvas canvas) {
    }


    @Override
    public void restoreGameState() {
    }

    @Override
    public void saveGameState() {
    }

    @Override
    protected void doInitThread(long time) {
    }

    @Override
    protected boolean logEnabled() {
        return false;
    }

    @Override
    public void reset() {
    }

    @Override
    public float getCharSpacing() {
        return EngineConstants.CHAR_SPACING;
    }

}
