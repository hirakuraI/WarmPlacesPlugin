package com.hirakurai.warmplacesplugin.models.CustomItemRecipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.List;

public class ItemShapedRecipeData extends ItemRecipeGeneralData{
    private String itemCraftRecipeUpperLine;
    private String itemCraftRecipeMiddleLine;
    private String itemCraftRecipeBottomLine;
    private HashMap<Character, NamespacedKey> craftIngredients;

    public ItemShapedRecipeData(String itemRecipeKey,
                                String itemCraftRecipeUpperLine,
                                String itemCraftRecipeMiddleLine,
                                String itemCraftRecipeBottomLine,
                                HashMap<Character, NamespacedKey> craftIngredients,
                                Material resultItemMaterial,
                                String resultItemName,
                                List<String> resultItemLore) {
        super(itemRecipeKey, resultItemMaterial, resultItemName, resultItemLore);
        this.itemCraftRecipeUpperLine = itemCraftRecipeUpperLine;
        this.itemCraftRecipeMiddleLine = itemCraftRecipeMiddleLine;
        this.itemCraftRecipeBottomLine = itemCraftRecipeBottomLine;
        this.craftIngredients = craftIngredients;
    }

    public String getItemCraftRecipeUpperLine() {
        return itemCraftRecipeUpperLine;
    }

    public void setItemCraftRecipeUpperLine(String itemCraftRecipeUpperLine) {
        this.itemCraftRecipeUpperLine = itemCraftRecipeUpperLine;
    }

    public String getItemCraftRecipeMiddleLine() {
        return itemCraftRecipeMiddleLine;
    }

    public void setItemCraftRecipeMiddleLine(String itemCraftRecipeMiddleLine) {
        this.itemCraftRecipeMiddleLine = itemCraftRecipeMiddleLine;
    }

    public String getItemCraftRecipeBottomLine() {
        return itemCraftRecipeBottomLine;
    }

    public void setItemCraftRecipeBottomLine(String itemCraftRecipeBottomLine) {
        this.itemCraftRecipeBottomLine = itemCraftRecipeBottomLine;
    }

    public HashMap<Character, NamespacedKey> getCraftIngredients() {
        return craftIngredients;
    }

    public void setCraftIngredients(HashMap<Character, NamespacedKey> craftIngredients) {
        this.craftIngredients = craftIngredients;
    }
}
