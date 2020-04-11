package info.simplyapps.gameengine;

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
