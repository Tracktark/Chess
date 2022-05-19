package sk.uniza.fri.gui;

import sk.uniza.fri.game.ChessPiece;
import sk.uniza.fri.game.Chessboard;
import sk.uniza.fri.game.PlayerColor;
import sk.uniza.fri.game.Pozicia;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public class ChessboardGUI extends JComponent {
    public static final int PREFEROVANA_VELKOST_STVORCA = 50;
    public static final Color[] COLOR_DEFAULT = { new Color(0x779656), new Color(0xeeeed2) };
    public static final Color[] COLOR_HIGHLIGHT = { new Color(0xcc7428), new Color(0xf48151) };

    private Chessboard board;
    private int velkostStvorca;
    private IListener listener;
    private final PlayerColor playerColor;
    private final HashMap<Pozicia, Color[]> zvyraznenePolicka;


    public ChessboardGUI(PlayerColor playerColor) {
        this.playerColor = playerColor;
        this.zvyraznenePolicka = new HashMap<>();

        this.setPreferredSize(new Dimension(
                PREFEROVANA_VELKOST_STVORCA * Chessboard.SIRKA,
                PREFEROVANA_VELKOST_STVORCA * Chessboard.VYSKA
        ));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Pozicia pozicia = ChessboardGUI.this.getClickedSquare(e.getX(), e.getY());
                if (pozicia != null && ChessboardGUI.this.listener != null) {
                    ChessboardGUI.this.listener.onClicked(pozicia);
                }
            }
        });
    }

    public void resetZvyraznenie() {
        this.zvyraznenePolicka.clear();
        this.repaint();
    }

    public interface IListener {
        void onClicked(Pozicia pozicia);
    }

    public void setClickListener(IListener listener) {
        this.listener = listener;
    }


    public void vykresliSachovnicu(Chessboard board) {
        this.board = board;
        this.repaint();
    }

    public void zvyrazniPolicko(Pozicia pozicia, Color[] farby) {
        if (farby.length != 2) {
            throw new IllegalArgumentException("Farby musia byť presne 2");
        }
        this.zvyraznenePolicka.put(pozicia, farby);
        this.repaint();
    }

    public void zvyrazniVsetky(ArrayList<Pozicia> pozicie, Color[] farby) {
        for (Pozicia pozicia: pozicie) {
            if (farby.length != 2) {
                throw new IllegalArgumentException("Farby musia byť presne 2");
            }
            this.zvyraznenePolicka.put(pozicia, farby);
        }
        this.repaint();
    }


    @Override
    public void paint(Graphics g) {
        Dimension size = this.getSize();
        this.velkostStvorca = Math.min(size.height / Chessboard.VYSKA, size.width / Chessboard.SIRKA);
        for (int y = 0; y < Chessboard.VYSKA; y++) {
            for (int x = 0; x < Chessboard.SIRKA; x++) {
                this.vykresliPolicko(g, x, y);
            }
        }
    }

    private void vykresliPolicko(Graphics g, int x, int y) {
        int vykresleneY = y;
        int vykresleneX = x;
        if (this.playerColor == PlayerColor.WHITE) {
            vykresleneY = Chessboard.VYSKA - y - 1;
        } else {
            vykresleneX = Chessboard.SIRKA - x - 1;
        }

        int i = y + x;
        Color[] color = this.zvyraznenePolicka.getOrDefault(new Pozicia(x, y), COLOR_DEFAULT);
        g.setColor(color[i % 2]);
        g.fillRect(vykresleneX * this.velkostStvorca, vykresleneY * this.velkostStvorca, this.velkostStvorca, this.velkostStvorca);

        if (this.board == null) {
            return;
        }
        ChessPiece piece = this.board.getPiece(x, y);
        if (piece == null) {
            return;
        }
        BufferedImage image = piece.getImage();

        g.drawImage(image, vykresleneX * this.velkostStvorca, vykresleneY * this.velkostStvorca, this.velkostStvorca, this.velkostStvorca, null);
    }

    private Pozicia getClickedSquare(int mouseX, int mouseY) {
        int x = mouseX / this.velkostStvorca;
        int y = mouseY / this.velkostStvorca;
        if (this.playerColor == PlayerColor.WHITE) {
            y = Chessboard.VYSKA - y - 1;
        } else {
            x = Chessboard.SIRKA - x - 1;
        }
        if (x < 0 || x >= Chessboard.SIRKA || y < 0 || y >= Chessboard.VYSKA) {
            return null;
        }
        return new Pozicia(x, y);
    }
}
