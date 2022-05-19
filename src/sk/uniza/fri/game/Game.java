package sk.uniza.fri.game;

import sk.uniza.fri.gui.ChessboardGUI;
import sk.uniza.fri.network.Websocket;

import javax.swing.*;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public class Game {
    private final PlayerColor localPlayerColor;
    private final Websocket ws;
    private final ChessboardGUI boardGui;
    private final JFrame frame;

    private Chessboard sachovnica;

    public Game(PlayerColor color, Websocket ws) {
        this.localPlayerColor = color;
        this.ws = ws;
        this.sachovnica = new Chessboard();
        this.boardGui = new ChessboardGUI(color);
        this.boardGui.vykresliSachovnicu(this.sachovnica);

        this.frame = new JFrame();
        this.frame.setContentPane(this.boardGui);
        this.frame.pack();
        this.frame.setVisible(true);
    }
}
