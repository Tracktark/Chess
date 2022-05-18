package sk.uniza.fri.network;

import sk.uniza.fri.game.PlayerColor;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
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
    private final HashMap<Class<?>, IMessageListener<?>> messageListeners;
    private WebSocket ws;

    public Websocket(IListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Websocket Listener can't be null");
        }
        this.listener = listener;
        this.messageListeners = new HashMap<>();

        HttpClient httpClient = HttpClient.newHttpClient();
        WebSocket.Listener wsListener = new WebSocket.Listener() {
            @Override
            public void onOpen(WebSocket webSocket) {
                WebSocket.Listener.super.onOpen(webSocket);
                webSocket.sendText("getCode", true);
            }

            // Textové správy sú zo servera
            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                Websocket.this.processServerMessage(data);
                return WebSocket.Listener.super.onText(webSocket, data, last);
            }

            // Binárne správy sú od protihráča
            @Override
            public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
                Websocket.this.processClientMessage(data);
                return WebSocket.Listener.super.onBinary(webSocket, data, last);
            }

            @Override
            public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
                Websocket.this.listener.onClosed();
                return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
            }
        };

        httpClient.newWebSocketBuilder().buildAsync(URI.create(SERVER_URL), wsListener)
                .thenApply(webSocket -> this.ws = webSocket);
    }

    public <T> void registerMessageListener(Class<T> message, IMessageListener<T> listener) {
        this.messageListeners.put(message, listener);
    }

    private void processClientMessage(ByteBuffer data) {
        byte[] dataArray = new byte[data.limit()];
        data.get(dataArray);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(dataArray);
        try {
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            Object object = objectStream.readObject();
            Class<?> messageClass = object.getClass();
            IMessageListener<?> handler = this.messageListeners.get(messageClass);
            messageClass.cast(object);
            if (handler != null) {
                handler.handle(object);
            } else {
                System.err.format("Message %s doesn't have a handler%n", messageClass);
            }
        } catch (IOException e) {
            System.err.println("Client Message reading failed");
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.err.println("Client Message class not found");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void connect(String kod) {
        this.ws.sendText("connect\n" + kod, true);
    }

    public void close() {
        if (this.ws != null) {
            this.ws.sendClose(WebSocket.NORMAL_CLOSURE, "");
        }
    }

    public void send(Serializable message) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(message);
            objectStream.flush();
        } catch (IOException e) {
            System.err.println("Client Message serialization failed");
            e.printStackTrace();
            System.exit(1);
        }
        byte[] serialized = byteStream.toByteArray();
        this.ws.sendBinary(ByteBuffer.wrap(serialized), true);
    }

    private void processServerMessage(CharSequence data) {
        String[] splitString = data.toString().split("\n", 2);
        String command = splitString[0];
        String rest = null;
        if (splitString.length > 1) {
            rest = splitString[1];
        }

        switch (command) {
            case "connectionCode" -> this.listener.onConnectedToServer(rest);
            case "connected" -> this.listener.onConnectedToPlayer(this, PlayerColor.fromString(rest));
            case "error" -> this.listener.onError(rest);
            default -> System.err.printf("Unknown command: %s%n", command);
        }
    }

    public interface IListener {
        void onConnectedToServer(String kod);
        void onConnectedToPlayer(Websocket ws, PlayerColor color);
        void onError(String error);
        void onClosed();
    }
}
