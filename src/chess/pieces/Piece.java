package chess.pieces;
import chess.Team;
import chess.board.Board;
import chess.board.Move;

import java.util.Collection;


public abstract class Piece {

    protected final Piece_Type pieceType;
    protected final int piecePosition;
    protected final Team pieceTeam;
    protected final boolean isFirstMove;

    public int getPiecePosition() {
        return piecePosition;
    }

    public Team getPieceTeam() {
        return this.pieceTeam;
    }


    Piece(final int piecePosition, final Team pieceTeam, final Piece_Type pieceType)
    {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceTeam = pieceTeam;
        this.isFirstMove = false; // needs work still
    }

    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public Piece_Type getPieceType(){
        return this.pieceType;
    }
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public enum Piece_Type {

        PAWN( "P"),
        KNIGHT( "N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN( "Q"),
        KING( "K");

        private final String pieceName;

        @Override
        public String toString() {
            return this.pieceName;
        }

        public boolean isKing(){
            return false;
        }

        Piece_Type(final String pieceName) {
            this.pieceName = pieceName;
        }

    }

}
