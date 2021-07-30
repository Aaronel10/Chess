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

public class Queen extends Piece{
    private final static int[] possible_vector_moves = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(int piecePosition, Team pieceTeam) {
        super(piecePosition, pieceTeam, Piece_Type.QUEEN);
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
                        legalMoves.add(new Move.MajorMove(board, this, possibleCoordinate));
                    } else
                    {
                        Piece pieceAtDestination = possibleTileDestination.getPiece();
                        Team pieceTeam = pieceAtDestination.getPieceTeam();
                        if(this.pieceTeam != pieceTeam)
                        {
                            legalMoves.add(new Move.AttackingMove(board, this, possibleCoordinate, pieceAtDestination));
                        }
                        break; // this break is to stop checking any further tiles if theres already a piece in the way
                    }
                }
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }

    // two column exceptions where algorithm fails
    private static boolean isFirstColumnException(int currentPosition, int candidateOffset)
    {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1 || candidateOffset == -9 || candidateOffset == 7);
    }

    private static boolean isEighthColumnException(int currentPosition, int candidateOffset)
    {
        return BoardUtils.EIGHTH_COLUMN [currentPosition] && (candidateOffset == 1 || candidateOffset == -7 || candidateOffset == 9);
    }
    @Override
    public Queen movePiece(Move move) {
        return new Queen(move.getDestinationCoordinate(), move.getMovedPiece().getPieceTeam());
    }


    @Override
    public String toString() {
        return Piece.Piece_Type.QUEEN.toString();
    }
}
