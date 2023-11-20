package com.hirakurai.warmplacesplugin.models;

import org.bukkit.Location;
import org.bukkit.Material;

public class WarmBlockData {
    private Location location;
    private Material blockMaterial;

    public WarmBlockData(Location location, Material blockMaterial) {
        this.location = location;
        this.blockMaterial = blockMaterial;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }

    public void setBlockMaterial(Material blockMaterial) {
        this.blockMaterial = blockMaterial;
    }
}
