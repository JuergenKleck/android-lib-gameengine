package com.juergenkleck.android.gameengine.rendering.kits;

import android.graphics.Rect;
import com.juergenkleck.android.gameengine.rendering.objects.Graphic;

/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class ScreenKit {

    public enum ScreenPosition {
        TOP_LEFT,
        BOTTOM_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT,
        CENTER,
        CENTER_TOP,
        CENTER_BOTTOM,
        CENTER_LEFT,
        CENTER_RIGHT;
    }

    // assume this is the 100% value
    public final static int height = 960;
    public final static int width = 540;

    public static int scaleWidth(int value, int screen) {
        return value * screen / width;
    }

    public static int scaleHeight(int value, int screen) {
        return value * screen / height;
    }

    public static int unscaleWidth(int value, int screen) {
        return Float.valueOf((Float.valueOf(value) / Float.valueOf(screen)) * Float.valueOf(width)).intValue();
    }

    public static int unscaleHeight(int value, int screen) {
        return Float.valueOf((Float.valueOf(value) / Float.valueOf(screen)) * Float.valueOf(height)).intValue();
    }

    /**
     * Scale a image
     *
     * @param screenWidth  the screen width
     * @param screenHeight the screen height
     * @param scale        the scaling factor
     * @param borderLR     the border on the left or right side
     * @param borderTB     the border on the top or bottom side
     * @param graphics     the array of graphics
     */
    public static void scaleImage(int screenWidth, int screenHeight, ScreenPosition position, float scale, int borderLR, int borderTB, Graphic... graphics) {
        for (Graphic graphic : graphics) {
            graphic.image.setBounds(scaleRect(screenWidth, screenHeight, position, scale, borderLR, borderTB, graphic.image.getBounds()));
        }
    }


    /**
     * Scale a image
     *
     * @param screenWidth  the screen width
     * @param screenHeight the screen height
     * @param scale        the scaling factor
     * @param borderLR     the border on the left or right side
     * @param borderTB     the border on the top or bottom side
     * @param bounds       the bounds to set
     */
    public static Rect scaleRect(int screenWidth, int screenHeight, ScreenPosition position, float scale, int borderLR, int borderTB, Rect bounds) {
        final int graphicWidth = Float.valueOf(screenWidth * scale).intValue();
        final int graphicHeight = graphicWidth * (bounds.bottom - bounds.top) / bounds.right;
        switch (position) {
            case BOTTOM_LEFT:
                bounds.set(
                        ScreenKit.scaleWidth(borderLR, screenWidth),
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight) - graphicHeight,
                        ScreenKit.scaleWidth(borderLR, screenWidth) + graphicWidth,
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight));
                break;
            case BOTTOM_RIGHT:
                bounds.set(
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth) - graphicWidth,
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight) - graphicHeight,
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth),
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight));
                break;
            case CENTER:
                bounds.set(
                        ((screenWidth - graphicWidth) / 2),
                        ((screenHeight - graphicHeight) / 2),
                        ((screenWidth - graphicWidth) / 2) + graphicWidth,
                        ((screenHeight - graphicHeight) / 2) + graphicHeight);
                break;
            case CENTER_TOP:
                bounds.set(
                        ((screenWidth - graphicWidth) / 2),
                        ScreenKit.scaleHeight(borderTB, screenHeight),
                        ((screenWidth - graphicWidth) / 2) + graphicWidth,
                        ScreenKit.scaleHeight(borderTB, screenHeight) + graphicHeight);
                break;
            case CENTER_BOTTOM:
                bounds.set(
                        ((screenWidth - graphicWidth) / 2),
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight) - graphicHeight,
                        ((screenWidth - graphicWidth) / 2) + graphicWidth,
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight));
                break;
            case CENTER_LEFT:
                bounds.set(
                        ScreenKit.scaleWidth(borderLR, screenWidth),
                        ((screenHeight - graphicHeight) / 2),
                        ScreenKit.scaleWidth(borderLR, screenWidth) + graphicWidth,
                        ((screenHeight - graphicHeight) / 2) + graphicHeight);
                break;
            case CENTER_RIGHT:
                bounds.set(
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth) - graphicWidth,
                        ((screenHeight - graphicHeight) / 2),
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth),
                        ((screenHeight - graphicHeight) / 2) + graphicHeight);
                break;
            case TOP_LEFT:
                bounds.set(
                        ScreenKit.scaleWidth(borderLR, screenWidth),
                        ScreenKit.scaleHeight(borderTB, screenHeight),
                        ScreenKit.scaleWidth(borderLR, screenWidth) + graphicWidth,
                        ScreenKit.scaleHeight(borderTB, screenHeight) + graphicHeight);
                break;
            case TOP_RIGHT:
                bounds.set(
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth) - graphicWidth,
                        ScreenKit.scaleHeight(borderTB, screenHeight),
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth),
                        ScreenKit.scaleHeight(borderTB, screenHeight) + graphicHeight);
                break;
            default:
                break;
        }
        return bounds;
    }


    /**
     * Move a image
     *
     * @param screenWidth  the screen width
     * @param screenHeight the screen height
     * @param borderLR     the border on the left or right side
     * @param borderTB     the border on the top or bottom side
     * @param bounds       the bounds to set
     */
    public static Rect moveRect(int screenWidth, int screenHeight, ScreenPosition position, int borderLR, int borderTB, Rect bounds) {
        final int graphicWidth = bounds.width();
        final int graphicHeight = bounds.height();
        switch (position) {
            case BOTTOM_LEFT:
                bounds.set(
                        ScreenKit.scaleWidth(borderLR, screenWidth),
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight) - graphicHeight,
                        ScreenKit.scaleWidth(borderLR, screenWidth) + graphicWidth,
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight));
                break;
            case BOTTOM_RIGHT:
                bounds.set(
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth) - graphicWidth,
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight) - graphicHeight,
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth),
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight));
                break;
            case CENTER:
                bounds.set(
                        ((screenWidth - graphicWidth) / 2),
                        ((screenHeight - graphicHeight) / 2),
                        ((screenWidth - graphicWidth) / 2) + graphicWidth,
                        ((screenHeight - graphicHeight) / 2) + graphicHeight);
                break;
            case CENTER_TOP:
                bounds.set(
                        ((screenWidth - graphicWidth) / 2),
                        ScreenKit.scaleHeight(borderTB, screenHeight),
                        ((screenWidth - graphicWidth) / 2) + graphicWidth,
                        ScreenKit.scaleHeight(borderTB, screenHeight) + graphicHeight);
                break;
            case CENTER_BOTTOM:
                bounds.set(
                        ((screenWidth - graphicWidth) / 2),
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight) - graphicHeight,
                        ((screenWidth - graphicWidth) / 2) + graphicWidth,
                        screenHeight - ScreenKit.scaleHeight(borderTB, screenHeight));
                break;
            case CENTER_LEFT:
                bounds.set(
                        ScreenKit.scaleWidth(borderLR, screenWidth),
                        ((screenHeight - graphicHeight) / 2),
                        ScreenKit.scaleWidth(borderLR, screenWidth) + graphicWidth,
                        ((screenHeight - graphicHeight) / 2) + graphicHeight);
                break;
            case CENTER_RIGHT:
                bounds.set(
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth) - graphicWidth,
                        ((screenHeight - graphicHeight) / 2),
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth),
                        ((screenHeight - graphicHeight) / 2) + graphicHeight);
                break;
            case TOP_LEFT:
                bounds.set(
                        ScreenKit.scaleWidth(borderLR, screenWidth),
                        ScreenKit.scaleHeight(borderTB, screenHeight),
                        ScreenKit.scaleWidth(borderLR, screenWidth) + graphicWidth,
                        ScreenKit.scaleHeight(borderTB, screenHeight) + graphicHeight);
                break;
            case TOP_RIGHT:
                bounds.set(
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth) - graphicWidth,
                        ScreenKit.scaleHeight(borderTB, screenHeight),
                        screenWidth - ScreenKit.scaleWidth(borderLR, screenWidth),
                        ScreenKit.scaleHeight(borderTB, screenHeight) + graphicHeight);
                break;
            default:
                break;
        }
        return bounds;
    }
}
