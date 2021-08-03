package chess.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] EIGHTH_RANK = initRow(0);
    public static final boolean[] SEVENTH_RANK = initRow(8);
    public static final boolean[] SIXTH_RANK = initRow(16);
    public static final boolean[] FIFTH_RANK = initRow(24);
    public static final boolean[] FOURTH_RANK = initRow(32);
    public static final boolean[] THIRD_RANK = initRow(40);
    public static final boolean[] SECOND_RANK = initRow(48);
    public static final boolean[] FIRST_RANK = initRow(56);

    private static boolean[] initRow(int rowNumber) {
        final boolean[] row = new boolean[NUM_TILES];
        do{
            row[rowNumber] = true;
            rowNumber++;

        }while(rowNumber % NUM_TILE_PER_ROW != 0);

        return row;
    }

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
