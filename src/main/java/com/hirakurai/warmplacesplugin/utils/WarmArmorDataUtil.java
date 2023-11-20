package com.hirakurai.warmplacesplugin.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class WarmArmorDataUtil implements JsonConfigUtil{
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File file = new File("./plugins/WarmPlacesPlugin/WarmArmorData.json");
    private Map<String, Integer> warmArmorDataMap;

    public WarmArmorDataUtil(){
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
                Map<String, Integer> warmArmorDataMapExample = Map.of(
                    "Warm hat", 20,
                    "Warm chestplate", 50,
                    "Warm leggins", 40,
                    "Warm boots", 31
                );
                Writer writer = new FileWriter(file, false);
                gson.toJson(warmArmorDataMapExample, writer);
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
        Type mapType = new TypeToken<HashMap<String, Integer>>() {}.getType();
        warmArmorDataMap = gson.fromJson(reader, mapType);
    }

    public Map<String, Integer> getWarmArmorDataMap() {

        return warmArmorDataMap;
    }
}
