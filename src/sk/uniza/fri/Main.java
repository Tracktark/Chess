package sk.uniza.fri;

import sk.uniza.fri.game.Game;
import sk.uniza.fri.game.PlayerColor;
import sk.uniza.fri.gui.MainMenu;
import sk.uniza.fri.network.Websocket;


/**
 * Created by IntelliJ IDEA.
 * User: RZ
 * Date: 17. 5. 2022
 * Time: 18:58
 */
public class Main {
    public static void main(String[] args) {
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

    private static void vytvorHru(Websocket ws, PlayerColor color) {
        new Game(color, ws);
    }
}
