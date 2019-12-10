package com.miniapps.maze;

import java.util.LinkedList;
import java.util.List;

import static com.miniapps.maze.Wall.LEFT;
import static com.miniapps.maze.Wall.RIGHT;
import static com.miniapps.maze.Wall.TOP;
import static com.miniapps.maze.Wall.BOTTOM;


public class MapDesigns {
    public static final List<MapDesign> designList = new LinkedList<MapDesign>();

    static {
        designList.add(new MapDesign(
                "Small 1",
                5, 5,
                new int[][]{
                        {LEFT | TOP, TOP, TOP | RIGHT, TOP, TOP | RIGHT},
                        {LEFT, 0, 0, RIGHT, RIGHT},
                        {LEFT | BOTTOM, LEFT, BOTTOM | RIGHT, 0, RIGHT},
                        {LEFT, BOTTOM, LEFT, 0, RIGHT},
                        {LEFT | BOTTOM, BOTTOM, BOTTOM, BOTTOM, BOTTOM | RIGHT | TOP}
                },
                new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1}
                },
                0, 0
        ));
        designList.add(new MapDesign(
                "Small 2",
                5, 5,
                new int[][]{
                        {LEFT | TOP, TOP, TOP, TOP, TOP | RIGHT},
                        {LEFT, 0, 0, RIGHT, RIGHT},
                        {LEFT, LEFT | BOTTOM, BOTTOM | RIGHT, RIGHT, RIGHT},
                        {LEFT, BOTTOM, LEFT | BOTTOM, 0, RIGHT},
                        {LEFT | BOTTOM, BOTTOM, BOTTOM, BOTTOM, BOTTOM | RIGHT | TOP}
                },
                new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 1}
                },
                0, 0
        ));
        designList.add(new MapDesign(
                "Medium 1",
                7, 7,
                new int[][]{
                        {LEFT | TOP, TOP, TOP, TOP, TOP | RIGHT, TOP, TOP | RIGHT},
                        {LEFT, 0, 0, 0, 0, RIGHT, RIGHT},
                        {LEFT, 0, TOP | LEFT | RIGHT, 0, 0, RIGHT, RIGHT},
                        {LEFT, LEFT, 0, 0, 0, RIGHT, RIGHT},
                        {LEFT | BOTTOM, 0, RIGHT, 0, BOTTOM, BOTTOM, RIGHT},
                        {LEFT, TOP, 0, 0, 0, 0, RIGHT},
                        {LEFT | BOTTOM, RIGHT | BOTTOM, BOTTOM, BOTTOM, BOTTOM, LEFT | BOTTOM, BOTTOM | RIGHT | TOP}
                },
                new int[][]{
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0}
                },
                0, 0
        ));
    }

    private MapDesigns() {
        throw new AssertionError();
    }
}
