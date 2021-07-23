package chess.pieces;
import chess.Team;
import chess.board.Board;
import chess.board.Move;

import java.util.Collection;


public abstract class Piece {
    protected final int piecePosition;
    protected final Team pieceTeam;
    protected final boolean isFirstMove;

    public int getPiecePosition() {
        return piecePosition;
    }

    public Team getPieceTeam() {
        return pieceTeam;
    }


    Piece(final int piecePosition, final Team pieceTeam)
    {
        this.piecePosition = piecePosition;
        this.pieceTeam = pieceTeam;
        this.isFirstMove = false; // needs work still
    }

    public boolean isFirstMove(){
        return this.isFirstMove;
    }


    public abstract Collection<Move> calculateLegalMoves(final Board board);



}
