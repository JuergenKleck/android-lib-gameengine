package info.simplyapps.gameengine.engine;


public interface OnlineGameEngine extends GameEngine {

    // multi player mode
    void updateMaster();

    void updatePlayerState(int rank);

    void updateLevel(int level);

    void updateSync();

    void updateStart();

    // old values
    void updateFinish(int score);

    void playerForfeit();

    void updatePing();

    void updateScore(int score);
}
