package com.hirakurai.warmplacesplugin;

import com.hirakurai.warmplacesplugin.armor.CustomRecipes;
import com.hirakurai.warmplacesplugin.listeners.WarmBlockPlaceListener;
import com.hirakurai.warmplacesplugin.models.CustomItemRecipes.ItemShapedRecipeData;
import com.hirakurai.warmplacesplugin.models.WarmBlockData;
import com.hirakurai.warmplacesplugin.models.WarmBlockSettings;
import com.hirakurai.warmplacesplugin.tasks.WarmBlockCheckAroundTask;
import com.hirakurai.warmplacesplugin.utils.craftRecipeUtils.ItemShapedRecipeUtil;
import com.hirakurai.warmplacesplugin.utils.WarmArmorDataUtil;
import com.hirakurai.warmplacesplugin.utils.WarmBlockLocationUtil;
import com.hirakurai.warmplacesplugin.utils.WarmBlockPresetConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;

public final class WarmPlacesPlugin extends JavaPlugin {
    private static WarmPlacesPlugin plugin;
    private List<WarmBlockData> warmBlockDataList;
    private Long ticksPerSecond = 20L;
    private Long delayInSeconds = 3L;

    @Override
    public void onEnable() {
        plugin = this;
        PluginManager pluginManager = this.getServer().getPluginManager();
        BukkitScheduler scheduler = Bukkit.getScheduler();

        WarmBlockLocationUtil warmBlockLocationUtil = new WarmBlockLocationUtil();
        WarmBlockPresetConfig warmBlockPresetConfig = new WarmBlockPresetConfig();
        ItemShapedRecipeUtil itemShapedRecipeUtil = new ItemShapedRecipeUtil();
        WarmArmorDataUtil warmArmorDataUtil = new WarmArmorDataUtil();
        this.warmBlockDataList = warmBlockLocationUtil.getWarmBlockDataList();
        List<WarmBlockSettings> warmBlockSettingsList = warmBlockPresetConfig.getWarmBlockSettingsList();
        List<ItemShapedRecipeData> itemShapedRecipeDataList = itemShapedRecipeUtil.getItemShapedRecipeDataList();
        CustomRecipes.registerShapedRecipes(itemShapedRecipeDataList);
        for (WarmBlockSettings warmBlockSettings : warmBlockSettingsList) {
            Material blockMaterial = warmBlockSettings.getBlockMaterial();
            pluginManager.registerEvents(new WarmBlockPlaceListener(blockMaterial, warmBlockDataList), this);
        }
        scheduler.runTaskTimer(this, new WarmBlockCheckAroundTask(
                warmBlockDataList,
                warmBlockPresetConfig.getPreferredRanges(),
                warmArmorDataUtil.getWarmArmorDataMap()), ticksPerSecond, ticksPerSecond * delayInSeconds);
        this.getLogger().info("There's " + warmBlockSettingsList.size() + " warm places found!");
        this.getLogger().info("WarmPlacesPlugin successfully enabled!");
    }

    @Override
    public void onDisable() {
        if(warmBlockDataList == null){
            return;
        }
        WarmBlockLocationUtil warmBlockLocationUtil = new WarmBlockLocationUtil(warmBlockDataList);
        warmBlockLocationUtil.saveToJsonFile();
        this.getLogger().info("WarmPlacesPlugin has been disabled!");
    }

    public static WarmPlacesPlugin getPlugin() {
        return plugin;
    }
}
