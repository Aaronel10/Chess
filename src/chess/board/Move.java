package chess.board;

import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Rook;

import static chess.board.Board.*;

public abstract class Move {

    public static final Move NULL_MOVE = new NullMove();

    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoordinate;
    protected final boolean isFirstMove;

    public Piece getMovedPiece() {
        return movedPiece;
    }

     Move(final Board board, final Piece movedPiece, final int destinationCoordinate)
    {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board, final int destinationCoordinate){ //only for convenience
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode(){
         int prime = 31;
         int result = 1;
         result = prime * result + this.destinationCoordinate;
         result = prime * result + this.movedPiece.hashCode();
         result = prime * result + this.movedPiece.getPiecePosition();
         return result;
    }
    @Override
    public boolean equals(Object other){
        if(this == other) {
            return true;
        }
        if(!(other instanceof Move)){
            return false;
        }
        Move otherMove = (Move) other;
                return getCurrentCoordinate() == otherMove.getCurrentCoordinate() && getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
                getMovedPiece() == otherMove.getMovedPiece();

    }
    public int getCurrentCoordinate(){
         return this.movedPiece.getPiecePosition();
    }

    public int getDestinationCoordinate() {
         return this.destinationCoordinate;
    }

    public boolean isAttack(){
         return false;
    }
    public boolean isCastlingMove(){
         return false;
    }
    public Piece getAttackedPiece(){
         return null;
    }


    public Board execute() {
        Builder builder = new Builder();

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

        @Override
        public boolean equals(Object other){
             return this == other || other instanceof MajorMove && super.equals(other);
        }

        @Override
        public String toString(){
             return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    public static class AttackingMove extends Move {
        Piece attackedPiece;
         public AttackingMove(Board board, Piece movedPiece, int destinationCoordinate , Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;

        }
        @Override
        public int hashCode(){
             return this.attackedPiece.hashCode() + super.hashCode();
        }
        @Override
        public boolean equals(Object other){
             if(this == other){
                 return true;
             }
             if(!(other instanceof AttackingMove)){
                 return false;
             }
             AttackingMove otherMove = (AttackingMove) other;
            return super.equals(otherMove) && getAttackedPiece() == otherMove.getAttackedPiece();
        }

        @Override
        public boolean isAttack(){
             return true;
        }
        @Override
        public Piece getAttackedPiece(){
             return this.attackedPiece;
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
        @Override
        public Board execute(){
            Builder builder = new Builder();
            for(Piece piece: this.board.currentPlayer().getActivePieces()){
                if(this.movedPiece != piece){
                    builder.setPiece(piece);
                }
            }
            for(Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
            return builder.build();
        }

    }


     static abstract class CastleMove extends Move {
         protected Rook castleRook;

         public Rook getCastleRook() {
             return castleRook;
         }

         @Override
         public Board execute(){
             Builder builder = new Builder();
             for(Piece piece: this.board.currentPlayer().getActivePieces()){
                 if(this.movedPiece != piece && !this.castleRook.equals(piece)){
                     builder.setPiece(piece);
                 }
             }
             for(Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()){
                 builder.setPiece(piece);
             }
             builder.setPiece(this.movedPiece.movePiece(this));
             builder.setPiece(new Rook(this.castleRookDestination ,this.castleRook.getPieceTeam()));
             builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
             return builder.build();
         }

         @Override
         public boolean isCastlingMove(){
             return true;
         }

         protected int castleRookStart;
         protected int castleRookDestination;


        public CastleMove(Board board, Piece movedPiece, int destinationCoordinate, Rook castleRook, int castleRookStart, int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }
    }

    public static final class KingSideCastleMove extends CastleMove {
        public KingSideCastleMove(Board board, Piece movedPiece, int destinationCoordinate, Rook castleRook, int castleRookStart, int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString(){
            return "O-O";
        }

    }

    public static final class QueenSideCastleMove extends CastleMove {
        public QueenSideCastleMove(Board board, Piece movedPiece, int destinationCoordinate, Rook castleRook, int castleRookStart, int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString(){
            return "O-O-O";
        }
    }


    public static class NullMove extends Move {
        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute(){
            throw new RuntimeException("cant execute null move");
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
