package sk.uniza.fri;

import sk.uniza.fri.game.Chessboard;
import sk.uniza.fri.game.Game;
import sk.uniza.fri.game.PlayerColor;
import sk.uniza.fri.game.pieces.Bishop;
import sk.uniza.fri.gui.ChessboardComponent;
import sk.uniza.fri.gui.MainMenu;
import sk.uniza.fri.network.Websocket;

import javax.swing.JFrame;

/**
 * Created by IntelliJ IDEA.
 * User: RZ
 * Date: 17. 5. 2022
 * Time: 18:58
 */
public class Main {
    public static void main2(String[] args) {
        MainMenu menu = new MainMenu();
        Websocket ws = new Websocket(new Websocket.IListener() {
            @Override
            public void onConnectedToServer(String kod) {
                menu.setKod(kod);
            }

            @Override
            public void onConnectedToPlayer(Websocket ws, PlayerColor color) {
                menu.skry();
                Main.vytvorHru(ws, color);
            }

            @Override
            public void onError(String error) {
                if ("codeNotFound".equals(error)) {
                    menu.codeNotFound();
                }
            }

            @Override
            public void onClosed() {
                // TODO
            }
        });

        menu.setListener(new MainMenu.IListener() {
            @Override
            public void onConnect(String kod) {
                System.out.printf("Connecting to %s%n", kod);
                ws.connect(kod);
            }

            @Override
            public void onExit() {
                System.out.println("Exit");
                ws.close();
                System.exit(0);
            }
        });

        menu.zobraz();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        var boardCom = new ChessboardComponent(PlayerColor.BLACK);
        var board = new Chessboard();
        board.setPiece(0, 0, new Bishop(PlayerColor.BLACK));
        boardCom.vykresliSachovnicu(board);
        frame.setContentPane(boardCom);
        frame.pack();
        frame.setVisible(true);
    }

    private static void vytvorHru(Websocket ws, PlayerColor color) {
        new Game(color, ws);
    }
}
