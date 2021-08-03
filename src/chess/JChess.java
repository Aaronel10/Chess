package chess;

import chess.board.Board;
import chess.gui.Table;
import chess.pieces.King;

import java.util.ArrayList;
import java.util.List;

public class JChess {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);

        Table table = new Table();

    }

}
