package com.juergenkleck.android.gameengine;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class GameContainer {

    public GameContainer() {
        super();
    }

    private static GameContainer self;

    public static GameContainer getInstance() {
        if (self == null) {
            self = new GameContainer();
        }
        return self;
    }
}
