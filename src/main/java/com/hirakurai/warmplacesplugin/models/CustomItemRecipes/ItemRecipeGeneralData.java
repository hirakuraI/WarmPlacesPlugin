package com.hirakurai.warmplacesplugin.models.CustomItemRecipes;

import org.bukkit.Material;

import java.util.List;

public abstract class ItemRecipeGeneralData {
    private String itemRecipeKey;
    private Material resultItemMaterial;
    //private ChatColor resultItemNameColor;
    private String resultItemName;
    private List<String> resultItemLore;

    public ItemRecipeGeneralData(String itemRecipeKey, Material resultItemMaterial, String resultItemName, List<String> resultItemLore) {
        this.itemRecipeKey = itemRecipeKey;
        this.resultItemMaterial = resultItemMaterial;
        this.resultItemName = resultItemName;
        this.resultItemLore = resultItemLore;
    }

    public String getItemRecipeKey() {
        return itemRecipeKey;
    }

    public void setItemRecipeKey(String itemRecipeKey) {
        this.itemRecipeKey = itemRecipeKey;
    }

    public Material getResultItemMaterial() {
        return resultItemMaterial;
    }

    public void setResultItemMaterial(Material resultItemMaterial) {
        this.resultItemMaterial = resultItemMaterial;
    }

    public String getResultItemName() {
        return resultItemName;
    }

    public void setResultItemName(String resultItemName) {
        this.resultItemName = resultItemName;
    }

    public List<String> getResultItemLore() {
        return resultItemLore;
    }

    public void setResultItemLore(List<String> resultItemLore) {
        this.resultItemLore = resultItemLore;
    }
}
