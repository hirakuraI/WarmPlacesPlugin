package com.hirakurai.warmplacesplugin.utils.craftRecipeUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hirakurai.warmplacesplugin.WarmPlacesPlugin;
import com.hirakurai.warmplacesplugin.models.CustomItemRecipes.ItemShapedRecipeData;
import com.hirakurai.warmplacesplugin.utils.JsonConfigUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemShapedRecipeUtil implements JsonConfigUtil {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File file = new File("./plugins/WarmPlacesPlugin/CraftRecipes/ItemShapedRecipesData.json");
    private List<ItemShapedRecipeData> itemShapedRecipeDataList;
    public ItemShapedRecipeUtil(){
        reload();
        uploadFromJsonFile();
    }
    public void reload() {
        if (!file.exists()) {
            try {
                if(!file.getParentFile().getParentFile().exists()){
                    file.getParentFile().getParentFile().mkdir();
                }
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdir();
                }
                file.createNewFile();
                List<ItemShapedRecipeData> itemShapedRecipeDataListExample = new ArrayList<>(
                        Arrays.asList(
                                new ItemShapedRecipeData("test_item_key1",
                                        "L L",
                                        "BBB",
                                        "BBB",
                                        new HashMap<Character, NamespacedKey>(){{
                                            put('B', NamespacedKey.minecraft("bedrock"));
                                            put('L', NamespacedKey.minecraft("leather"));
                                        }},
                                        Material.BEDROCK,
                                        "TestItemName1",
                                        Arrays.asList(
                                                "TestItemLore1",
                                                "TestItemLore2"
                                        )
                                ),
                                new ItemShapedRecipeData("test_item_key2",
                                        "LLL",
                                        "BLB",
                                        "BCB",
                                        new HashMap<Character, NamespacedKey>(){{
                                            put('B', NamespacedKey.minecraft("bedrock"));
                                            put('L', NamespacedKey.minecraft("leather"));
                                            put('C', NamespacedKey.fromString("test_item_key1",WarmPlacesPlugin.getPlugin()));
                                        }},
                                        Material.BEDROCK,
                                        "TestItemName2",
                                        Arrays.asList(
                                                "TestItemLore3",
                                                "TestItemLore4"
                                        )
                                )
                        )
                );
                Writer writer = new FileWriter(file, false);
                gson.toJson(itemShapedRecipeDataListExample, writer);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadFromJsonFile(){
        Reader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        itemShapedRecipeDataList = new ArrayList<>(Arrays.asList(gson.fromJson(reader, ItemShapedRecipeData[].class)));
    }

    public List<ItemShapedRecipeData> getItemShapedRecipeDataList() {
        return itemShapedRecipeDataList;
    }
}
