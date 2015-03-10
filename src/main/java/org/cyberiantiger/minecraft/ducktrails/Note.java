/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cyberiantiger.minecraft.ducktrails;

/**
 *
 * @author antony
 */
public enum Note {
    F_SHARP3,
    G3,
    G_SHARP3,
    A3,
    A_SHARP3,
    B3,
    C4,
    C_SHARP4,
    D4,
    D_SHARP4,
    E4,
    F4,
    F_SHARP4,
    G4,
    G_SHARP4,
    A4,
    A_SHARP4,
    B4,
    C5,
    C_SHARP5,
    D5,
    D_SHARP5,
    E5,
    F5,
    F_SHARP5,
    G5,
    G_SHARP5,
    A5,
    A_SHARP5,
    B5,
    C6,
    C_SHARP6,
    D6,
    D_SHARP6,
    E6,
    F6,
    F_SHARP6;

    private float pitch;

    private Note() {
        this.pitch = (float) Math.pow(2, (ordinal() - 12) / 12d);
    }

    public float pitch() {
        return pitch;
    }
}