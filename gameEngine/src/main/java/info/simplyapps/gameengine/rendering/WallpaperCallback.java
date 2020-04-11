package info.simplyapps.gameengine.rendering;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.MotionEvent;
import info.simplyapps.gameengine.rendering.data.ValueContainer;

/**
 * Interface definition for the InternalEngine into the GenericWallpaper
 *
 * @author juergen
 */
public interface WallpaperCallback {

    void callbackTouchEvent(MotionEvent event);

    void callbackInitSharedPrefs(ValueContainer vc, SharedPreferences prefs, Editor editor);

    void callbackOnSharedPreferenceChanged(ValueContainer vc, SharedPreferences prefs, String key);

    Context callbackGetContext();

}
