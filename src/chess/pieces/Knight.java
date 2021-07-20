package chess.pieces;

import chess.Team;
import chess.board.Board;
import chess.board.BoardUtils;
import chess.board.Move;
import chess.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Knight extends Piece{

    private final static int[] possible_moves = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition,final Team pieceTeam) {
        super(piecePosition, pieceTeam);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        int possibleCoordinate;
        List<Move> legalMoves = new ArrayList<>();

        for(int candidateMoveOffset: possible_moves)
        { // loop through all possible moves from current position
            possibleCoordinate = this.piecePosition + candidateMoveOffset;
            if(BoardUtils.isValidTileDestination(possibleCoordinate))
            { // if the tile is a valid location (it exists or isnt taken by a ally piece)
                if(isFirstColumnExclusion(this.piecePosition, candidateMoveOffset) ||
                        isSecondColumnExclusion(this.piecePosition, candidateMoveOffset) ||
                        isSeventhColumnExclusion(this.piecePosition, candidateMoveOffset) ||
                        isEighthColumnExclusion(this.piecePosition, candidateMoveOffset))
                {
                    continue;
                }

                final Tile possibleTileDestination = board.getTile(possibleCoordinate);

                if(!possibleTileDestination.isTileOccupied())
                {// tile to move to is empty so add it to legal moves list
                    legalMoves.add(new Move(possibleTileDestination));
                } else
                {
                    Piece pieceAtDestination = possibleTileDestination.getPiece();
                    Team pieceTeam = pieceAtDestination.getPieceTeam();
                    if(this.pieceTeam != pieceTeam)
                    {
                        legalMoves.add(new Move());
                    }else{

                    }
                }
            }

        }
        return Collections.unmodifiableList(legalMoves);
    }

    private static boolean isFirstColumnExclusion(int currentPosition, int offset)
    {
        return BoardUtils.FIRST_COLUMN[currentPosition] && ((offset== -17) || (offset == -10) || (offset == 6) || (offset == 15));
    }

    private static boolean isSecondColumnExclusion(int currentPosition, int offset)
    {
        return BoardUtils.SECOND_COLUMN[currentPosition] && ((offset == -10) || (offset == 6));
    }

    private static boolean isSeventhColumnExclusion(int currentPosition, int offset)
    {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((offset== -6) || (offset == 10));
    }

    private static boolean isEighthColumnExclusion(int currentPosition, int offset)
    {
        return BoardUtils.EIGTH_COLUMN[currentPosition] && ((offset== -15) || (offset == -6) || (offset == 10) || (offset == 17));
    }



}