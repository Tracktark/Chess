package sk.uniza.fri.gui;

import sk.uniza.fri.game.ChessPiece;
import sk.uniza.fri.game.Chessboard;
import sk.uniza.fri.game.PlayerColor;
import sk.uniza.fri.game.Pozicia;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public class ChessboardComponent extends JComponent {
    public static final int VELKOST_STVORCA = 50;
    public static final Color[] FARBY_POLICKA = { new Color(0x779656), new Color(0xeeeed2) };

    private Chessboard board;
    private final PlayerColor playerColor;
    private final HashMap<Pozicia, Color[]> zvyraznenePolicka;

    public ChessboardComponent(PlayerColor playerColor) {
        this.playerColor = playerColor;
        this.zvyraznenePolicka = new HashMap<>();

        this.setPreferredSize(new Dimension(
                VELKOST_STVORCA * Chessboard.SIRKA,
                VELKOST_STVORCA * Chessboard.VYSKA
        ));
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
    }

    public void zvyrazniVsetky(ArrayList<Pozicia> pozicie, Color[] farby) {
        for (Pozicia pozicia: pozicie) {
            this.zvyrazniPolicko(pozicia, farby);
        }
    }


    @Override
    public void paint(Graphics g) {
        Dimension size = this.getSize();
        int velkostStvorca = Math.min(size.height / Chessboard.VYSKA, size.width / Chessboard.SIRKA);
        for (int y = 0; y < Chessboard.VYSKA; y++) {
            for (int x = 0; x < Chessboard.SIRKA; x++) {
                this.vykresliPolicko(g, x, y, velkostStvorca);
            }
        }
    }

    private void vykresliPolicko(Graphics g, int x, int y, int velkostStvorca) {
        int vykresleneY;
        if (this.playerColor == PlayerColor.WHITE) {
            vykresleneY = Chessboard.VYSKA - y - 1;
        } else {
            vykresleneY = y;
        }

        int i = y + x;
        Color[] color = this.zvyraznenePolicka.getOrDefault(new Pozicia(x, y), FARBY_POLICKA);
        g.setColor(color[i % 2]);
        g.fillRect(x * velkostStvorca, vykresleneY * velkostStvorca, velkostStvorca, velkostStvorca);

        if (this.board == null) {
            return;
        }
        ChessPiece piece = this.board.getPiece(x, y);
        if (piece == null) {
            return;
        }
        BufferedImage image = piece.getImage();

        g.drawImage(image, x * velkostStvorca, vykresleneY * velkostStvorca, velkostStvorca, velkostStvorca, null);
    }
}
