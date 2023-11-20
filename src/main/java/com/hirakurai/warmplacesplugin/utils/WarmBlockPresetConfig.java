package com.hirakurai.warmplacesplugin.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hirakurai.warmplacesplugin.models.WarmBlockSettings;
import org.bukkit.Material;

import java.io.*;
import java.util.*;

public class WarmBlockPresetConfig implements JsonConfigUtil{
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File file = new File("./plugins/WarmPlacesPlugin/WarmBlockSettings.json");
    private List<WarmBlockSettings> warmBlockSettingsList;

    public WarmBlockPresetConfig() {
        reload();
        uploadFromJsonFile();
    }

    public void reload() {
        if (!file.exists()) {
            try {
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdir();
                }
                file.createNewFile();
                List<WarmBlockSettings> warmBlockSettingsListExample = new ArrayList<>(
                        Arrays.asList(
                                new WarmBlockSettings(Material.CAMPFIRE, 10D),
                                new WarmBlockSettings(Material.FURNACE, 12D)
                        )
                );
                Writer writer = new FileWriter(file, false);
                gson.toJson(warmBlockSettingsListExample, writer);
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
        warmBlockSettingsList = new ArrayList<>(Arrays.asList(gson.fromJson(reader, WarmBlockSettings[].class)));
    }

    public List<WarmBlockSettings> getWarmBlockSettingsList() {
        return warmBlockSettingsList;
    }

    public Map<Material, Double> getPreferredRanges(){
        Map<Material, Double> preferredRangesMap = new HashMap<>();
        for (WarmBlockSettings warmBlockSettings : warmBlockSettingsList) {
            preferredRangesMap.put(warmBlockSettings.getBlockMaterial(), warmBlockSettings.getPreferredRange());
        }
        return preferredRangesMap;
    }
}
