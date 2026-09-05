package org.studio4sv.utils;

public class xpConverter {

    public static int LVLtoXP(int level) {
        if (level <= 15) return level * level + 6 * level;
        if (level <= 31) return (int) (2.5 * level * level - 40.5 * level + 360);
        return (int) (4.5 * level * level - 162.5 * level + 2220);
    }
}