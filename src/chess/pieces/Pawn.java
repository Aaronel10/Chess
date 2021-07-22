package chess.pieces;

import chess.Team;
import chess.board.Board;
import chess.board.BoardUtils;
import chess.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece{

    private static final int[] possible_moves = {8};

    Pawn(int piecePosition, Team pieceTeam) {
        super(piecePosition, pieceTeam);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        int possibleDestinationCoordinate;
        for(int candidateOffset: possible_moves)
        {
            possibleDestinationCoordinate  = this.piecePosition + (candidateOffset * this.getPieceTeam().getDirection());
            if(!BoardUtils.isValidTileDestination(possibleDestinationCoordinate))
            {
                continue;
            }

            if(candidateOffset == 8 && !board.getTile(possibleDestinationCoordinate).isTileOccupied()){
                // eligible for 2nd move up
                legalMoves.add(new Move.MajorMove(board,this, possibleDestinationCoordinate)); // this needs to be changed
            }

            

        }


        return legalMoves;
    }
}
