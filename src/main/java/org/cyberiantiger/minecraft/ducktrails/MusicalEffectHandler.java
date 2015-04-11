/*
 * Copyright 2015 Antony Riley
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.Copyright [yyyy] [name of copyright owner]
 */
package org.cyberiantiger.minecraft.ducktrails;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static org.cyberiantiger.minecraft.ducktrails.Note.*;

/**
 *
 * @author antony
 */
public abstract class MusicalEffectHandler extends EffectHandler {

    private static final Note[] NYAN_CAT_MELODY = new Note[]{
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
        C_SHARP4,};
    private static final Note[] PFUDOR_MELODY = new Note[]{
        C5, // Bar 1
        null,
        A4,
        C5,
        A4,
        G4,
        null,
        F4,
        D4,
        F4,
        G4,
        C5,
        null,
        C4,
        null,
        null,};
    private static final Note[] DUCK_TALES_MELODY = new Note[]{
        // 8 notes per bar.
        // key: C#, D#, F#, G#
        // bar 3
        E4,
        null,
        G_SHARP4,
        null,
        B4,
        null,
        C_SHARP5,
        null,
        // bar 4
        D5,
        null,
        D_SHARP5,
        C_SHARP5,
        null,
        B4,
        A4,
        null,
        // bar 5
        A4,
        null,
        null,
        G_SHARP4,
        null,
        null,
        null,
        null,
        // bar 6
        A4,
        null,
        null,
        G_SHARP4,
        null,
        null,
        null,
        null,
        // bar 7
        E4,
        null,
        G_SHARP4,
        null,
        B4,
        null,
        C_SHARP5,
        null,
        // bar 8
        D5,
        null,
        D_SHARP5,
        C_SHARP5,
        null,
        B4,
        A4,
        null,
        // bar 9
        D5,
        null,
        null,
        C_SHARP5,
        null,
        null,
        null,
        null,
        // bar 10
        D5,
        null,
        null,
        C_SHARP5,
        null,
        null,
        null,
        null,
        // bar 11
        null,
        null,
        F_SHARP4,
        null,
        A4,
        null,
        C_SHARP5,
        null,
        // bar 12
        C_SHARP5,
        null,
        null,
        B4,
        null,
        null,
        null,
        null,
        // bar 13
        null,
        null,
        C_SHARP5,
        null,
        E5,
        null,
        G_SHARP5,
        null,
        // bar 14
        G_SHARP5,
        null,
        null,
        F_SHARP5,
        null,
        G_SHARP5,
        G_SHARP5,
        null,
        // bar 15
        E5,
        null,
        E5,
        null,
        null,
        E5,
        B5,
        null,
        // bar 16
        G_SHARP5,
        null,
        E4,
        null,
        G_SHARP5,
        B4,
        C_SHARP5,
        E5,
        // bar 17
        E5,
        null,
        D5,
        null,
        C5,
        null,
        D_SHARP5,
        null,
        // bar 18
        E5,
        null,
        D5,
        null,
        C5,
        G5,
        F_SHARP5,
        null,
        // bar 19
        E5,
        null,
        E5,
        null,
        null,
        E5,
        B5,
        null,
        // bar 20
        G_SHARP5,
        null,
        null,
        G5,
        F_SHARP5,
        E5,
        D5,
        E5,
        // bar 21
        E5,
        null,
        D5,
        null,
        C5,
        null,
        D_SHARP5,
        null,
        // bar 22
        E5,
        null,
        D5,
        null,
        C5,
        G5,
        F_SHARP5,
        null,
        // bar 23
        E5,
        null,
        E5,
        null,
        null,
        null,
        null,
        null,
        // bar 24
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,};
    static {
        // Pitch shift it so it fits between F#3, and F#5
        for (int i = 0; i < DUCK_TALES_MELODY.length; i++) {
            Note note = DUCK_TALES_MELODY[i];
            if (note != null) {
                DUCK_TALES_MELODY[i] = Note.values()[note.ordinal() - 2];
            }
        }
    }
    private static final Note[] MELODY_DRAGONBORN1 = new Note[] {
        // 3/4 timing.
        // Key: C#, D#, F#, G#
        // 6 Notes per bar
        // Bar 1
        F_SHARP4,
        null,
        G_SHARP4,
        null,
        G_SHARP4,
        null,
        // Bar 2
        B4,
        null,
        C_SHARP5,
        null,
        C_SHARP5,
        null,
        // Bar 3
        D_SHARP5,
        E5,
        D_SHARP5,
        null,
        D_SHARP5,
        C_SHARP5,
        // Bar 4
        B4,
        null,
        C_SHARP5,
        null,
        null,
        null,
        // Bar 5
        F_SHARP4,
        null,
        G_SHARP4,
        null,
        G_SHARP4,
        null,
        // Bar 6
        B4,
        null,
        C_SHARP5,
        null,
        C_SHARP5,
        null,
        // Bar 7
        E5,
        null,
        D_SHARP5,
        null,
        D_SHARP5,
        C_SHARP5,
        // Bar 8
        B4,
        null,
        C_SHARP5,
        null,
        null,
        null,
        // Bar 9
        G_SHARP5,
        G_SHARP5,
        G_SHARP5,
        null,
        B4,
        null,
        // Bar 10
        C_SHARP5,
        null,
        E5,
        F_SHARP5,
        F_SHARP5,
        null,
        // Bar 11
        D_SHARP5,
        E5,
        D_SHARP5,
        null,
        D_SHARP5,
        C_SHARP5,
        // Bar 12
        B4,
        null,
        C_SHARP5,
        null,
        null,
        null,
        // Bar 13
        F_SHARP4,
        null,
        G_SHARP4,
        null,
        null,
        null,
        // Bar 14
        B4,
        null,
        C_SHARP5,
        null,
        null,
        null,
        // Bar 15
        E5,
        null,
        D_SHARP5,
        null,
        D_SHARP5,
        C_SHARP5,
        // Bar 16
        B4,
        null,
        C_SHARP5,
        null,
        null,
        null,
        // Bar 17
        G_SHARP5,
        G_SHARP5,
        G_SHARP5,
        null,
        B4,
        null,
        // Bar 18
        C_SHARP5,
        null,
        E5,
        null,
        F_SHARP5,
        null,
        // Bar 19
        F_SHARP5,
        D_SHARP5,
        C_SHARP5,
        null,
        G_SHARP5,
        null,
        // Bar 20
        C_SHARP5,
        null,
        C_SHARP5,
        null,
        null,
        null,
        // Bar 21
        F_SHARP4,
        null,
        G_SHARP4,
        null,
        null,
        null,
        // Bar 22
        B4,
        null,
        C_SHARP5,
        null,
        null,
        null,
        // Bar 23
        E5,
        null,
        D_SHARP5,
        null,
        D_SHARP5,
        C_SHARP5,
        // Bar 24
        B4,
        null,
        C_SHARP5,
        null,
        null,
        null,
        // Bar 25
        G_SHARP5,
        G_SHARP5,
        G_SHARP5,
        null,
        C_SHARP5,
        null,
        // Bar 26
        D_SHARP5,
        null,
        E5,
        null,
        E5,
        D_SHARP5,
        // Bar 27
        C_SHARP5,
        null,
        D_SHARP5,
        G_SHARP5,
        G_SHARP5,
        null,
        // Bar 28
        C_SHARP5,
        null,
        C_SHARP5,
        null,
        null,
        null,
        // Bar 29
        G_SHARP5,
        null,
        G_SHARP5,
        C_SHARP5,
        C_SHARP5,
        null,
        // Bar 30
        D_SHARP5,
        null,
        E5,
        F_SHARP5,
        F_SHARP5,
        null,
        // Bar 31
        E5,
        null,
        D_SHARP5,
        null,
        D_SHARP5,
        C_SHARP5,
        // Bar 32
        B4,
        null,
        C_SHARP5,
        null,
        null,
        null,
        // Bar 33
        C_SHARP5,
        null,
        null,
        null,
        null,
        null,
    };
    static {
        // Pitch shift it so it fits between F#3, and F#5
        for (int i = 0; i < MELODY_DRAGONBORN1.length; i++) {
            Note note = MELODY_DRAGONBORN1[i];
            if (note != null) {
                MELODY_DRAGONBORN1[i] = Note.values()[note.ordinal() - 2];
            }
        }
    }
    private static final Note[] MELODY_DRAGONBORN2 = new Note[] {
        // 3/4 timing.
        // Key: C#, D#, F#, G#
        // 6 Notes per bar
        // Bar 1
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 2
        null,
        null,
        F_SHARP4,
        null,
        null,
        null,
        // Bar 3
        null,
        null,
        G_SHARP4,
        null,
        null,
        null,
        // Bar 4
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 5
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 6
        null,
        null,
        F_SHARP4,
        null,
        null,
        null,
        // Bar 7
        null,
        null,
        G_SHARP4,
        null,
        null,
        null,
        // Bar 8
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 9
        null,
        null,
        E4,
        null,
        null,
        null,
        // Bar 10
        null,
        null,
        G_SHARP4,
        null,
        null,
        null,
        // Bar 11
        null,
        null,
        D_SHARP4,
        null,
        null,
        null,
        // Bar 12
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 13
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 14
        null,
        null,
        F_SHARP4,
        null,
        null,
        null,
        // Bar 15
        null,
        null,
        G_SHARP4,
        null,
        null,
        null,
        // Bar 16
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 17
        null,
        null,
        E4,
        null,
        null,
        null,
        // Bar 18
        null,
        null,
        G_SHARP4,
        null,
        null,
        null,
        // Bar 19
        null,
        null,
        D_SHARP4,
        null,
        null,
        null,
        // Bar 20
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 21
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 22
        null,
        null,
        F_SHARP4,
        null,
        null,
        null,
        // Bar 23
        null,
        null,
        G_SHARP4,
        null,
        null,
        null,
        // Bar 24
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 25
        null,
        null,
        E4,
        null,
        null,
        null,
        // Bar 26
        null,
        null,
        G_SHARP4,
        null,
        null,
        null,
        // Bar 27
        null,
        null,
        D_SHARP4,
        null,
        null,
        null,
        // Bar 28
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 29
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 30
        null,
        null,
        F_SHARP4,
        null,
        null,
        null,
        // Bar 31
        null,
        null,
        G_SHARP4,
        null,
        null,
        null,
        // Bar 32
        null,
        null,
        C_SHARP4,
        null,
        null,
        null,
        // Bar 33
        C_SHARP4,
        null,
        null,
        null,
        null,
        null,
    };
    static {
        // Pitch shift it so it fits between F#3, and F#5
        for (int i = 0; i < MELODY_DRAGONBORN2.length; i++) {
            Note note = MELODY_DRAGONBORN2[i];
            if (note != null) {
                MELODY_DRAGONBORN2[i] = Note.values()[note.ordinal() - 2];
            }
        }
    }
    public static final Track DRAGONBORN_TRACK1 = new Track(Sound.NOTE_PIANO) {
        @Override
        public Note getNote(int offset) {
            return MELODY_DRAGONBORN1[offset % MELODY_DRAGONBORN1.length];
        }

        @Override
        public float getVolume(int offset) {
            return 1f;
        }
    };
    public static final Track DRAGONBORN_TRACK2 = new Track(Sound.NOTE_PIANO) {
        @Override
        public Note getNote(int offset) {
            return MELODY_DRAGONBORN2[offset % MELODY_DRAGONBORN2.length];
        }

        @Override
        public float getVolume(int offset) {
            return 1f;
        }
    };
    public static final Track DUCK_TALES_TRACK = new Track(Sound.NOTE_PIANO) {
        @Override
        public Note getNote(int offset) {
            return DUCK_TALES_MELODY[offset % DUCK_TALES_MELODY.length];
        }

        @Override
        public float getVolume(int offset) {
            return 1f;
        }
    };
    public static final Track PFUDOR_MELODY_TRACK = new Track(Sound.NOTE_PIANO) {
        @Override
        public Note getNote(int offset) {
            return PFUDOR_MELODY[offset % PFUDOR_MELODY.length];
        }

        @Override
        public float getVolume(int offset) {
            return 1f;
        }
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

    public static class DragonbornEffectHandler extends MusicalEffectHandler {
        private final Effect effect;
        private final List<Vector> redCircle = new ArrayList<Vector>(20);
        private final List<Vector> blueCircle = new ArrayList<Vector>(20);
        private final Vector red = new Vector(1f,0f,0f);
        private final Vector blue = new Vector(0.01f, 0f, 1f);
        private double rotate = 0.0D;
        public DragonbornEffectHandler(Effect effect) {
            super(new Track[] { DRAGONBORN_TRACK1, DRAGONBORN_TRACK2 });
            this.effect = effect;
            for (int i = 0; i < 20; i++) {
                double sin = Math.sin((i/10d) * Math.PI);
                double cos = Math.cos((i/10d) * Math.PI);
                redCircle.add(new Vector(0.2*cos, 0.8 + 0.2*sin, 0d));
                blueCircle.add(new Vector(0.2*cos, -0.8 + 0.2*sin, 0d));
            }
        }
        @Override
        public void showEffect(Server server, Player player, Location from, Location to) {
            super.showEffect(server, player, from, to);
            Vector z = to.toVector().subtract(from.toVector());
            double movement = z.length();
            rotate += movement / 2; // rotate effects 360 degrees
            z.multiply(1 / movement);
            Vector x = new Vector(-z.getZ(), 0, z.getX());
            double sideLength = x.length();
            if (sideLength == 0D) {
                x.setX(1D);
                x.setZ(0D);
            } else {
                x.multiply(1 / sideLength);
            }
            Vector y = x.clone();
            y.crossProduct(z);
            to.add(0,player.getEyeHeight(),0);

            double sin = Math.sin(rotate);
            double cos = Math.cos(rotate);

            // Vector sideways = cos, -sin, 0
            // Vector up = sin, cos, 0
            // Vector forwards = 0, 0, 1
            Vector xx = new Vector(
                    cos * x.getX() - sin * y.getX(),
                    cos * x.getY() - sin * y.getY(),
                    cos * x.getZ() - sin * y.getZ());
            Vector yy = new Vector(
                    sin * x.getX() + cos * y.getX(),
                    sin * x.getY() + cos * y.getY(),
                    sin * x.getZ() + cos * y.getZ());

            for (Vector v : redCircle) {
                translateEffect(server, player, to, v, 1f, 0f, 0f, xx, yy, z);
            }
            for (Vector v : blueCircle) {
                translateEffect(server, player, to, v, 0.01f, 0f, 1f, xx, yy, z);
            }
        }

        private void translateEffect(Server server, Player player, Location base, Vector offset, float r, float g, float b, Vector x, Vector y, Vector z) {
            base = base.clone();
            Vector realOffset = new Vector(
                    offset.getX() * x.getX() + offset.getY() * y.getX() + offset.getZ() * z.getX(),
                    offset.getX() * x.getY() + offset.getY() * y.getY() + offset.getZ() * z.getY(),
                    offset.getX() * x.getZ() + offset.getY() * y.getZ() + offset.getZ() * z.getZ());
            base.setX(realOffset.getX() + base.getX());
            base.setY(realOffset.getY() + base.getY());
            base.setZ(realOffset.getZ() + base.getZ());
            sendEffect(server, player, effect, base, r, g, b, 1f, 256f, 0);
        }
    }

    public static class DuckTalesEffectHandler extends MusicalEffectHandler {

        private final double cos45 = Math.cos(Math.PI/4);
        private final double sin45 = Math.sin(Math.PI/4);
        private final Effect effect;
        private double rotate = 0.0D;

        public DuckTalesEffectHandler(Effect effect) {
            super(new Track[]{DUCK_TALES_TRACK});
            this.effect = effect;
        }

        @Override
        public void showEffect(Server server, Player player, Location from, Location to) {
            super.showEffect(server, player, from, to);
            Vector z = to.toVector().subtract(from.toVector());
            double movement = z.length();
            rotate += movement / 4; // rotate effects 360 degrees
            z.multiply(1 / movement);
            Vector x = new Vector(-z.getZ(), 0, z.getX());
            double sideLength = x.length();
            if (sideLength == 0D) {
                x.setX(1D);
                x.setZ(0D);
            } else {
                x.multiply(1 / sideLength);
            }
            Vector y = x.clone();
            y.crossProduct(z);
            to.add(0,player.getEyeHeight(),0);

            double sin = Math.sin(rotate);
            double cos = Math.cos(rotate);
            Vector offset = new Vector (-0.2 * sin, 0.2 * cos, -0.2);
            Vector velocity = new Vector (-0.1 * sin, 0.1 * cos, -0.2);
            translateEffect(server, player, to, offset, velocity, x, y, z);
            for (int i = 0; i < 7; i++) {
                double offsetX = offset.getX();
                double offsetY = offset.getY();
                double velocityX = velocity.getX();
                double velocityY = velocity.getY();
                offset.setX(offsetX * cos45 - offsetY * sin45);
                offset.setY(offsetX * sin45 + offsetY * cos45);
                velocity.setX(velocityX * cos45 - velocityY * sin45);
                velocity.setY(velocityX * sin45 + velocityY * cos45);
                translateEffect(server, player, to, offset, velocity, x, y, z);
            }
        }

        private void translateEffect(Server server, Player player, Location base, Vector offset, Vector velocity, Vector x, Vector y, Vector z) {
            base = base.clone();
            Vector realOffset = new Vector(
                    offset.getX() * x.getX() + offset.getY() * y.getX() + offset.getZ() * z.getX(),
                    offset.getX() * x.getY() + offset.getY() * y.getY() + offset.getZ() * z.getY(),
                    offset.getX() * x.getZ() + offset.getY() * y.getZ() + offset.getZ() * z.getZ());
            Vector realVelocity = new Vector(
                    velocity.getX() * x.getX() + velocity.getY() * y.getX() + velocity.getZ() * z.getX(),
                    velocity.getX() * x.getY() + velocity.getY() * y.getY() + velocity.getZ() * z.getY(),
                    velocity.getX() * x.getZ() + velocity.getY() * y.getZ() + velocity.getZ() * z.getZ());
            base.setX(realOffset.getX() + base.getX());
            base.setY(realOffset.getY() + base.getY());
            base.setZ(realOffset.getZ() + base.getZ());
            sendEffect(server, player, effect, base, (float)realVelocity.getX(), (float)realVelocity.getY(), (float)realVelocity.getZ(), 1f, 256f, 0);
        }
    }

    public static class NyanEffectHandler extends MusicalEffectHandler {

        private final Effect effect;

        public NyanEffectHandler(Effect effect) {
            super(new Track[]{NYAN_CAT_MELODY_TRACK});
            this.effect = effect;
        }

        @Override
        public void showEffect(Server server, Player player, Location from, Location to) {
            super.showEffect(server, player, from, to);
            Location toCopy = to.clone();
            for (int i = 0; i < 20; i++) {
                int argb = java.awt.Color.HSBtoRGB(i / 20.0f, 1f, 1f);
                float r = ((argb >> 16) & 0xff) / 255f;
                float g = ((argb >> 8) & 0xff) / 255f;
                float b = (argb & 0xff) / 255f;
                r = r == 0f ? 0.001f : r;
                sendEffect(server, player, effect, toCopy, r, g, b, 1f, 256f, 0);
                toCopy.setY(toCopy.getY() + 2 / 20d);
            }
        }
    }

    public static class PFUDOREffectHandler extends MusicalEffectHandler {

        private final Effect effect;
        double distance = 0.0D;

        public PFUDOREffectHandler(Effect effect) {
            super(new Track[]{PFUDOR_MELODY_TRACK});
            this.effect = effect;
        }

        @Override
        public void showEffect(Server server, Player player, Location from, Location to) {
            super.showEffect(server, player, from, to);
            Vector forwards = from.toVector().subtract(to.toVector());
            double movement = forwards.length();
            forwards.multiply(1 / movement);
            Vector sideways = new Vector(forwards.getZ(), 0, -forwards.getX());
            double sideLength = sideways.length();
            if (sideLength == 0) {
                sideways.setX(1D);
                sideways.setZ(0D);
            } else {
                sideways.multiply(1 / sideLength);
            }
            distance += movement;
            // Show pink fluffy unicorn every 8 blocks
            if (distance >= 8.0D) {
                distance = 0.0D;
                Vector up = forwards.clone();
                up.crossProduct(sideways);
                Location pony = to.clone();
                pony.add(up);
                double baseX = pony.getX() - 0.5;
                double baseY = pony.getY() - 0.5;
                double baseZ = pony.getZ() - 0.5;
                for (int i = 0; i < 50; i++) {
                    pony.setX(baseX + Math.random());
                    pony.setY(baseY + Math.random());
                    pony.setZ(baseZ + Math.random());
                    sendEffect(server, player, Effect.POTION_SWIRL, pony, 1f, 0.7f, 0.7f, 1f, 256f, 0);
                }
            }

            sideways.multiply(0.5D);
            to.subtract(sideways);
            sideways.multiply(1 / 8D);

            for (int i = 0; i < 17; i++) {
                int argb = java.awt.Color.HSBtoRGB(i / 15.0f, 1f, 1f);
                float r = ((argb >> 16) & 0xff) / 255f;
                float g = ((argb >> 8) & 0xff) / 255f;
                float b = (argb & 0xff) / 255f;
                r = r == 0f ? 0.001f : r;
                sendEffect(server, player, effect, to, r, g, b, 1f, 256f, 0);
                to.add(sideways);
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
