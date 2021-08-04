package chess.gui;

import chess.board.Move;
import chess.pieces.Piece;
import com.google.common.primitives.Ints;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static chess.gui.Table.*;

public class TakenPiecesPanel extends JPanel {
    private final JPanel northPanel;
    private final JPanel southPanel;

    private final static Color PANEL_COLOR = Color.decode("#c8451e");
    private final static EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private final static Dimension TAKEN_PIECES_DIMENSION = new Dimension(40, 80);

    public TakenPiecesPanel(){
        super(new BorderLayout());
        setBackground(Color.decode("#44887c"));
        this.setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8,2));
        this.southPanel = new JPanel(new GridLayout(8,2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSION);

    }
    public void redo(MoveLog moveLog){
        southPanel.removeAll();
        northPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<Piece>();
        final List<Piece> blackTakenPieces = new ArrayList<Piece>();

        for(final Move move: moveLog.getMoves()){
            if(move.isAttack()){
                final Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getPieceTeam().isWhite()){
                    whiteTakenPieces.add(takenPiece);
                } else if(takenPiece.getPieceTeam().isBlack()){
                    blackTakenPieces.add(takenPiece);
                } else{
                    throw new RuntimeException("Should not read here MoveLog function");
                }
            }
        }
        Collections.sort(whiteTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        Collections.sort(blackTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        for(final Piece takenPiece : whiteTakenPieces){
            try{
                BufferedImage image = ImageIO.read(new File("ChessPics/" + takenPiece.getPieceTeam().toString().substring(0, 1)
                        + "" + takenPiece.toString()));
                ImageIcon icon = new ImageIcon(image);
                JLabel imageLabel = new JLabel();
                this.southPanel.add(imageLabel);

            }catch (IOException e){
                e.printStackTrace();
            }
        } // end white forloops
        for(final Piece blackPiece : blackTakenPieces){
            try{
                BufferedImage image = ImageIO.read(new File("ChessPics/" + blackPiece.getPieceTeam().toString().substring(0, 1)
                        + "" + blackPiece.toString()));
                ImageIcon icon = new ImageIcon(image);
                JLabel imageLabel = new JLabel();
                this.southPanel.add(imageLabel);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        validate();
    }
}
