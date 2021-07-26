package chess.player;

import chess.Team;
import chess.board.Board;
import chess.board.Move;
import chess.pieces.King;
import chess.pieces.Piece;

import java.util.Collection;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves){
        this.board = board;
        this.legalMoves = legalMoves;
        this.playerKing = establishKing();
    }

    private King establishKing() {
        for(final Piece piece: getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here ever! Cant still have a game with no king");
    }

    public boolean isMoveLegal(Move move){
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck(){
        return false;
    }

    public boolean isInCheckMate(){
        return false;
    }

    public boolean isInStalemate(){
        return false;
    }

    public boolean isCastled(){
        return false;
    }

    public MoveTransition makeMove(final Move move){
        return null;
    }


    public abstract Collection<Piece> getActivePieces();
    public abstract Team getTeam();
    public abstract Player getOpponent();




}
