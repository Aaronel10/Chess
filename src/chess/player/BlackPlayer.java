package chess.player;

import chess.Team;
import chess.board.Board;
import chess.board.Move;
import chess.board.Tile;
import chess.pieces.Piece;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BlackPlayer extends Player{
    public BlackPlayer(Board board, Collection<Move> whiteStandardLegalmoves, Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalmoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Team getTeam() {
        return Team.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }
    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        List<Move> kingCastles = new ArrayList<>();
        if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()){
            // this if for black kingside Castle
            Tile rook = this.board.getTile(7);
            if(rook.isTileOccupied() && rook.getPiece().isFirstMove()){

                if(calculateAttacksOnTile(5, opponentLegals).isEmpty() &&
                        calculateAttacksOnTile(6, opponentLegals).isEmpty() && rook.getPiece().getPieceType().isRook()){
                    // If you reached here its because the king can kingside castle
                    kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing,6,
                            (Rook)rook.getPiece(), rook.getTileCoordinate(),5)); //
                }
            }
        }
        if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() &&
                !this.board.getTile(3).isTileOccupied()){
            // black queen side castle
            Tile rook = this.board.getTile(0);
            if(rook.isTileOccupied() && rook.getPiece().isFirstMove() &&
                    Player.calculateAttacksOnTile(2, opponentLegals).isEmpty() &&
                    Player.calculateAttacksOnTile(3, opponentLegals).isEmpty() &&
                    rook.getPiece().getPieceType().isRook()){
                kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing,
                        2,(Rook)rook.getPiece(), rook.getTileCoordinate(), 3));
            }

        }
        return Collections.unmodifiableList(kingCastles);
    }
}
