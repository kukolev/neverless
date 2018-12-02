package neverless.util;

public class Constants {
    public static final int CELL_WIDTH = 32;
    public static final int CELL_HEIGHT = 32;

    public static final int CELL_HOR_COUNT = 21;
    public static final int CELL_VERT_COUNT = 11;

    public static final int CELL_HOR_CENTER = (CELL_HOR_COUNT / 2) + 1;
    public static final int CELL_VERT_CENTER = (CELL_VERT_COUNT / 2) + 1;

    public static final int CANVAS_WIDTH = CELL_WIDTH * CELL_HOR_COUNT;
    public static final int CANVAS_HEIGHT = CELL_HEIGHT * CELL_VERT_COUNT;
}
