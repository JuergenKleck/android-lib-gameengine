package com.juergenkleck.android.gameengine.system;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class OnlineGame extends Game {

    // multi player add-ins
    public boolean masterSet;
    public boolean playerPublished;

    public OnlineGame(GameRound[] rounds) {
        super(rounds);
    }

}
