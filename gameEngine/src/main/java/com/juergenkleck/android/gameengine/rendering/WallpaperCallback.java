package com.juergenkleck.android.gameengine.rendering;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.MotionEvent;
import com.juergenkleck.android.gameengine.rendering.data.ValueContainer;

/**
 * Interface definition for the InternalEngine into the GenericWallpaper
 *
 *
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public interface WallpaperCallback {

    void callbackTouchEvent(MotionEvent event);

    void callbackInitSharedPrefs(ValueContainer vc, SharedPreferences prefs, Editor editor);

    void callbackOnSharedPreferenceChanged(ValueContainer vc, SharedPreferences prefs, String key);

    Context callbackGetContext();

}
