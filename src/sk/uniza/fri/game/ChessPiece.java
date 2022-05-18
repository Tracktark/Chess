package sk.uniza.fri.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Abstraktná trieda pre všetky typy figúrok
 * Obrázky figúrok musia byť uložené v priečinku images pod názvom white_nazovFigurky.png alebo black_nazovFigurky.png, napríklad white_rook.png
 * 
 * @author Richard Závodský
 * @version 1.0.0
 */
public abstract class ChessPiece implements Cloneable {
    private BufferedImage image;
    private int x;
    private int y;
    private final PlayerColor color;

    /**
     * Vytvorí novú figúrku
     * Tento konštruktor by sa nikdy nemal používať priamo, je len pre triedy jednotlivých figúrok
     * @param pieceName Názov figúrky, podľa neho sa bude vyhľadávať obrázok
     * @param color Farba figúrky
     */
    public ChessPiece(String pieceName, PlayerColor color) {
        this.color = color;
        this.x = 0;
        this.y = 0;

        String imagePath = String.format("images/%s_%s.png", this.color.toString(), pieceName);
        System.out.println(imagePath);
        URL imageFile = ChessPiece.class.getClassLoader().getResource(imagePath);
        try {
            assert imageFile != null;
            this.image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Nepodarilo sa načítať obrázok " + imagePath);
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Vypočíta všetky možné ťahy tejto figúrky na danej šachovnici
     * Pri tomto výpočte sú zahrnuté aj ťahy, ktoré by dostali kráľa do šachu
     * @return Všetky ťahy, ktoré figúrka môže spraviť
     */
    public abstract ArrayList<Pozicia> mozneTahy(Chessboard sachovnica);

    /**
     * Vráti X-ovú súradnicu figúrky
     * @return X-ová súradnica figúrky
     */
    public int getX() {
        return this.x;
    }

    /**
     * Vráti Y-ovú súradnicu figúrky
     * @return Y-ová súradnica figúrky
     */
    public int getY() {
        return this.y;
    }

    /**
     * Vráti, či je figúrka biela alebo čierna
     * @return true ak je biela, false ak je čierna
     */
    public PlayerColor getColor() {
        return this.color;
    }

    /**
     * Nastaví pozíciu figúrky
     * @param x X-ová súradnica novej pozície
     * @param y Y-ová súradnica novej pozície
     */
    public void nastavPoziciu(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Nastaví figúrku na jej začiatočnú pozíciu
     * Pre väčšinu figúrok je táto metóda rovnaká, ako {@link ChessPiece#nastavPoziciu(int, int)}, ale pre niektoré môže byť iná
     */
    public void zaciatocnaPozicia(int x, int y) {
        this.nastavPoziciu(x, y);
    }

    /**
     * Vytvorí kópiu tejto figúrky
     * Zmeny vykonané na kópií neovplyvňujú pôvodnú figúrku
     * @return Kópia figúrky */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
