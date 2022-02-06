package com.juergenkleck.android.gameengine.engine;

import com.juergenkleck.android.gameengine.system.BasicGame;
import com.juergenkleck.android.gameengine.system.GameState;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public interface GameEngine extends BasicEngine {

    BasicGame getGame();

    void setMode(GameState mode);

    GameState getMode();

    void setBonus(boolean b);

}
