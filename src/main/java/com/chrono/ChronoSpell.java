package com.chrono;

import lombok.Getter;

@Getter
public enum ChronoSpell {
    LUMBRIDGE_HOME_TELEPORT("Lumbridge Home Teleport", 14286854, 356, 406),
    WIND_STRIKE("Wind Strike", 14286855, 15,65),
    CONFUSE("Confuse", 14286856, 16, 66),
    ENCHANT_CROSSBOW_BOLT("Enchant Crossbow Bolt", 14286857, 358, 408),
    WATER_STRIKE("Water Strike", 14286858, 17,67),
    LVL_1_ENCHANT("Lvl-1 Enchant", 14286859, 18, 68),
    EARTH_STRIKE("Earth Strike", 14286860, 19, 69),
    WEAKEN("Weaken", 14286861, 20, 70),
    FIRE_STRIKE("Fire Strike", 14286862, 21, 71);
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),
//    WIND_STRIKE("", ),


    private String name;
    private int packedID;
    private int unlockedSpriteID;
    private int lockedSpriteID;

    ChronoSpell(String name, int packedID, int unlockedSpriteID, int lockedSpriteID) {
        this.name = name;
        this.packedID = packedID;
        this.lockedSpriteID = lockedSpriteID;
        this.unlockedSpriteID = unlockedSpriteID;
    }
}
