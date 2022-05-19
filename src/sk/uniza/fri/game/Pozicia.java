package sk.uniza.fri.game;

import java.io.Serializable;

/**
 * Predstavuje pozíciu na šachovnici
 * 
 * @author Richard Závodský
 * @version 1.0.0
 */
public record Pozicia(int x, int y) implements Serializable { }
