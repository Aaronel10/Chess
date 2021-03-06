package chess.board;
import chess.pieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    public int getTileCoordinate() {
        return tileCoordinate;
    }

    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_Cache = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return Collections.unmodifiableMap(emptyTileMap); // can use immutable copy of from guava library
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece)
    {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_Cache.get(tileCoordinate);
    }

    private Tile(int tileCoordinate)
    {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {
        private EmptyTile(final int coordinate)
        {
            super(coordinate);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }

        @Override
        public String toString(){
            return "-";
        }

    }

    public static final class OccupiedTile extends Tile{
        private final Piece pieceOnTile;

        private OccupiedTile(int coordinate, Piece piece)
        {
            super(coordinate);
            this.pieceOnTile = piece;
        }

        @Override
        public String toString(){
            return getPiece().getPieceTeam().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }


}
