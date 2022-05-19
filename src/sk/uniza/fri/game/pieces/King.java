package sk.uniza.fri.game.pieces;

import sk.uniza.fri.game.ChessPiece;
import sk.uniza.fri.game.Chessboard;
import sk.uniza.fri.game.PlayerColor;
import sk.uniza.fri.game.Pozicia;

import java.util.ArrayList;

/**
 * Trieda predstavujúca figúrku Kráľ
 *
 * @author Richard Závodský
 * @version 1.0.0
 */
public class King extends ChessPiece {
    private boolean didMove;

    public King(PlayerColor color) {
        super("king", color);
        this.didMove = false;
    }

    public ArrayList<Pozicia> mozneTahy(Chessboard sachovnica) {
        ArrayList<Pozicia> tahy = new ArrayList<>();
        int[][] offsets = new int[][] {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
        for (int[] offset : offsets) {
            int x = this.getX() + offset[0];
            int y = this.getY() + offset[1];
            if (x >= 0 && x < 8 && y >= 0 && y < 8) {
                ChessPiece figurka = sachovnica.getPiece(x, y);
                if (figurka == null || figurka.getColor() != this.getColor()) {
                    tahy.add(new Pozicia(x, y));
                }
            }
        }
        // Rošáda
        if (!this.didMove) {
            int y = this.getColor() == PlayerColor.WHITE ? 0 : 7;
            // Malá
            if (sachovnica.getPiece(7, y) instanceof Rook rook && !rook.didMove()
                && sachovnica.getPiece(6, y) == null && sachovnica.getPiece(5, y) == null) {
                tahy.add(new Pozicia(6, y));
            }

            // Veľká
            if (sachovnica.getPiece(0, y) instanceof Rook rook && !rook.didMove() &&
                sachovnica.getPiece(1, y) == null && sachovnica.getPiece(2, y) == null && sachovnica.getPiece(3, y) == null) {
                tahy.add(new Pozicia(2, y));
            }
        }
        return tahy;
    }

    /**
     * Presunie kráľa na inú pozíciu
     * Po zavolaní tejto metódy sa už nemôže vykonať rošáda
     * @param pozicia Nová pozícia
     */
    @Override
    public void nastavPoziciu(Pozicia pozicia) {
        super.nastavPoziciu(pozicia);
        this.didMove = true;
    }

    /**
     * Nastaví kráľa na začiatočnú pozíciu
     * Tento posun sa nepočíta ako ťah
     * @param x X-ová súradnica začiatočnej pozície
     * @param y Y-ová súradnica začiatočnej pozície
     */
    @Override
    public void zaciatocnaPozicia(int x, int y) {
        super.nastavPoziciu(new Pozicia(x, y));
    }
}
