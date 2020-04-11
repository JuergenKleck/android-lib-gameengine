package info.simplyapps.gameengine.system;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;


public class GameValues {

    public static final long threadDelay = 10l;

    public static final int BANNER_HEIGHT = 70;

    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_MEDIUM = 3;
    public static final int DIFFICULTY_HARD = 5;

    public static final ColorFilter cFilterWhite = new LightingColorFilter(Color.parseColor("#ffffff"), 255);
    public static final ColorFilter cFilterBlack = new LightingColorFilter(Color.parseColor("#000000"), 255);
    public static final ColorFilter cFilterBlue = new LightingColorFilter(Color.parseColor("#4dcaff"), 255);
    public static final ColorFilter cFilterRed = new LightingColorFilter(Color.parseColor("#fe5858"), 1);
    public static final ColorFilter cFilterGreen = new LightingColorFilter(Color.parseColor("#67fe44"), 1);
    public static final ColorFilter cFilterYellow = new LightingColorFilter(Color.parseColor("#fdff63"), 1);
    public static final ColorFilter cFilterOrange = new LightingColorFilter(Color.parseColor("#ffa735"), 1);
    public static final ColorFilter cFilterDarkRed = new LightingColorFilter(Color.parseColor("#cb2525"), 1);

    public static final int blackColor = Color.parseColor("#ff000000");
    public static final int whiteColor = Color.parseColor("#ffffffff");
    public static final int greenPoolColor = Color.parseColor("#ff1d9c1d");

}
