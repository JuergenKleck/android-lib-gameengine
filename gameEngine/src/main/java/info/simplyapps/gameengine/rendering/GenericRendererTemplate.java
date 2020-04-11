package info.simplyapps.gameengine.rendering;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import info.simplyapps.gameengine.EngineConstants;
import info.simplyapps.gameengine.R;
import info.simplyapps.gameengine.RenderingSystem;
import info.simplyapps.gameengine.engine.BasicEngine;
import info.simplyapps.gameengine.rendering.kits.Renderkit;
import info.simplyapps.gameengine.rendering.kits.ScreenKit;
import info.simplyapps.gameengine.rendering.objects.Graphic;
import info.simplyapps.gameengine.screens.GenericScreenTemplate;
import info.simplyapps.gameengine.sprites.ViewSprites;
import info.simplyapps.gameengine.system.GameValues;

import java.util.Properties;

public abstract class GenericRendererTemplate implements BasicEngine {

    private Paint pBg;
    /**
     * Indicate whether the surface has been created & is ready to draw
     */
    private boolean mRun = false;
    private boolean init = false;
    protected int mLevel = 0;

    protected ViewSprites sprites;
    protected Context mContext;

    // we assume the display won't change - the orientation was fixed
    protected final int screenWidth;
    protected final int screenHeight;
    protected final int realScreenHeight;

    protected int activeButton = -1;
    protected int delayedAction = -1;
    protected long delayedActionTimeout = 0;

    protected final RenderingSystem renderingSystem;

    protected Properties gameProperties;

    protected Graphic[] gChar;

    protected int spaceLR = 0;
    protected int spaceTB = 0;

    protected abstract void doInitThread(long time);

    /**
     * Starts the game, setting parameters for the current difficulty.
     */
    public abstract void doStart();

    /**
     * Pauses the physics update & animation.
     */
    public abstract void pause();

    /**
     * Resumes from a pause.
     */
    public abstract void unpause();

    public abstract boolean onTouchEvent(MotionEvent event);

    public abstract void doUpdateRenderState();

    public abstract void doDrawRenderer(Canvas canvas);

    public abstract void restoreGameState();

    public abstract void saveGameState();

    protected abstract boolean logEnabled();

    public abstract float getCharSpacing();

    public GenericRendererTemplate(Context context, Properties properties) {

        final long time = System.currentTimeMillis();

        gameProperties = properties;

        // get handles to some important objects
        mContext = context;

        pBg = new Paint();
        pBg.setColor(GameValues.blackColor);
        pBg.setStyle(Paint.Style.FILL);

        // text graphics
        gChar = initAlphabet();

        renderingSystem = (info.simplyapps.gameengine.RenderingSystem) properties.get(EngineConstants.GameProperties.RENDERING_SYSTEM);

        // we assume the display won't change - the orientation was fixed
        screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        realScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        screenHeight = mContext.getResources().getDisplayMetrics().heightPixels - (Integer) gameProperties.get(EngineConstants.GameProperties.SCREEN_SCALE);

        mLevel = (Integer) properties.get(EngineConstants.GameProperties.LEVEL);
        if (properties.contains(EngineConstants.GameProperties.SPACE_LR)) {
            spaceLR = (Integer) properties.get(EngineConstants.GameProperties.SPACE_LR);
        } else {
            spaceLR = EngineConstants.spaceLR;
        }
        if (properties.contains(EngineConstants.GameProperties.SPACE_TB)) {
            spaceTB = (Integer) properties.get(EngineConstants.GameProperties.SPACE_TB);
        } else {
            spaceTB = EngineConstants.spaceTB;
        }

        if (!init) {
            initThread(time);
        }
    }

    private void initThread(long time) {
        init = true;
        doInitThread(time);
        init = false;
    }

    public synchronized void exit() {
        pause();
    }

    public synchronized void create() {
        if (!init) {
            initThread(SystemClock.uptimeMillis());
        }
        unpause();
    }

