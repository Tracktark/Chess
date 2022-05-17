package sk.uniza.fri.network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.HashMap;
import java.util.concurrent.CompletionStage;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public class Websocket {
    public static final String SERVER_URL = "ws://localhost:8765";

    private final IListener listener;
    private final HashMap<String, IWSCommand> commands;
    private WebSocket ws;

    public Websocket(IListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Websocket Listener can't be null");
        }
        this.listener = listener;

        this.commands = new HashMap<>();
        this.registerCommands();

        HttpClient httpClient = HttpClient.newHttpClient();
        WebSocket.Listener wsListener = new WebSocket.Listener() {
            @Override
            public void onOpen(WebSocket webSocket) {
                WebSocket.Listener.super.onOpen(webSocket);
                webSocket.sendText("getCode", true);
            }

            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                System.out.printf("Message: %s%n", data);
                Websocket.this.dispatchEvent(data);
                return WebSocket.Listener.super.onText(webSocket, data, last);
            }

            @Override
            public void onError(WebSocket webSocket, Throwable error) {
                WebSocket.Listener.super.onError(webSocket, error);
            }
        };

        httpClient.newWebSocketBuilder().buildAsync(URI.create(SERVER_URL), wsListener)
                .thenApply(webSocket -> this.ws = webSocket);
    }

    public void connect(String kod) {
        this.ws.sendText("connect\n" + kod, true);
    }

    public void close() {
        if (this.ws != null) {
            this.ws.sendClose(WebSocket.NORMAL_CLOSURE, "");
        }
    }

    public interface IListener {
        void onConnectedToServer(String kod);
        void onConnectedToPlayer();
        void onError();
    }

    private void registerCommands() {
        this.commands.put("connectionCode", this.listener::onConnectedToServer);
        this.commands.put("connected", _rest -> this.listener.onConnectedToPlayer());
    }

    private void dispatchEvent(CharSequence data) {
        String[] splitString = data.toString().split("\n", 2);
        String command = splitString[0];
        String rest = null;
        if (splitString.length > 1) {
            rest = splitString[1];
        }
        IWSCommand commandHandler = this.commands.get(command);
        if (commandHandler != null) {
            commandHandler.onCommand(rest);
        }
    }
}
