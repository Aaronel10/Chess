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

import static chess.board.Move.*;

public class Bishop extends Piece {

    private final static int[] possible_vector_moves = {-9, -7, 7, 9};

    public Bishop(int piecePosition, Team pieceTeam)
    {
        super(piecePosition, pieceTeam, Piece_Type.BISHOP);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        int possibleCoordinate;
        for(int candidateMoveOffset:possible_vector_moves)
        {
            possibleCoordinate = this.piecePosition;
            while(BoardUtils.isValidTileDestination(possibleCoordinate)){
                if(isFirstColumnException(possibleCoordinate, candidateMoveOffset) ||
                isEighthColumnException(possibleCoordinate, candidateMoveOffset))
                {
                    break;
                }

                possibleCoordinate += candidateMoveOffset;
                if(BoardUtils.isValidTileDestination(possibleCoordinate))
                {
                    final Tile possibleTileDestination = board.getTile(possibleCoordinate);

                    if(!possibleTileDestination.isTileOccupied())
                    {// tile to move to is empty so add it to legal moves list
                        legalMoves.add(new MajorMove(board, this, possibleCoordinate));
                    } else
                    {
                        Piece pieceAtDestination = possibleTileDestination.getPiece();
                        Team pieceTeam = pieceAtDestination.getPieceTeam();
                        if(this.pieceTeam != pieceTeam)
                        {
                            legalMoves.add(new AttackingMove(board, this, possibleCoordinate, pieceAtDestination));
                        }
                    break; // this break is to stop checking any further tiles if theres already a piece in the way
                    }
                }
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public Bishop movePiece(Move move) {
        return new Bishop(move.getDestinationCoordinate(), move.getMovedPiece().getPieceTeam());
        // this will create a new bishop everytime it moves
        // Might switch out with lookup board containing all 12 possible pieces at all 64 squares for performance later
    }

    // two column exceptions where algorithm fails
    private static boolean isFirstColumnException(int currentPosition, int candidateOffset)
    {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
    }

    private static boolean isEighthColumnException(int currentPosition, int candidateOffset)
    {
        return BoardUtils.EIGHTH_COLUMN [currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
    }

    @Override
    public String toString() {
        return Piece.Piece_Type.BISHOP.toString();
    }
}
