package info.simplyapps.gameengine.pathfinding.impl;

import info.simplyapps.gameengine.pathfinding.interfaces.Mover;

public class UnitMover implements Mover {

    // The unit type
    public int type;

    // more complex data for each unit
    public float life;
    public int coins;

    /**
     * Create a new mover to be used while path finder
     *
     * @param type The ID of the unit moving
     */
    public UnitMover(int type) {
        this.type = type;
    }

}