package info.simplyapps.gameengine.system;

public class OnlineGame extends Game {

    // multi player add-ins
    public boolean masterSet;
    public boolean playerPublished;

    public OnlineGame(GameRound[] rounds) {
        super(rounds);
    }

}
