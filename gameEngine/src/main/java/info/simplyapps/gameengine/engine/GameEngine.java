package info.simplyapps.gameengine.engine;

import info.simplyapps.gameengine.system.BasicGame;
import info.simplyapps.gameengine.system.GameState;

public interface GameEngine extends BasicEngine {

    BasicGame getGame();

    void setMode(GameState mode);

    GameState getMode();

    void setBonus(boolean b);

}
