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
