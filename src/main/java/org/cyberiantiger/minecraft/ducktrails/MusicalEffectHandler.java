/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cyberiantiger.minecraft.ducktrails;

import java.util.concurrent.atomic.AtomicLong;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static org.cyberiantiger.minecraft.ducktrails.Note.*;

/**
 *
 * @author antony
 */
public abstract class MusicalEffectHandler extends EffectHandler {
    private static final Note[] NYAN_CAT_MELODY =
            new Note[] {
                D_SHARP4, // Bar 1
                E4,
                F_SHARP4,
                null,
                B4,
                null,
                D_SHARP4,
                E4,
                F_SHARP4,
                B4,
                C_SHARP5,
                D_SHARP5,
                C_SHARP5,
                A_SHARP4,
                B4,
                null,
                F_SHARP4, // Bar 2
                null,
                D_SHARP4,
                E4,
                F_SHARP4,
                null,
                B4,
                null,
                C_SHARP5,
                A_SHARP4,
                B4,
                C_SHARP5,
                E5,
                D_SHARP5,
                E5,
                C_SHARP5,
                F_SHARP4, // Bar 3
                null,
                G_SHARP4,
                null,
                D_SHARP4,
                D_SHARP4,
                null,
                B3,
                D4,
                C_SHARP4,
                B3,
                null,
                B3,
                null,
                C_SHARP4,
                null,
                D4, // Bar 4
                null,
                D4,
                C_SHARP4,
                B3,
                C_SHARP4,
                D_SHARP4,
                F_SHARP4,
                G_SHARP4,
                D_SHARP4,
                F_SHARP4,
                C_SHARP4,
                D_SHARP4,
                B3,
                C_SHARP4,
                B3,
                D_SHARP4, // Bar 5
                null,
                F_SHARP4,
                null,
                G_SHARP4,
                D_SHARP4,
                F_SHARP4,
                C_SHARP4,
                D_SHARP4,
                B3,
                D4,
                D_SHARP4,
                D4,
                C_SHARP4,
                B3,
                C_SHARP4,
                D4, // Bar 6
                null,
                B3,
                C_SHARP4,
                D_SHARP4,
                F_SHARP4,
                C_SHARP4,
                D_SHARP4,
                C_SHARP4,
                B3,
                C_SHARP4,
                null,
                B3,
                null,
                C_SHARP4,
                null,
                B3, // Bar 7
                null,
                F_SHARP3,
                G_SHARP3,
                B3,
                null,
                F_SHARP3,
                G_SHARP3,
                B3,
                C_SHARP4,
                D_SHARP4,
                B3,
                E4,
                D_SHARP4,
                E4,
                F_SHARP4,
                B3, // Bar 8
                null,
                B3,
                null,
                F_SHARP3,
                G_SHARP3,
                B3,
                F_SHARP3,
                E4,
                D_SHARP4,
                C_SHARP4,
                B3,
                F_SHARP3,
                null, // D_SHARP3,
                null, // E3
                F_SHARP3,
                B3, // Bar 9
                null,
                F_SHARP3,
                G_SHARP3,
                B3,
                null,
                F_SHARP3,
                G_SHARP3,
                B3,
                B3,
                C_SHARP4,
                D_SHARP4,
                B3,
                F_SHARP3,
                G_SHARP3,
                F_SHARP3,
                B3, // Bar 10
                null,
                B3,
                A_SHARP3,
                B3,
                F_SHARP3,
                G_SHARP3,
                B3,
                E4,
                D_SHARP4,
                E4,
                F_SHARP4,
                B3,
                null,
                A_SHARP3,
                null,
                B3, // Bar 11
                null,
                B3,
                A_SHARP3,
                B3,
                F_SHARP3,
                G_SHARP3,
                B3,
                E4,
                D_SHARP4,
                E4,
                F_SHARP4,
                B4,
                C_SHARP4,
            };

    public static final Track NYAN_CAT_MELODY_TRACK = new Track(Sound.NOTE_PIANO) {
        @Override
        public Note getNote(int offset) {
            // LOOP to 3rd Bar (16 notes per bar)
            if (offset >= NYAN_CAT_MELODY.length) {
                offset -= 32;
                offset %= NYAN_CAT_MELODY.length - 32;
                offset += 32;
            }
            return NYAN_CAT_MELODY[offset];
        }

        @Override
        public float getVolume(int offset) {
            return 1f;
        }
    };

    public static class NyanEffectHandler extends MusicalEffectHandler {
        private final Effect effect;

        public NyanEffectHandler(Effect effect) {
            super(new Track[] {NYAN_CAT_MELODY_TRACK});
            this.effect = effect;
        }

        @Override
        public void showEffect(Server server, Player player, Location from, Location to) {
            super.showEffect(server, player, from, to);
            for (int i = 0; i < 20; i++) {
                int argb = java.awt.Color.HSBtoRGB(i/20.0f, 1f, 1f);
                float r = ((argb >> 16) & 0xff) / 255f;
                float g = ((argb >> 8) & 0xff) / 255f;
                float b = (argb & 0xff) / 255f;
                r = r == 0f ? 0.001f : r;
                sendEffect(server, player, effect, to, r, g, b, 1f, 256f, 0);
                to.setY(to.getY() + 2/20d);
            }
        }
    }

    private static final long RESET_AFTER = 400000000L; // 0.4s
    private final Track[] track;
    private int trackOffset = 0;
    private AtomicLong lastMove = new AtomicLong(0L);

    public MusicalEffectHandler(Track[] track) {
        this.track = track;
    }

    public void showEffect(Server server, Player player, Location from, Location to) {
        lastMove.set(System.nanoTime());
    }

    public boolean isPlaying(long nanoTime) {
        boolean result = nanoTime < lastMove.get() + RESET_AFTER;
        if (!result) {
            trackOffset = 0;
        }
        return result;
    }

    public void playNext(Server server, Player player) {
        for (Track t : track) {
            t.playNote(player.getLocation(), trackOffset);
        }
        trackOffset++;
    }

    private static abstract class Track {
        private final Sound sound;
        public Track(Sound sound) {
            this.sound = sound;
        }

        public void playNote(Location location, int offset) {
            Note nextNote = getNote(offset);
            if (nextNote != null) {
                location.getWorld().playSound(location, sound, getVolume(offset), nextNote.pitch());
            }
        }

        public abstract Note getNote(int offset);
        public abstract float getVolume(int offset);
    }
}
