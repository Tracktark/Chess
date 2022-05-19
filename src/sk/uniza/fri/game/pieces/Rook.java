package sk.uniza.fri.game.pieces;

import sk.uniza.fri.game.ChessPiece;
import sk.uniza.fri.game.Chessboard;
import sk.uniza.fri.game.PlayerColor;
import sk.uniza.fri.game.Pozicia;

import java.util.ArrayList;

/**
 * Trieda predstavujúca figúrku Veža
 *
 * @author Richard Závodský
 * @version 1.0.0
 */
public class Rook extends ChessPiece {
    private boolean didMove;

    public Rook(PlayerColor color) {
        super("rook", color);
        this.didMove = false;
    }

    public ArrayList<Pozicia> mozneTahy(Chessboard sachovnica) {
        ArrayList<Pozicia> tahy = new ArrayList<>();
        // East
        for (int x = this.getX() + 1; x < 8; x++) {
            ChessPiece figurka = sachovnica.getPiece(x, this.getY());
            if (figurka != null) {
                if (figurka.getColor() != this.getColor()) {
                    tahy.add(new Pozicia(x, this.getY()));
                }
                break;
            } else {
                tahy.add(new Pozicia(x, this.getY()));
            }
        }
        // West
        for (int x = this.getX() - 1; x >= 0; x--) {
            ChessPiece figurka = sachovnica.getPiece(x, this.getY());
            if (figurka != null) {
                if (figurka.getColor() != this.getColor()) {
                    tahy.add(new Pozicia(x, this.getY()));
                }
                break;
            } else {
                tahy.add(new Pozicia(x, this.getY()));
            }
        }
        // North
        for (int y = this.getY() + 1; y < 8; y++) {
            ChessPiece figurka = sachovnica.getPiece(this.getX(), y);
            if (figurka != null) {
                if (figurka.getColor() != this.getColor()) {
                    tahy.add(new Pozicia(this.getX(), y));
                }
                break;
            } else {
                tahy.add(new Pozicia(this.getX(), y));
            }
        }
        // South
        for (int y = this.getY() - 1; y >= 0; y--) {
            ChessPiece figurka = sachovnica.getPiece(this.getX(), y);
            if (figurka != null) {
                if (figurka.getColor() != this.getColor()) {
                    tahy.add(new Pozicia(this.getX(), y));
                }
                break;
            } else {
                tahy.add(new Pozicia(this.getX(), y));
            }
        }
        return tahy;
    }

    /**
     * Vráti, či sa veža od začiatku hry pohla
     * @return true ak sa veža pohla, inak false
     */
    public boolean didMove() {
        return this.didMove;
    }

    /**
     * Presunie vežu na inú pozíciu
     * Po zavolaní tejto metódy sa už nemôže vykonať rošáda
     * @param pozicia Nová pozícia
     */
    @Override
    public void nastavPoziciu(Pozicia pozicia) {
        super.nastavPoziciu(pozicia);
        this.didMove = true;
    }

    /**
     * Nastaví vežu na začiatočnú pozíciu
     * Tento posun sa nepočíta ako ťah
     * @param x X-ová súradnica začiatočnej pozície
     * @param y Y-ová súradnica začiatočnej pozície
     */
    @Override
    public void zaciatocnaPozicia(int x, int y) {
        super.nastavPoziciu(new Pozicia(x, y));
    }
}
