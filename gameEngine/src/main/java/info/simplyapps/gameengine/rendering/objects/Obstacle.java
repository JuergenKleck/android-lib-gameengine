package info.simplyapps.gameengine.rendering.objects;

import android.graphics.Rect;

public class Obstacle {

    public int type;
    public float life;
    public int gReference;
    public boolean hit;
    public int points;
    public Rect rect;

    public Obstacle(int type, float life, int gReference, int points) {
        this.type = type;
        this.life = life;
        this.gReference = gReference;
        this.points = points;
        rect = new Rect();
    }

}
