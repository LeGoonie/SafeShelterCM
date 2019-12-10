package com.miniapps.maze;

public class Map {
    private MapDesign mDesign;

    private int[][] mGoals;
    private int mGoalCount;

    public Map(MapDesign design) {
        mDesign = design;
        init();
    }

    public void init() {
        if (mGoals == null) mGoals = new int[mDesign.getSizeY()][mDesign.getSizeX()];

        int[][] goals = mDesign.getGoals();
        for (int y = 0; y < mDesign.getSizeY(); y++)
            for (int x = 0; x < mDesign.getSizeX(); x++)
                mGoals[y][x] = goals[y][x];

        mGoalCount = mDesign.getGoalCount();
    }

    public String getName() {
        return mDesign.getName();
    }

    public int[][] getWalls() {
        return mDesign.getWalls();
    }

    public int getWalls(int x, int y) {
        return mDesign.getWalls(x, y);
    }

    public int[][] getGoals() {
        return mGoals;
    }

    public int getGoal(int x, int y) {
        return mGoals[y][x];
    }

    public void removeGoal(int x, int y) {
        mGoalCount = mGoalCount - mGoals[y][x];
        mGoals[y][x] = 0;
    }

    public void setGoal(int x, int y, int value) {
        mGoalCount = mGoalCount - (mGoals[y][x] - value);
        mGoals[y][x] = value;
    }

    public int getSizeX() {
        return mDesign.getSizeY();
    }

    public int getSizeY() {
        return mDesign.getSizeY();
    }

    public int getInitialPositionX() {
        return mDesign.getInitialPositionX();
    }

    public int getInitialPositionY() {
        return mDesign.getInitialPositionY();
    }

    public int getGoalCount() {
        return mGoalCount;
    }
}
