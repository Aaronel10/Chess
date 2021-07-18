package chess.pieces;
import chess.Team;
import chess.board.Board;
import chess.board.Move;

import java.util.List;


public abstract class Piece {
    protected final int piecePosition;
    protected final Team pieceTeam;

    Piece(final int piecePosition, final Team pieceTeam)
    {
        this.piecePosition = piecePosition;
        this.pieceTeam = pieceTeam;
    }

    public abstract List<Move> calculateLegalMoves(final Board board);



}
