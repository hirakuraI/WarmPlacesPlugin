package com.hirakurai.warmplacesplugin.helpers;

import org.bukkit.Location;

import java.util.List;

public class DistanceHelper {
    public static double getMinDistance(Location playerLocation, List<Location> blockLocations, double preferredDistance){
        double minDistance = Double.MAX_VALUE;
        for (Location blockLocation : blockLocations) {
            if(minDistance < preferredDistance){
                break;
            }
            if(playerLocation.distance(blockLocation) < minDistance){
                minDistance = playerLocation.distance(blockLocation);
            }
        }
        return minDistance;
    }
}
