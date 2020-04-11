package info.simplyapps.gameengine.rendering.objects;

import android.graphics.drawable.Drawable;

/**
 * Class containing the bitmap for a graphic to display in the screen
 *
 * @author juergen
 */
public class Graphic {

    public int clickAction;

    public Drawable image;

    public Graphic(Drawable image) {
        this.image = image;
    }

    public Graphic(Drawable image, int action) {
        this.image = image;
        this.clickAction = action;
    }

}
