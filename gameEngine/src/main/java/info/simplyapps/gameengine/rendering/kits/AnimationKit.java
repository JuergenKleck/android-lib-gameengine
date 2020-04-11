package info.simplyapps.gameengine.rendering.kits;

import info.simplyapps.gameengine.rendering.objects.Animation;
import info.simplyapps.gameengine.rendering.objects.AnimationFrame;

public class AnimationKit {

    public static void addAnimation(Animation animation, int gReference, int delay) {
        AnimationFrame frame = new AnimationFrame();
        frame.delay = delay;
        frame.gReference = gReference;
        animation.frames.add(frame);
    }

}
