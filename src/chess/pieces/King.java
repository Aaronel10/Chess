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

public class King extends Piece {
    private static final int[] possible_moves = {-9, -8, -7, -1, 1, 7, 8 ,9};


    public King(int piecePosition, Team pieceTeam) {
        super(piecePosition, pieceTeam, Piece_Type.KING);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        int possibleDestinationCoordinate;
        for(int candidateOffset: possible_moves){
           possibleDestinationCoordinate = this.piecePosition + candidateOffset;
           if(BoardUtils.isValidTileDestination(possibleDestinationCoordinate)){
               final Tile possibleTileDestination = board.getTile(possibleDestinationCoordinate);

               if(isFirstColumnExclusion(this.piecePosition, candidateOffset) ||isEighthColumnExclusion(this.piecePosition, candidateOffset)){
                   continue;
               }

               if(!possibleTileDestination.isTileOccupied())
               {// tile to move to is empty so add it to legal moves list
                   legalMoves.add(new Move.MajorMove(board, this, possibleDestinationCoordinate));
               } else
               {
                   Piece pieceAtDestination = possibleTileDestination.getPiece();
                   Team pieceTeam = pieceAtDestination.getPieceTeam();
                   if(this.pieceTeam != pieceTeam)
                   {
                       legalMoves.add(new Move.AttackingMove(board, this, possibleDestinationCoordinate, pieceAtDestination));
                   }
               }
           }

        }

        return Collections.unmodifiableList(legalMoves);
    }

    private static boolean isFirstColumnExclusion(int currentPosition, int offset)
    {
        return BoardUtils.FIRST_COLUMN[currentPosition] && ((offset== -1) || (offset == -9) || (offset == 7));
    }
    private static boolean isEighthColumnExclusion(int currentPosition, int offset)
    {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((offset== -7) || (offset == 1) || (offset == 9));
    }

    @Override
    public String toString() {
        return Piece.Piece_Type.KING.toString();
    }

    public Boolean isKing(){
        return true;
    }
}
