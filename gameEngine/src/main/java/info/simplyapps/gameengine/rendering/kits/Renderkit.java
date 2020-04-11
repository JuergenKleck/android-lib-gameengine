package info.simplyapps.gameengine.rendering.kits;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import info.simplyapps.gameengine.rendering.objects.Graphic;

public class Renderkit {

    public static Graphic loadButtonGraphic(Resources resources, int drawable, int boundLeft, int boundTop, int clickAction) {
        Drawable background = resources.getDrawable(drawable);
        Rect r = new Rect(boundLeft, boundTop, boundLeft + background.getIntrinsicWidth(), boundTop + background.getIntrinsicHeight());
        background.setBounds(r);
        return new Graphic(background, clickAction);
    }

    public static Graphic loadGraphic(Resources resources, int drawable, int boundLeft, int boundTop) {
        Drawable background = resources.getDrawable(drawable);
        Rect r = new Rect(boundLeft, boundTop, boundLeft + background.getIntrinsicWidth(), boundTop + background.getIntrinsicHeight());
        background.setBounds(r);
        return new Graphic(background);
    }

}
