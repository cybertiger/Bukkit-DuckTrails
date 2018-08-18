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

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import static org.cyberiantiger.minecraft.ducktrails.Note.*;

/**
 *
 * @author antony
 */
public class DuckTalesEffectHandler extends MusicalEffectHandler {
    private static final Note[] DUCK_TALES_MELODY = new Note[]{ // 8 notes per bar.
        // key: C#, D#, F#, G#
        // bar 3
        E4, null, G_SHARP4, null, B4, null, C_SHARP5, null,
        // bar 4
        D5, null, D_SHARP5, C_SHARP5, null, B4, A4, null,
        // bar 5
        A4, null, null, G_SHARP4, null, null, null, null,
        // bar 6
        A4, null, null, G_SHARP4, null, null, null, null,
        // bar 7
        E4, null, G_SHARP4, null, B4, null, C_SHARP5, null,
        // bar 8
        D5, null, D_SHARP5, C_SHARP5, null, B4, A4, null,
        // bar 9
        D5, null, null, C_SHARP5, null, null, null, null,
        // bar 10
        D5, null, null, C_SHARP5, null, null, null, null,
        // bar 11
        null, null, F_SHARP4, null, A4, null, C_SHARP5, null,
        // bar 12
        C_SHARP5, null, null, B4, null, null, null, null,
        // bar 13
        null, null, C_SHARP5, null, E5, null, G_SHARP5, null,
        // bar 14
        G_SHARP5, null, null, F_SHARP5, null, G_SHARP5, G_SHARP5, null,
        // bar 15
        E5, null, E5, null, null, E5, B5, null,
        // bar 16
        G_SHARP5, null, E4, null, G_SHARP5, B4, C_SHARP5, E5,
        // bar 17
        E5, null, D5, null, C5, null, D_SHARP5, null,
        // bar 18
        E5, null, D5, null, C5, G5, F_SHARP5, null,
        // bar 19
        E5, null, E5, null, null, E5, B5, null,
        // bar 20
        G_SHARP5, null, null, G5, F_SHARP5, E5, D5, E5,
        // bar 21
        E5, null, D5, null, C5, null, D_SHARP5, null,
        // bar 22
        E5, null, D5, null, C5, G5, F_SHARP5, null,
        // bar 23
        E5, null, E5, null, null, null, null, null,
        // bar 24
        null, null, null, null, null, null, null, null,};
    static {
        // Pitch shift it so it fits between F#3, and F#5
        for (int i = 0; i < DUCK_TALES_MELODY.length; i++) {
            Note note = DUCK_TALES_MELODY[i];
            if (note != null) {
                DUCK_TALES_MELODY[i] = Note.values()[note.ordinal() - 2];
            }
        }
    }
    public static final Track DUCK_TALES_TRACK = new Track(Sound.BLOCK_NOTE_BLOCK_HARP) {
        @Override
        public Note getNote(int offset) {
            return DUCK_TALES_MELODY[offset % DUCK_TALES_MELODY.length];
        }

        @Override
        public float getVolume(int offset) {
            return 1f;
        }
    };
    private final double cos45 = Math.cos(Math.PI / 4);
    private final double sin45 = Math.sin(Math.PI / 4);
    private final Particle effect;
    private double rotate = 0.0D;

    public DuckTalesEffectHandler(Particle effect) {
        super(new Track[]{DUCK_TALES_TRACK});
        this.effect = effect;
    }

    @Override
    protected void showEffectInternal(Server server, Player player, Location from, Location to) {
        super.showEffectInternal(server, player, from, to);
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
        to.add(0, player.getEyeHeight(), 0);
        double sin = Math.sin(rotate);
        double cos = Math.cos(rotate);
        Vector offset = new Vector(-0.2 * sin, 0.2 * cos, -0.2);
        Vector velocity = new Vector(-0.1 * sin, 0.1 * cos, -0.2);
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
        Vector realOffset = new Vector(offset.getX() * x.getX() + offset.getY() * y.getX() + offset.getZ() * z.getX(), offset.getX() * x.getY() + offset.getY() * y.getY() + offset.getZ() * z.getY(), offset.getX() * x.getZ() + offset.getY() * y.getZ() + offset.getZ() * z.getZ());
        Vector realVelocity = new Vector(velocity.getX() * x.getX() + velocity.getY() * y.getX() + velocity.getZ() * z.getX(), velocity.getX() * x.getY() + velocity.getY() * y.getY() + velocity.getZ() * z.getY(), velocity.getX() * x.getZ() + velocity.getY() * y.getZ() + velocity.getZ() * z.getZ());
        base.setX(realOffset.getX() + base.getX());
        base.setY(realOffset.getY() + base.getY());
        base.setZ(realOffset.getZ() + base.getZ());
        Object data = new Particle.DustOptions(Color.ORANGE,.7f);
        sendEffect(server, player, effect, base, (float) realVelocity.getX(), (float) realVelocity.getY(), (float) realVelocity.getZ(), 1f, 256f,0,1,data);
    }
    
}
