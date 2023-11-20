package com.hirakurai.warmplacesplugin.listeners;

import com.hirakurai.warmplacesplugin.models.WarmBlockData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

public class WarmBlockPlaceListener implements Listener {

    private Material blockMaterial;
    private List<WarmBlockData> blockDataList;

    public WarmBlockPlaceListener(Material blockMaterial, List<WarmBlockData> blockDataList) {
        this.blockMaterial = blockMaterial;
        this.blockDataList = blockDataList;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType().equals(blockMaterial)) {
            blockDataList.add(new WarmBlockData(block.getLocation(), blockMaterial));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType().equals(blockMaterial)) {
            blockDataList.removeIf(warmBlockData -> warmBlockData.getBlockMaterial().equals(blockMaterial)
                    && warmBlockData.getLocation().equals(block.getLocation()));
        }
    }
}
