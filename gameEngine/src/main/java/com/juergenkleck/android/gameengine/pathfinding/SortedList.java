package com.juergenkleck.android.gameengine.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple sorted list
 *
 *
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class SortedList {
    /**
     * The list of elements
     */
    private List<Node> list = new ArrayList<Node>();

    /**
     * Retrieve the first element from the list
     *
     * @return The first element from the list
     */
    public Node first() {
        return list.get(0);
    }

    /**
     * Empty the list
     */
    public void clear() {
        list.clear();
    }

    /**
     * Add an element to the list - causes sorting
     *
     * @param o The element to add
     */
    public void add(Node o) {
        list.add(o);
        Collections.sort(list);
    }

    /**
     * Remove an element from the list
     *
     * @param o The element to remove
     */
    public void remove(Node o) {
        list.remove(o);
    }

    /**
     * Get the number of elements in the list
     *
     * @return The number of element in the list
     */
    public int size() {
        return list.size();
    }

    /**
     * Check if an element is in the list
     *
     * @param o The element to search for
     * @return True if the element is in the list
     */
    public boolean contains(Node o) {
        return list.contains(o);
    }
}
