package chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Table {
    private final JFrame gameFrame;
    private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);


    public Table(){
        this.gameFrame = new JFrame("Chess");
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setJMenuBar(tableMenuBar);
        gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
    }

    private void populateMenuBar(JMenuBar tableMenuBar) {
        tableMenuBar.add(createFileMenu());

    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File"); // PGN is a file that stores info about a chess match
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Open pgn");
            }
        });
        fileMenu.add(openPGN);
        return fileMenu;
    }


}
