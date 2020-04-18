package info.simplyapps.gameengine.screens;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import info.simplyapps.gameengine.rendering.GenericViewTemplate;

public abstract class GenericScreenTemplate extends AppCompatActivity {

    protected GenericViewTemplate mScreenView;

    public abstract String getViewKey();

    protected abstract int getScreenLayout();

    protected abstract int getViewLayoutId();

    public abstract void doUpdateChecks();

    public abstract void prepareStorage(Context context);

    public abstract void activateGame();

    public abstract Properties getEngineProperties();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Store the game state
        super.onSaveInstanceState(outState);
        if (mScreenView != null) {
            outState.putBundle(getViewKey(), mScreenView.saveState());
        }
    }

    public GenericViewTemplate getScreenView() {
        return mScreenView;
    }

    public abstract void actionHandler(int action);

}