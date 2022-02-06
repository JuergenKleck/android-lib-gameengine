package com.juergenkleck.android.gameengine.pathfinding;


/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class Step {

    /**
     * The x coordinate at the given step
     */
    public int x;
    /**
     * The y coordinate at the given step
     */
    public int y;

    /**
     * Create a new step
     *
     * @param x The x coordinate of the new step
     * @param y The y coordinate of the new step
     */
    public Step(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        return x * y;
    }

    /**
     * @see Object#equals(Object)
     */
    public boolean equals(Object other) {
        if (other instanceof Step) {
            Step o = (Step) other;

            return (o.x == x) && (o.y == y);
        }

        return false;
    }
}
