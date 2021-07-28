package chess.board;

import chess.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;
     Move(final Board board, final Piece movedPiece, final int destinationCoordinate)
    {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public int getDestinationCoordinate() {
         return this.destinationCoordinate;
    }

    public abstract Board execute();


    public static final class MajorMove extends Move {
         public MajorMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    public static final class AttackingMove extends Move {
        Piece attackedPiece;
         public AttackingMove(Board board, Piece movedPiece, int destinationCoordinate , Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;

        }

        @Override
        public Board execute() {
            return null;
        }
    }


}
