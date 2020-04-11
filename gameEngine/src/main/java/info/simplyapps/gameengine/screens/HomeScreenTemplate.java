package info.simplyapps.gameengine.screens;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import info.simplyapps.gameengine.EngineConstants;
import info.simplyapps.gameengine.rendering.GenericViewTemplate;

public abstract class HomeScreenTemplate extends GenericScreenTemplate {

    public static boolean mGameModeContinue = false;

    public abstract void actionNewGame();

    public abstract void actionContinueGame();

    public abstract void actionOptions();

    public abstract void actionQuit();

    public abstract void actionAdditionalAction(int action);

    public abstract void prepareStorage(Context context);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepareStorage(getApplicationContext());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        doUpdateChecks();

        setContentView(getScreenLayout());

        mScreenView = (GenericViewTemplate) findViewById(getViewLayoutId());

        if (savedInstanceState == null) {
        } else {
            Bundle map = savedInstanceState.getBundle(getViewKey());
            if (map != null) {
                mScreenView.restoreState(map);
            }
        }
    }

    public void actionHandler(int action) {
        switch (action) {
            case EngineConstants.ACTION_NONE:
                break;
            case EngineConstants.ACTION_START:
                actionNewGame();
                break;
            case EngineConstants.ACTION_CONTINUE:
                actionContinueGame();
                break;
            case EngineConstants.ACTION_OPTIONS:
                actionOptions();
                break;
            case EngineConstants.ACTION_QUIT:
                actionQuit();
                break;
            default:
                actionAdditionalAction(action);
                break;
        }
    }

}