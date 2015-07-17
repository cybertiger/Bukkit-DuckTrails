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

import org.bukkit.Location;
import org.bukkit.Sound;

/**
 *
 * @author antony
 */
public abstract class Track {
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
