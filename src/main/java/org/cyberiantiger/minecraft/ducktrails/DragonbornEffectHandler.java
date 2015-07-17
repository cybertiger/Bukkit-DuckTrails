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
 * limitations under the License.
 */
package org.cyberiantiger.minecraft.ducktrails;

import java.util.ArrayList;
import java.util.List;
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
public class DragonbornEffectHandler extends MusicalEffectHandler {
    private static final Note[] MELODY_DRAGONBORN1 = new Note[] {
        // 3/4 timing.
        // Key: C#, D#, F#, G#
        // 6 Notes per bar
        // Bar 1
        F_SHARP4, null, G_SHARP4, null, G_SHARP4, null,
        // Bar 2
        B4, null, C_SHARP5, null, C_SHARP5, null,
        // Bar 3
        D_SHARP5, E5, D_SHARP5, null, D_SHARP5, C_SHARP5,
        // Bar 4
        B4, null, C_SHARP5, null, null, null,
        // Bar 5
        F_SHARP4, null, G_SHARP4, null, G_SHARP4, null,
        // Bar 6
        B4, null, C_SHARP5, null, C_SHARP5, null,
        // Bar 7
        E5, null, D_SHARP5, null, D_SHARP5, C_SHARP5,
        // Bar 8
        B4, null, C_SHARP5, null, null, null,
        // Bar 9
        G_SHARP5, G_SHARP5, G_SHARP5, null, B4, null,
        // Bar 10
        C_SHARP5, null, E5, F_SHARP5, F_SHARP5, null,
        // Bar 11
        D_SHARP5, E5, D_SHARP5, null, D_SHARP5, C_SHARP5,
        // Bar 12
        B4, null, C_SHARP5, null, null, null,
        // Bar 13
        F_SHARP4, null, G_SHARP4, null, null, null,
        // Bar 14
        B4, null, C_SHARP5, null, null, null,
        // Bar 15
        E5, null, D_SHARP5, null, D_SHARP5, C_SHARP5,
        // Bar 16
        B4, null, C_SHARP5, null, null, null,
        // Bar 17
        G_SHARP5, G_SHARP5, G_SHARP5, null, B4, null,
        // Bar 18
        C_SHARP5, null, E5, null, F_SHARP5, null,
        // Bar 19
        F_SHARP5, D_SHARP5, C_SHARP5, null, G_SHARP5, null,
        // Bar 20
        C_SHARP5, null, C_SHARP5, null, null, null,
        // Bar 21
        F_SHARP4, null, G_SHARP4, null, null, null,
        // Bar 22
        B4, null, C_SHARP5, null, null, null,
        // Bar 23
        E5, null, D_SHARP5, null, D_SHARP5, C_SHARP5,
        // Bar 24
        B4, null, C_SHARP5, null, null, null,
        // Bar 25
        G_SHARP5, G_SHARP5, G_SHARP5, null, C_SHARP5, null,
        // Bar 26
        D_SHARP5, null, E5, null, E5, D_SHARP5,
        // Bar 27
        C_SHARP5, null, D_SHARP5, G_SHARP5, G_SHARP5, null,
        // Bar 28
        C_SHARP5, null, C_SHARP5, null, null, null,
        // Bar 29
        G_SHARP5, null, G_SHARP5, C_SHARP5, C_SHARP5, null,
        // Bar 30
        D_SHARP5, null, E5, F_SHARP5, F_SHARP5, null,
        // Bar 31
        E5, null, D_SHARP5, null, D_SHARP5, C_SHARP5,
        // Bar 32
        B4, null, C_SHARP5, null, null, null,
        // Bar 33
        C_SHARP5, null, null, null, null, null,
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
        null, null, C_SHARP4, null, null, null,
        // Bar 2
        null, null, F_SHARP4, null, null, null,
        // Bar 3
        null, null, G_SHARP4, null, null, null,
        // Bar 4
        null, null, C_SHARP4, null, null, null,
        // Bar 5
        null, null, C_SHARP4, null, null, null,
        // Bar 6
        null, null, F_SHARP4, null, null, null,
        // Bar 7
        null, null, G_SHARP4, null, null, null,
        // Bar 8
        null, null, C_SHARP4, null, null, null,
        // Bar 9
        null, null, E4, null, null, null,
        // Bar 10
        null, null, G_SHARP4, null, null, null,
        // Bar 11
        null, null, D_SHARP4, null, null, null,
        // Bar 12
        null, null, C_SHARP4, null, null, null,
        // Bar 13
        null, null, C_SHARP4, null, null, null,
        // Bar 14
        null, null, F_SHARP4, null, null, null,
        // Bar 15
        null, null, G_SHARP4, null, null, null,
        // Bar 16
        null, null, C_SHARP4, null, null, null,
        // Bar 17
        null, null, E4, null, null, null,
        // Bar 18
        null, null, G_SHARP4, null, null, null,
        // Bar 19
        null, null, D_SHARP4, null, null, null,
        // Bar 20
        null, null, C_SHARP4, null, null, null,
        // Bar 21
        null, null, C_SHARP4, null, null, null,
        // Bar 22
        null, null, F_SHARP4, null, null, null,
        // Bar 23
        null, null, G_SHARP4, null, null, null,
        // Bar 24
        null, null, C_SHARP4, null, null, null,
        // Bar 25
        null, null, E4, null, null, null,
        // Bar 26
        null, null, G_SHARP4, null, null, null,
        // Bar 27
        null, null, D_SHARP4, null, null, null,
        // Bar 28
        null, null, C_SHARP4, null, null, null,
        // Bar 29
        null, null, C_SHARP4, null, null, null,
        // Bar 30
        null, null, F_SHARP4, null, null, null,
        // Bar 31
        null, null, G_SHARP4, null, null, null,
        // Bar 32
        null, null, C_SHARP4, null, null, null,
        // Bar 33
        C_SHARP4, null, null, null, null, null,
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
    private final Effect effect;
    private final List<Vector> redCircle = new ArrayList<Vector>(20);
    private final List<Vector> blueCircle = new ArrayList<Vector>(20);
    private final Vector red = new Vector(1f, 0f, 0f);
    private final Vector blue = new Vector(0.01f, 0f, 1f);
    private double rotate = 0.0D;

    public DragonbornEffectHandler(Effect effect) {
        super(new Track[]{DRAGONBORN_TRACK1, DRAGONBORN_TRACK2});
        this.effect = effect;
        for (int i = 0; i < 20; i++) {
            double sin = Math.sin((i / 10d) * Math.PI);
            double cos = Math.cos((i / 10d) * Math.PI);
            redCircle.add(new Vector(0.2 * cos, 0.8 + 0.2 * sin, 0d));
            blueCircle.add(new Vector(0.2 * cos, -0.8 + 0.2 * sin, 0d));
        }
    }

    @Override
    protected void showEffectInternal(Server server, Player player, Location from, Location to) {
        super.showEffectInternal(server, player, from, to);
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
        to.add(0, player.getEyeHeight(), 0);
        double sin = Math.sin(rotate);
        double cos = Math.cos(rotate);
        // Vector sideways = cos, -sin, 0
        // Vector up = sin, cos, 0
        // Vector forwards = 0, 0, 1
        Vector xx = new Vector(cos * x.getX() - sin * y.getX(), cos * x.getY() - sin * y.getY(), cos * x.getZ() - sin * y.getZ());
        Vector yy = new Vector(sin * x.getX() + cos * y.getX(), sin * x.getY() + cos * y.getY(), sin * x.getZ() + cos * y.getZ());
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
