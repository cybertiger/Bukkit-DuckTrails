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

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import static org.cyberiantiger.minecraft.ducktrails.MusicalEffectHandler.*;

/**
 *
 * @author antony
 */
public enum Trail {

    FLAME("ducktrails.trail.flame") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Particle.FLAME, 0f, 0f, 0f, 0.02f, 1);
        }
    },
    HEARTS("ducktrails.trail.hearts") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Particle.HEART, 0f, 1f, 0f, 0.2f, 0);
        }
    },
    RUBY("ducktrails.trail.ruby") {
        public EffectHandler createHandler(Player player) {
            Object data = new Particle.DustOptions(Color.RED,1);
            return new BasicEffectHandler(Particle.REDSTONE, 1f,0f,0f,1f,1, data, 0);
        }
    },
    EMERALD("ducktrails.trail.emerald") {
        public EffectHandler createHandler(Player player) {
            Object data = new Particle.DustOptions(Color.GREEN,1);
            return new BasicEffectHandler(Particle.REDSTONE, 1f,0f,0f,1f,1, data, 0);
        }
    },
    SAPPHIRE("ducktrails.trail.sapphire") {
        public EffectHandler createHandler(Player player) {
            Object data = new Particle.DustOptions(Color.BLUE,1);
            return new BasicEffectHandler(Particle.REDSTONE, 1f,0f,0f,1f,1, data, 0);
        }
    },
    TOPAZ("ducktrails.trail.topaz") {
        public EffectHandler createHandler(Player player) {
            Object data = new Particle.DustOptions(Color.YELLOW,1);
            return new BasicEffectHandler(Particle.REDSTONE, 1f,0f,0f,1f,1, data, 0);
        }
    },
    AMETHYST("ducktrails.trail.amethyst") {
        public EffectHandler createHandler(Player player) {
            Object data = new Particle.DustOptions(Color.PURPLE,1);
            return new BasicEffectHandler(Particle.REDSTONE, 1f,0f,0f,1f,1, data, 0);
        }
    },
    DIAMOND("ducktrails.trail.diamond") {
        public EffectHandler createHandler(Player player) {
            Object data = new Particle.DustOptions(Color.WHITE,1);
            return new BasicEffectHandler(Particle.REDSTONE, 1f,0f,0f,1f,1, data, 0);
        }
    },
    OPAL("ducktrails.trail.opal") {
        public EffectHandler createHandler(Player player) {
            Object data = new Particle.DustOptions(Color.AQUA,1);
            return new BasicEffectHandler(Particle.REDSTONE, 1f,0f,0f,1f,1, data, 0);
        }
    },
    NYAN("ducktrails.trail.nyan") {
        public EffectHandler createHandler(Player player) {
            return new NyanEffectHandler(Particle.REDSTONE);
        }
    },
    PFUDOR("ducktrails.trail.pfudor") {
        public EffectHandler createHandler(Player player) {
            return new PFUDOREffectHandler(Particle.REDSTONE);
        }
    },
    DUCKTALES("ducktrails.trail.ducktales") {
        public EffectHandler createHandler(Player player) {
            return new DuckTalesEffectHandler(Particle.REDSTONE);
        }
    },
    DRAGONBORN("ducktrails.trail.dragonborn") {
        public EffectHandler createHandler(Player player) {
            return new DragonbornEffectHandler(Particle.REDSTONE);
        }
    },
    GAMEOFTHRONES("ducktrails.trail.gameofthrones") {;
        public EffectHandler createHandler(Player player) {
            return new GameOfThronesEffectHandler();
        }
    };

    private final String permission;

    private Trail(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public abstract EffectHandler createHandler(Player player);
}
