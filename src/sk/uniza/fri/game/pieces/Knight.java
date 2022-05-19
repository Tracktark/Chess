package sk.uniza.fri.game.pieces;

import sk.uniza.fri.game.ChessPiece;
import sk.uniza.fri.game.Chessboard;
import sk.uniza.fri.game.PlayerColor;
import sk.uniza.fri.game.Pozicia;

import java.util.ArrayList;

/**
 * Trieda predstavujúca figúrku Jazdec
 *
 * @author Richard Závodský
 * @version 1.0.0
 */
public class Knight extends ChessPiece {

    public Knight(PlayerColor color) {
        super("knight", color);
    }

    public ArrayList<Pozicia> mozneTahy(Chessboard sachovnica) {
        ArrayList<Pozicia> tahy = new ArrayList<>();
        int[][] offsets = new int[][] {{1, 2}, {-1, 2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, -2}, {-1, -2}};
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
        return tahy;
    }
}
