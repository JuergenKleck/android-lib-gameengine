package com.juergenkleck.android.gameengine.rendering.kits;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.juergenkleck.android.gameengine.rendering.objects.Graphic;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class Renderkit {

    public static Graphic loadButtonGraphic(Resources resources, int drawable, int boundLeft, int boundTop, int clickAction) {
        Drawable background = resources.getDrawable(drawable);
        Rect r = new Rect(boundLeft, boundTop, boundLeft + background.getIntrinsicWidth(), boundTop + background.getIntrinsicHeight());
        background.setBounds(r);
        return new Graphic(background, clickAction);
    }

    public static Graphic loadGraphic(Resources resources, int drawable, int boundLeft, int boundTop) {
        Drawable background = resources.getDrawable(drawable);
        Rect r = new Rect(boundLeft, boundTop, boundLeft + background.getIntrinsicWidth(), boundTop + background.getIntrinsicHeight());
        background.setBounds(r);
        return new Graphic(background);
    }

}
