package sk.uniza.fri.game.pieces;

import sk.uniza.fri.game.ChessPiece;
import sk.uniza.fri.game.Chessboard;
import sk.uniza.fri.game.PlayerColor;
import sk.uniza.fri.game.Pozicia;

import java.util.ArrayList;

/**
 * Trieda predstavujúca figúrku Strelec
 *
 * @author Richard Závodský
 * @version 1.0.0
 */
public class Bishop extends ChessPiece {

    public Bishop(PlayerColor color) {
        super("bishop", color);
    }

    public ArrayList<Pozicia> mozneTahy(Chessboard sachovnica) {
        ArrayList<Pozicia> tahy = new ArrayList<>();
        // North East
        for (int x = this.getX() + 1, y = this.getY() + 1; x < 8 && y < 8; x++, y++) {
            ChessPiece figurka = sachovnica.getPiece(x, y);
            if (figurka != null) {
                if (this.getColor() != figurka.getColor()) {
                    tahy.add(new Pozicia(x, y));
                }
                break;
            } else {
                tahy.add(new Pozicia(x, y));
            }
        }
        // North West
        for (int x = this.getX() - 1, y = this.getY() + 1; x >= 0 && y < 8; x--, y++) {
            ChessPiece figurka = sachovnica.getPiece(x, y);
            if (figurka != null) {
                if (this.getColor() != figurka.getColor()) {
                    tahy.add(new Pozicia(x, y));
                }
                break;
            } else {
                tahy.add(new Pozicia(x, y));
            }
        }
        // South East
        for (int x = this.getX() + 1, y = this.getY() - 1; x < 8 && y >= 0; x++, y--) {
            ChessPiece figurka = sachovnica.getPiece(x, y);
            if (figurka != null) {
                if (this.getColor() != figurka.getColor()) {
                    tahy.add(new Pozicia(x, y));
                }
                break;
            } else {
                tahy.add(new Pozicia(x, y));
            }
        }
        // South West
        for (int x = this.getX() - 1, y = this.getY() - 1; x >= 0 && y >= 0; x--, y--) {
            ChessPiece figurka = sachovnica.getPiece(x, y);
            if (figurka != null) {
                if (this.getColor() != figurka.getColor()) {
                    tahy.add(new Pozicia(x, y));
                }
                break;
            } else {
                tahy.add(new Pozicia(x, y));
            }
        }
        return tahy;
    }
}