    /**
     * Restores game state from the indicated Bundle. Typically called when the
     * Activity is being restored after having been previously destroyed.
     *
     * @param savedState Bundle containing the game state
     */
    public synchronized void restoreState(Bundle savedState) {
        restoreGameState();
    }

    /**
     * Dump game state to the provided Bundle. Typically called when the
     * Activity is being suspended.
     *
     * @return Bundle with this view's state
     */
    public Bundle saveState(Bundle map) {
        if (map != null) {
            saveGameState();
        }
        return map;
    }

    /**
     * return the run state for the case a failure happens
     */
    public boolean run(SurfaceHolder newHolder) {
        if (mRun) {
            Canvas c = null;
            try {
                synchronized (newHolder) {
                    if (newHolder.getSurface().isValid()) {
                        c = newHolder.lockCanvas(null);
                    } else {
                        mRun = false;
                    }
                }
                // the fixed time for drawing this frame

                if (c != null) {
                    synchronized (newHolder) {
                        updateRenderState();
                        doDraw(c);
                    }
                }
                Thread.sleep(GameValues.threadDelay);

            } catch (Exception e) {
                mRun = false;
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    newHolder.unlockCanvasAndPost(c);
                }
            }
        }
        return mRun;
    }


    /**
     * Update the graphic x/y values in real time. This is called before the
     * draw() method
     */
    private void updateRenderState() {
        if (delayedAction != EngineConstants.ACTION_NONE) {
            delayedActionTimeout += 100;
            if (delayedActionTimeout > 500) {
                instantActionHandler(delayedAction, activeButton);
            }
        }
        doUpdateRenderState();
    }

    /**
     * Used to signal the thread whether it should be running or not. Passing
     * true allows the thread to run; passing false will shut it down if it's
     * already running. Calling start() after this was most recently called with
     * false will result in an immediate shutdown.
     *
     * @param b true to run, false to shut down
     */
    public void setRunning(boolean b) {
        mRun = b;
    }

    /**
     * Draws the graphics onto the Canvas.
     */
    private void doDraw(Canvas canvas) {
        canvas.drawRect(0, 0, screenWidth, realScreenHeight, pBg);
        doDrawRenderer(canvas);
    }


    public void delayedActionHandler(int action, int active) {
        delayedActionTimeout = 0;
        delayedAction = action;
        activeButton = active;
    }

    public void instantActionHandler(int action, int active) {
        // reset delayed data
        activeButton = EngineConstants.ACTION_NONE;
        delayedAction = EngineConstants.ACTION_NONE;
        delayedActionTimeout = 0;
        actionHandler(action);
    }

    @Override
    public void actionHandler(int action) {
        GenericScreenTemplate.class.cast(mContext).actionHandler(action);
    }

    protected GenericScreenTemplate getScreen() {
        return GenericScreenTemplate.class.cast(mContext);
    }

    protected final boolean containsClick(Graphic g, float x, float y) {
        return containsClick(g.image.getBounds(), x, y);
    }

    protected final boolean containsClick(Rect r, float x, float y) {
        return r.contains(Float.valueOf(x).intValue(), Float.valueOf(y).intValue());
    }

    protected final boolean containsClick(Rect r, float x, float y, float percentX, float percentY) {
        Rect newR = new Rect(r.left + Float.valueOf(r.left * percentX / 100).intValue(),
                r.top + Float.valueOf(r.top * percentY / 100).intValue(),
                r.right - Float.valueOf(r.right * percentX / 100).intValue(),
                r.bottom - Float.valueOf(r.bottom * percentY / 100).intValue());
        return newR.contains(Float.valueOf(x).intValue(), Float.valueOf(y).intValue());
    }

    protected int scaleWidth(int width) {
        return ScreenKit.scaleWidth(width, screenWidth);
    }

    protected int scaleHeight(int height) {
        return ScreenKit.scaleHeight(height, screenHeight);
    }

    private void drawCharacter(Canvas canvas, Drawable d, int pos, int logoWidth, int logoHeight, int initialLeft, int initialTop) {
        int scaleLeft = initialLeft + pos * logoWidth;
        d.setBounds(scaleLeft,
                initialTop,
                scaleLeft + logoWidth,
                initialTop + logoHeight);
        d.draw(canvas);
    }

    protected void drawText(Canvas canvas, Graphic[] gChar, Rect bounds, String text, int spaceLR, int spaceTB) {
        drawText(canvas, gChar, bounds, text, spaceLR, spaceTB, GameValues.cFilterBlue);
    }

    protected void drawTextScaled(Canvas canvas, Rect bounds, String text, ColorFilter filter) {
        drawText(canvas, gChar, bounds, text, ScreenKit.scaleWidth(spaceLR, screenWidth), ScreenKit.scaleHeight(spaceTB, screenHeight), filter);
    }

    protected void drawTextScaled(Canvas canvas, Rect bounds, String text) {
        drawText(canvas, gChar, bounds, text, ScreenKit.scaleWidth(spaceLR, screenWidth), ScreenKit.scaleHeight(spaceTB, screenHeight), GameValues.cFilterBlue);
    }

    protected void drawText(Canvas canvas, Rect bounds, String text, ColorFilter filter) {
        drawText(canvas, gChar, bounds, text, ScreenKit.scaleWidth(EngineConstants.spaceLR, screenWidth), ScreenKit.scaleHeight(EngineConstants.spaceTB, screenHeight), filter);
    }

    protected void drawText(Canvas canvas, Rect bounds, String text) {
        drawText(canvas, gChar, bounds, text, ScreenKit.scaleWidth(EngineConstants.spaceLR, screenWidth), ScreenKit.scaleHeight(EngineConstants.spaceTB, screenHeight), GameValues.cFilterBlue);
    }

    protected void drawText(Canvas canvas, Rect bounds, String text, int spaceLR, int spaceTB) {
        drawText(canvas, gChar, bounds, text, spaceLR, spaceTB, GameValues.cFilterBlue);
    }

    protected void drawText(Canvas canvas, Rect bounds, String text, int spaceLR, int spaceTB, ColorFilter filter) {
        drawText(canvas, gChar, bounds, text, spaceLR, spaceTB, filter);
    }

    protected void drawTextUnbounded(Canvas canvas, Rect bounds, String text, ColorFilter filter) {
        drawTextUnbounded(canvas, gChar, bounds, text, ScreenKit.scaleWidth(spaceLR, screenWidth), ScreenKit.scaleHeight(spaceTB, screenHeight), filter);
    }

    protected void drawTextUnbounded(Canvas canvas, Rect bounds, String text) {
        drawTextUnbounded(canvas, gChar, bounds, text, ScreenKit.scaleWidth(spaceLR, screenWidth), ScreenKit.scaleHeight(spaceTB, screenHeight), GameValues.cFilterYellow);
    }

    protected void drawTextUnbounded(Canvas canvas, Rect bounds, String text, int spaceLR, int spaceTB) {
        drawTextUnbounded(canvas, gChar, bounds, text, spaceLR, spaceTB, GameValues.cFilterYellow);
    }

    protected void drawTextUnbounded(Canvas canvas, Rect bounds, String text, int spaceLR, int spaceTB, ColorFilter filter) {
        drawTextUnbounded(canvas, gChar, bounds, text, spaceLR, spaceTB, filter);
    }

    protected void drawTextUnbounded(Canvas canvas, Graphic[] gChar, Rect bounds, String text, int spaceLR, int spaceTB) {
        drawTextUnbounded(canvas, gChar, bounds, text, spaceLR, spaceTB, GameValues.cFilterBlue);
    }

    protected void drawTextUnboundedScaled(Canvas canvas, Rect bounds, String text, ColorFilter filter) {
        drawTextUnbounded(canvas, gChar, bounds, text, ScreenKit.scaleWidth(spaceLR, screenWidth), ScreenKit.scaleHeight(spaceTB, screenHeight), filter);
    }

    protected void drawNumbers(Canvas canvas, int scaleWidth, int scaleHeight, int number, Graphic g, float width, float height, ColorFilter highlightFilter) {
        internalDrawNumbers(canvas, scaleWidth, scaleHeight, Integer.toString(number), g, width, height, highlightFilter);
    }

    protected void drawNumbers(Canvas canvas, int scaleWidth, int scaleHeight, int number, Graphic g, float width, float height) {
        internalDrawNumbers(canvas, scaleWidth, scaleHeight, Integer.toString(number), g, width, height, GameValues.cFilterYellow);
    }

    protected void drawNumbers(Canvas canvas, int scaleWidth, int scaleHeight, long number, Graphic g, float width, float height) {
        internalDrawNumbers(canvas, scaleWidth, scaleHeight, Long.toString(number), g, width, height, GameValues.cFilterYellow);
    }

    protected void drawNumbers(Canvas canvas, int scaleWidth, int scaleHeight, long number, Graphic g, float width, float height, ColorFilter highlightFilter) {
        internalDrawNumbers(canvas, scaleWidth, scaleHeight, Long.toString(number), g, width, height, highlightFilter);
    }

    protected void drawNumbers(Canvas canvas, int scaleWidth, int scaleHeight, String strValue, Graphic g, float width, float height) {
        internalDrawNumbers(canvas, scaleWidth, scaleHeight, strValue, g, width, height, GameValues.cFilterYellow);
    }

    protected void drawNumbers(Canvas canvas, int scaleWidth, int scaleHeight, String strValue, Graphic g, float width, float height, ColorFilter highlightFilter) {
        internalDrawNumbers(canvas, scaleWidth, scaleHeight, strValue, g, width, height, highlightFilter);
    }

    protected void baseDraw(Canvas canvas, Rect rect, Graphic gBase, ColorFilter highlightFilter) {
        gBase.image.setColorFilter(highlightFilter);
        gBase.image.setBounds(new Rect(rect));
        gBase.image.draw(canvas);
        gBase.image.clearColorFilter();
    }

    protected void baseDraw(Canvas canvas, Rect rect, Graphic gBase) {
        gBase.image.clearColorFilter();
        gBase.image.setBounds(new Rect(rect));
        gBase.image.draw(canvas);
    }

    protected void choiceBaseDraw(Canvas canvas, Rect rect, Graphic gIf, Graphic gBase, Object choice1, Object choice2, ColorFilter highlightFilter) {
        gBase.image.setBounds(new Rect(rect));
        gBase.image.draw(canvas);
        if (choice1.toString().equals(choice2.toString())) {
            gIf.image.setBounds(new Rect(rect));
            gIf.image.setColorFilter(highlightFilter);
            gIf.image.draw(canvas);
            gIf.image.clearColorFilter();
        }
    }

    protected void choiceDraw(Canvas canvas, Rect rect, Graphic gIf, Graphic gElse, Object choice1, Object choice2, ColorFilter highlightFilter) {
        if (choice1.toString().equals(choice2.toString())) {
            gIf.image.setBounds(new Rect(rect));
            gIf.image.setColorFilter(highlightFilter);
            gIf.image.draw(canvas);
            gIf.image.clearColorFilter();
        } else {
            gElse.image.setBounds(new Rect(rect));
            gElse.image.draw(canvas);
        }
    }

    protected void choiceDraw(Canvas canvas, Rect rect, Graphic gIf, Graphic gElse, Object choice1, Object choice2, Object choice3, Object choice4, ColorFilter highlightFilter, ColorFilter highlightFilterElse) {
        if (choice1.toString().equals(choice2.toString())) {
            gIf.image.setBounds(new Rect(rect));
            gIf.image.setColorFilter(highlightFilter);
            gIf.image.draw(canvas);
            gIf.image.clearColorFilter();
        } else if (choice3.toString().equals(choice4.toString())) {
            gIf.image.setBounds(new Rect(rect));
            gIf.image.setColorFilter(highlightFilterElse);
            gIf.image.draw(canvas);
            gIf.image.clearColorFilter();
        } else {
            gElse.image.setBounds(new Rect(rect));
            gElse.image.draw(canvas);
        }
    }

    protected void choiceDraw(Canvas canvas, Rect rect, Graphic gIf, Graphic gElse, Object choice1, Object choice2) {
        if (choice1.toString().equals(choice2.toString())) {
            gIf.image.setBounds(new Rect(rect));
            gIf.image.draw(canvas);
        } else {
            gElse.image.setBounds(new Rect(rect));
            gElse.image.draw(canvas);
        }
    }

    protected void drawGraphic(Canvas canvas, Graphic g, Rect rect) {
        g.image.setBounds(rect);
        g.image.draw(canvas);
    }

    protected void multiDraw(Canvas canvas, Graphic g, Rect rect, Graphic underlay) {
        underlay.image.setBounds(new Rect(rect));
        underlay.image.draw(canvas);
        g.image.setBounds(rect);
        g.image.draw(canvas);
    }

    protected void drawGraphic(Canvas canvas, Graphic g, int sX, int sY, int tX, int tY) {
        if (sX == tX) {
            // vertical
            int cY = sY;
            Rect r = g.image.copyBounds();
            while (cY < tY) {
                r.offsetTo(sX, cY);
                g.image.setBounds(r);
                g.image.draw(canvas);
                cY += r.height();
            }

        } else if (sY == tY) {
            // horizontal
            int cX = sX;
            Rect r = g.image.copyBounds();
            while (cX < tX) {
                r.offsetTo(cX, sY);
                g.image.setBounds(r);
                g.image.draw(canvas);
                cX += r.width();
            }
        }
    }

    protected void drawGraphic(Canvas canvas, Graphic g, int sX, int sY, int tX, int tY, float rotation) {
        if (sX == tX) {
            // vertical
            int cY = sY;
            Rect r = g.image.copyBounds();
            while (cY < tY) {
                r.offsetTo(sX, cY);
                g.image.setBounds(r);
                canvas.save();
                canvas.rotate(rotation, r.exactCenterX(), r.exactCenterY());
                g.image.draw(canvas);
                canvas.restore();
                cY += r.height();
            }

        } else if (sY == tY) {
            // horizontal
            int cX = sX;
            Rect r = g.image.copyBounds();
            while (cX < tX) {
                r.offsetTo(cX, sY);
                g.image.setBounds(r);
                canvas.save();
                canvas.rotate(rotation, r.exactCenterX(), r.exactCenterY());
                g.image.draw(canvas);
                canvas.restore();
                cX += r.width();
            }
        }
    }

    /**
     * Get the message from the string.xml file
     *
     * @param resId the id
     * @return the string message
     */
    protected String getString(int resId) {
        return mContext.getString(resId);
    }

    protected Graphic loadGraphic(int drawable, int boundLeft, int boundTop) {
        return Renderkit.loadGraphic(mContext.getResources(), drawable, boundLeft, boundTop);
    }

    protected Graphic loadGraphic(int drawable) {
        return Renderkit.loadGraphic(mContext.getResources(), drawable, 0, 0);
    }

    protected Graphic loadButtonGraphic(int drawable, int clickable) {
        return Renderkit.loadButtonGraphic(mContext.getResources(), drawable, 0, 0, clickable);
    }

    protected void log(String message) {
        if (logEnabled()) {
            Log.d("GenericRenderer", message);
        }
    }


    private Graphic[] initAlphabet() {
        Graphic[] g = new Graphic[39];
        g[0] = loadGraphic(R.drawable.char_a, 0, 0);
        g[1] = loadGraphic(R.drawable.char_b, 0, 0);
        g[2] = loadGraphic(R.drawable.char_c, 0, 0);
        g[3] = loadGraphic(R.drawable.char_d, 0, 0);
        g[4] = loadGraphic(R.drawable.char_e, 0, 0);
        g[5] = loadGraphic(R.drawable.char_f, 0, 0);
        g[6] = loadGraphic(R.drawable.char_g, 0, 0);
        g[7] = loadGraphic(R.drawable.char_h, 0, 0);
        g[8] = loadGraphic(R.drawable.char_i, 0, 0);
        g[9] = loadGraphic(R.drawable.char_j, 0, 0);
        g[10] = loadGraphic(R.drawable.char_k, 0, 0);
        g[11] = loadGraphic(R.drawable.char_l, 0, 0);
        g[12] = loadGraphic(R.drawable.char_m, 0, 0);
        g[13] = loadGraphic(R.drawable.char_n, 0, 0);
        g[14] = loadGraphic(R.drawable.char_o, 0, 0);
        g[15] = loadGraphic(R.drawable.char_p, 0, 0);
        g[16] = loadGraphic(R.drawable.char_q, 0, 0);
        g[17] = loadGraphic(R.drawable.char_r, 0, 0);
        g[18] = loadGraphic(R.drawable.char_s, 0, 0);
        g[19] = loadGraphic(R.drawable.char_t, 0, 0);
        g[20] = loadGraphic(R.drawable.char_u, 0, 0);
        g[21] = loadGraphic(R.drawable.char_v, 0, 0);
        g[22] = loadGraphic(R.drawable.char_w, 0, 0);
        g[23] = loadGraphic(R.drawable.char_x, 0, 0);
        g[24] = loadGraphic(R.drawable.char_y, 0, 0);
        g[25] = loadGraphic(R.drawable.char_z, 0, 0);
        g[26] = loadGraphic(R.drawable.n0, 0, 0);
        g[27] = loadGraphic(R.drawable.n1, 0, 0);
        g[28] = loadGraphic(R.drawable.n2, 0, 0);
        g[29] = loadGraphic(R.drawable.n3, 0, 0);
        g[30] = loadGraphic(R.drawable.n4, 0, 0);
        g[31] = loadGraphic(R.drawable.n5, 0, 0);
        g[32] = loadGraphic(R.drawable.n6, 0, 0);
        g[33] = loadGraphic(R.drawable.n7, 0, 0);
        g[34] = loadGraphic(R.drawable.n8, 0, 0);
        g[35] = loadGraphic(R.drawable.n9, 0, 0);
        g[36] = loadGraphic(R.drawable.ndbl, 0, 0);
        g[37] = loadGraphic(R.drawable.nplus, 0, 0);
        g[38] = loadGraphic(R.drawable.nminus, 0, 0);
        return g;
    }

    private void drawTextUnbounded(Canvas canvas, Graphic[] gChar, Rect bounds, String text, int spaceLR, int spaceTB, ColorFilter colorFilter) {
        if (text == null) {
            return;
        }
        int logoHeight = bounds.height() - spaceTB * 2;
        int logoWidth = logoHeight;
        int initialLeft = bounds.left + spaceLR;
        int initialTop = bounds.top + spaceTB;

        String strValue = text.toUpperCase();
        int pos = 0;
        int c, d;
        int spacing;
        for (int i = 0; i < strValue.length(); i++) {
            // alphabet
            spacing = pos > 0 ? Float.valueOf(logoWidth * getCharSpacing()).intValue() : 0;
            c = strValue.charAt(i) - 65;
            d = -1;
            if (c >= 0 && c <= 25) {
                d = c;
            }
            // number
            c = strValue.charAt(i) - 48;
            if (c >= 0 && c <= 10) {
                d = c + 26;
            }
            if (strValue.charAt(i) == 32) {
                // blank character
                pos++;
            }
            if (strValue.charAt(i) == 43) {
                // plus
                d = 37;
            }
            if (strValue.charAt(i) == 45) {
                // minus
                d = 38;
            }
            if (d > -1) {
                if (colorFilter != null) {
                    gChar[d].image.setColorFilter(colorFilter);
                }
                drawCharacter(canvas, gChar[d].image, pos, logoWidth, logoHeight, initialLeft - spacing * pos, initialTop);
                if (colorFilter != null) {
                    gChar[d].image.clearColorFilter();
                }
                pos++;
            }
        }
    }


    private void drawText(Canvas canvas, Graphic[] gChar, Rect bounds, String text, int spaceLR, int spaceTB, ColorFilter colorFilter) {
        if (text == null) {
            return;
        }
        int totalWidth = bounds.width() - spaceLR * 2;
        int logoHeight = bounds.height() - spaceTB * 2;
        int logoWidth = Float.valueOf(totalWidth / text.length()).intValue();
        int initialLeft = bounds.left + spaceLR;
        int initialTop = bounds.top + spaceTB;

        String strValue = text.toUpperCase();
        int pos = 0;
        int c, d;
        for (int i = 0; i < strValue.length(); i++) {
            // alphabet
            c = strValue.charAt(i) - 65;
            d = -1;
            if (c >= 0 && c <= 25) {
                d = c;
            }
            // number
            c = strValue.charAt(i) - 48;
            if (c >= 0 && c <= 10) {
                d = c + 26;
            }
            if (strValue.charAt(i) == 32) {
                // blank character
                pos++;
            }
            if (strValue.charAt(i) == 43) {
                // plus
                d = 37;
            }
            if (strValue.charAt(i) == 45) {
                // minus
                d = 38;
            }
            if (d > -1) {
                if (colorFilter != null) {
                    gChar[d].image.setColorFilter(colorFilter);
                }
                drawCharacter(canvas, gChar[d].image, pos, logoWidth, logoHeight, initialLeft, initialTop);
                if (colorFilter != null) {
                    gChar[d].image.clearColorFilter();
                }
                pos++;
            }
        }
    }


    private void internalDrawNumbers(Canvas canvas, int scaleWidth, int scaleHeight, String strValue, Graphic g, float width, float height, ColorFilter colorFilter) {
        // points
        final int logoWidth = Float.valueOf(screenWidth * width).intValue();
        final int logoHeight = Float.valueOf(logoWidth * height).intValue();
        int pos = 0;
        int top = 0;
        int x = 0;
        for (int i = strValue.length() - 1; i >= 0; i--) {
            char c = strValue.charAt(i);
            switch (c) {
                case '0':
                    x = 0;
                    break;
                case '1':
                    x = 1;
                    break;
                case '2':
                    x = 2;
                    break;
                case '3':
                    x = 3;
                    break;
                case '4':
                    x = 4;
                    break;
                case '5':
                    x = 5;
                    break;
                case '6':
                    x = 6;
                    break;
                case '7':
                    x = 7;
                    break;
                case '8':
                    x = 8;
                    break;
                case '9':
                    x = 9;
                    break;
                case ':':
                    x = 10;
                    break;
            }
            x += 26;
            if (colorFilter != null) {
                gChar[x].image.setColorFilter(colorFilter);
            }
            top = internalDrawNumber(canvas, gChar[x].image, pos, logoWidth, logoHeight, scaleWidth, scaleHeight);
            if (colorFilter != null) {
                gChar[x].image.clearColorFilter();
            }
            pos++;
        }
        if (g != null) {
            Rect r = g.image.copyBounds();
            r.offsetTo(scaleWidth(scaleWidth) - pos * logoWidth - scaleWidth(10), (top > 0 ? top - scaleHeight(10) : g.image.getBounds().top));
            g.image.setBounds(r);
            g.image.draw(canvas);
        }
    }

    private int internalDrawNumber(Canvas canvas, Drawable d, int pos, int logoWidth, int logoHeight, int scaleWidth, int scaleHeight) {
        int scaleLeft = scaleWidth(scaleWidth) - pos * logoWidth - scaleWidth(5);
        d.setBounds(scaleLeft, scaleHeight(scaleHeight), scaleLeft + logoWidth, scaleHeight(scaleHeight) + logoHeight);
        d.draw(canvas);
        return d.getBounds().top;
    }

}
