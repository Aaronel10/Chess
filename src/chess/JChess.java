package chess;

import chess.board.Board;
import chess.pieces.King;

public class JChess {
    public static void main(String[] args) {
        //Board board = Board.createStandardBoard();
        //System.out.println(board);
        King ob = new King(0, Team.BLACK);
        System.out.println(ob.isKing());

    }

}
