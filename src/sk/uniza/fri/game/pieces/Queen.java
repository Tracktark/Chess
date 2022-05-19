package sk.uniza.fri.game.pieces;

import sk.uniza.fri.game.ChessPiece;
import sk.uniza.fri.game.Chessboard;
import sk.uniza.fri.game.PlayerColor;
import sk.uniza.fri.game.Pozicia;

import java.util.ArrayList;

/**
 * Trieda predstavujúca figúrku Dáma
 *
 * @author Richard Závodský
 * @version 1.0.0
 */
public class Queen extends ChessPiece {

    public Queen(PlayerColor color) {
        super("queen", color);
    }

    public ArrayList<Pozicia> mozneTahy(Chessboard sachovnica) {
        ArrayList<Pozicia> tahy = new ArrayList<>();
        int[][] offsets = { {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1} };
        for (int[] offset : offsets) {
            int x = this.getX() + offset[0];
            int y = this.getY() + offset[1];
            while (x >= 0 && x < Chessboard.SIRKA && y >= 0 && y < Chessboard.VYSKA) {
                ChessPiece figurka = sachovnica.getPiece(x, y);
                if (figurka != null) {
                    if (figurka.getColor() != this.getColor()) {
                        tahy.add(new Pozicia(x, y));
                    }
                    break;
                } else {
                    tahy.add(new Pozicia(x, y));
                }

                x += offset[0];
                y += offset[1];
            }
        }
        return tahy;
    }
}
