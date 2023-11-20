package com.hirakurai.warmplacesplugin.models.CustomItemRecipes;

import org.bukkit.Material;

import java.util.List;

public class ItemFurnaceRecipeData extends ItemRecipeGeneralData {
    public ItemFurnaceRecipeData(String itemRecipeKey, Material resultItemMaterial, String resultItemName, List<String> resultItemLore) {
        super(itemRecipeKey, resultItemMaterial, resultItemName, resultItemLore);
    }
}
