package com.juergenkleck.android.gameengine.rendering;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import com.juergenkleck.android.gameengine.engine.BasicEngine;
import com.juergenkleck.android.gameengine.system.GameValues;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public abstract class GenericViewTemplate extends SurfaceView implements Callback {

    // Attribute names
    private static final String ATTR_SCREENSCALE_VALUE = "screenScale";
    private static final String ATTR_ORIENTATION_VALUE = "orientation";
    // Default values for defaults
    private static final int SCREENSCALE_CURRENT_VALUE = GameValues.BANNER_HEIGHT;
    private static final boolean ORIENTATION_CURRENT_VALUE = false;
    // Real defaults
    private final int mScreenScaleValue;
    public final boolean mOrientationValue;

    // we assume the display won't change - the orientation was fixed
    final int screenWidth;
    final int screenHeight;

    /**
     * The thread that actually draws the animation
     */
    private boolean visible = false;
    private final Handler mHandler = new Handler();
    private BasicEngine mEngine = null;

    /**
     * Create the game engine instantiation
     *
     * @param context
     */
    protected abstract void createThread(Context context);

    /**
     * Is dragging of buttons enabled
     *
     * @return
     */
    protected abstract boolean isDragable();

    /**
     * Get the namespace
     *
     * @return
     */
    protected abstract String getNameSpace();

    private final Runnable drawRunner = new Runnable() {
        public void run() {
            mEngine.setRunning(true);
            mEngine.run(getHolder());
            mHandler.removeCallbacks(drawRunner);
            if (visible) {
                mHandler.post(drawRunner);
            }
        }
    };

    public GenericViewTemplate(Context context) {
        super(context);
        // Read parameters from attributes
        mScreenScaleValue = SCREENSCALE_CURRENT_VALUE;
        mOrientationValue = ORIENTATION_CURRENT_VALUE;

        screenWidth = getResources().getDisplayMetrics().widthPixels / (mOrientationValue ? 2 : 1);
        screenHeight = getResources().getDisplayMetrics().heightPixels - mScreenScaleValue;

        initGameView(context);
    }

    public GenericViewTemplate(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // Read parameters from attributes
        mScreenScaleValue = attrs.getAttributeIntValue(getNameSpace(), ATTR_SCREENSCALE_VALUE, SCREENSCALE_CURRENT_VALUE);
        mOrientationValue = attrs.getAttributeBooleanValue(getNameSpace(), ATTR_ORIENTATION_VALUE, ORIENTATION_CURRENT_VALUE);

        screenWidth = getResources().getDisplayMetrics().widthPixels / (mOrientationValue ? 2 : 1);
        screenHeight = getResources().getDisplayMetrics().heightPixels - mScreenScaleValue;
        initGameView(context);
    }

    public GenericViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Read parameters from attributes
        mScreenScaleValue = attrs.getAttributeIntValue(getNameSpace(), ATTR_SCREENSCALE_VALUE, SCREENSCALE_CURRENT_VALUE);
        mOrientationValue = attrs.getAttributeBooleanValue(getNameSpace(), ATTR_ORIENTATION_VALUE, ORIENTATION_CURRENT_VALUE);

        screenWidth = getResources().getDisplayMetrics().widthPixels / (mOrientationValue ? 2 : 1);
        screenHeight = getResources().getDisplayMetrics().heightPixels - mScreenScaleValue;
        initGameView(context);
    }

    private void initGameView(Context context) {

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        if (isDragable()) {
            // ! mandatory for dragging
            setOnClickListener(null);
        }

        createThread(context);

        setFocusable(true); // make sure we get key events
    }

    /**
     * Save game state so that the user does not lose anything
     * if the game process is killed while we are in the
     * background.
     *
     * @return a Bundle with this view's state
     */
    public Bundle saveState() {
        Bundle map = new Bundle();


        return map;
    }

    /**
     * Restore game state if our process is being relaunched
     *
     * @param icicle a Bundle containing the game state
     */
    public void restoreState(Bundle icicle) {

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // this won't occur
    }

    /**
     * Called when creating the screen or when restoring from elsewhere
     */
    public void surfaceCreated(SurfaceHolder holder) {
        // start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
        visible = true;
        mEngine.setRunning(true);
        mEngine.restoreGameState();
        mEngine.setRunning(true);
        mEngine.doStart();
    }

    /**
     * Always called when leaving the game screen
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        mHandler.removeCallbacks(drawRunner);
        mEngine.setRunning(false);
        mEngine.saveGameState();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mEngine.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * Fetches the animation thread corresponding to this View.
     *
     * @return the animation thread
     */
    public BasicEngine getBasicEngine() {
        return mEngine;
    }

    public void setBasicEngine(BasicEngine engine) {
        this.mEngine = engine;
    }

    /**
     * Standard window-focus override. Notice focus lost so we can pause on
     * focus lost. e.g. user switches to take a call.
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) mEngine.pause();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (!this.visible && View.VISIBLE == visibility) {
            mEngine.unpause();
        }
        this.visible = View.VISIBLE == visibility;
        if (visible) {
            mHandler.post(drawRunner);
        } else {
            mEngine.pause();
            mHandler.removeCallbacks(drawRunner);
        }
        super.onWindowVisibilityChanged(visibility);
    }

    public int getScreenScaleValue() {
        return mScreenScaleValue;
    }


    public void changeEngine(BasicEngine engine) {
        visible = false;
        if (mEngine != null) {
            // exit old engine
            mEngine.exit();
            mHandler.removeCallbacks(drawRunner);
        }
        mEngine = engine;
        // launch
        mEngine.restoreGameState();
        mEngine.setRunning(true);
        mEngine.doStart();
        visible = true;
        mHandler.post(drawRunner);
    }

}