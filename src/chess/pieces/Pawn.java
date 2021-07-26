package chess.pieces;

import chess.Team;
import chess.board.Board;
import chess.board.BoardUtils;
import chess.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static chess.board.Move.*;

public class Pawn extends Piece{

    private static final int[] possible_moves = {8, 16, 7 , 9};

    public Pawn(final int piecePosition, final Team pieceTeam) {
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

            if(candidateOffset == 8 && !board.getTile(possibleDestinationCoordinate).isTileOccupied()) { // handles non attacking move
                // eligible for 2nd move up
                legalMoves.add(new MajorMove(board, this, possibleDestinationCoordinate)); // this needs to be changed
            } else if(candidateOffset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceTeam.isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceTeam.isWhite())){ // handles pawn jump
                // some of this is overkill instead of just checking if its the first move but Ill go with this in case I want to make puzzles
                int behindDestinationCoordinate = this.piecePosition + (this.pieceTeam.getDirection() * 8);
                if(!board.getTile(behindDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(possibleDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new MajorMove(board, this, possibleDestinationCoordinate)); // this needs to be changed
                }
            } else if(candidateOffset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceTeam.isWhite()) ||
                    (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceTeam.isBlack()))){
                if(board.getTile(possibleDestinationCoordinate).isTileOccupied()){
                    Piece pieceOnCandidate = board.getTile(possibleDestinationCoordinate).getPiece();
                    if(this.pieceTeam != pieceOnCandidate.getPieceTeam()){
                        // attack viable since enemy piece
                        legalMoves.add(new MajorMove(board,this, possibleDestinationCoordinate));

                    }
                }
            } else if (candidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceTeam.isWhite()) ||
                            (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceTeam.isBlack()))){
                if(board.getTile(possibleDestinationCoordinate).isTileOccupied()){
                    Piece pieceOnCandidate = board.getTile(possibleDestinationCoordinate).getPiece();
                    if(this.pieceTeam != pieceOnCandidate.getPieceTeam()){
                        // attack viable since enemy piece
                        legalMoves.add(new MajorMove(board,this, possibleDestinationCoordinate));

                    }
                }
            }

        }
        return Collections.unmodifiableList(legalMoves);
    }
    @Override
    public String toString() {
        return Piece.Piece_Type.PAWN.toString();
    }
}
