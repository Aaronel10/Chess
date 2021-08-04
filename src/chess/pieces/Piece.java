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
    // because our object is immutable we can just use a member field for hashcode instead of computing it each time
    private final int cachedHashCode;


    public int getPiecePosition() {
        return piecePosition;
    }

    public Team getPieceTeam() {
        return this.pieceTeam;
    }


    Piece(final int piecePosition, final Team pieceTeam, final Piece_Type pieceType, final boolean isFirstMove)
    {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceTeam = pieceTeam;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceTeam.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if( !(other instanceof Piece)){
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == ((Piece) other).getPiecePosition() && otherPiece.getPieceType() == pieceType
                && pieceTeam == ((Piece) other).getPieceTeam() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode(){
        return this.cachedHashCode;
    }

    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public Piece_Type getPieceType(){
        return this.pieceType;
    }
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public int getPieceValue(){
        return this.pieceType.getPieceValue();
    }

    public enum Piece_Type {

        PAWN( "P", 100){
            @Override
            public boolean isRook(){
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT( "N",300){
            @Override
            public boolean isRook(){
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B", 300){
            @Override
            public boolean isRook(){
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R", 500){
            @Override
            public boolean isRook(){
                return true;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN( "Q",900){
            @Override
            public boolean isRook(){
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING( "K", 10000){
            @Override
            public boolean isRook(){
                return false;
            }

            @Override
            public boolean isKing() {
                return true;
            }
        };

        private final String pieceName;
        private final int pieceValue;
        @Override
        public String toString() {
            return this.pieceName;
        }


        public abstract boolean isRook();
        public abstract boolean isKing();
        public int getPieceValue(){
            return this.pieceValue;
        }

        Piece_Type(final String pieceName, int pieceValue) {
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }
    }

}
