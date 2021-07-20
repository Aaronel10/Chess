package chess.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = null;
    public static final boolean[] SECOND_COLUMN = null;
    public static final boolean[] SEVENTH_COLUMN = null;
    public static final boolean[] EIGTH_COLUMN = null;


    private BoardUtils()
    {
        throw new RuntimeException("Cannot instantiate board utils object");
    }

    public static boolean isValidTileDestination(int possibleCoordinate) {
        return possibleCoordinate >= 0 && possibleCoordinate < 64;
    }
}
