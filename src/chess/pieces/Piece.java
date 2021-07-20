package chess.pieces;
import chess.Team;
import chess.board.Board;
import chess.board.Move;

import java.util.Collection;


public abstract class Piece {
    protected final int piecePosition;

    public int getPiecePosition() {
        return piecePosition;
    }

    public Team getPieceTeam() {
        return pieceTeam;
    }

    protected final Team pieceTeam;

    Piece(final int piecePosition, final Team pieceTeam)
    {
        this.piecePosition = piecePosition;
        this.pieceTeam = pieceTeam;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);



}
