package com.juergenkleck.android.gameengine.rendering.kits;

import com.juergenkleck.android.gameengine.rendering.objects.Animation;
import com.juergenkleck.android.gameengine.rendering.objects.AnimationFrame;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class AnimationKit {

    public static void addAnimation(Animation animation, int gReference, int delay) {
        AnimationFrame frame = new AnimationFrame();
        frame.delay = delay;
        frame.gReference = gReference;
        animation.frames.add(frame);
    }

}
