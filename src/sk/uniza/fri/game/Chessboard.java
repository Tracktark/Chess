package sk.uniza.fri.game;

import sk.uniza.fri.game.pieces.Bishop;
import sk.uniza.fri.game.pieces.King;
import sk.uniza.fri.game.pieces.Knight;
import sk.uniza.fri.game.pieces.Pawn;
import sk.uniza.fri.game.pieces.Queen;
import sk.uniza.fri.game.pieces.Rook;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public class Chessboard {
    public static final int VYSKA = 8;
    public static final int SIRKA = 8;

    private ChessPiece[][] sachovnica;

    public Chessboard() {
        this.sachovnica = new ChessPiece[VYSKA][SIRKA];
        this.zakladnePostavenie();
    }

    public Chessboard(Chessboard staraSachovnica, Pohyb pohyb) {
        this.sachovnica = new ChessPiece[VYSKA][SIRKA];
        try {
            for (int y = 0; y < VYSKA; y++) {
                for (int x = 0; x < SIRKA; x++) {
                    ChessPiece oldPiece = staraSachovnica.sachovnica[y][x];
                    if (oldPiece != null) {
                        this.sachovnica[y][x] = (ChessPiece)oldPiece.clone();
                    } else {
                        this.sachovnica[y][x] = null;
                    }
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        this.movePiece(pohyb);
    }

    private void movePiece(Pohyb pohyb) {
        ChessPiece movedPiece = this.getPiece(pohyb.odkial());
        assert movedPiece != null : "Pohyb neexistujúcej figúrky";

        for (ChessPiece[] riadok : this.sachovnica) {
            for (ChessPiece piece : riadok) {
                if (piece instanceof Pawn pawn) {
                    pawn.resetDoubleStep();
                }
            }
        }

        if (movedPiece instanceof Pawn) {
            // Check for En Passant
            if (pohyb.odkial().x() != pohyb.kam().x() && this.getPiece(pohyb.kam()) == null) {
                this.sachovnica[pohyb.odkial().y()][pohyb.kam().x()] = null;
            }
        }
        // Check for castle
        if (movedPiece instanceof King && Math.abs(pohyb.odkial().x() - pohyb.kam().x()) == 2) {
            if (pohyb.kam().x() == 6) { // Kingside castle
                this.sachovnica[pohyb.kam().y()][5] = this.sachovnica[pohyb.kam().y()][7];
                this.sachovnica[pohyb.kam().y()][7] = null;
            } else { // Queenside castle
                this.sachovnica[pohyb.kam().y()][3] = this.sachovnica[pohyb.kam().y()][0];
                this.sachovnica[pohyb.kam().y()][0] = null;
            }
        }

        this.sachovnica[pohyb.odkial().y()][pohyb.odkial().x()] = null;
        this.sachovnica[pohyb.kam().y()][pohyb.kam().x()] = movedPiece;
        movedPiece.nastavPoziciu(pohyb.kam());
    }

    private void zakladnePostavenie() {
        if (VYSKA != 8 || SIRKA != 8) {
            throw new IllegalArgumentException("Pre základné postavenie musí byť výška a šírka 8");
        }

        PlayerColor w = PlayerColor.WHITE;
        PlayerColor b = PlayerColor.BLACK;

        this.sachovnica = new ChessPiece[][]{
                { new Rook(w), new Knight(w), new Bishop(w), new Queen(w), new King(w), new Bishop(w), new Knight(w), new Rook(w) },
                { new Pawn(w), new Pawn(w),   new Pawn(w),   new Pawn(w),  new Pawn(w), new Pawn(w),   new Pawn(w),   new Pawn(w) },
                { null,        null,          null,          null,         null,        null,          null,          null        },
                { null,        null,          null,          null,         null,        null,          null,          null        },
                { null,        null,          null,          null,         null,        null,          null,          null        },
                { null,        null,          null,          null,         null,        null,          null,          null        },
                { new Pawn(b), new Pawn(b),   new Pawn(b),   new Pawn(b),  new Pawn(b), new Pawn(b),   new Pawn(b),   new Pawn(b) },
                { new Rook(b), new Knight(b), new Bishop(b), new Queen(b), new King(b), new Bishop(b), new Knight(b), new Rook(b) },
        };

        for (int y = 0; y < VYSKA; y++) {
            for (int x = 0; x < SIRKA; x++) {
                ChessPiece piece = this.sachovnica[y][x];
                if (piece != null) {
                    piece.zaciatocnaPozicia(x, y);
                }
            }
        }
    }

    public ChessPiece getPiece(int x, int y) {
        if (x < 0 || x >= SIRKA || y < 0 || y >= VYSKA) {
            throw new IllegalArgumentException("Pozícia mimo šachovnice");
        }
        return this.sachovnica[y][x];
    }

    public ChessPiece getPiece(Pozicia pozicia) {
        return this.getPiece(pozicia.x(), pozicia.y());
    }
}
