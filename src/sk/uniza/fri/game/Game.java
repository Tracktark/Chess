package sk.uniza.fri.game;

import sk.uniza.fri.network.Websocket;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public class Game {
    private final PlayerColor localPlayerColor;
    private final Websocket ws;

    private Chessboard sachovnica;

    public Game(PlayerColor color, Websocket ws) {
        this.localPlayerColor = color;
        this.ws = ws;
    }
}
