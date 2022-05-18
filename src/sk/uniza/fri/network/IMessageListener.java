package sk.uniza.fri.network;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public interface IMessageListener<T> {
    void onMessage(T message);

    default void handle(Object message) {
        this.onMessage((T)message);
    }
}
