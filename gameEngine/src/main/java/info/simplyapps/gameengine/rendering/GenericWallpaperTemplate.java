package info.simplyapps.gameengine.rendering;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import info.simplyapps.gameengine.engine.BasicEngine;
import info.simplyapps.gameengine.rendering.data.ValueContainer;

public abstract class GenericWallpaperTemplate extends WallpaperService implements WallpaperCallback {

    /**
     * The thread that actually draws the animation
     */

    private InternalEngine mWallEngine = null;

    public GenericWallpaperTemplate() {
    }

    // interface implementations
    public void callbackTouchEvent(MotionEvent event) {
        touchEvent(event);
    }

    public void callbackInitSharedPrefs(ValueContainer vc, SharedPreferences prefs, Editor editor) {
        initSharedPrefs(vc, prefs, editor);
    }

    public void callbackOnSharedPreferenceChanged(ValueContainer vc, SharedPreferences prefs, String key) {
        onSharedPreferenceChanged(vc, prefs, key);
    }

    public Context callbackGetContext() {
        return this;
    }

    public abstract void touchEvent(MotionEvent event);

    public abstract void initSharedPrefs(ValueContainer vc, SharedPreferences prefs, Editor editor);

    public abstract void onSharedPreferenceChanged(ValueContainer vc, SharedPreferences prefs, String key);

    public abstract BasicEngine initializeEngine();

    public abstract String getSharedPrefsName();

    public abstract ValueContainer getValueContainer();

    /**
     * Fetches the animation thread corresponding to this View.
     *
     * @return the animation thread
     */
    public BasicEngine getBasicEngine() {
        return mWallEngine.getBasicEngine();
    }

    public void setBasicEngine(BasicEngine engine) {
        mWallEngine.setBasicEngine(engine);
    }

    public void changeEngine(BasicEngine engine) {
        mWallEngine.changeEngine(engine);
    }

    private void checkEngine() {
        if (mWallEngine != null) {
            mWallEngine.shutdownEngine();
            mWallEngine = null;
        }
    }

    private void createEngine() {
        mWallEngine = new InternalEngine(this);
    }

    @Override
    public Engine onCreateEngine() {
        checkEngine();
        createEngine();
        mWallEngine.initialize();
        mWallEngine.setBasicEngine(initializeEngine());
        return mWallEngine;
    }

    class InternalEngine extends Engine
            implements SharedPreferences.OnSharedPreferenceChangeListener {

        private boolean visible = false;
        boolean init = false;
        private SharedPreferences mPreferences;
        private ValueContainer mValueContainer;
        private WallpaperCallback callback;

        private final Handler mHandler = new Handler();
        private BasicEngine mEngine = null;

        private final Runnable drawRunner = new Runnable() {
            public void run() {
                mEngine.setRunning(visible);
                // always get the engine surfaceholder - the other surface holder may be outdated
                // switch off visibility if a failure happens
                visible = mEngine.run(getSurfaceHolder());
                mHandler.removeCallbacks(drawRunner);
                if (visible) {
                    mHandler.post(drawRunner);
                }
            }
        };

        // Constructor
        public InternalEngine(WallpaperCallback wallpaperCallback) {
            super();

            callback = wallpaperCallback;
        }

        public boolean initialize() {
            if (init) {
                return false;
            }
            mValueContainer = getValueContainer();
            mPreferences = callback.callbackGetContext().getSharedPreferences(getSharedPrefsName(), Context.MODE_PRIVATE);
            mPreferences.registerOnSharedPreferenceChangeListener(this);

            // synchronize preferences and value container
            initSharedPrefs(mPreferences);
            onSharedPreferenceChanged(mPreferences, null);

            init = true;
            return true;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                mHandler.post(drawRunner);
            } else {
                mHandler.removeCallbacks(drawRunner);
            }
            super.onVisibilityChanged(visible);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);

            visible = true;
            mEngine.setRunning(true);
            mEngine.restoreGameState();
            mEngine.setRunning(true);
            mEngine.doStart();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            mHandler.removeCallbacks(drawRunner);
            mEngine.setRunning(false);
            mEngine.saveGameState();

            super.onSurfaceDestroyed(holder);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            callback.callbackTouchEvent(event);
            super.onTouchEvent(event);
        }


        private void initSharedPrefs(SharedPreferences prefs) {
            Editor editor = prefs.edit();

            callback.callbackInitSharedPrefs(mValueContainer, prefs, editor);

            editor.commit();
        }

        /**
         * Read the shared preferences into the container
         */
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            callback.callbackOnSharedPreferenceChanged(mValueContainer, prefs, key);
            if (mEngine != null) {
                mEngine.reset();
            }
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

        public void shutdownEngine() {
            if (mEngine != null) {
                mEngine.exit();
                mHandler.removeCallbacks(drawRunner);
            }
        }

        public void changeEngine(BasicEngine engine) {
            visible = false;
            shutdownEngine();
            mEngine = engine;
            // launch
            mEngine.restoreGameState();
            mEngine.setRunning(true);
            mEngine.doStart();
            visible = true;
            mHandler.post(drawRunner);
        }
    }

}