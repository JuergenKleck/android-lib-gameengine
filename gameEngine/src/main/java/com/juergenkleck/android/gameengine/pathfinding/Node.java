package com.juergenkleck.android.gameengine.pathfinding;

/**
 * A single node in the search graph
 *
 *
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class Node implements Comparable<Node> {
    /**
     * The x coordinate of the node
     */
    public int x;
    /**
     * The y coordinate of the node
     */
    public int y;
    /**
     * The path cost for this node
     */
    public float cost;
    /**
     * The parent of this node, how we reached it in the search
     */
    public Node parent;
    /**
     * The heuristic cost of this node
     */
    public float heuristic;
    /**
     * The search depth of this node
     */
    public int depth;

    /**
     * Create a new node
     *
     * @param x The x coordinate of the node
     * @param y The y coordinate of the node
     */
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the parent of this node
     *
     * @param parent The parent node which lead us to this node
     * @return The depth we have no reached in searching
     */
    public int setParent(Node parent) {
        depth = parent.depth + 1;
        this.parent = parent;

        return depth;
    }

    /**
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Node other) {

        float f = heuristic + cost;
        float of = other.heuristic + other.cost;

        if (f < of) {
            return -1;
        } else if (f > of) {
            return 1;
        } else {
            return 0;
        }
    }
}