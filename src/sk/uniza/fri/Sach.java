package sk.uniza.fri;

import sk.uniza.fri.gui.MainMenu;
import sk.uniza.fri.network.Websocket;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public class Sach {
    public Sach()  {
        MainMenu menu = new MainMenu();
        Websocket ws = new Websocket(new Websocket.IListener() {
            @Override
            public void onConnectedToServer(String kod) {
                menu.setKod(kod);
            }

            @Override
            public void onConnectedToPlayer() {
                menu.skry();
                System.out.println("Pripojené");
            }

            @Override
            public void onError() {
                System.out.println("Websocket Error");
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
}
