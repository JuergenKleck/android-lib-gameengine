package com.juergenkleck.android.gameengine;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class MultiPlayerConstants {

    public static final String MSG_MASTER = "M";
    public static final String MSG_PLAYERSTATE = "T";
    public static final String MSG_LEVEL = "L";
    public static final String MSG_SYNC = "Z";
    public static final String MSG_INIT = "I";

    public static final String MSG_PING = "N";
    public static final String MSG_START = "G";
    public static final String MSG_FINISH = "F";
    public static final String MSG_FORFEIT = "X";
    public static final String MSG_SCORE = "S";

    public class JSON {
        public static final String VALUE = "v";

        public static final String TYPE = "t";
        public static final String MIGRATION = "g";
        public static final String SYNC_STATE = "s";
        public static final String TOKEN = "TOKEN";
        public static final String RANK = "r";
        public static final String MASTER = "m";
        public static final String SCORE = "c";
        public static final String LEVEL = "l";
    }

}
