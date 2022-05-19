package sk.uniza.fri.game;

import java.io.Serializable;

/**
 * 17. 5. 2022 - 18:58
 *
 * @author RZ
 */
public record Pohyb(Pozicia odkial, Pozicia kam) implements Serializable {
}
