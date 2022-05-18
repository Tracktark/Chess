package sk.uniza.fri.game;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public enum PlayerColor {
    BLACK,
    WHITE;

    public static PlayerColor fromString(String color) {
        return switch (color) {
            case "black" -> BLACK;
            case "white" -> WHITE;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case WHITE -> "white";
            case BLACK -> "black";
        };
    }
}
