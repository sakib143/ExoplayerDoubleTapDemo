package com.example.exoplayertest.exoplayer_utils;

public interface PlayerDoubleTapListener {

    default void onDoubleTapStarted(float posX, float posY) { }
    default void onDoubleTapProgressDown(float posX, float posY) { }
    default void onDoubleTapProgressUp(float posX, float posY) { }
    default void onDoubleTapFinished() { }
}