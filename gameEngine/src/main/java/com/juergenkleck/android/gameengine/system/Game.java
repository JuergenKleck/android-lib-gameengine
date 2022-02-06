package com.juergenkleck.android.gameengine.system;

import java.util.ArrayList;
import java.util.List;


/**
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class Game extends BasicGame {

    public List<GameRound> rounds;
    public int currentRound;
    public float life;
    public int points;

    public Game(GameRound[] rounds) {
        this.rounds = new ArrayList<GameRound>();
        for (GameRound round : rounds) {
            this.rounds.add(round);
        }
        this.currentRound = -1;
    }

    public void addRound(GameRound round) {
        rounds.add(round);
    }

    public GameRound getCurrentRound() {
        if (currentRound >= rounds.size()) {
            currentRound = 0;
        }
        return rounds.get(currentRound);
    }

    public GameRound getNextRound() {
        currentRound++;
        if (currentRound >= rounds.size()) {
            currentRound = 0;
        }
        return rounds.get(currentRound);
    }

    public void reset() {
        currentRound = -1;
    }

    public boolean finished() {
        return life <= 0.0f;
    }

    @Override
    public boolean hasGame() {
        return currentRound >= 0;
    }

}
