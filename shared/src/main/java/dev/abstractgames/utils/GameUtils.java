package dev.abstractgames.utils;

import cn.nukkit.level.Location;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public final class GameUtils {

    public static Location stringToLocation(String string) {
        String[] split = string.split(";");

        if (split.length < 5) {
            throw new RuntimeException("Invalid split length");
        }

        return new Location(
                Double.parseDouble(split[0]),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Float.parseFloat(split[3]),
                Float.parseFloat(split[4]),
                null
        );
    }

    public static String locationToString(Location l) {
        return String.format("%s;%s;%s;%s;%s", l.getFloorX(), l.getFloorY(), l.getFloorZ(), l.getYaw(), l.getPitch());
    }

    public static void copyMap(File from, File to) {
        try {
            FileUtils.copyDirectory(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}