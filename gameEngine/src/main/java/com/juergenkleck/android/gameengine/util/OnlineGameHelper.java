package com.juergenkleck.android.gameengine.util;

/**
 * Multi player is now paid service as of 1st Apr 2020
 *
 *
 * Android library - GameEngine
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class OnlineGameHelper {


    // TODO must implement either
    // Firebase Realtime Database (Paid) -- https://firebase.google.com/docs/database
    // Google Cloud Open Match (Paid) -- https://cloud.google.com/blog/products/open-source/open-match-flexible-and-extensible-matchmaking-for-games


    static final String TAG = "OnlineGameHelper";

    /**
     * Listener for sign-in success or failure events.
     */
    public interface GameHelperListener {
        /**
         * Called when sign-in fails. As a result, a "Sign-In" button can be
         * shown to the user; when that button is clicked, call
         *
         * @link{GamesHelper#beginUserInitiatedSignIn . Note that not all calls
         * to this method mean an
         * error; it may be a result
         * of the fact that automatic
         * sign-in could not proceed
         * because user interaction
         * was required (consent
         * dialogs). So
         * implementations of this
         * method should NOT display
         * an error message unless a
         * call to @link{GamesHelper#
         * hasSignInError} indicates
         * that an error indeed
         * occurred.
         */
        void onSignInFailed();

        /**
         * Called when sign-in succeeds.
         */
        void onSignInSucceeded();
    }

}
