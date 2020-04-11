package info.simplyapps.gameengine.engine;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public interface BasicEngine {

    void exit();

    void create();

    void reset();

    void setRunning(boolean b);

    void doStart();

    boolean run(SurfaceHolder newHolder);

    void pause();

    void unpause();

    void saveGameState();

    void restoreGameState();

    Bundle saveState(Bundle map);

    void restoreState(Bundle savedState);

    boolean onTouchEvent(MotionEvent event);

    void actionHandler(int action);

}
