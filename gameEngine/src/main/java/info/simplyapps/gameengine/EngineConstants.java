package info.simplyapps.gameengine;

import info.simplyapps.gameengine.system.GameValues;

public final class EngineConstants {

    public static final String CONFIG_MUSIC = "music";
    public static final String CONFIG_DIFFICULTY = "difficulty";

    public static final String DEFAULT_CONFIG_MUSIC = Boolean.FALSE.toString();
    public static final String DEFAULT_CONFIG_DIFFICULTY = Integer.toString(GameValues.DIFFICULTY_EASY);

    public static final int ACTION_NONE = -1;
    public static final int ACTION_QUIT = 0;
    public static final int ACTION_START = 1;
    public static final int ACTION_CONTINUE = 2;
    public static final int ACTION_OPTIONS = 3;

    public static final int spaceLR = 10;
    public static final int spaceTB = 8;
    public static final int spaceTBWide = 48;

    public static final float CHAR_SPACING = 0.35f;

    public final class GameProperties {
        public static final String RENDERING_SYSTEM = "renderingSystem";
        public static final String SCREEN_SCALE = "screenScale";
        public static final String LEVEL = "level";
        public static final String SPACE_LR = "scaleLR";
        public static final String SPACE_TB = "scaleTB";
    }

    public final class PathFinding {

        public static final int TERRAIN_NONE = -1;

        /**
         * Indicate terrain type at a given location
         */
        public static final int TERRAIN_GRASS = 0;
        public static final int TERRAIN_WATER = 1;
        public static final int TERRAIN_DESERT = 2;
        public static final int TERRAIN_FOREST = 3;
        public static final int TERRAIN_STONES = 4;

        public static final int UNIT_HERO = 50;
        public static final int UNIT_HUNTER = 51;

        public static final int OBJECT_FOOD = 100;
        public static final int OBJECT_TUNNEL = 101;

        public static final int STATICS_FOREST = 200;
        public static final int STATICS_POOL = 201;
        public static final int STATICS_STONES = 202;
        public static final int STATICS_PALM = 203;
        public static final int STATICS_WATEREND = 204;
        public static final int STATICS_WATERMIDDLE = 205;

    }

}
