package com.hirakurai.warmplacesplugin.models;

import org.bukkit.Material;

public class WarmBlockSettings {
    private Material blockMaterial;
    private Double preferredRange;

    public WarmBlockSettings(Material blockMaterial, Double preferredRange) {
        this.blockMaterial = blockMaterial;
        this.preferredRange = preferredRange;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }

    public void setBlockMaterial(Material blockMaterial) {
        this.blockMaterial = blockMaterial;
    }

    public Double getPreferredRange() {
        return preferredRange;
    }

    public void setPreferredRange(Double preferredRange) {
        this.preferredRange = preferredRange;
    }
}
