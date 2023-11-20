package com.hirakurai.warmplacesplugin.armor;

import com.hirakurai.warmplacesplugin.WarmPlacesPlugin;
import com.hirakurai.warmplacesplugin.models.CustomItemRecipes.ItemRecipeGeneralData;
import com.hirakurai.warmplacesplugin.models.CustomItemRecipes.ItemShapedRecipeData;
import com.hirakurai.warmplacesplugin.utils.tools.StringHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CustomRecipes {
    public static void registerShapedRecipes(List<ItemShapedRecipeData> itemShapedRecipeDataList){
        for (ItemShapedRecipeData itemShapedRecipeData : itemShapedRecipeDataList) {
            registerShapedRecipe(itemShapedRecipeData);
        }
    }
    public static void registerShapedRecipe(ItemShapedRecipeData itemRecipeData){
        ItemStack resultItem = prepareResultItem(itemRecipeData);
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(WarmPlacesPlugin.getPlugin(), itemRecipeData.getItemRecipeKey()), resultItem);
        if(itemRecipeData.getItemCraftRecipeBottomLine().equals("   ")){
            recipe.shape(
                    itemRecipeData.getItemCraftRecipeUpperLine(),
                    itemRecipeData.getItemCraftRecipeMiddleLine());
        } else {
            recipe.shape(
                    itemRecipeData.getItemCraftRecipeUpperLine(),
                    itemRecipeData.getItemCraftRecipeMiddleLine(),
                    itemRecipeData.getItemCraftRecipeBottomLine());
        }
        HashMap<Character, NamespacedKey> craftIngredients = itemRecipeData.getCraftIngredients();
        Set<Character> ingredientsKeys = craftIngredients.keySet();
        for (Character ingredientsKey : ingredientsKeys) {
            NamespacedKey itemNamespacedKey = craftIngredients.get(ingredientsKey);
            if(itemNamespacedKey.namespace().equals("minecraft")){
                recipe.setIngredient(ingredientsKey, Material.valueOf(itemNamespacedKey.getKey().toUpperCase()));
            } else {
                recipe.setIngredient(ingredientsKey, Bukkit.getRecipe(craftIngredients.get(ingredientsKey)).getResult());
            }
        }

        Bukkit.getServer().addRecipe(recipe);
    }

    public static ItemStack prepareResultItem(ItemRecipeGeneralData itemRecipeData){
        ItemStack resultItem = new ItemStack(itemRecipeData.getResultItemMaterial());
        ItemMeta resultItemMeta = resultItem.getItemMeta();
        resultItemMeta.setDisplayName(StringHelper.color(itemRecipeData.getResultItemName()));
        List<String> coloredLore = new ArrayList<>();
        List<String> resultItemLore = itemRecipeData.getResultItemLore();
        for (int i = 0; i < resultItemLore.size(); i++) {
            coloredLore.add(i, StringHelper.color(resultItemLore.get(i)));
        }
        resultItemMeta.setLore(coloredLore);
        resultItem.setItemMeta(resultItemMeta);
        return resultItem;
    }
}
