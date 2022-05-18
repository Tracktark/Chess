package sk.uniza.fri.game;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public class Chessboard {
    public static final int VYSKA = 8;
    public static final int SIRKA = 8;

    private final ChessPiece[][] sachovnica;

    public Chessboard() {
        this.sachovnica = new ChessPiece[VYSKA][SIRKA];
    }

    public ChessPiece getPiece(int x, int y) {
        if (x < 0 || x >= SIRKA || y < 0 || y >= VYSKA) {
            throw new IllegalArgumentException("Pozícia mimo šachovnice");
        }
        return this.sachovnica[y][x];
    }

    public void setPiece(int x, int y, ChessPiece piece) {
        if (x < 0 || x >= SIRKA || y < 0 || y >= VYSKA) {
            throw new IllegalArgumentException("Pozícia mimo šachovnice");
        }
        this.sachovnica[y][x] = piece;
        piece.nastavPoziciu(x, y);
    }
}
