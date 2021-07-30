package chess.board;

import chess.pieces.Piece;

public abstract class Move {

    final Board board;

    public Piece getMovedPiece() {
        return movedPiece;
    }

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
            Board.Builder builder = new Board.Builder();

            for(Piece piece: this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }

            for(Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this)); //set piece thats moving
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
            return builder.build();
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
