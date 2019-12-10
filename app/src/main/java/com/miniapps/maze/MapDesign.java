package com.miniapps.maze;

class MapDesign {
    private String mName;
    private int mSizeX = 0;
    private int mSizeY = 0;
    private int[][] mWalls;
    private int[][] mGoals;
    private int mInitialPositionX;
    private int mInitialPositionY;
    private int mGoalCount = 0;

    public MapDesign(String name, int sizeX, int sizeY, int[][] walls, int[][] goals,
                     int initialPositionX, int initialPositionY) {
        mName = name;
        mSizeX = sizeX;
        mSizeY = sizeY;
        mWalls = walls;
        mGoals = goals;
        mInitialPositionX = initialPositionX;
        mInitialPositionY = initialPositionY;
        for (int y = 0; y < mSizeY; y++) {
            for (int x = 0; x < mSizeX; x++) {
                mGoalCount = mGoalCount + mGoals[y][x];
            }
        }
    }

    public String getName() {
        return mName;
    }

    public int[][] getWalls() {
        return mWalls;
    }

    public int getWalls(int x, int y) {
        return mWalls[y][x];
    }

    public int[][] getGoals() {
        return mGoals;
    }

    public int getGoalCount() {
        return mGoalCount;
    }

    public int getSizeX() {
        return mSizeX;
    }

    public int getSizeY() {
        return mSizeY;
    }

    public int getInitialPositionX() {
        return mInitialPositionX;
    }

    public int getInitialPositionY() {
        return mInitialPositionY;
    }
}
