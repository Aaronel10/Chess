package chess.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] SECOND_ROW = null;
    public static final boolean[] SEVENTH_ROW = null;

    public static final int NUM_TILES = 64;
    public static final int NUM_TILE_PER_ROW = 8;

    private static boolean[] initColumn(int columnNumber) {
        // method used to create boolean array for each group of exceptions for columns 1, 2, 7, and 8
        final boolean[] column = new boolean[NUM_TILES];
        do{
            column[columnNumber] = true;
            columnNumber += NUM_TILE_PER_ROW;
        }while (columnNumber < NUM_TILES);
        return column;

    }

    private BoardUtils()
    {
        throw new RuntimeException("Cannot instantiate board utils object");
    }

    public static boolean isValidTileDestination(int possibleCoordinate) {
        return possibleCoordinate >= 0 && possibleCoordinate < NUM_TILES;
    }

}
