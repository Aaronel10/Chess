package chess.board;

import chess.Team;
import chess.pieces.*;
import chess.board.Board.*;
import chess.player.BlackPlayer;
import chess.player.Player;
import chess.player.WhitePlayer;

import java.util.*;
import java.util.stream.Collectors;

public class Board {


    private final List<Tile> gameBoard;

    public Collection<Piece> getWhitePieces() {
        return white_pieces;
    }

    public Collection<Piece> getBlackPieces() {
        return black_pieces;
    }

    private final Collection<Piece> white_pieces;
    private final Collection<Piece> black_pieces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.white_pieces = calculateActivePieces(this.gameBoard, Team.WHITE);
        this.black_pieces = calculateActivePieces(this.gameBoard, Team.BLACK);
        Collection <Move> whiteStandardLegalmoves = calculateLegalMoves(this.white_pieces);
        Collection <Move> blackStandardLegalMoves = calculateLegalMoves(this.black_pieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalmoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalmoves, blackStandardLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }


    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if((i+1) % BoardUtils.NUM_TILE_PER_ROW == 0){
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Player whitePlayer(){
        return this.whitePlayer;
    }
    public Player blackPlayer(){
        return this.blackPlayer;
    }


    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
        List<Move> legalMoves = new ArrayList<>();
        for(Piece piece: pieces){
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return Collections.unmodifiableList(legalMoves);
    }


    private static Collection<Piece> calculateActivePieces(List<Tile> gameBoard, Team team) {
        final List<Piece> activePieces = new ArrayList<>();
        for(Tile tile : gameBoard){
            if(tile.isTileOccupied()){
                final Piece piece = tile.getPiece();
                if(piece.getPieceTeam() == team){
                    activePieces.add(piece);
                }
            }
        }
        return activePieces;
    }


    public Tile getTile(int tileCoordinate)
    {
        return gameBoard.get(tileCoordinate);
    }

    private static List <Tile> createGameBoard(Builder builder){
        Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfiguration.get(i));
        }
        return List.of(tiles);
    }

    public static Board createStandardBoard(){
        //creates initial position for chess game
        Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(0, Team.BLACK));
        builder.setPiece(new Knight(1, Team.BLACK));
        builder.setPiece(new Bishop(2, Team.BLACK));
        builder.setPiece(new Queen(3, Team.BLACK));
        builder.setPiece(new King(4, Team.BLACK));
        builder.setPiece(new Bishop(5, Team.BLACK));
        builder.setPiece(new Knight(6, Team.BLACK));
        builder.setPiece(new Rook(7, Team.BLACK));
        builder.setPiece(new Pawn(8, Team.BLACK));
        builder.setPiece(new Pawn(9, Team.BLACK));
        builder.setPiece(new Pawn(10, Team.BLACK));
        builder.setPiece(new Pawn(11, Team.BLACK));
        builder.setPiece(new Pawn(12, Team.BLACK));
        builder.setPiece(new Pawn(13, Team.BLACK));
        builder.setPiece(new Pawn(14, Team.BLACK));
        builder.setPiece(new Pawn(15, Team.BLACK));


        // White Layout
        builder.setPiece(new Pawn(48, Team.WHITE));
        builder.setPiece(new Pawn(49, Team.WHITE));
        builder.setPiece(new Pawn(50, Team.WHITE));
        builder.setPiece(new Pawn(51, Team.WHITE));
        builder.setPiece(new Pawn(52, Team.WHITE));
        builder.setPiece(new Pawn(53, Team.WHITE));
        builder.setPiece(new Pawn(54, Team.WHITE));
        builder.setPiece(new Pawn(55, Team.WHITE));
        builder.setPiece(new Rook(56, Team.WHITE));
        builder.setPiece(new Knight(57, Team.WHITE));
        builder.setPiece(new Bishop(58, Team.WHITE));
        builder.setPiece(new Queen(59, Team.WHITE));
        builder.setPiece(new King(60, Team.WHITE));
        builder.setPiece(new Bishop(61, Team.WHITE));
        builder.setPiece(new Knight(62, Team.WHITE));
        builder.setPiece(new Rook(63, Team.WHITE));


        //white to move
        builder.setMoveMaker(Team.WHITE);
        //build the board
        return builder.build();

    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }


    public static class Builder { // builder class to help build an instance of a board
        Map<Integer, Piece> boardConfiguration;
        Team nextMoveMaker;

        public Builder(){
            this.boardConfiguration = new HashMap<>();
        }
        public Builder setPiece(Piece piece){
            this.boardConfiguration.put(piece.getPiecePosition(), piece);
            return this;

        }

        public Builder setMoveMaker(Team whosTurnItIs){
            this.nextMoveMaker = whosTurnItIs;
            return this;
        }

        public Board build(){
            return new Board(this);
        }

    }


}
