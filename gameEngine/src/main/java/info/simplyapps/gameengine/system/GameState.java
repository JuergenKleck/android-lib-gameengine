package info.simplyapps.gameengine.system;

public enum GameState {

    // NEW STATES
    NONE // must initialize something first
    , INIT // startup the gameround
    , READY // ready for gameplay, can resume into here
    , PLAY // play mode where the game runs
    , END // play ended, collect score and other stats, maybe init next round
    ;
}
