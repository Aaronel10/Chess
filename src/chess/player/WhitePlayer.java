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

public class WhitePlayer extends Player {
    public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
        super(board,whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Team getTeam() {
        return Team.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        List<Move> kingCastles = new ArrayList<>();
        if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){
            // this if for whites kingside Castle
            Tile rook = this.board.getTile(63);
            if(rook.isTileOccupied() && rook.getPiece().isFirstMove()){

                if(calculateAttacksOnTile(61, opponentLegals).isEmpty() &&
                calculateAttacksOnTile(62, opponentLegals).isEmpty() && rook.getPiece().getPieceType().isRook()){
                    // If you reached here its because the king can kingside castle
                    kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing,62,
                            (Rook)rook.getPiece(), rook.getTileCoordinate(), 61));
                }
            }
        }
        if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() &&
        !this.board.getTile(57).isTileOccupied()){
            // white queen side castle
            Tile rook = this.board.getTile(56);
            if(rook.isTileOccupied() && rook.getPiece().isFirstMove() &&
                Player.calculateAttacksOnTile(58,opponentLegals).isEmpty() &&
                Player.calculateAttacksOnTile(59, opponentLegals).isEmpty() &&
                rook.getPiece().getPieceType().isRook()){
                kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing,
                        58,(Rook)rook.getPiece(), rook.getTileCoordinate(), 59));
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }
}
