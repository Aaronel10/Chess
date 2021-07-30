package chess.board;

import chess.pieces.Piece;

public abstract class Move {

    public static final NULL_MOVE = new NullMove();

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

    public int getCurrentCoordinate(){
         return this.movedPiece.getPiecePosition();
    }

    public int getDestinationCoordinate() {
         return this.destinationCoordinate;
    }

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


    public static final class MajorMove extends Move {
         public MajorMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

    }

    public static class AttackingMove extends Move {
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


    public static class PawnMove extends Move {
        public PawnMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }
    public static class PawnAttackMove extends AttackingMove {
        public PawnAttackMove(Board board, Piece movedPiece, int destinationCoordinate,Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }
    public static final class PawnEnPassantAttackMove extends PawnAttackMove {
        public PawnEnPassantAttackMove(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    public static final class PawnJump extends Move {
        public PawnJump(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }


     static abstract class CastleMove extends Move {
        public CastleMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class KingSideCastleMove extends CastleMove {
        public KingSideCastleMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class QueenSideCastleMove extends CastleMove {
        public QueenSideCastleMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }


    public static final class NullMove extends Move {
        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute(){
            throw new RuntimeException("cant execute null move")
        }

    }

    public static class MoveFactory {
         private MoveFactory(){
             throw new RuntimeException("Cant instantiate Move Factory");
         }

         public static Move createMove(Board board , int currentCoordinate, int destinationCoordinate){
            for(Move move: board.getAllLegalMoves()){
                if(move.getCurrentCoordinate() == currentCoordinate &&
                move.getDestinationCoordinate() == destinationCoordinate){
                    return move;
                }
            }
            return NULL_MOVE;
         }


    }



}
