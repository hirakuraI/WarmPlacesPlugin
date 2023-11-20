package com.hirakurai.warmplacesplugin.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hirakurai.warmplacesplugin.models.WarmBlockData;
import com.hirakurai.warmplacesplugin.utils.adapters.LocationAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WarmBlockLocationUtil implements JsonConfigUtil{
    private List<WarmBlockData> warmBlockDataList;
    private Gson gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationAdapter()).setPrettyPrinting().create();
    private File file = new File("./plugins/WarmPlacesPlugin/SavedLocations.json");

    public WarmBlockLocationUtil() {
        reload();
        uploadFromJsonFile();
    }

    public WarmBlockLocationUtil(List<WarmBlockData> warmBlockLocationList) {
        this.warmBlockDataList = warmBlockLocationList;
    }

    public void uploadFromJsonFile() {
        Reader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        warmBlockDataList = new ArrayList<>(Arrays.asList(gson.fromJson(reader, WarmBlockData[].class)));
    }

    public void saveToJsonFile() {
        try {
            Writer writer = new FileWriter(file, false);
            gson.toJson(warmBlockDataList, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        if (!file.exists()) {
            try {
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdir();
                }
                file.createNewFile();
                List<WarmBlockData> warmBlockSettingsListExample = new ArrayList<>(
                        Arrays.asList(
                                new WarmBlockData(new Location(Bukkit.getWorlds().get(0), 10000D, 10000D, 10000D), Material.CAMPFIRE)
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

    public List<WarmBlockData> getWarmBlockDataList() {
        return warmBlockDataList;
    }

    public void setWarmBlockDataList(List<WarmBlockData> warmBlockLocationList) {
        this.warmBlockDataList = warmBlockLocationList;
    }
}
