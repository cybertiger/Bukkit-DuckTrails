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

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import static org.cyberiantiger.minecraft.ducktrails.Note.*;


/**
 *
 * @author antony
 */
public class GameOfThronesEffectHandler extends MusicalEffectHandler {
    public static final Note[] GAME_OF_THRONES_MELODY= new Note[] {
        // Bar 1.
        G4,null,C4,null,D_SHARP4,F4,G4,null,C4,null,D_SHARP4,F4,
        // Bar 2
        G4,null,C4,null,D_SHARP4,F4,G4,null,C4,null,D_SHARP4,F4,
        // Bar 3
        G4,null,C4,null,E4,F4,G4,null,C4,null,D_SHARP4,F4,
        // Bar 4
        G4,null,C4,null,E4,F4,G4,null,C4,null,D_SHARP4,F4,
        // Bar 5
        G4,null,null,null,null,null,C4,null,null,null,null,null,
        // Bar 6
        D_SHARP4,F4,G4,null,null,null,C4,null,null,null,D_SHARP4,F4,
        // Bar 7
        D4,null,G3,null,A_SHARP3,C4,D4,null,G3,null,A_SHARP3,C4,
        // Bar 8
        D4,null,G3,null,A_SHARP3,C4,D4,null,G3,null,A_SHARP3,C4,
        // Bar 9
        F4,null,null,null,null,null,A_SHARP3,null,null,null,null,null,
        // Bar 10
        D_SHARP4,D4,F4,null,null,null,A_SHARP3,null,null,null,D_SHARP4,D4,
        // Bar 11
        C4,null,F3,null,G_SHARP3,A_SHARP3,C4,null,F3,null,G_SHARP3,A_SHARP3,
        // Bar 12
        C4,null,F3,null,G_SHARP3,A_SHARP3,C4,null,F3,null,G_SHARP3,A_SHARP3,
        // Bar 13
        G4,null,null,null,null,null,C4,null,null,null,null,null,
        // Bar 14
        D_SHARP4,F4,G4,null,null,null,C4,null,null,null,D_SHARP4,F4,
        // Bar 15
        D4,null,G3,null,A_SHARP3,C4,D4,null,G3,null,A_SHARP3,C4,
        // Bar 16
        D4,null,G3,null,A_SHARP3,C4,D4,null,G3,null,A_SHARP3,C4,
        // Bar 17
        F4,null,null,null,null,null,A_SHARP3,null,null,null,null,null,
        // Bar 18
        D_SHARP4,D4,F4,null,null,null,A_SHARP3,null,null,null,D_SHARP4,D4,
        // Bar 19
        C4,null,G3,null,G_SHARP3,A_SHARP3,C4,null,G3,null,G_SHARP3,A_SHARP3,
        // Bar 20
        C4,null,G3,null,G_SHARP3,A_SHARP3,C4,null,G3,null,G_SHARP3,A_SHARP3,
        // Bar 21
        // G4,null,null,null,null,null,C4,null,null,null,null,null
        null,null,null,null,null,null,null,null,null,null,null,null
    };
    static {
        // Pitch shift it so it fits between F#3, and F#5
        for (int i = 0; i < GAME_OF_THRONES_MELODY.length; i++) {
            Note note = GAME_OF_THRONES_MELODY[i];
            if (note != null) {
                GAME_OF_THRONES_MELODY[i] = Note.values()[note.ordinal() + 1];
            }
        }
    }
    public static final Track GAME_OF_THRONES_TRACK = new Track(Sound.BLOCK_NOTE_BLOCK_HARP) {
        @Override
        public Note getNote(int offset) {
            return GAME_OF_THRONES_MELODY[offset % GAME_OF_THRONES_MELODY.length];
        }

        @Override
        public float getVolume(int offset) {
            return 1f;
        }
    };

    private static final double ROTATE_X_FACTOR = 10;
    private static final double ROTATE_Y_FACTOR = 2 * Math.PI;
    private static final double ROTATE_Z_FACTOR = Math.sqrt(2) * 5;
    private List<Vector> particleRing = new ArrayList<Vector>();
    private Vector position;
    private double rotateX;
    private double rotateY;
    private double rotateZ;

    private boolean bColor = true;

    public GameOfThronesEffectHandler() {
        super(new Track[]{GAME_OF_THRONES_TRACK});
        for (int i = 0; i < 40; i++) {
            particleRing.add(new Vector(Math.sin(Math.PI * i / 20), 0d, Math.cos(Math.PI * i / 20)));
        }
    }

    @Override
    protected void showEffectInternal(Server server, Player player, Location from, Location to) {
        super.showEffectInternal(server, player, from, to);
        Vector z = to.toVector().subtract(from.toVector());
        double movement = z.length();
        rotateX += movement / ROTATE_X_FACTOR;
        rotateY += movement / ROTATE_Y_FACTOR;
        rotateZ += movement / ROTATE_Z_FACTOR;
        if (rotateX >= 2*Math.PI) {
            rotateX -= 2*Math.PI;
        }
        if (rotateY >= 2*Math.PI) {
            rotateY -= 2*Math.PI;
        }
        if (rotateZ >= 2*Math.PI) {
            rotateZ -= 2*Math.PI;
        }
        if (position == null) {
            z.normalize();
            z.multiply(2);
            position = to.toVector().subtract(z.normalize());
        } else {
            Vector delta = to.toVector().subtract(position);
            delta.normalize();
            delta.multiply(2);
            position = to.toVector().subtract(delta);
        }
        Matrix3 m = Matrix3.rotateX(rotateX).multiply(Matrix3.rotateY(rotateY)).multiply(Matrix3.rotateZ(rotateZ));
        Location base = new Location(to.getWorld(), position.getX(), position.getY() + 1.5, position.getZ());
        for (Vector p : particleRing) {
            //This was COLOURED_DUST. I can not find that anymore. I am not sure what it turned into.
            Object data;
            if(bColor) {
                data = new Particle.DustOptions(Color.BLACK, .7f);
                bColor = false;
            }
            else {
                data = new Particle.DustOptions(Color.GRAY, .7f);
                bColor = true;
            }
            translateEffect(server, player, base, Particle.REDSTONE, p, 1.0f, 215f/255f, 0f, m,data);
        }
    }

    private void translateEffect(Server server, Player player, Location base, Particle effect, Vector offset, float r, float g, float b, Matrix3 m,Object data) {
        base = base.clone();
        Vector realOffset = m.multiply(offset);
        base.setX(realOffset.getX() + base.getX());
        base.setY(realOffset.getY() + base.getY());
        base.setZ(realOffset.getZ() + base.getZ());
        sendEffect(server, player, effect, base, r, g, b, 1f, 256f, 0,1,data);
    }
}
