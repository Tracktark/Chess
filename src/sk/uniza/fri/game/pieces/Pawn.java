package sk.uniza.fri.game.pieces;

import sk.uniza.fri.game.ChessPiece;
import sk.uniza.fri.game.Chessboard;
import sk.uniza.fri.game.PlayerColor;
import sk.uniza.fri.game.Pozicia;

import java.util.ArrayList;

/**
 * Trieda predstavujúca figúrku Pešiak
 *
 * @author Richard Závodský
 * @version 1.0.0
 */
public class Pawn extends ChessPiece {
    private boolean didDoubleStep;

    public Pawn(PlayerColor color) {
        super("pawn", color);
        this.didDoubleStep = false;
    }

    public ArrayList<Pozicia> mozneTahy(Chessboard sachovnica) {
        ArrayList<Pozicia> tahy = new ArrayList<>();
        int yOffset = this.getColor() == PlayerColor.WHITE ? 1 : -1;
        int newY = this.getY() + yOffset;
        if (newY < 8 && newY >= 0) {
            if (sachovnica.getPiece(this.getX(), newY) == null) {
                tahy.add(new Pozicia(this.getX(), newY));

                int baseLine = this.getColor() == PlayerColor.WHITE ? 1 : 6;
                if (this.getY() == baseLine && sachovnica.getPiece(this.getX(), baseLine + 2 * yOffset) == null) {
                    tahy.add(new Pozicia(this.getX(), baseLine + 2 * yOffset));
                }
            }
            for (int newX : new int[] {this.getX() + 1, this.getX() - 1}) {
                if (newX >= 0 && newX < 8) {
                    ChessPiece figurka = sachovnica.getPiece(newX, newY);
                    if (figurka != null && figurka.getColor() != this.getColor()) {
                        tahy.add(new Pozicia(newX, newY));
                    }
                    // En Passant
                    figurka = sachovnica.getPiece(newX, this.getY());
                    if (figurka instanceof Pawn pesiak) {
                        if (pesiak.didDoubleStep()) {
                            tahy.add(new Pozicia(newX, newY));
                        }
                    }
                }
            }
        }
        return tahy;
    }

    /**
     * Vráti, či sa tento pešiak minulý ťah posunula o 2 políčka
     * @return true, ak sa pešiak posunul minulý ťah o 2 políčka, inak false
     */
    public boolean didDoubleStep() {
        return this.didDoubleStep;
    }

    /**
     * Vyresetuje parameter didDoubleStep na false.
     * Táto metóda by mala byť zavolaná pred každým vykonaným pohybom pre všetkých pešiakov
     */
    public void resetDoubleStep() {
        this.didDoubleStep = false;
    }

    @Override
    public void nastavPoziciu(Pozicia pozicia) {
        if (Math.abs(this.getY() - pozicia.y()) == 2) {
            this.didDoubleStep = true;
        }
        super.nastavPoziciu(pozicia);
    }
}
