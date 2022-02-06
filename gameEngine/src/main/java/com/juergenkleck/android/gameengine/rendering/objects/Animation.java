package com.juergenkleck.android.gameengine.rendering.objects;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class Animation {

    public boolean once;
    public boolean done;
    // the position on the screen
    public Coord coord;
    // the size on the screen
    public Rect rect;

    public List<AnimationFrame> frames;
    public long frameTime;
    public int frame;
    public boolean ltr;
    public boolean active;

    public Animation(boolean once) {
        this.once = once;
        init();
    }

    public Animation() {
        init();
    }

    public void init() {
        frames = new ArrayList<AnimationFrame>();
        active = true;

        coord = new Coord();
        rect = new Rect();
    }

    public void reset() {
        frameTime = 0;
        frame = 0;
        done = false;
    }

    /**
     * Determine the next frame to display
     *
     * @return frame to render
     */
    public AnimationFrame nextFrame(long time) {
        // initialize first execution for the loop
        if (frameTime == 0) {
            frameTime = time + frames.get(frame).delay;
        }
        // next frame
        if (frameTime <= time && !done) {
            frame++;
            if (frame >= frames.size() && !once) {
                // no repeating if once is set
                frame = 0;
            } else if (frame >= frames.size() && once) {
                frame--;
                done = true;
            }
            frameTime = time + frames.get(frame).delay;
            return frames.get(frame);
        }
        return frames.get(frame);
    }

    public AnimationFrame nextFrame() {
        return frames.get(frame);
    }

}
